package vn.ce.sale.ui;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.R.string;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class ZopostSpinnerAdapter implements SpinnerAdapter {

	JSONArray data;
	Context ctx;
	String[] arrTextValue;

	public ZopostSpinnerAdapter(Context ctx, JSONArray data, String[] arrTextValue) {
		this.ctx = ctx;
		this.data = data;
		this.arrTextValue = arrTextValue;
	}

	@Override
	public int getCount() {
		return data.length();
	}

	@Override
	public Object getItem(int position) {
		try {
			return data.get(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public long getItemId(int position) {

		try {
			return data.getJSONObject(position).getInt(arrTextValue[0]);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public int getPosition(String idvalue) {
		for (int jx = 0; jx <= data.length() - 1; jx++) {
			try {
				if (data.getJSONObject(jx).get(arrTextValue[0]).toString().equalsIgnoreCase(idvalue))
					return jx;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;
	}

	@Override
	public int getItemViewType(int position) {
		return android.R.layout.simple_spinner_dropdown_item;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView v = new TextView(ctx.getApplicationContext());
		v.setTextColor(Color.BLACK);
		try {
			v.setText(((JSONObject) data.get(position)).getString(arrTextValue[1]));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return v;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return this.getView(position, convertView, parent);
	}

}
