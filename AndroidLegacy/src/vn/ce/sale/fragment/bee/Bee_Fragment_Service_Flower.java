package vn.ce.sale.fragment.bee;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.adapter.bee.DisplayProductGrid;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.CircleImageView;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Bee_Fragment_Service_Flower extends ZopostFragment
		implements ICallBackActivityToFragment, ICallBackUI, OnScrollListener {

	public static final String ARG_OBJECT = "object";
	DisplayProductGrid adapter;
	CircleImageView viewUser;
	View rootView;
	View footerGrid;

	GridView grid;
	int page=1;
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	private String mParam1;
	private String mParam2;
	RelativeLayout rllprogressbar;
	int firstVisibleItem, visibleItemCount, totalItemCount;
	public static Bee_Fragment_Service_Flower newInstance(String param1, String param2) {
		Bee_Fragment_Service_Flower fragment = new Bee_Fragment_Service_Flower();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.bee_fragment_service_flower, container, false);
		grid = (GridView) rootView.findViewById(R.id.grvProductEcom);
		rllprogressbar = (RelativeLayout)rootView.findViewById(R.id.rllprogressbar);
		grid.setOnScrollListener(this);
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
	public void process(String key, int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void startLoadData() {
		rllprogressbar.setVisibility(View.VISIBLE);
		ServiceManager.factoryData()
				.getDataRaw("http://192.168.10.218:9981/mobile/GetAllProductByCategory?categoryid=107" + "&page=" + page
						+ "&size=20" + "&username" + ShareMemManager.getInstance().readFromDB(getContext(), "username")
						+ "&password" + ShareMemManager.getInstance().readFromDB(getContext(), "password"), "",
				DataOrder.NETWORK_THEN_CACHE, this);

	}


	@Override
	public void processRaw(String key, final int status, final String json) {

		runOnUiThreadX(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (status == -1001) {
//					pd.dismiss();
					return;
				}
				// displayloading with json is percentage of loading data
				if (status == 200) {
					grid.setVisibility(View.VISIBLE);
//					pd.dismiss();

					JSONObject oJson;
					List<JSONObject> lstJsonObjects = new ArrayList<JSONObject>();
					List<JSONObject> lstJsonPriceInfo = new ArrayList<JSONObject>();
					try {
						oJson = new JSONObject(json);
						String arr = oJson.getString("ResponseMessage");
						lstJsonObjects = TransformDataManager.convertArrayToListJSON(new JSONArray(arr));
						for (int i = 0; i < lstJsonObjects.size(); i++) {

							if (lstJsonObjects.get(i).getString("PriceInfo").equals("[]")) {

							} else {
								lstJsonPriceInfo.add(lstJsonObjects.get(i));
							}
						}

					} catch (JSONException e1) {
						// TODO 11223-generated catch block
						e1.printStackTrace();
					}
					ShareMemManager.getInstance().saveToDB(getContext(), "productDetail", lstJsonPriceInfo.toString());
					adapter = new DisplayProductGrid(getActivity(), lstJsonPriceInfo);
					// adapter.getFilter().filter(mParam1);
					grid.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					rllprogressbar.setVisibility(View.GONE);
				}
			}
		});

	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub

		return false;

	}

	@Override
	public void onParamsFromActivity(Bundle data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		page++;
		if (firstVisibleItem + visibleItemCount >= totalItemCount && scrollState == SCROLL_STATE_IDLE) {
			Toast.makeText(getActivity(), "Load more", Toast.LENGTH_SHORT).show();
			startLoadData();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		this.firstVisibleItem = firstVisibleItem;
		this.visibleItemCount = visibleItemCount;
		this.totalItemCount = totalItemCount;
	}

}