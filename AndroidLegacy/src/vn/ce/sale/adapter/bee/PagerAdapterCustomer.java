package vn.ce.sale.adapter.bee;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import vn.ce.sale.R;

public class PagerAdapterCustomer extends PagerAdapter {
	Context mContext;
	LayoutInflater mLayoutInflater;
	String img[];

	public PagerAdapterCustomer(Context context, String u[]) {
		mContext = context;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		img = u;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return img.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == ((LinearLayout) arg1);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View itemView = mLayoutInflater.inflate(R.layout.bee_item, container, false);

		ImageView imvItemViewPager = (ImageView) itemView.findViewById(R.id.imvItemViewPager);
		// new ImageLoadTask(img[position], imvItemViewPager).execute();
//		Picasso.with(mContext).load(img[position]).into(imvItemViewPager);
		Picasso.with(mContext).load(img[position]).placeholder(R.drawable.backviewpager).into(imvItemViewPager);
		container.addView(itemView);

		return itemView;
	}

	@Override
	public void destroyItem(ViewGroup collection, int position, Object view) {
		collection.removeView((View) view);
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