package vn.ce.sale.fragment.bee;

import java.util.List;

import org.json.JSONObject;

import vn.ce.sale.Home;
import vn.ce.sale.fragment.airtimemix.Fragment_Home_Air;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.SlidingTabLayout;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.util.PagerSlidingTabStrip.IconTabProvider;
import vn.ce.sale.R;
import android.app.Dialog;
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

public class Bee_Fragment_Home_TabViewer extends ZopostFragment
		implements ICallBackActivityToFragment, OnPageChangeListener {

	DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void initParamsForFragment() {
	}

	SlidingTabLayout mSlidingTabLayout;
	private int currentSelectedFragmentPosition;

	public static Bee_Fragment_Home_TabViewer newInstance() {
		Bee_Fragment_Home_TabViewer fragment = new Bee_Fragment_Home_TabViewer();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		((Home) getActivity()).getSupportActionBar().show();
		// getActivity().setTitle("Con Ong Vàng");
		// ((Home) getActivity()).getSupportActionBar().show();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.bee_fragment_home_tabs_viewer, container, false);
		mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getChildFragmentManager());
		mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
		mViewPager.setAdapter(mDemoCollectionPagerAdapter);

		mSlidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.fragment_content_sliding_tabs);
		mSlidingTabLayout.setDistributeEvenly(true);
		mDemoCollectionPagerAdapter.notifyDataSetChanged();
		mSlidingTabLayout.setViewPager(mViewPager);
		mSlidingTabLayout.setOnPageChangeListener(this);
		mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.background_tab));
		if (Util.checkSwallowPager == true) {
			mViewPager.setCurrentItem(3, true);
			Util.checkSwallowPager = false;
		}
//		if (Util.checkViewPager == true) {
//			mViewPager.setCurrentItem(1, true);
//			Util.checkViewPager = false;
//		}
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
//		((ICallBackActivityToFragment) mDemoCollectionPagerAdapter.getItem(currentSelectedFragmentPosition))
//				.onParamsFromActivity(data);
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
//				ShareMemManager.getInstance().saveToDB(getActivity(), "pageview", "a");
				sendDataToActivity(BundleData.createNew().putString("screen", "aaa").data());
				Fragment fragment = new Bee_Fragment_Category_Product_List();
				Bundle args = new Bundle();
				// Our object is just an integer :-P
				args.putString("status", String.valueOf(i));
				fragment.setArguments(args);
				registeredFragments.put(i, fragment);
				return fragment;
			}
			if (i == 1) {
				ShareMemManager.getInstance().deleteFromDB(getActivity(), "pageview");
				sendDataToActivity(BundleData.createNew().putString("screen", "aaa").data());
				Fragment fragment = new Bee_Fragment_Regional_Specialties();
				Bundle args = new Bundle();
				// Our object is just an integer :-P
				args.putString("status", String.valueOf(i));
				fragment.setArguments(args);
				registeredFragments.put(i, fragment);
				return fragment;
			}
			if (i == 2) {
				ShareMemManager.getInstance().deleteFromDB(getActivity(), "pageview");
				sendDataToActivity(BundleData.createNew().putString("screen", "aaa").data());
				Fragment fragment = new Bee_Fragment_Service_Flower();
				Bundle args = new Bundle();
				// Our object is just an integer :-P
				args.putString("status", String.valueOf(i));
				fragment.setArguments(args);
				registeredFragments.put(i, fragment);
				return fragment;
			}
			if (i == 3) {
//				Util.checkMenuHome2=true;
//				ShareMemManager.getInstance().deleteFromDB(getActivity(), "pageview");
//				sendDataToActivity(BundleData.createNew().putString("screen", "aaa").data());
				Fragment fragment = new Bee_Fragment_Home_Swallow();
				Bundle args = new Bundle();
				// Our object is just an integer :-P
				args.putString("status", String.valueOf(i));
				fragment.setArguments(args);
				registeredFragments.put(i, fragment);
				
				return fragment;
			} else {
//				ShareMemManager.getInstance().deleteFromDB(getActivity(), "pageview");
//				sendDataToActivity(BundleData.createNew().putString("screen", "ccc").data());
				Fragment fragment = new Bee_Fragment_Home_Vnpost();
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
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// return "OBJECT " + (position + 1);
			if (position == 0)
				return "Market Place";
			if (position == 1)
				return "Đặc Sản Vùng Miền";
			if (position == 2)
				return "Điện Hoa";
			if (position == 3)
				return "Chim Én";
			if (position == 4)
				return "VnPost";
			return "";
		}

	}

	// @Override
	// public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	// menu.clear();
	// inflater.inflate(R.menu.menu_home_tab, menu);
	// super.onCreateOptionsMenu(menu, inflater);
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case R.id.userHomeTab:
	// final Dialog dialog = new Dialog(getActivity());
	// dialog.setCancelable(false);
	// dialog.setContentView(R.layout.bee_custom_dialog_home_tab);
	// dialog.setTitle("Title...");
	// dialog.getWindow().getAttributes().windowAnimations =
	// R.style.dialog_animation_user_top;
	// dialog.show();
	// return true;
	//
	// default:
	// return super.onOptionsItemSelected(item);
	// }
	// }
	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

}