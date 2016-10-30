package vn.ce.sale.adapter;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.R;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProductGridInline extends BaseAdapter {
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

	public View getHeader() {
		return footer;
	}

	public void setHeader(View header) {
		this.header = header;
	}

	public ProductGridInline(Context c, List<JSONObject> d) {
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

				//
				if (layoutRow == -1)
					grid = inflater.inflate(R.layout.layout_customer_list_row2_inline, null);
				else
					grid = inflater.inflate(layoutRow, null);

				// setup for holder
				final ViewHolder1 viewHolder = new ViewHolder1();
				viewHolder.txtStt = (TextView) grid.findViewById(R.id.customer_stt);
				viewHolder.txtTensp = (TextView) grid.findViewById(R.id.customer_tensp);
				viewHolder.editSoluong = (TextView) grid.findViewById(R.id.edtSoLuong);
				viewHolder.txtDonGia = (TextView) grid.findViewById(R.id.customer_dongia);
				viewHolder.txtThanhTien = (TextView) grid.findViewById(R.id.customer_thanhtien);
				viewHolder.chkKM = (TextView) grid.findViewById(R.id.customer_khuyenmai);

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

			holder.editSoluong.setText(lineSource.getString("Quantity"));
			holder.editSoluong.setTag(position);

			holder.txtDonGia.setText((lineSource.getString("TotalPrice")).split("\\.")[0]);
			holder.txtDonGia.setTag(position);

			holder.txtThanhTien.setText(lineSource.getString("TotalProducValue"));
			holder.txtThanhTien.setTag(position);

			holder.chkKM.setTag(position);
			holder.chkKM.setText(lineSource.getString("PromotionQuantity"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return grid;
	}

	public void setLayoutRow(int layoutRow) {
		this.layoutRow = layoutRow;
	}

	static class ViewHolder2 {

	}

	static class ViewHolder1 {
		public TextView txtRemove;
		public TextView chkKM;
		public TextView txtThanhTien;
		public TextView txtDonGia;
		public TextView editSoluong;
		public TextView txtTensp;
		public TextView txtStt;

	}
}