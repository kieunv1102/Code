package vn.ce.sale.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Filter;
import vn.ce.sale.util.Util;

public class CustomGridAndFilter extends CustomGrid {

	private Filter filter;
	private List<JSONObject> orginalDataSource;
	private List<JSONObject> searchDataSource;
	private String[] filterFiledString = new String[] { "title" };

	public CustomGridAndFilter(Context c, List<JSONObject> d, int rowLineLayput) {
		super(c, d, rowLineLayput);
		orginalDataSource = (new ArrayList<JSONObject>());
		orginalDataSource.addAll(d);
		// TODO Auto-generated constructor stub
	}

	public CustomGridAndFilter(Context c, List<JSONObject> d, int rowLineLayput, String[] _filterFiledString) {
		super(c, d, rowLineLayput);
		orginalDataSource = (new ArrayList<JSONObject>());
		orginalDataSource.addAll(d);
		filterFiledString = _filterFiledString;
		// TODO Auto-generated constructor stub
	}

	public CustomGridAndFilter(Context c, List<JSONObject> d, int rowLineLayput, Filter filterData) {
		super(c, d, rowLineLayput);
		filter = filterData;
		orginalDataSource = (new ArrayList<JSONObject>());
		orginalDataSource.addAll(d);
		// TODO Auto-generated constructor stub
	}

	public CustomGridAndFilter(Context c, List<JSONObject> d) {
		super(c, d);
		// TODO Auto-generated constructor stub
	}

	public Filter getFilter() {
		if (filter == null)
			filter = new CountryFilter();
		return filter;
	}

	class CountryFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {

			constraint = constraint.toString().toLowerCase();
			FilterResults result = new FilterResults();
			if (constraint != null && constraint.toString().length() > 0) {
				List<JSONObject> filteredItems = new ArrayList<JSONObject>();

				// for (int i = 0, l = orginalDataSource.size(); i < l - 1; i++)
				// {
				for (int i = 0; i <= orginalDataSource.size() - 1; i++) {
					JSONObject o = orginalDataSource.get(i);
					try {
						for (String sField : filterFiledString) {
							if (o.getString(sField).toLowerCase().contains(constraint)
									|| Util.TrimVietnameseMark(o.getString(sField)).toLowerCase()
											.contains(Util.TrimVietnameseMark(constraint.toString()))) {
								filteredItems.add(o);
								break;
							}
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				result.count = filteredItems.size();
				result.values = filteredItems;
			} else {
				synchronized (this) {
					result.values = orginalDataSource;
					result.count = orginalDataSource.size();
				}
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {

			searchDataSource = (ArrayList<JSONObject>) results.values;
			notifyDataSetChanged();
			getDataSource().clear();
			/*
			 * for(int i = 0, l = searchDataSource.size(); i < l; i++)
			 * getDataSource().add(countryList.get(i));
			 */
			getDataSource().addAll(searchDataSource);
			notifyDataSetInvalidated();
		}
	}

}
