package vn.ce.sale.adapter;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.DownloadImageTask;
import vn.ce.sale.ui.ImageLoadingHolder;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.ui.ZopostFragment.ViewHolder;
import vn.ce.sale.R;
import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CustomGrid1 extends BaseAdapter {
	private Context mContext;
	private final List<JSONObject> dataSource;
	private HashMap<Integer, BindDataUI> bindRule;
	private int layoutRow = -1;

	public CustomGrid1(Context c, List<JSONObject> d, int rowLineLayput) {
		mContext = c;
		dataSource = d;
		layoutRow = rowLineLayput;
	}

	public CustomGrid1(Context c, List<JSONObject> d) {
		mContext = c;
		dataSource = d;
		layoutRow = R.layout.fragment_home_single;
	}

	public void bindFields(BindDataUI[] _arrBindDataUIs) {
		// arrBindDataUIs=_arrBindDataUIs;
		bindRule = new HashMap<Integer, BindDataUI>();
		for (BindDataUI o : _arrBindDataUIs) {
			bindRule.put(o.getIdUI(), o);
		}
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
			JSONObject lineSource = dataSource.get(position);
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// mapping datasourcename and id of view
			if (convertView == null) {

				grid = new View(mContext);
				// grid = inflater.inflate(R.layout.fragment_home_single, null);
				grid = inflater.inflate(layoutRow, null);
				// setup for holder
				final ViewHolder viewHolder = new ViewHolder();
				for (Integer i : bindRule.keySet()) {
					BindDataUI o = bindRule.get(i);
					if (o.isText()) {
						TextView textView = (TextView) grid.findViewById(i.intValue());
						viewHolder.text = textView;
						continue;
					}
					if (o.isImage()) {
						ImageView imageView = (ImageView) grid.findViewById(i.intValue());
						ProgressBar pb = (ProgressBar) grid.findViewById(R.id.grid_pb);
						viewHolder.icon = imageView;
						viewHolder.pb = pb;
						continue;
					}
				}
				grid.setTag(viewHolder);

			} else {
				grid = (View) convertView;
			}

			// bind data
			ViewHolder holder = (ViewHolder) grid.getTag();
			for (Integer i : bindRule.keySet()) {
				BindDataUI o = bindRule.get(i);
				if (o.isText()) {
					holder.text.setText(lineSource.getString(o.getDatasourceId()));
					continue;
				}

				if (o.isImage()) {
					holder.icon.setTag(lineSource.getString(o.getDatasourceId()));
					holder.icon.setId(position);

					ImageLoadingHolder pb_and_image = new ImageLoadingHolder();
					pb_and_image.setImg(holder.icon);
					pb_and_image.setPb(holder.pb);
					new DownloadImageTask().execute(pb_and_image);
					continue;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return grid;
	}

	static class ViewHolder1 {
		TextView[] text = new TextView[10];
		ImageView[] icon = new ImageView[10];
		ProgressBar[] pb = new ProgressBar[10];
	}
}