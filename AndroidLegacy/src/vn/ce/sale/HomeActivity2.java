package vn.ce.sale;

import vn.ce.sale.adapter.CustomGridAndFilter;
import vn.ce.sale.adapter.CustomGridDummy;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.app.Activity;
import android.view.View;
import android.widget.GridView;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.view.Menu;
import android.widget.AdapterView;

public class HomeActivity2 extends Activity {
	// ListView grid;
	static String[] web = { "Bán hàng", // 0
			"Đặt hàng", // 1
			"Thiết lập", // 2
			"Thoát" };
	static int[] imageId = { R.drawable.d_order, R.drawable.d_kiemhang, R.drawable.d_setting, R.drawable.d_setting };
	public static final String ARG_OBJECT = "object";
	CustomGridAndFilter adapter = null;
	View rootView;
	GridView grid;
	ProgressBar form_pb;
	int page;

	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.vi21_homescreennew1);
		grid = (GridView) findViewById(R.id.gridView1);
		startLoadData();
	}

	private void startLoadData() {
		// form_pb.setVisibility(View.GONE);
		// TODO Auto-generated method stub
		// ServiceManager.factoryData().getDataRaw(Util.SERVER_URL+"api/Location.aspx?type=get&",
		// "zip=1&token=abc12345&s="+String.valueOf(page), this);
		CustomGridDummy adapter = new CustomGridDummy(getApplicationContext(), web, imageId);
		// listview=(ListView)rootView.findViewById(R.id.grid);
		grid.setAdapter(adapter);// okie
		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Toast.makeText(getActivity(), "You Clicked at " + position,
				// Toast.LENGTH_SHORT).show();
				// startActivity(new
				// Intent(getActivity(),DetailActivity.class));
				// nextToFragment(new Fragment_Customer_List(), null);
				// sendDataToActivity(BundleData.createNew().putString("screen",
				// getScreenByIndex(position)).data());
			}

			private String getScreenByIndex(int position) {
				if (position == 0)
					return Util.SCREEN_ORDER;
				if (position == 1)
					return Util.SCREEN_ORDER_CREATE;
				if (position == 2)
					return Util.SCREEN_SETTING;
				if (position == 3)
					return "";
				return String.valueOf(position);
			}
		});
		grid.setVisibility(View.VISIBLE);
	}

	protected void refreshData() {
		page = 0;
		// TODO Auto-generated method stub
		// ServiceManager.factoryData().getDataRaw(Util.SERVER_URL+"api/Location.aspx?type=get&",
		// "zip=1&token=abc12345&s="+String.valueOf(page),DataOrder.ONLY_NETWORK,
		// this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			// save it here
			page = 0;
			startLoadData();
			return true;
		case R.id.action_add:
			// nextToFragment(new Fragment_Location_Edit(), null);
			return true;
		case R.id.action_upload:
			// nextToFragment(new Fragment_Location_UploadOffline(), null);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		// Util.SCREEN_AUDIO
	}

	public void setupMenuItem(Menu menu) {

		menu.findItem(R.id.action_add).setVisible(false);
		menu.findItem(R.id.action_search).setVisible(false);
		menu.findItem(R.id.action_save).setVisible(false);
		menu.findItem(R.id.action_edit).setVisible(false);
		menu.findItem(R.id.action_refresh).setVisible(false);
		menu.findItem(R.id.action_list).setVisible(false);
		menu.findItem(R.id.action_upload).setVisible(false);
		menu.findItem(R.id.action_saveoffline).setVisible(false);
		menu.findItem(R.id.action_exit).setVisible(false);

	}
}