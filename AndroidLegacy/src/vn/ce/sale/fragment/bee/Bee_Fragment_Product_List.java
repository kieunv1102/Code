package vn.ce.sale.fragment.bee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.Home;
import vn.ce.sale.LoginActivity;
import vn.ce.sale.adapter.CustomGrid;
import vn.ce.sale.adapter.CustomGridAndFilter;
import vn.ce.sale.adapter.bee.BeeAdapterProduct;
import vn.ce.sale.adapter.bee.DisplayProductGrid;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.fragment.FragmentBarCode;
import vn.ce.sale.fragment.Fragment_Customer_Edit;
import vn.ce.sale.fragment.Fragment_Home_List2;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.TypeUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.ui.ZopostValue;
import vn.ce.sale.util.CircleImageView;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.TimeUtil;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.R.integer;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.view.MenuInflater;

public class Bee_Fragment_Product_List extends ZopostFragment implements ICallBackActivityToFragment, OnClickListener {

	public static final String ARG_OBJECT = "object";
	RelativeLayout btnSaleView, btnNewView, btnManyOrderView;
	DisplayProductGrid adapter;
	CircleImageView viewUser;
	View rootView;
	View footerGrid;

	GridView grid;
	private ProgressDialog pd;
	int page = 1;
	String cate = "107";
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	private String mParam1;
	private String mParam2;

	public static Bee_Fragment_Product_List newInstance(String param1, String param2) {
		Bee_Fragment_Product_List fragment = new Bee_Fragment_Product_List();
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

		rootView = inflater.inflate(R.layout.bee_layout_customer_grid_product, container, false);
		grid = (GridView) rootView.findViewById(R.id.grvProductEcom);
		btnSaleView = (RelativeLayout) rootView.findViewById(R.id.btnSaleView);
		btnNewView = (RelativeLayout) rootView.findViewById(R.id.btnNewView);
		btnManyOrderView = (RelativeLayout) rootView.findViewById(R.id.btnManyOrderView);
		btnSaleView.setOnClickListener(this);
		btnNewView.setOnClickListener(this);
		btnManyOrderView.setOnClickListener(this);
		pd = new ProgressDialog(getActivity());
		if (Util.checkSearch == true) {
			Search();
			Util.checkSearch = false;
		} else {
			LoadProduct();
		}

		return rootView;
	}

	private void LoadProduct() {
		// TODO Auto-generated method stub
		pd.setMessage("Đang tải dữ liệu!");
		pd.show();
		ServiceManager.factoryData().getDataRaw(
				"http://192.168.10.214:9981/mobile/GetAllProductByCategory?categoryid=107&page=" + page
						+ "&size=20" + "&username=" + ShareMemManager.getInstance().readFromDB(getContext(), "username")
						+ "&password=" + ShareMemManager.getInstance().readFromDB(getContext(), "password"),
				"", DataOrder.NETWORK_THEN_CACHE, new ICallBackUI() {

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
								// displayloading with json is percentage of
								// loading data
								if (status == 200) {
									grid.setVisibility(View.VISIBLE);
									pd.dismiss();

									JSONObject oJson;
									List<JSONObject> lstJsonObjects = new ArrayList<JSONObject>();
									List<JSONObject> lstJsonPriceInfo = new ArrayList<JSONObject>();
									try {
										oJson = new JSONObject(json);
										String arr = oJson.getString("ResponseMessage");
										lstJsonObjects = TransformDataManager
												.convertArrayToListJSON(new JSONArray(arr));
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
									ShareMemManager.getInstance().saveToDB(getContext(), "productDetail",
											lstJsonPriceInfo.toString());
									adapter = new DisplayProductGrid(getActivity(), lstJsonPriceInfo);
									grid.setAdapter(adapter);
									adapter.notifyDataSetChanged();

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

	private void Search() {
		// TODO Auto-generated method stub

		pd.setMessage("Đang tải dữ liệu!");
		pd.show();
		ServiceManager.factoryData().getDataRaw(
				"http://192.168.10.214:9981/mobile/GetAllProduct?categoryid=107&username="
						+ ShareMemManager.getInstance().readFromDB(getContext(), "username") + "&password="
						+ ShareMemManager.getInstance().readFromDB(getContext(), "password"),
				"", DataOrder.NETWORK_THEN_CACHE, new ICallBackUI() {

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
								// displayloading with json is percentage of
								// loading data
								if (status == 200) {
									grid.setVisibility(View.VISIBLE);
									pd.dismiss();

									JSONObject oJson;
									List<JSONObject> lstJsonObjects = new ArrayList<JSONObject>();
									List<JSONObject> lstJsonPriceInfo = new ArrayList<JSONObject>();
									try {
										oJson = new JSONObject(json);
										String arr = oJson.getString("ResponseMessage");
										lstJsonObjects = TransformDataManager
												.convertArrayToListJSON(new JSONArray(arr));
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
									ShareMemManager.getInstance().saveToDB(getContext(), "productDetail",
											lstJsonPriceInfo.toString());
									adapter = new DisplayProductGrid(getActivity(), lstJsonPriceInfo);
									adapter.getFilter().filter(mParam1);
									grid.setAdapter(adapter);
									adapter.notifyDataSetChanged();

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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnSaleView:
			FragmentTransaction fmdetail1 = ((FragmentActivity) getActivity()).getSupportFragmentManager()
					.beginTransaction();
			fmdetail1.replace(R.id.container, new Bee_Fragment_Product_List().newInstance("a", "b"));
			fmdetail1.addToBackStack("tag");
			fmdetail1.commit();
			break;
		case R.id.btnNewView:
			FragmentTransaction fmdetail2 = ((FragmentActivity) getActivity()).getSupportFragmentManager()
					.beginTransaction();
			fmdetail2.replace(R.id.container, new Bee_Fragment_Product_List().newInstance("a", "b"));
			fmdetail2.addToBackStack("tag");
			fmdetail2.commit();
			break;
		case R.id.btnManyOrderView:
			FragmentTransaction fmdetail3 = ((FragmentActivity) getActivity()).getSupportFragmentManager()
					.beginTransaction();
			fmdetail3.replace(R.id.container, new Bee_Fragment_Product_List().newInstance("a", "b"));
			fmdetail3.addToBackStack("tag");
			fmdetail3.commit();
			break;

		default:
			break;
		}
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
	protected void startLoadData() {
	}

	public boolean onBackPressed() {
		// TODO Auto-generated method stub

		return false;

	}

	@Override
	public void onParamsFromActivity(Bundle data) {
		// TODO Auto-generated method stub

	}

}