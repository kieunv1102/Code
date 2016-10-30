package vn.ce.sale.adapter.airtimemix;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.R;
import android.R.integer;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class AutoAdapterAirTimeMix extends BaseAdapter implements Filterable {

	Context _context;
	ArrayList<JSONObject> games;
	int layoutResourceId = -1;

	public AutoAdapterAirTimeMix(Context context, ArrayList<JSONObject> _games, int _layoutResourceId) {
		_context = context;
		layoutResourceId = _layoutResourceId;
		games = _games;
		orig = games;
		filter = new AutoFilter();
	}

	@Override
	public int getCount() {
		if (games != null)
			return games.size();
		else
			return 0;
	}

	@Override
	public String getItem(int arg0) {
		try {
			return games.get(arg0).getString("Account");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		try {
			return games.get(arg0).getInt("SalePrice");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/*
		 * JSONObjectView gv; if (arg1 == null) gv = new
		 * JSONObjectView(_context, games.get(arg0)); else { gv =
		 * (JSONObjectView) arg1; gv.setID(games.get(arg0).getID());
		 * gv.setName(games.get(arg0).getName()); } return gv;
		 */
		if (convertView == null) {
			// inflate the layout
			LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(layoutResourceId, parent, false);
		}

		// object item based on the position
		JSONObject objectItem = games.get(position);

		// get the TextView and then set the text (item name) and tag (item ID)
		// values
		TextView textViewItem = (TextView) convertView.findViewById(R.id.title);
		try {
			textViewItem.setText(objectItem.getString("Account"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// in case you want to add some style, you can do something like:
		textViewItem.setBackgroundColor(Color.CYAN);
		return convertView;
	}

	@Override
	public Filter getFilter() {
		return filter;
	}

	private AutoFilter filter;
	ArrayList<JSONObject> orig;

	private class AutoFilter extends Filter {

		public AutoFilter() {

		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults oReturn = new FilterResults();
			ArrayList<JSONObject> results = new ArrayList<JSONObject>();
			if (orig == null)
				orig = games;

			if (constraint != null) {
				if (orig != null && orig.size() > 0) {
					for (JSONObject g : orig) {
						try {
							if (g.getString("Account").toLowerCase().contains(constraint.toString().toLowerCase()))
								results.add(g);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				oReturn.values = results;
			}
			return oReturn;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			games = (ArrayList<JSONObject>) results.values;
			notifyDataSetChanged();
		}
	}

}