package vn.ce.sale.fragment.bee;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;

/**
 * Created by VuHung on 1/23/2015.
 */
public class ItemFragment extends Fragment {
    private static final String EXTRA_MESSAGE = "mes";

    public static final ItemFragment newInstance(String message){
        ItemFragment f = new ItemFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String message = getArguments().getString(EXTRA_MESSAGE);
        View v = inflater.inflate(R.layout.bee_item, container, false);
        ImageView imvItemViewPager = (ImageView)v.findViewById(R.id.imvItemViewPager);
        
        new ImageLoadTask(message, imvItemViewPager).execute();
        return v;
    }
    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

		private String url;
		private ImageView imageView;

		public ImageLoadTask(String url, ImageView imageView) {
			this.url = url;
			this.imageView = imageView;
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
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
