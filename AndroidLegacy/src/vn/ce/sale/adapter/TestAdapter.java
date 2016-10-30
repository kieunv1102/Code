package vn.ce.sale.adapter;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.R;

public class TestAdapter extends BaseAdapter{

	private Context mContext;
	private List<JSONObject> dataSource;
	private int pos;
	LayoutInflater inflater;
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

	public TestAdapter(Context c, List<JSONObject> d,int p) {
		mContext = c;
		dataSource = d;
		pos = p;
		inflater = LayoutInflater.from(this.mContext);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_customer_list_row_catalog, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        
        mViewHolder.txtStt.setText(String.valueOf(position+1));
        try {
			mViewHolder.txtTensp.setText("tensp "+dataSource.get(pos).getString("ProductCode") + "." + dataSource.get(pos).getString("ProductName"));
			mViewHolder.editSoluong.setText(dataSource.get(pos).getInt("Quantity"));
	        mViewHolder.txtDonGia.setText(FormatUtil.formatCurrency(dataSource.get(pos).getDouble("Price")));
	        mViewHolder.txtThanhTien.setText(String.valueOf(position+1));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        

        
        return convertView;
    }

    private class MyViewHolder {
    	public ImageView txtRemove;
		public CheckBox chkKM;
		public TextView txtThanhTien;
		public TextView txtDonGia;
		public EditText editSoluong;
		public TextView txtTensp;
		public TextView txtStt;

        public MyViewHolder(View item) {
        	txtStt = (TextView) item.findViewById(R.id.customer_stt);
			txtTensp = (TextView) item.findViewById(R.id.customer_tensp);
			editSoluong = (EditText) item.findViewById(R.id.edtSoLuong);
			txtDonGia = (TextView) item.findViewById(R.id.customer_dongia);
			txtThanhTien = (TextView) item.findViewById(R.id.customer_thanhtien);
			chkKM = (CheckBox) item.findViewById(R.id.customer_khuyenmai);
			txtRemove = (ImageView) item.findViewById(R.id.customer_remove);
        }
    }

}
