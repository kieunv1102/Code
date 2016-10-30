package vn.ce.sale.adapter.bee;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import vn.ce.sale.R;

public class BeeHomeNavDrawerListAdapter extends BaseAdapter {

	Context mContext;
	LayoutInflater mLayoutInflater;
	List<String> title;
	
	Integer[] res={

	};

	public BeeHomeNavDrawerListAdapter(Context context,List<String> t) {
		mContext = context;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		title = t;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return title.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return title.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View itemView = mLayoutInflater.inflate(R.layout.bee_home_item_list_menu_sliding, arg2, false);
		ViewHolder viewHolder  =new ViewHolder();
		viewHolder.imgIcon = (ImageView)itemView.findViewById(R.id.iconNavi);
		viewHolder.txtTitle = (TextView)itemView.findViewById(R.id.txtTitleNavi);
		viewHolder.txtTitle.setText(title.get(arg0));
		return itemView;
	}


	static class ViewHolder {
		public ImageView imgIcon;
		public TextView txtTitle;


	}

}