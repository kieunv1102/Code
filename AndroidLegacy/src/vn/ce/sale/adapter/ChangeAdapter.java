package vn.ce.sale.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kieu on 7/17/2015.
 */
public class ChangeAdapter extends BaseAdapter {
	private Context mContext;
	private List<JSONObject> dataSource;
	private LayoutInflater mInflater;
	double ttnew;

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

	public ChangeAdapter(Context c, List<JSONObject> d) {
		mContext = c;
		dataSource = d;
		this.mInflater = LayoutInflater.from(c);
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

		final ViewHolder vh;
		final JSONObject lineSource = dataSource.get(position);
		if (convertView == null) {
			View view = mInflater.inflate(R.layout.layout_customer_list_row_catalog, parent, false);
			vh = ViewHolder.create((LinearLayout) view);
			view.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		try {

			vh.txtStt.setText(String.valueOf(position + 1));
			vh.txtStt.setTag(position);
			vh.txtTensp.setText(String.valueOf(position) + "." + lineSource.getString("ProductCode") + "."
					+ lineSource.getString("ProductName"));
			vh.txtTensp.setTag(position);
			vh.editSoluong.setText(lineSource.getString("Quantity"));
			vh.editSoluong.setTag(position);
			vh.txtDonGia.setText(FormatUtil.formatCurrency(lineSource.getDouble("ProducValue")));
			vh.txtDonGia.setTag(position);
			vh.txtThanhTien.setText(FormatUtil.formatCurrency(lineSource.getDouble("TotalProducValue")));
			vh.txtThanhTien.setTag(position);

			vh.chkKM.setTag(position);
			if (lineSource.getString("QuantityKm").equals("1")) {
				vh.chkKM.setChecked(true);
			} else {
				vh.chkKM.setChecked(false);
			}

			vh.editSoluongNew.setText(dataSource.get(position).getString("SL"));
			vh.editSoluongNew.setTag(position);
			vh.editSoluongNew.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// holder.txtThanhTien.setText("Click...");
					// TODO Auto-generated method stub
					final Dialog dialog = new Dialog(mContext);
					dialog.setTitle("Nhập số lượng");
					final View view = mInflater.inflate(R.layout.custom_dialog, null);
					final EditText edtChange = (EditText) view.findViewById(R.id.edit_txt);
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

							try {
								dataSource.get(position).put("SL", x);
								vh.editSoluongNew.setText(editText.getText().toString());
								double ttnew = lineSource.getInt("SL") * lineSource.getDouble("ProducValue")
										* lineSource.getInt("KM");
								vh.txtThanhTienNew.setText(String.valueOf(FormatUtil.formatCurrency(ttnew)));
								dataSource.get(position).put("TT", ttnew);
								updateFooter();
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

			vh.chkKMNew.setTag(position);
			dataSource.get(position).put("KM", 1);
			vh.chkKMNew.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					boolean isChecked = ((CheckBox) v).isChecked();
					try {
						// dataSource.get(position).put("KM", isChecked ? 1 :
						// 0);
						if (!isChecked) {

							vh.txtThanhTienNew.setText(String.valueOf(FormatUtil.formatCurrency(
									(int) lineSource.getInt("SL") * lineSource.getDouble("ProducValue"))));
							dataSource.get(position).put("TT", String
									.valueOf((int) lineSource.getInt("SL") * lineSource.getDouble("ProducValue")));
							dataSource.get(position).put("KM", 1);

						} else {
							vh.txtThanhTienNew.setText("0");

							dataSource.get(position).put("TT", "0");
							dataSource.get(position).put("KM", 0);

						}
						updateFooter();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
			vh.txtThanhTienNew.setTag(position);
			vh.txtThanhTienNew.setText(String.valueOf(FormatUtil.formatCurrency(lineSource.getDouble("TT"))));
			vh.txtRemove.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					dataSource.remove(position);
					notifyDataSetChanged();
					updateFooter();
				}
			});
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return vh.rootView;
	}

	void updateFooter() {
		long totalNew = 0;
		try {
			for (int jx = 0; jx < getDataSource().size(); jx++) {

				totalNew = totalNew + getDataSource().get(jx).getLong("TT");
				TextView txtTotalNew = (TextView) footer.findViewById(R.id.txtTotalMoneyNew);

				txtTotalNew.setText(String.valueOf(FormatUtil.formatCurrency((double) totalNew)) + "\n"
						+ FormatUtil.numberToString(totalNew));

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (getDataSource().size() == 0) {
			footer.setVisibility(View.GONE);
			header.setVisibility(View.GONE);
		} else {
			footer.setVisibility(View.VISIBLE);
			header.setVisibility(View.VISIBLE);
		}
	}

	private static class ViewHolder {
		public LinearLayout rootView;
		public ImageView txtRemove;
		public CheckBox chkKM, chkKMNew;
		public TextView txtThanhTien, txtThanhTienNew;
		public TextView txtDonGia;
		public EditText editSoluong, editSoluongNew;
		public TextView txtTensp;
		public TextView txtStt;

		private ViewHolder(LinearLayout rootView, TextView txtStt, TextView txtTensp, EditText editSoluong,
				EditText editSoluongNew, TextView txtDonGia, TextView txtThanhTien, TextView txtThanhTienNew,
				CheckBox chkKM, CheckBox chkKMNew, ImageView txtRemove) {
			this.rootView = rootView;
			this.txtStt = txtStt;
			this.txtTensp = txtTensp;
			this.editSoluong = editSoluong;
			this.editSoluongNew = editSoluongNew;
			this.txtDonGia = txtDonGia;
			this.txtThanhTien = txtThanhTien;
			this.txtThanhTienNew = txtThanhTienNew;
			this.chkKM = chkKM;
			this.chkKMNew = chkKMNew;
			this.txtRemove = txtRemove;
		}

		public static ViewHolder create(LinearLayout rootView) {

			TextView txtStt = (TextView) rootView.findViewById(R.id.customer_stt);
			TextView txtTensp = (TextView) rootView.findViewById(R.id.customer_tensp);
			EditText editSoluong = (EditText) rootView.findViewById(R.id.edtSoLuong);
			TextView txtDonGia = (TextView) rootView.findViewById(R.id.customer_dongia);
			TextView txtThanhTien = (TextView) rootView.findViewById(R.id.customer_thanhtien);
			CheckBox chkKM = (CheckBox) rootView.findViewById(R.id.customer_khuyenmai);
			ImageView txtRemove = (ImageView) rootView.findViewById(R.id.customer_remove);

			EditText editSoluongNew = (EditText) rootView.findViewById(R.id.edtSoLuongNew);
			TextView txtThanhTienNew = (TextView) rootView.findViewById(R.id.customer_thanhtien_new);
			CheckBox chkKMNew = (CheckBox) rootView.findViewById(R.id.customer_khuyenmai_new);
			return new ViewHolder(rootView, txtStt, txtTensp, editSoluong, editSoluongNew, txtDonGia, txtThanhTien,
					txtThanhTienNew, chkKM, chkKMNew, txtRemove);
		}
	}
}
