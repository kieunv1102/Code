package vn.ce.sale.adapter.bee;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.fragment.bee.Bee_Fragment_Product_List_Detail;
import vn.ce.sale.util.CircleImageView;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;

public class PagerAdapterCustomerViewBanner extends PagerAdapter {
	Context mContext;
	LayoutInflater mLayoutInflater;
	List<JSONObject> lstJsonObjects;
	List<JSONObject> lstJsonPriceInfo;
	String u5 = "http://k14.vcmedia.vn/k:thumb_w/600/pgHuXrcq18KdYtKp3bAtptdIKIxsLl/Image/2013/04/1-03387/10-san-pham-co-thiet-ke-dep-nhat-moi-thoi-dai.jpg";

	public PagerAdapterCustomerViewBanner(Context context, List<JSONObject> lst) {
		mContext = context;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		lstJsonObjects = lst;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lstJsonObjects.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == ((LinearLayout) arg1);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		View itemView = mLayoutInflater.inflate(R.layout.bee_item_view_banner, container, false);
		ViewHolder viewHolder = new ViewHolder();
		viewHolder.imvItemViewPagerBanner = (CircleImageView) itemView.findViewById(R.id.imvItemViewPagerBanner);
		viewHolder.txtNameProductBanner = (TextView) itemView.findViewById(R.id.txtNameProductBanner);
		viewHolder.txtProductGiftBanner = (TextView) itemView.findViewById(R.id.txtProductGiftBanner);
		viewHolder.txtPriceProductBanner = (TextView) itemView.findViewById(R.id.txtPriceProductBanner);
		viewHolder.txtPriceSaleBanner = (TextView) itemView.findViewById(R.id.txtPriceSaleBanner);
		Picasso.with(mContext).load(u5).placeholder(R.drawable.backviewpager).into(viewHolder.imvItemViewPagerBanner);
		container.addView(itemView);
		itemView.setTag(position);
		try {
			final JSONObject lineSource = lstJsonObjects.get(position);
			String lstPriceInfo = lineSource.getString("PriceInfo");
			lstJsonPriceInfo = TransformDataManager.convertArrayToListJSON(new JSONArray(lstPriceInfo));
			int t = lstJsonPriceInfo.get(0).getInt("Price");

			viewHolder.txtNameProductBanner.setTag(position);
			viewHolder.txtNameProductBanner.setText(lineSource.getString("Name"));

			viewHolder.txtPriceProductBanner.setTag(position);
			viewHolder.txtPriceProductBanner.setText(FormatUtil.formatCurrency((double) t) + " VND");

			itemView.setTag(position);
			itemView.setOnClickListener(new OnClickListener() {

				@SuppressWarnings("static-access")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						Util.checkBanner = true;
						FragmentTransaction fmdetail1 = ((FragmentActivity) mContext).getSupportFragmentManager()
								.beginTransaction();
						fmdetail1.replace(R.id.container,
								new Bee_Fragment_Product_List_Detail().newInstance(lineSource.getString("Id"), ""));
						fmdetail1.addToBackStack("tag");
						fmdetail1.commit();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemView;
	}

	@Override
	public void destroyItem(ViewGroup collection, int position, Object view) {
		collection.removeView((View) view);
	}

	static class ViewHolder {
		public CircleImageView imvItemViewPagerBanner;
		public TextView txtNameProductBanner;
		public TextView txtProductGiftBanner;
		public TextView txtPriceProductBanner;
		public TextView txtPriceSaleBanner;

	}

	public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

		private String url;
		private ImageView imageView;

		public ImageLoadTask(String url, ImageView imageView) {
			this.url = url;
			this.imageView = imageView;
			imageView.setBackgroundResource(R.drawable.backviewpager);
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

}