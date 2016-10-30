package vn.ce.sale.fragment;

import java.util.List;

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
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class FragmentContainTabIdt extends ZopostFragment implements ICallBackActivityToFragment {
	private ListView grid;
	FragmentTabHost mTabHost;

	// private View rootView ;
	// start
	@Override
	protected void initParamsForFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_tabs, container, false);

		mTabHost = (FragmentTabHost) rootView.findViewById(android.R.id.tabhost);
		mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

		mTabHost.addTab(mTabHost.newTabSpec("open").setIndicator("Open"), FragmentHomeTabIdt.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("activated").setIndicator("Activated"), FragmentHomeTabIdt.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("close").setIndicator("Closed"), FragmentHomeTabIdt.class, null);

		return rootView;
	}

	@Override
	protected void startLoadData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void fillDataSource(int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

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