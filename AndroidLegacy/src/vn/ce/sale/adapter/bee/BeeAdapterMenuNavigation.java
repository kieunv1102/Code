package vn.ce.sale.adapter.bee;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kieu on 7/17/2015.
 */
@SuppressLint("ViewHolder")
public class BeeAdapterMenuNavigation extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<String> menuCate;

	public BeeAdapterMenuNavigation(Context context, List<String> menuCate) {
		this.mInflater = LayoutInflater.from(context);
		this.menuCate = menuCate;
	}

	@Override
	public int getCount() {
		return menuCate.size();
	}

	@Override
	public Object getItem(int position) {
		return menuCate.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressWarnings("unused")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			View view = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
			TextView text = (TextView)view.findViewById(android.R.id.text1);
			text.setText(menuCate.get(position));
			return view;
	}
}
