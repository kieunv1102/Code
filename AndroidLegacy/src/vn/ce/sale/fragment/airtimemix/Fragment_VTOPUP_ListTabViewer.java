package vn.ce.sale.fragment.airtimemix;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import vn.ce.sale.fragment.vi21.FragmentOrderProduct;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.SlidingTabLayout;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_VTOPUP_ListTabViewer extends ZopostFragment
		implements ICallBackActivityToFragment, OnPageChangeListener {

	DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
	ViewPager mViewPager;
	@Override
	protected void initParamsForFragment() {
	}

	SlidingTabLayout mSlidingTabLayout;
	private int currentSelectedFragmentPosition;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.app_fragment_order_list_tabs_viewer, container, false);
		mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getChildFragmentManager());
		mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
		mViewPager.setAdapter(mDemoCollectionPagerAdapter);

		mSlidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.fragment_content_sliding_tabs);
		mSlidingTabLayout.setDistributeEvenly(true);
		mDemoCollectionPagerAdapter.notifyDataSetChanged();

		mSlidingTabLayout.setViewPager(mViewPager);
		mSlidingTabLayout.setOnPageChangeListener(this);
		mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.background_tab));
		setHasOptionsMenu(true);
		return rootView;

	}

	void setUpTabColor() {
	}

	@Override
	protected void startLoadData() {
	}

	@Override
	protected void fillDataSource(int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onParamsFromActivity(Bundle data) {

		// TODO Auto-generated method stub
		((ICallBackActivityToFragment) mDemoCollectionPagerAdapter.getItem(currentSelectedFragmentPosition))
				.onParamsFromActivity(data);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add:
			nextToFragmentAndKeepState(new FragmentOrderProduct(), null, true);
			return true;
		case R.id.action_refresh:
			// nextToFragment(new Fragment_Customer_List(), null);
			((ZopostFragment) mDemoCollectionPagerAdapter.getItem(currentSelectedFragmentPosition)).reloadData();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.main_action, menu);
		setupMenuItem(menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	public void setupMenuItem(Menu menu) {
		menu.findItem(R.id.action_add).setVisible(true);
		menu.findItem(R.id.action_search).setVisible(true);
		menu.findItem(R.id.action_save).setVisible(false);
		menu.findItem(R.id.action_edit).setVisible(false);
		menu.findItem(R.id.action_refresh).setVisible(true);
		menu.findItem(R.id.action_list).setVisible(false);
		menu.findItem(R.id.action_upload).setVisible(false);
		menu.findItem(R.id.action_exit).setVisible(false);
		menu.findItem(R.id.action_saveoffline).setVisible(false);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		currentSelectedFragmentPosition = arg0;
		// savePageIndexStateCurrent("page-viewer", arg0);
		// mSlidingTabLayout.setc
	}

	public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
		SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

		public DemoCollectionPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			if (registeredFragments.get(i) != null)
				return registeredFragments.get(i);
			if (i == 0) {
				Fragment fragment = new FragmentVtopup();
				Bundle args = new Bundle();
				// Our object is just an integer :-P
				args.putString("status", String.valueOf(i));
				fragment.setArguments(args);
				registeredFragments.put(i, fragment);
				return fragment;
			} else {

				Fragment fragment = new FragmentVtopupHistory();
				Bundle args = new Bundle();
				// Our object is just an integer :-P
				args.putString("status", String.valueOf(i));
				fragment.setArguments(args);
				registeredFragments.put(i, fragment);
				return fragment;
			}
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// return "OBJECT " + (position + 1);
			if (position == 0)
				return "Nạp tiền";
			if (position == 1)
				return "Lịch sử";
			// if(position==2) return "XỬ LÝ KO OK";
			return "OFFLINE";
			/**/
		}

	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

}