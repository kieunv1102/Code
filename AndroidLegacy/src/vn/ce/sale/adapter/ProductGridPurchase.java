package vn.ce.sale.adapter;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.R;
import android.app.Dialog;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProductGridPurchase extends BaseAdapter {
	private Context mContext;
	private List<JSONObject> dataSource;

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

	public ProductGridPurchase(Context c, List<JSONObject> d) {
		mContext = c;
		dataSource = d;

	}

	public View getHeader() {
		return footer;
	}

	public void setHeader(View header) {
		this.header = header;
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
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		View grid = null;
		try {
			final JSONObject lineSource = dataSource.get(position);
			final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// mapping datasourcename and id of view
			if (convertView == null) {

				grid = new View(mContext);
				if (layoutRow == -1)
					grid = inflater.inflate(R.layout.layout_customer_list_row2, null);
				else
					grid = inflater.inflate(layoutRow, null);

				// setup for holder
				final ViewHolder1 viewHolder = new ViewHolder1();
				viewHolder.txtStt = (TextView) grid.findViewById(R.id.customer_stt);
				viewHolder.txtTensp = (TextView) grid.findViewById(R.id.customer_tensp);
				viewHolder.editSoluong = (EditText) grid.findViewById(R.id.edtSoLuong);
				viewHolder.txtDonGia = (TextView) grid.findViewById(R.id.customer_dongia);
				viewHolder.txtThanhTien = (TextView) grid.findViewById(R.id.customer_thanhtien);
				viewHolder.chkKM = (CheckBox) grid.findViewById(R.id.customer_khuyenmai);
				viewHolder.txtRemove = (ImageView) grid.findViewById(R.id.customer_remove);

				grid.setTag(viewHolder);

			} else {
				grid = (View) convertView;
			}

			// bind data
			final ViewHolder1 holder = (ViewHolder1) grid.getTag();
			holder.txtStt.setText(String.valueOf(position + 1));
			holder.txtStt.setTag(position);

			holder.txtTensp.setText(lineSource.getString("ProductCode") + "." + lineSource.getString("ProductName"));
			holder.txtTensp.setTag(position);

			holder.editSoluong.setText(lineSource.getString("SL"));
			holder.editSoluong.setTag(position);

			holder.editSoluong.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					updateFooter();
					// holder.txtThanhTien.setText("Click...");
					// TODO Auto-generated method stub
					final Dialog dialog = new Dialog(mContext);
					dialog.setTitle("Nhập số lượng");
					final View view = inflater.inflate(R.layout.custom_dialog, null);
					EditText editText1 = (EditText) view.findViewById(R.id.edit_txt);
					try {
						editText1.setText(lineSource.getString("SL"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					dialog.setContentView(view);
					Button btn = (Button) view.findViewById(R.id.btn_ok);
					btn.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							EditText editText = (EditText) view.findViewById(R.id.edit_txt);
							try {
								int x = Integer.parseInt(editText.getText().toString());
//								holder.editSoluong.setText(editText.getText().toString());
								dataSource.get(position).put("SL", String.valueOf(x));
								holder.txtThanhTien
										.setText(FormatUtil.formatCurrency((double) lineSource.getDouble("Price")
												* lineSource.getInt("SL") * (lineSource.getInt("KM") == 1 ? 0 : 1)));
								updateFooter();
								/*
								 * int position =
								 * Integer.parseInt(arg0.getTag().toString());
								 * for (int jx = 0; jx <= getDataSource().size()
								 * - 1; jx++) {
								 * 
								 * if (getDataSource().get(jx).getString("KM").
								 * equals("1")) {
								 * holder.txtThanhTien.setText(""); }
								 * 
								 * }
								 */
							} catch (Exception ex) {
								ex.printStackTrace();
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
			holder.txtStt.setTag(position);

			holder.txtDonGia.setText(FormatUtil.formatCurrency(lineSource.getDouble("Price")));
			holder.txtDonGia.setTag(position);
			double tt = (double) lineSource.getDouble("Price") * lineSource.getInt("SL")
					* (lineSource.getInt("KM") == 1 ? 0 : 1);

			holder.txtThanhTien.setText(FormatUtil.formatCurrency(tt));
			holder.txtThanhTien.setTag(position);

			// holder.chkKM.setText(lineSource.getString("KM"));
			holder.chkKM.setTag(position);
			holder.chkKM.setChecked(lineSource.getString("KM").equals("1"));
			holder.chkKM.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// TODO Auto-generated method stub
					int position = Integer.parseInt(v.getTag().toString());
					boolean isChecked = ((CheckBox) v).isChecked();
					try {
						int countIndex = 0;
						for (JSONObject o : dataSource) {
							if (o.getString("ProductID")
									.equalsIgnoreCase(dataSource.get(position).getString("ProductID"))) {
								countIndex++;
							}
						}
						dataSource.get(position).put("KM", isChecked ? 1 : 0);
						// ko khuyển mại trỏ về khuyến mại thì xóa đi
						if (!isChecked) {

							if (countIndex >= 2) {
								dataSource.remove(position);
							} else {
								holder.txtThanhTien.setText(FormatUtil.formatCurrency(
										(double) (long) lineSource.getDouble("Price") * lineSource.getInt("SL")));
								dataSource.get(position).put("TT",
										String.valueOf((long) lineSource.getDouble("Price") * lineSource.getInt("SL")));
								updateFooter();
							}
							notifyDataSetChanged();
						} else {
							holder.txtThanhTien.setText("0");
							dataSource.get(position).put("TT", 0);
						}
						mergDataSourceKM(lineSource, position, isChecked);
						updateFooter();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			// holder.txtRemove.setText("Xóa");
			holder.txtRemove.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					dataSource.remove(position);
					notifyDataSetChanged();
					updateFooter();
				}
			});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return grid;
	}

	public void mergDataSourceKM(JSONObject oJson, int position, boolean isKMOrNot) throws JSONException {
		// tìm ra vị trí của ProductID cùng id mà khác position mà có cùng
		// khuyến mại hoặc ko?
		boolean isFound = false;
		for (int jx = 0; jx <= dataSource.size() - 1; jx++) {
			// nếu tìm ra thì tăng số lượng lên 1- chỉnh số text

			if (dataSource.get(jx).getString("ProductID").equalsIgnoreCase(oJson.getString("ProductID"))
					&& dataSource.get(jx).getString("KM").equalsIgnoreCase("1") && jx != position) {

				try {
					isFound = true;
					dataSource.get(jx).put("SL", dataSource.get(jx).getInt("SL") + 1);
					// dataSource.remove(jx);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		// nếu tìm ra thì + SL lên 1
		if (isFound)
			dataSource.remove(position);

		// và remove thằng hiện tại đi

		// sau đó call notifyDataSetchanged...
		notifyDataSetChanged();
	}

	void updateFooter() {
		long total = 0;
		for (int jx = 0; jx <= getDataSource().size() - 1; jx++) {
			try {
				getDataSource().get(jx).put("STT", String.valueOf(jx + 1));
				if (getDataSource().get(jx).getString("KM").equals("1")) {
					getDataSource().get(jx).put("TT", 0);
				} else {
					getDataSource().get(jx).put("TT",
							getDataSource().get(jx).getDouble("Price") * getDataSource().get(jx).getInt("SL"));
					total = total + getDataSource().get(jx).getLong("TT");
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		TextView txtTotal = (TextView) footer.findViewById(R.id.txtTotalMoney);
		txtTotal.setText(
				String.valueOf(FormatUtil.formatCurrency((double) total)) + "\n" + FormatUtil.numberToString(total));

		if (getDataSource().size() == 0) {
			footer.setVisibility(View.GONE);
			header.setVisibility(View.GONE);
		} else {
			footer.setVisibility(View.VISIBLE);
			header.setVisibility(View.VISIBLE);
		}
	}

	static class ViewHolder2 {

	}

	static class ViewHolder1 {
		public ImageView txtRemove;
		public CheckBox chkKM;
		public TextView txtThanhTien;
		public TextView txtDonGia;
		public EditText editSoluong;
		public TextView txtTensp;
		public TextView txtStt;
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

	public void setLayoutRow(int layoutCustomerListRow2Noedit) {
		// TODO Auto-generated method stub
		layoutRow = layoutCustomerListRow2Noedit;
	}
}