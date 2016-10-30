package vn.ce.sale.fragment.bee;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.adapter.bee.BeeAutoAdapterSearchProduct;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.fragment.airtimemix.Fragment_Home_Air;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.SlidingTabLayout;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.PagerSlidingTabStrip;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.util.PagerSlidingTabStrip.IconTabProvider;
import vn.ce.sale.R;
import android.app.Dialog;
import android.app.ProgressDialog;
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

public class Bee_Fragment_Home_TabIcons extends ZopostFragment
		implements ICallBackActivityToFragment, OnPageChangeListener {

	DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void initParamsForFragment() {
	}

	PagerSlidingTabStrip mSlidingTabLayout;
	private int currentSelectedFragmentPosition;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// getActivity().setTitle("Con Ong Vàng");
		// ((Home) getActivity()).getSupportActionBar().show();
		Util.checkMenuHome = false;
//		pay();
		// sendDataToActivity(BundleData.createNew().putString("screen",
		// "aaa").data());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.bee_fragment_home_tabs_icon, container, false);
		Util.checkMenuHome = false;
		mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getChildFragmentManager());
		mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
		mViewPager.setAdapter(mDemoCollectionPagerAdapter);

		mSlidingTabLayout = (PagerSlidingTabStrip) rootView.findViewById(R.id.fragment_content_sliding_tabs);
		// mSlidingTabLayout.setDistributeEvenly(true);
		mDemoCollectionPagerAdapter.notifyDataSetChanged();

		mSlidingTabLayout.setViewPager(mViewPager);
		mSlidingTabLayout.setOnPageChangeListener(this);
		// mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.background_tab));
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
		((ICallBackActivityToFragment) mDemoCollectionPagerAdapter.getItem(currentSelectedFragmentPosition))
				.onParamsFromActivity(data);

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

	public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter
			implements PagerSlidingTabStrip.IconTabProvider {
		SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
		private int tabIcons[] = { R.drawable.aaaa, R.drawable.aaaa, R.drawable.aaaa, R.drawable.aaaa,
				R.drawable.aaaa };

		public DemoCollectionPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			if (registeredFragments.get(i) != null)
				return registeredFragments.get(i);
			if (i == 0) {
				Fragment fragment = new Bee_Fragment_Category_Product_List();
				Bundle args = new Bundle();
				// Our object is just an integer :-P
				args.putString("status", String.valueOf(i));
				fragment.setArguments(args);
				registeredFragments.put(i, fragment);
				return fragment;
			}
//			if (i == 1) {
//				Fragment fragment = new Bee_Fragment_Regional_Specialties();
//				Bundle args = new Bundle();
//				// Our object is just an integer :-P
//				args.putString("status", String.valueOf(i));
//				fragment.setArguments(args);
//				registeredFragments.put(i, fragment);
//				return fragment;
//			}
//			if (i == 2) {
//				Fragment fragment = new Bee_Fragment_Service_Flower();
//				Bundle args = new Bundle();
//				// Our object is just an integer :-P
//				args.putString("status", String.valueOf(i));
//				fragment.setArguments(args);
//				registeredFragments.put(i, fragment);
//				return fragment;
//			}
			if (i == 1) {
				Fragment fragment = new Bee_Fragment_Home_Swallow();
				Bundle args = new Bundle();
				// Our object is just an integer :-P
				args.putString("status", String.valueOf(i));
				fragment.setArguments(args);
				registeredFragments.put(i, fragment);
				return fragment;
			} else {
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
			return 3;
		}

		// @Override
		// public CharSequence getPageTitle(int position) {
		// // return "OBJECT " + (position + 1);
		// if (position == 0)
		// return "Market Place";
		// if (position == 1)
		// return "Vtop";
		// if (position == 2)
		// return "Chim Én";
		// if (position == 3)
		// return "VnPost";
		// return "";
		// }

		@Override
		public int getPageIconResId(int position) {
			// TODO Auto-generated method stub
			return tabIcons[position];
		}

	}

	private void pay() {
		final ProgressDialog pd = new ProgressDialog(getActivity());
		pd.setMessage("Đang tải dữ liệu");
		pd.show();
		ServiceManager.factoryData().getDataRaw("http://192.168.10.206:7963/bee/process/?data={\"ActionType\":\"PRODUCT\""
				+ ",\"UserName\":\"" + ShareMemManager.getInstance().readFromDB(getActivity(), "username")
				+ "\",\"Password\":\"" + ShareMemManager.getInstance().readFromDB(getActivity(), "password")
				+ "\",\"FromDate\":\"" + "20150115000000" + "\",\"ToDate\":\"" + "20151216235959" + "\"}", "",
				DataOrder.NETWORK_THEN_CACHE, new ICallBackUI() {

					@Override
					public void processRaw(String key, final int status, final String json) {
						// TODO Auto-generated method stub
						runOnUiThreadX(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (status == -1001) {
									pd.dismiss();
									return;
								}
								if (status == 200) {
									pd.dismiss();
									JSONObject object;
									ArrayList<JSONObject> lst = new ArrayList<JSONObject>();
									try {
										object = new JSONObject(json);
										JSONArray array = new JSONArray(object.getString("ResponseMessage"));
										lst = (ArrayList<JSONObject>) TransformDataManager
												.convertArrayToListJSON(array);
										ShareMemManager.getInstance().saveToDB(getActivity(), "ProductBee",
												lst.toString());
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

								}
							}

						});
					}

					@Override
					public void process(String key, int status, List<JSONObject> lst) {
						// TODO Auto-generated method stub

					}
				});
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

}