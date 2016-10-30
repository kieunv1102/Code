package vn.ce.sale.fragment.vi21;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
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
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.view.Menu;
import android.widget.AdapterView;
import android.view.MenuInflater;

@SuppressLint("InlinedApi")
public class Fragment_Report_List extends ZopostFragment implements ICallBackActivityToFragment, ICallBackUI {
	// ListView grid;
	static String[] web = { "Báo cáo tồn kho", // 0
			"Báo cáo bán hàng" };
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

		// lay ra tu db
		Util.saveActionUser(getActivity(), "VÀO-BÁO-CÁO", (new Date()).getTime());

		String tSaleProduct = String.valueOf((new Date().getTime()));
		ShareMemManager.getInstance().saveToDB(getActivity(), "tReportList", tSaleProduct);
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
		// lưu 1 cái
		// lưu tiếp 1 hoặc nhiều cái khác
		// lấy ra tất cả
		// gửi lên server

		// s=lay ra tu db
		// cong vao cai moi a: s=s+a
		// save vao db:a
		// lay ra s

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

				sendDataToActivity(BundleData.createNew().putString("screen", getScreenByIndex(position)).data());
			}

			private String getScreenByIndex(int position) {
				if (position == 0) {
					// nextToFragmentAndKeepState(new
					// Fragment_Report_Inventory_List(), null, true);
					Util.checkReport = true;
					sendDataToActivity(
							BundleData.createNew().putString("screen", Util.SCREEN_REPORT_INVENTORY_LIST).data());
					return Util.SCREEN_REPORT_INVENTORY_LIST;
				}
				if (position == 1) {
				}
				Util.checkReport = true;
				sendDataToActivity(BundleData.createNew().putString("screen", Util.SCREEN_REPORT_SALE_LIST).data());
				return Util.SCREEN_REPORT_SALE_LIST;
				// Toast.makeText(getActivity(), " hihi :D :D :D hihi ",
				// Toast.LENGTH_SHORT).show();
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

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

}