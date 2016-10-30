package vn.ce.sale.adapter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.ui.DownloadImageTask;
import vn.ce.sale.ui.ImageLoadingHolder;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.ui.ZopostFragment.ViewHolder;
import vn.ce.sale.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CustomList extends BaseAdapter {
	private Context mContext;
	private final List<JSONObject> dataSource;

	public CustomList(Context c, List<JSONObject> _dataSource) {
		mContext = c;
		dataSource = _dataSource;
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
		// TODO Auto-generated method stub
		View grid = null;
		try {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {

				grid = new View(mContext);
				grid = inflater.inflate(R.layout.fragment_listview_item, null);

				// setup for holder
				final ViewHolder viewHolder = new ViewHolder();
				TextView textView = (TextView) grid.findViewById(R.id.grid_text);
				ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image);
				ProgressBar pb = (ProgressBar) grid.findViewById(R.id.grid_pb);

				viewHolder.text = textView;
				viewHolder.icon = imageView;
				viewHolder.pb = pb;
				grid.setTag(viewHolder);

			} else {
				grid = (View) convertView;
			}

			ViewHolder holder = (ViewHolder) grid.getTag();

			holder.text.setText(dataSource.get(position).getString("movieName"));
			holder.icon.setTag(dataSource.get(position).getString("urlImage"));
			holder.icon.setId(position);

			ImageLoadingHolder pb_and_image = new ImageLoadingHolder();
			pb_and_image.setImg(holder.icon);
			pb_and_image.setPb(holder.pb);
			new DownloadImageTask().execute(pb_and_image);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return grid;
	}

}