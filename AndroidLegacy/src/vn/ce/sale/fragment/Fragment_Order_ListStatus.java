package vn.ce.sale.fragment;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.adapter.CustomGrid;
import vn.ce.sale.data.ICallBack;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.TypeUI;
import vn.ce.sale.ui.ZopostActivity;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.R;
import vn.ce.sale.R.layout;
import android.R.id;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Fragment_Order_ListStatus extends ZopostFragment implements ICallBackActivityToFragment {
	private ListView grid;

	// private View rootView ;
	// start
	@Override
	protected void initParamsForFragment() {
		Bundle bund = new Bundle();
		bund.putInt("number", 0);
		bund.putString("title", "Home Page IDT");
		paramsToActivity = bund;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// setup ui
		rootView = inflater.inflate(R.layout.fragment_home, container, false);

		getParams();

		setupUI();

		// start load data in other thread

		return rootView;// return super.onCreateView(inflater, container,
						// savedInstanceState);
	}

	protected void fillDataSource(int status, final List<JSONObject> lst) {
		// new DownloadDataFromURL().execute("reporteds.aspx",
		// "typeid=2&status=done&memberid="+MemberUtil.memberid);
	}

	@Override
	protected void startLoadData() {
		// TODO Auto-generated method stub
		// loadDataSource("catalog");
	}

	@Override
	public void onParamsFromActivity(Bundle data) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}
}