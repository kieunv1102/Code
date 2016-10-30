package vn.ce.sale.fragment.bee;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import vn.ce.sale.adapter.bee.BeeAdapterHorizontalListView;
import vn.ce.sale.adapter.bee.PagerAdapterCustomer;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.CircleImageView;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.HorizontalListView;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.view.MenuInflater;

public class Bee_Fragment_Order_Product_Details extends ZopostFragment
		implements ICallBackActivityToFragment, ICallBackUI {

	private static final String ARG_PARAM1 = "param1";
	private String posBanner;
	@SuppressWarnings("unused")
	private String posCategory;

	public static Bee_Fragment_Order_Product_Details newInstance(String aa) {
		Bee_Fragment_Order_Product_Details fragment = new Bee_Fragment_Order_Product_Details();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, aa);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		getActivity().setTitle("Chi Tiết Đơn Hàng");
		if (getArguments() != null) {
			posBanner = getArguments().getString(ARG_PARAM1);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.bee_layout_customer_list_detail, container, false);

		setHasOptionsMenu(true);
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
		if (args == null)
			return;
		// TODO Auto-generated method stub
		// ((TextView)
		// rootView.findViewById(android.R.id.text1)).setText(args.getString(ARG_OBJECT));
	}

	@Override
	public void process(String key, int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void startLoadData() {
		try {
		String arr = ShareMemManager.getInstance().readFromDB(getActivity(), "dataOrderProduct");
		List<JSONObject> lstJsonObjects = TransformDataManager.convertArrayToListJSON(new JSONArray(arr));
		for(JSONObject obj:lstJsonObjects){
//			if (obj.getString("")) {
//				
//			}
		}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void refreshData() {

		ServiceManager.factoryData()
				.getDataRaw(Util.SERVER_URL + "data={\"ActionType\":\"STORE\"" + ",\"UserName\":\""
						+ ShareMemManager.getInstance().readFromDB(getContext(), "username") + "\",\"Password\":\""
						+ ShareMemManager.getInstance().readFromDB(getContext(), "password") + "\"}", "",
				DataOrder.NETWORK_THEN_CACHE, this);
	}

	@Override
	public void processRaw(String key, final int status, final String json) {

		runOnUiThreadX(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (status == 1001) {
					return;
				}
				// displayloading with json is percentage of loading data
				if (status == 200) {

				}
			}
		});

	}

	// @Override
	// public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	// menu.clear();
	// // ((IdtActivity)
	// //
	// getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
	// inflater.inflate(R.menu.bee_menu_product, menu);
	// SearchManager searchManager = (SearchManager)
	// (getActivity().getSystemService(Context.SEARCH_SERVICE));
	// SearchView searchView = (SearchView)
	// menu.findItem(R.id.actionSearchProductBee).getActionView();
	// searchView.setSearchableInfo(searchManager.getSearchableInfo((getActivity().getComponentName())));
	//
	// String barCode = searchView.getQuery().toString();
	// // auto search on client
	// searchView.setOnQueryTextListener((SearchView.OnQueryTextListener)
	// getActivity());
	// super.onCreateOptionsMenu(menu, inflater);
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case R.id.actionRefreshBee:
	// // save it here
	// page = 0;
	// startLoadData();
	// return true;
	// case R.id.actionSearchProductBee:
	//
	// return true;
	// default:
	// return super.onOptionsItemSelected(item);
	// }
	// }

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
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

}