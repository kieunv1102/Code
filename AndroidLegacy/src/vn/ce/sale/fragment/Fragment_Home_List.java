package vn.ce.sale.fragment;

import java.util.List;

import org.json.JSONObject;

import vn.ce.sale.HomeActivity1;
import vn.ce.sale.adapter.CustomGridAndFilter;
import vn.ce.sale.adapter.CustomGridDummy;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.view.Menu;
import android.widget.AdapterView;
import android.view.MenuInflater;

@SuppressLint("InlinedApi")
public class Fragment_Home_List extends ZopostFragment implements ICallBackActivityToFragment, ICallBackUI {
	// ListView grid;
	static String[] web = { "Bán hàng", // 0
			"Đặt hàng", // 1
			"Báo cáo", // 2
			"Thoát" };
	static int[] imageId = { R.drawable.v21_icon_sale, R.drawable.v21_icon_order, R.drawable.v21_icon_setting,
			R.drawable.v21_icon_exit };
	public static final String ARG_OBJECT = "object";
	CustomGridAndFilter adapter = null;
	View rootView;
	GridView grid;
	ProgressBar form_pb;
	int page;

	@SuppressLint("InlinedApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.vi21_homescreennew1, container, false);
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		grid = (GridView) rootView.findViewById(R.id.gridView1);
		// form_pb=(ProgressBar)rootView.findViewById(R.id.form_pb);
		setHasOptionsMenu(false);
		return rootView;
	}

	@Override
	protected void initParamsForFragment() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void fillDataSource(int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onParamsFromActivity(Bundle args) {
	}

	@Override
	public void process(String key, int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void startLoadData() {
		((TextView) rootView.findViewById(R.id.txtStatusUserName))
				.setText("Xin chào: " + ShareMemManager.getInstance().readFromDB(getContext(), "username"));
		// form_pb.setVisibility(View.GONE);
		// TODO Auto-generated method stub
		// ServiceManager.factoryData().getDataRaw(Util.SERVER_URL+"api/Location.aspx?type=get&",
		// "zip=1&token=abc12345&s="+String.valueOf(page), this);
		CustomGridDummy adapter = new CustomGridDummy(getActivity(), web, imageId);
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
				if (position == 3) {
					AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
					b.setMessage("Bạn có muốn thoát chương trình?");
					b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Fragment_Home_List.this.getActivity().finish();
						}
					});
					b.setNegativeButton("Không", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
					b.create().show();

				} else {
					sendDataToActivity(BundleData.createNew().putString("screen", getScreenByIndex(position)).data());
				}
			}

			private String getScreenByIndex(int position) {
				if (position == 0)
					return Util.SCREEN_ORDER;
				if (position == 1)
					return Util.SCREEN_ORDER_CREATE;
				if (position == 2) {
					// nextToFragmentAndKeepState(new Fragment_Report_List(),
					// null, true);
					sendDataToActivity(BundleData.createNew().putString("screen", Util.SCREEN_REPORT_LIST).data());
					return Util.SCREEN_REPORT_LIST;
				}

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
	public void processRaw(String key, final int status, final String json) {

	}
	// @Override
	// public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	// {
	// menu.clear();
	// inflater.inflate(R.menu.main_action, menu);
	// /*
	// //((IdtActivity)
	// getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
	// SearchManager searchManager =
	// (SearchManager)(getActivity().getSystemService(Context.SEARCH_SERVICE));
	// SearchView searchView =
	// (SearchView) menu.findItem(R.id.action_search).getActionView();
	// searchView.setSearchableInfo(
	// searchManager.getSearchableInfo((getActivity().getComponentName())));
	//
	// //auto search on client
	// searchView.setOnQueryTextListener((SearchView.OnQueryTextListener)getActivity());
	// */
	// setupMenuItem(menu);
	// super.onCreateOptionsMenu(menu, inflater);
	// }
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item)
	// {
	// switch (item.getItemId())
	// {
	// case R.id.action_refresh:
	// // save it here
	// page=0;
	// startLoadData();
	// return true;
	// case R.id.action_add:
	// //nextToFragment(new Fragment_Location_Edit(), null);
	// return true;
	// case R.id.action_upload:
	// //nextToFragment(new Fragment_Location_UploadOffline(), null);
	// return true;
	// default:
	// return super.onOptionsItemSelected(item);
	// }
	// //Util.SCREEN_AUDIO
	// }
	// public void setupMenuItem(Menu menu)
	// {
	//
	// menu.findItem(R.id.action_add).setVisible(false);
	// menu.findItem(R.id.action_search).setVisible(false);
	// menu.findItem(R.id.action_save).setVisible(false);
	// menu.findItem(R.id.action_edit).setVisible(false);
	// menu.findItem(R.id.action_refresh).setVisible(false);
	// menu.findItem(R.id.action_list).setVisible(false);
	// menu.findItem(R.id.action_upload).setVisible(false);
	// menu.findItem(R.id.action_saveoffline).setVisible(false);
	// menu.findItem(R.id.action_exit).setVisible(false);
	//
	// }

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

}