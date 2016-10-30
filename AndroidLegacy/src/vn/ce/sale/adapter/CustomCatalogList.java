package vn.ce.sale.adapter;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.ui.DownloadImageTask;
import vn.ce.sale.ui.ImageLoadingHolder;
import vn.ce.sale.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CustomCatalogList extends BaseAdapter {

	private static final int ITEM_VIEW_TYPE_SEPARATOR = 0;// header
	private static final int ITEM_VIEW_TYPE_REGULAR = 1;// header
	// -- Separators and Regular rows --: number of different other
	private static final int ITEM_VIEW_TYPE_COUNT = 2;

	private Context mContext;
	List<JSONObject> dataSource;

	public CustomCatalogList(Context c, List<JSONObject> _dataSource) {
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
	public int getItemViewType(int position) {
		boolean isSection = dataSource.get(position).has("movies");// it is very
																	// important
																	// for
																	// developers
																	// to use
																	// this
																	// framework
		if (isSection) {
			return ITEM_VIEW_TYPE_SEPARATOR;
		} else {
			return ITEM_VIEW_TYPE_REGULAR;
		}
	}

	@Override
	public int getViewTypeCount() {
		return ITEM_VIEW_TYPE_COUNT;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		try {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			int itemViewType = getItemViewType(position);
			if (convertView == null) {
				// view = new View(mContext);
				if (itemViewType == ITEM_VIEW_TYPE_SEPARATOR) {
					view = inflater.inflate(R.layout.fragment_listview_section_header, null);
				} else {
					view = inflater.inflate(R.layout.fragment_listview_section_item, null);
					// setup for holder
					final ViewHolder viewHolder = new ViewHolder();
					TextView textView = (TextView) view.findViewById(R.id.grid_text);
					ImageView imageView = (ImageView) view.findViewById(R.id.grid_image);
					ProgressBar pb = (ProgressBar) view.findViewById(R.id.grid_pb);

					viewHolder.text = textView;
					viewHolder.icon = imageView;
					viewHolder.pb = pb;
					view.setTag(viewHolder);
				}

			} else {
				view = (View) convertView;
			}

			if (itemViewType == ITEM_VIEW_TYPE_REGULAR) {
				ViewHolder holder = (ViewHolder) view.getTag();

				Log.v("lamlt", dataSource.get(position).toString());

				holder.text.setText(dataSource.get(position).getString("movieName"));

				// holder.icon.setTag(dataSource.get(position));
				holder.icon.setTag(dataSource.get(position).getString("urlImage"));// set
																					// params
																					// url
																					// for
																					// this
																					// object
				holder.icon.setId(position);

				ImageLoadingHolder pb_and_image = new ImageLoadingHolder();
				pb_and_image.setImg(holder.icon);
				pb_and_image.setPb(holder.pb);
				new DownloadImageTask().execute(pb_and_image);
			} else {
				((TextView) view.findViewById(R.id.grid_separator))
						.setText(dataSource.get(position).getString("ChapterName"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	static class ViewHolder {
		TextView text;
		ImageView icon;
		ProgressBar pb;
	}
}