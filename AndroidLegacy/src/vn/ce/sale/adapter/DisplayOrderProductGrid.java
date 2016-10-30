package vn.ce.sale.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.R.bool;
import android.R.integer;
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
import android.widget.Toast;

public class DisplayOrderProductGrid extends BaseAdapter {
	private Context mContext;
	private List<JSONObject> dataSource;
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

	public DisplayOrderProductGrid(Context c, List<JSONObject> d) {
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
		// final JSONObject lineSourceChange = dataSourceChange.get(position);

		final JSONObject lineSource = dataSource.get(position);
		final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {

			grid = new View(mContext);

			//
			if (layoutRow == -1)
				grid = inflater.inflate(R.layout.layout_customer_list_row_catalog, null);
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

			viewHolder.editSoluongNew = (EditText) grid.findViewById(R.id.edtSoLuongNew);
			viewHolder.txtThanhTienNew = (TextView) grid.findViewById(R.id.customer_thanhtien_new);
			viewHolder.chkKMNew = (CheckBox) grid.findViewById(R.id.customer_khuyenmai_new);

			grid.setTag(viewHolder);

		} 
		else {
			grid = (View) convertView;
		}

		// bind data
		final ViewHolder1 holder = (ViewHolder1) grid.getTag();
		holder.txtStt.setText(String.valueOf(position + 1));
		holder.txtStt.setTag(position);

		try {
			holder.txtTensp.setText(lineSource.getString("ProductCode") +"."+ lineSource.getString("ProductName"));
			holder.txtTensp.setTag(position);
			holder.editSoluong.setText(lineSource.getString("Quantity"));
			holder.editSoluong.setTag(position);
			holder.txtDonGia.setText(FormatUtil.formatCurrency(lineSource.getDouble("ProducValue")));
			holder.txtDonGia.setTag(position);
			holder.txtThanhTien.setText(FormatUtil.formatCurrency(lineSource.getDouble("TotalProducValue")));
			holder.txtThanhTien.setTag(position);

			holder.chkKM.setTag(position);
			if (lineSource.getString("DetailType").equals("2")) {
				holder.chkKM.setChecked(true);
			} else {
				holder.chkKM.setChecked(false);
			}
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			holder.editSoluongNew.setText(dataSource.get(position).getString("SL"));
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		holder.editSoluongNew.setTag(position);
		holder.editSoluongNew.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// holder.txtThanhTien.setText("Click...");
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(mContext);
				dialog.setTitle("Nhập số lượng");
				final View view = inflater.inflate(R.layout.custom_dialog, null);
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
							holder.editSoluongNew.setText(editText.getText().toString());
							dataSource.get(position).put("SL", x);
							double ttnew = lineSource.getInt("SL") * lineSource.getDouble("ProducValue")
									* lineSource.getInt("KM");
							holder.txtThanhTienNew.setText(String.valueOf(FormatUtil.formatCurrency(ttnew)));
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
		holder.chkKMNew.setTag(position);

		try {
			dataSource.get(position).put("KM", 1);
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		holder.chkKMNew.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				boolean isChecked = ((CheckBox) v).isChecked();
				try {
					// dataSource.get(position).put("KM", isChecked ? 1 : 0);
					if (!isChecked) {

						holder.txtThanhTienNew.setText(String.valueOf(FormatUtil
								.formatCurrency((int) lineSource.getInt("SL") * lineSource.getDouble("ProducValue"))));
						dataSource.get(position).put("TT",
								String.valueOf((int) lineSource.getInt("SL") * lineSource.getDouble("ProducValue")));
						dataSource.get(position).put("KM", 1);

					} else {
						holder.txtThanhTienNew.setText("0");

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

		try {
			//
			holder.txtThanhTienNew.setTag(position);
			holder.txtThanhTienNew.setText(String.valueOf(FormatUtil.formatCurrency(lineSource.getDouble("TT"))));
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		holder.txtRemove.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dataSource.remove(position);
				notifyDataSetChanged();
				updateFooter();
			}
		});

		return grid;
	}

	public void setLayoutRow(int layoutRow) {
		this.layoutRow = layoutRow;
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

	static class ViewHolder2 {

	}

	static class ViewHolder1 {
		public ImageView txtRemove;
		public CheckBox chkKM, chkKMNew;
		public TextView txtThanhTien, txtThanhTienNew;
		public TextView txtDonGia;
		public EditText editSoluong, editSoluongNew;
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
}