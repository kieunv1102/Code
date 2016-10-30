package vn.ce.sale.adapter.bee;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import vn.ce.sale.adapter.bee.DisplayProductGrid.ViewHolder1;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.fragment.bee.Bee_Fragment_Product_List_Detail;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.util.CircleImageView;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BeeAdapterProduct extends BaseAdapter implements Filterable {
	private Context mContext;
	private List<JSONObject> dataSource;
	private List<JSONObject> searchDataSource;
	private List<JSONObject> lstJsonPriceInfo;
	private Filter filter;
	private String[] filterFiledString = new String[] { "Name", "Id", "BarCode" };
	private List<String> arrUnit;
	String dv;
	double t;
	LayoutInflater mLayoutInflater;
	public List<JSONObject> getDataSource() {
		return dataSource;

	}

	public void setDataSource(List<JSONObject> dt) {
		dataSource = dt;
	}

	private HashMap<Integer, BindDataUI> bindRule;
	private int layoutRow = -1;
	private View footer;
	private View header;

	public View getHeader() {
		return footer;
	}

	public void setHeader(View header) {
		this.header = header;
	}

	public BeeAdapterProduct(Context c, List<JSONObject> d) {
		mContext = c;
		dataSource = d;
		searchDataSource = d;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public View getFooter() {
		return footer;
	}

	public void setFooter(View footer) {
		this.footer = footer;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataSource.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dataSource.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View itemView = mLayoutInflater.inflate(R.layout.bee_item_view_banner, parent, false);
		final ViewHolder1 viewHolder = new ViewHolder1();
		viewHolder.txtNameProductEcom = (TextView) itemView.findViewById(R.id.txtNameProductEcom);
		viewHolder.txtPriceSaleEcom = (TextView) itemView.findViewById(R.id.txtPriceSaleEcom);
		viewHolder.txtSaleEcom = (TextView) itemView.findViewById(R.id.txtSaleEcom);
		viewHolder.txtPriceEcom = (TextView) itemView.findViewById(R.id.txtPriceEcom);
		viewHolder.imvGrvProduct = (ImageView) itemView.findViewById(R.id.imv_grv_product);
		viewHolder.viewBorderRight = (View) itemView.findViewById(R.id.viewBorderRight);
		viewHolder.btnOrderProdcutEcom = (Button) itemView.findViewById(R.id.btnOrderProdcutEcom);
		
		try {
			final JSONObject lineSource = dataSource.get(position);
			String lstPriceInfo = lineSource.getString("PriceInfo");
			lstJsonPriceInfo = TransformDataManager.convertArrayToListJSON(new JSONArray(lstPriceInfo));
			t = lstJsonPriceInfo.get(0).getInt("Price");
			
//			viewHolder.btnOrderProdcutEcom.setTag(position);
			viewHolder.btnOrderProdcutEcom.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					final Dialog dialog = new Dialog(mContext);
					dialog.setCancelable(false);
					dialog.setTitle("Nhập số lượng và đơn vị");
					final View view = mLayoutInflater.inflate(R.layout.custom_dialog_bee, null);
					final EditText edtChange = (EditText) view.findViewById(R.id.edit_txt);
					final Spinner spinnerUnit = (Spinner) view.findViewById(R.id.spinner_unit);
					spinnerUnit.setTag(position);
					String u;
					arrUnit = new ArrayList<String>();
					for (int i = 0; i < lstJsonPriceInfo.size(); i++) {
						try {
							u = lstJsonPriceInfo.get(i).getString("UnitName");
							arrUnit.add(u);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					ArrayAdapter<String> adapterUnit = new ArrayAdapter<String>(mContext,
							android.R.layout.simple_spinner_item, arrUnit);
					adapterUnit.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
					spinnerUnit.setAdapter(adapterUnit);
					adapterUnit.notifyDataSetChanged();
					spinnerUnit.setOnItemSelectedListener(new UnitProcessEvent());
					dialog.setContentView(view);
					Button btn = (Button) view.findViewById(R.id.btn_ok);
					btn.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {

							String s = ShareMemManager.getInstance().readFromDB(mContext, "productOrder");
							EditText editText = (EditText) view.findViewById(R.id.edit_txt);
							int x = Integer.parseInt(editText.getText().toString());
							if (s.equals("")||s.equals("[]")) {
								try {
									if (x > 9999) {
										lineSource.put("SL", 9999);
										int tt = (int) (9999 * t);
										lineSource.put("TT", tt);
										lineSource.put("DV", dv);
									} else {
										lineSource.put("SL", x);
										int tt = (int) (x * t);
										lineSource.put("TT", tt);
										lineSource.put("DV", dv);
									}
									Util.saveOrderProduct(mContext, lineSource);
									Toast.makeText(mContext, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT)
											.show();
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								List<JSONObject> lstJsonObjects = new ArrayList<JSONObject>();
								try {
									lstJsonObjects = TransformDataManager.convertArrayToListJSON(new JSONArray(s));
									for (int i = 0; i < lstJsonObjects.size(); i++) {
										if (lstJsonObjects.get(i).getInt("Id") == lineSource.getInt("Id")) {
											try {
												if (x > 9999) {
													lineSource.put("SL", 9999);
													int tt = (int) (9999 * t);
													lineSource.put("TT", tt);
												} else {
													int sl = lstJsonObjects.get(i).getInt("SL") + x;
													lineSource.put("SL", sl);
													int tt = (int) (sl * t);
													lineSource.put("TT", tt);
												}
												lineSource.put("DV", dv);
												Util.saveOrderProduct(mContext, lineSource);
												Toast.makeText(mContext, "Sản phẩm đã được thêm vào giỏ hàng",
														Toast.LENGTH_SHORT).show();
											} catch (JSONException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}
											break;
										} else {
											if (x > 9999) {
												lineSource.put("SL", 9999);
												int tt = (int) (9999 * t);
												lineSource.put("TT", tt);
											} else {
												lineSource.put("SL", x);
												int tt = (int) (x * t);
												lineSource.put("TT", tt);
											}
											lineSource.put("DV", dv);
											Util.saveOrderProduct(mContext, lineSource);
											Toast.makeText(mContext, "Sản phẩm đã được thêm vào giỏ hàng",
													Toast.LENGTH_SHORT).show();
										}

									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							dialog.dismiss();
							arrUnit = new ArrayList<String>();
						}
					});
					Button btn1 = (Button) view.findViewById(R.id.btn_canel_order);
					btn1.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							dialog.dismiss();
//							arrUnit.clear();
							arrUnit = new ArrayList<String>();
						}
					});

					dialog.show();
				}
			});
			
			
			viewHolder.viewBorderRight.setTag(position);
			viewHolder.viewBorderRight.setBackgroundColor(Color.RED);
			viewHolder.txtNameProductEcom.setTag(position);
			viewHolder.txtNameProductEcom.setText(lineSource.getString("Name"));
			viewHolder.txtPriceEcom.setTag(position);
			viewHolder.txtPriceEcom.setText(FormatUtil.formatCurrency(t) + " VND");
			viewHolder.txtPriceEcom.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			viewHolder.txtPriceSaleEcom.setTag(position);
			viewHolder.txtPriceSaleEcom.setText(FormatUtil.formatCurrency(t) + " VND");
			viewHolder.txtSaleEcom.setTag(position);
			viewHolder.txtSaleEcom.setText("40%");

			viewHolder.imvGrvProduct.setTag(position);
			Picasso.with(mContext).load(Util.IMAGE_URL).placeholder(R.drawable.backimvproduct)
					.into(viewHolder.imvGrvProduct);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		itemView.setTag(viewHolder);
		itemView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentTransaction fmdetail1 = ((FragmentActivity) mContext).getSupportFragmentManager()
						.beginTransaction();
				fmdetail1.replace(R.id.container, new Bee_Fragment_Product_List_Detail().newInstance(String.valueOf(position),""));
				fmdetail1.addToBackStack("tag");
				fmdetail1.commit();
			}
		});
		return itemView;
	}

	private class UnitProcessEvent implements android.widget.AdapterView.OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			dv = arrUnit.get(position);

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			// txtDisplayNhaMang.setText(arrNhaMang[0]);
		}

	}

	public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

		private String url;
		private ImageView imageView;

		public ImageLoadTask(String url, ImageView imageView) {
			this.url = url;
			this.imageView = imageView;
		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			try {
				URL urlConnection = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				Bitmap myBitmap = BitmapFactory.decodeStream(input);
				return myBitmap;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			imageView.setImageBitmap(result);
		}

	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		if (filter == null)
			filter = new CountryFilter();
		return filter;
	}

	class CountryFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {

			constraint = constraint.toString().toLowerCase();
			FilterResults result = new FilterResults();
			if (constraint != null && constraint.toString().length() > 0) {
				List<JSONObject> filteredItems = new ArrayList<JSONObject>();

				// for (int i = 0, l = orginalDataSource.size(); i < l - 1; i++)
				// {
				for (int i = 0; i <= dataSource.size() - 1; i++) {
					JSONObject o = dataSource.get(i);
					try {
						for (String sField : filterFiledString) {
							if (o.getString(sField).toLowerCase().contains(constraint)
									|| Util.TrimVietnameseMark(o.getString(sField)).toLowerCase()
											.contains(Util.TrimVietnameseMark(constraint.toString()))) {
								filteredItems.add(o);
								break;
							}
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				result.count = filteredItems.size();
				result.values = filteredItems;
			} else {
				synchronized (this) {
					result.values = dataSource;
					result.count = dataSource.size();
				}
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {

			searchDataSource = (ArrayList<JSONObject>) results.values;
			notifyDataSetChanged();
			getDataSource().clear();
			/*
			 * for(int i = 0, l = searchDataSource.size(); i < l; i++)
			 * getDataSource().add(countryList.get(i));
			 */
			getDataSource().addAll(searchDataSource);
			notifyDataSetInvalidated();
		}
	}

	public void setLayoutRow(int layoutRow) {
		this.layoutRow = layoutRow;
	}

	static class ViewHolder2 {

	}

	static class ViewHolder1 {
		public ImageView imvGrvProduct;
		public ImageView imvAddProduct;
		public TextView txtNameProductEcom;
		public TextView txtPriceSaleEcom;
		public TextView txtSaleEcom;
		public TextView txtPriceEcom;
		public View viewBorderRight;
		public Button btnOrderProdcutEcom;

		SparseArray<CheckBox> checkbox = new SparseArray<CheckBox>();
		SparseArray<TextView> text = new SparseArray<TextView>();
		SparseArray<EditText> editText = new SparseArray<EditText>();
		SparseArray<ImageView> icon = new SparseArray<ImageView>();
		SparseArray<ProgressBar> pb = new SparseArray<ProgressBar>();
		SparseArray<Button> button = new SparseArray<Button>();

		public void addEditText(int idui, EditText view) {
			editText.append(idui, view);
		}

		public void addTextView(int idui, TextView view) {
			text.append(idui, view);
		}

		public void addButton(int idui, Button view) {
			button.append(idui, view);
		}

		public void addCheckBox(int idui, CheckBox view) {
			checkbox.append(idui, view);
		}

		public void addImageView(int idUI, ImageView imageView, ProgressBar pb2) {
			// TODO Auto-generated method stub
			icon.append(idUI, imageView);
			pb.append(idUI, pb2);
		}

		public void addImageViewStatic(int idUI, ImageView imageView) {
			// TODO Auto-generated method stub
			icon.append(idUI, imageView);
		}
	}

}
