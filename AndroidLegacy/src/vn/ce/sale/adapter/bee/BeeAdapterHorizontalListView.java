package vn.ce.sale.adapter.bee;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.util.CircleImageView;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.R;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

@SuppressLint("ViewHolder")
public class BeeAdapterHorizontalListView extends BaseAdapter {

	private Context mContext;
	private List<JSONObject> listObject;
	private LayoutInflater mInflater;
	String u5 = "http://k14.vcmedia.vn/k:thumb_w/600/pgHuXrcq18KdYtKp3bAtptdIKIxsLl/Image/2013/04/1-03387/10-san-pham-co-thiet-ke-dep-nhat-moi-thoi-dai.jpg";

	public BeeAdapterHorizontalListView(Context context, List<JSONObject> lstObj) {
		this.mContext = context;
		this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.listObject = lstObj;

	}

	@Override
	public int getCount() {
		return listObject.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.bee_item_hlv_product_connection, parent, false);
		ViewHolder viewHolder = new ViewHolder();
		viewHolder.imvProduct = (CircleImageView) view.findViewById(R.id.imvProductConnection);
		viewHolder.txtNameProduct = (TextView) view.findViewById(R.id.txtNameProductConnection);
		viewHolder.txtGiftProduct = (TextView) view.findViewById(R.id.txtProductGiftConnection);
		viewHolder.txtPriceProduct = (TextView) view.findViewById(R.id.txtPriceProductConnection);
		
		try {
			final JSONObject lineSource = listObject.get(position);
			String lstPriceInfo = lineSource.getString("PriceInfo");
			List<JSONObject> lstJsonPriceInfo = TransformDataManager.convertArrayToListJSON(new JSONArray(lstPriceInfo));
//			int t = lstJsonPriceInfo.get(0).getInt("Price");
			
			viewHolder.txtNameProduct.setText(lineSource.getString("Name"));
			viewHolder.txtGiftProduct.setText(lineSource.getString("Name"));
//			viewHolder.txtPriceProduct.setText(FormatUtil.formatCurrency((double)t) + " VND");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Picasso.with(mContext).load(u5).placeholder(R.drawable.backviewpager).into(viewHolder.imvProduct);

		return view;
	}

	@SuppressWarnings("unused")
	private static class ViewHolder {
		public CircleImageView imvProduct;
		public TextView txtNameProduct, txtGiftProduct, txtPriceProduct;

	}
}