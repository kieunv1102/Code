package vn.ce.sale.fragment.vi21;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.adapter.CustomGrid;
import vn.ce.sale.adapter.CustomGridAndFilter;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.IData;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.DialogZopostFragment;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.TypeUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.ui.ZopostValue;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.TimeUtil;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.R.integer;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
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
import android.view.Menu;
import android.widget.AdapterView;
import android.view.MenuInflater;

public class Fragment_Order_Create_ListDlg extends DialogZopostFragment
		implements ICallBackActivityToFragment, ICallBackUI {

	public static final String ARG_OBJECT = "object";
	CustomGridAndFilter adapter = null;
	View rootView;
	View footerGrid;
	Button imbBack;
	ListView grid;
	ProgressBar form_pb;
	IData handleData;
	private Timer timer = new Timer();
	private final long DELAY = 300;

	public IData getHandleData() {
		return handleData;
	}

	public void setHandleData(IData handleData) {
		this.handleData = handleData;
	}

	int page = 0;
	private EditText edtOrderBarcode;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.vi21_layout_productdlg_list, container, false);
		grid = (ListView) rootView.findViewById(R.id.grid_Customer);
		form_pb = (ProgressBar) rootView.findViewById(R.id.form_pb);
		// setHasOptionsMenu(true);imbBack = (Button)
		// rootView.findViewById(R.id.imbBack);
		imbBack = (Button) rootView.findViewById(R.id.imbBack);
		imbBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		Util.saveActionUser(getActivity(), "SEARCH-SP-DON-SALE", (new Date()).getTime());
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

	public void triggerSearchProduct(String args) {
		if (args == null)
			return;
		if (adapter == null)
			return;

		if (args.length() < 3 && args.length() != 0)
			return;
		// TODO Auto-generated method stub
		// ((TextView)
		// rootView.findViewById(android.R.id.text1)).setText(args.getString(ARG_OBJECT));

		adapter.getFilter().filter(args);
	}

	@Override
	public void process(String key, int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void startLoadData() {

		/*
		 * ServiceManager.factoryData().getDataRaw( Util.SERVER_URL +
		 * "data={\"ActionType\":\"PRODUCT\"" + ",\"UserName\":\""
		 * +ShareMemManager.getInstance().readFromDB(getContext(), "username")
		 * +"\",\"Password\":\""
		 * +ShareMemManager.getInstance().readFromDB(getContext(), "password") +
		 * "\"}", "", DataOrder.CACHE_THEN_NETWORK, this);
		 */
		getDialog().setTitle(">>Thêm sản phẩm");
		processRaw("", 200, ShareMemManager.getInstance().readFromDB(getContext(), "product_store"));

		edtOrderBarcode = (EditText) rootView.findViewById(R.id.edtSearchProduct);
		edtOrderBarcode.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				if (timer != null)
					timer.cancel();
			}

			@Override
			public void afterTextChanged(final Editable s) {
				triggerSearchProduct(s.toString());

			}
		});

	}

	protected void refreshData() {

		// data={"ActionType":"login","UserName":"khotongbtcom","Password":"123467"}

		// nextToFragment(new Fragment_Home_List(), null);
		page = 0;
		// TODO Auto-generated method stub
		// ServiceManager.factoryData().getDataRaw(
		// Util.SERVER_URL +
		// "data={\"ActionType\":\"login\",\"UserName\":\"khotongbtcom\",\"Password\":\"123467\"}","",
		// DataOrder.NETWORK_THEN_CACHE, this);
		ServiceManager.factoryData()
				.getDataRaw(Util.SERVER_URL + "data={\"ActionType\":\"PRODUCT\"" + ",\"UserName\":\""
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

					form_pb.setVisibility(View.VISIBLE);
					return;
				}
				// long--->date

				// displayloading with json is percentage of loading data
				if (status == 200) {
					form_pb.setVisibility(View.GONE);
					grid.setVisibility(View.VISIBLE);

					JSONObject oJson;
					List<JSONObject> lstJsonObjectsZZ = new ArrayList<JSONObject>();

					try {
						// oJson = new JSONObject(json);
						// String arr = oJson.getString("ResponseMessage");
						lstJsonObjectsZZ = TransformDataManager.convertArrayToListJSON(new JSONArray(json));
						/*
						 * for(JSONObject o:lstJsonObjectsZZ) {
						 * lstJsonObjects.add(o); }
						 */
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// ShareMemManager.getInstance().saveToDB(getContext(),
					// "product", lstJsonObjects.toString());
					// List<JSONObject> lstJsonObjects =
					// TransformDataManager.getListJsonByXPath(g,"ResponseMessage");//''.convertStringToListJSON(json1);
					if (adapter == null) {
						// TODO Auto-generated method stu
						adapter = new CustomGridAndFilter(getActivity(), lstJsonObjectsZZ,
								R.layout.layout_customer_list_row3,
								new String[] { "ProductCode", "ProductName", "BarCode" });
						// TicketNumber_TradeType_Currency_TimeFrame
						adapter.bindFields(new BindDataUI[] { BindDataUI.createNew(R.id.customer_title, "ProductCode",
								TypeUI.COMPLEX, null, new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o, BindDataUI ui) {
										// TODO Auto-generated method stub
										try {
											return o.getString("ProductCode");
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											return e.toString();
										}
									}
								}), BindDataUI.createNew(R.id.customer_address, "ProductName", TypeUI.TEXT),
								BindDataUI.createNew(R.id.imgdetail, "ProductID", TypeUI.IMAGE_STATIC,
										new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										int position = Integer.valueOf(v.getTag().toString());
										handleData.sendData(adapter.getDataSource().get(position));
										Toast toast = Toast.makeText(getContext(), "Thêm thành công",
												Toast.LENGTH_SHORT);
										toast.setGravity(Gravity.TOP | Gravity.CENTER, 10, 10);
										toast.show();
									}
								}) });
						// uiCallBack.onParamsFromFragment(paramsToActivity);
						// grid=(ListView)rootView.findViewById(R.id.grid);

						grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								handleData.sendData(adapter.getDataSource().get(position));
								dismiss();
							}
						});
						grid.setAdapter(adapter);
					} else {
						adapter.setDataSource(lstJsonObjectsZZ);
						// if (page == 0)
						// else
						// adapter.getDataSource().addAll(lstJsonObjectsZZ);
						adapter.notifyDataSetChanged();
					}
				}
			}
		});

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		// ((IdtActivity)
		// getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		inflater.inflate(R.menu.main_action, menu);
		SearchManager searchManager = (SearchManager) (getActivity().getSystemService(Context.SEARCH_SERVICE));
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo((getActivity().getComponentName())));

		String barCode = searchView.getQuery().toString();
		// auto search on client
		searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) getActivity());
		setupMenuItem(menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			// save it here
			page = 0;
			startLoadData();
			return true;

		case R.id.action_upload:
			// nextToFragment(new Fragment_Customer_UploadOffline(), null);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void setupMenuItem(Menu menu) {

	}

	@Override
	public void onParamsFromActivity(Bundle data) {
		// TODO Auto-generated method stub

	}
}