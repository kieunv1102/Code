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

import vn.ce.sale.LoginActivity;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.util.CircleImageView;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class DisplayOrderEcomProductGrid extends BaseAdapter {
	private Context mContext;
	private List<JSONObject> dataSource;
	private List<JSONObject> lstJsonPriceInfo;
	private ArrayList<String> arrUnit;
	private ArrayList<String> arrUnitID;
	String dv;
	int uId;
	double t;
	public List<JSONObject> getDataSource() {
		return dataSource;
	}

	public void setDataSource(List<JSONObject> dt) {
		dataSource = dt;
	}

	private HashMap<Integer, BindDataUI> bindRule;
	private int layoutRow = -1;
	private View footer;

	public DisplayOrderEcomProductGrid(Context c, List<JSONObject> d) {
		mContext = c;
		dataSource = d;
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

		// TODO Auto-generated method stub
		View grid;

		new ICallBackUI() {

			@Override
			public void processRaw(String key, int status, String json) {
				// TODO Auto-generated method stub
			}

			@Override
			public void process(String key, int status, List<JSONObject> lst) {
				// TODO Auto-generated method stub

			}
		};

		final JSONObject lineSource = dataSource.get(position);
		final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		try {
			String lstPriceInfo = lineSource.getString("PriceInfo");
			lstJsonPriceInfo = TransformDataManager.convertArrayToListJSON(new JSONArray(lstPriceInfo));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (convertView == null) {

			grid = new View(mContext);

			//
			if (layoutRow == -1)
				grid = inflater.inflate(R.layout.fragment_order_product_item, null);
			else
				grid = inflater.inflate(layoutRow, null);

			// setup for holder
			final ViewHolder1 viewHolder = new ViewHolder1();
			viewHolder.txtSTT = (TextView) grid.findViewById(R.id.txtSTT);
			viewHolder.txtNameProduct = (TextView) grid.findViewById(R.id.txtNameProduct);
			viewHolder.txtDonVi = (TextView) grid.findViewById(R.id.txtDonVi);
			viewHolder.txtPrice = (TextView) grid.findViewById(R.id.txtPrice);
			viewHolder.txtMoney = (TextView) grid.findViewById(R.id.txtMoney);
			viewHolder.edtQuantityProduct = (EditText) grid.findViewById(R.id.edtQuantityProduct);
			viewHolder.imvDeleteItem = (ImageView) grid.findViewById(R.id.imvDeleteItem);
			grid.setTag(viewHolder);

		} else {
			grid = (View) convertView;
		}

		// bind data
		final ViewHolder1 holder = (ViewHolder1) grid.getTag();

		try {

			t = lineSource.getDouble("PRICE");

			holder.txtSTT.setTag(position);
			holder.txtSTT.setText(String.valueOf(position + 1));
			holder.txtNameProduct.setTag(position);
			holder.txtNameProduct.setText(lineSource.getString("Id") + "." + lineSource.getString("Name"));
			holder.txtDonVi.setTag(position);
			holder.txtDonVi.setText(lineSource.getString("UNAME"));
			holder.txtPrice.setTag(position);
			holder.txtPrice.setText(String.valueOf(FormatUtil.formatCurrency((double) t)));

			holder.edtQuantityProduct.setTag(position);
			try {
				holder.edtQuantityProduct.setText(lineSource.getString("SL"));
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			holder.edtQuantityProduct.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// holder.txtThanhTien.setText("Click...");
					// TODO Auto-generated method stub
					
					try {
						String lstPriceInfo2 = lineSource.getString("PriceInfo");
						List<JSONObject> lstJsonPriceInfo2 = TransformDataManager.convertArrayToListJSON(new JSONArray(lstPriceInfo2));
//						String Uname = lstJsonPriceInfo2.get(0).getString("UnitName");
						arrUnit = new ArrayList<String>();
						arrUnitID = new ArrayList<String>();
						for (int i = 0; i < lstJsonPriceInfo2.size(); i++) {
							arrUnitID.add(String.valueOf(lstJsonPriceInfo2.get(i).getInt("UnitID")));
							arrUnit.add(String.valueOf(lstJsonPriceInfo2.get(i).getString("UnitName")));
						}
						
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					final Dialog dialog = new Dialog(mContext);
					dialog.setTitle("Nhập số lượng");
					final View view = inflater.inflate(R.layout.custom_dialog_bee, null);
					final EditText edtChange = (EditText) view.findViewById(R.id.edit_txt);
					final Spinner spinnerUnit = (Spinner) view.findViewById(R.id.spinner_unit);
					ArrayAdapter<String> adapterUnit = new ArrayAdapter<String>(mContext,
							android.R.layout.simple_spinner_item, arrUnit);
					adapterUnit.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
					spinnerUnit.setAdapter(adapterUnit);
					spinnerUnit.setOnItemSelectedListener(new UnitProcessEvent());
					try {
						edtChange.setText(lineSource.getString("SL"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dialog.setContentView(view);
					Button btn = (Button) view.findViewById(R.id.btn_ok);
					btn.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {

							EditText editText = (EditText) view.findViewById(R.id.edit_txt);
							int x = Integer.parseInt(editText.getText().toString());
							int tt;
							try {
								if (x > 9999) {
									dataSource.get(position).put("SL", 9999);
									tt = (int) (9999 * t);
									dataSource.get(position).put("TT", tt);
//									lineSource.put("UNAME", dv);
//									lineSource.put("UNAME", uId);
									dataSource.get(position).put("PRICE", t);
								} else {

									dataSource.get(position).put("SL", x);
									tt = (int) (x * t);
									dataSource.get(position).put("TT", tt);
//									lineSource.put("UNAME", dv);
//									lineSource.put("UID", uId);
									dataSource.get(position).put("PRICE", t);
								}
								
								holder.edtQuantityProduct.setText(String.valueOf(x));
								holder.txtPrice.setText(String.valueOf(FormatUtil.formatCurrency((double) t)));
//								double ttnew = lineSource.getInt("SL") * lineSource.getDouble("Price");
								holder.txtMoney.setText(String.valueOf(FormatUtil.formatCurrency((double) tt)));
//								dataSource.get(position).put("TT", ttnew);
//								holder.txtDonVi.setText(dv);
								updateFooter();
								ShareMemManager.getInstance().saveToDB(mContext, "productOrder", dataSource.toString());
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							dialog.dismiss();
						}
					});
					Button btn1 = (Button) view.findViewById(R.id.btn_canel_order);
					btn1.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					dialog.show();
				}
			});

			holder.txtMoney.setTag(position);
//			int tien = (int) (Integer.parseInt(holder.edtQuantityProduct.getText().toString()) * t);
			// int tien = lineSource.getInt("SL")*lineSource.getInt("Price");
			// lineSource.put("TT", tien);
			int tien = lineSource.getInt("TT");
			holder.txtMoney.setText(String.valueOf(FormatUtil.formatCurrency((double) tien)));
			
			holder.imvDeleteItem.setTag(position);
			holder.imvDeleteItem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					AlertDialog.Builder b = new AlertDialog.Builder(mContext);
					b.setCancelable(false);
					b.setMessage("Bạn chắc chắn muốn xóa sản phẩm này!");
					b.setNegativeButton("Không", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					b.setPositiveButton("Có", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dataSource.remove(position);
							ShareMemManager.getInstance().saveToDB(mContext, "productOrder", dataSource.toString());
							notifyDataSetChanged();
							updateFooter();
						}
					});
					b.show();

				}
			});

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return grid;
	}

	private class UnitProcessEvent implements android.widget.AdapterView.OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			dv = arrUnit.get(position);
			uId = Integer.parseInt(arrUnitID.get(position));
			dv = arrUnit.get(position);
			try {
				t = lstJsonPriceInfo.get(position).getDouble("Price");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			// txtDisplayNhaMang.setText(arrNhaMang[0]);
		}

	}

	void updateFooter() {
		long total = 0;
		for (int jx = 0; jx <= getDataSource().size() - 1; jx++) {
			try {
				int tt = getDataSource().get(jx).getInt("TT");
				total = (long) (total + tt);

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		TextView txtTotal = (TextView) footer.findViewById(R.id.txtHelloUser);
		txtTotal.setText("Tổng tiền: " + String.valueOf(FormatUtil.formatCurrency((double) total)));

	}

	public void setLayoutRow(int layoutRow) {
		this.layoutRow = layoutRow;
	}

	static class ViewHolder2 {

	}

	static class ViewHolder1 {
		public EditText edtQuantityProduct;
		public ImageView imvDeleteItem;
		public TextView txtSTT, txtNameProduct;
		public TextView txtDonVi, txtMoney;
		public TextView txtPrice;

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