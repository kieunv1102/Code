package vn.ce.sale.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.adapter.CustomGridAndFilter;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.TypeUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.ui.ZopostValue;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;

public class Fragment_Product_List extends ZopostFragment implements ICallBackActivityToFragment, ICallBackUI {

	public static final String ARG_OBJECT = "object";
	CustomGridAndFilter adapter = null;
	View rootView;
	View footerGrid;
	private ProgressDialog pd;
	ListView grid;
	private TextView txtTotalCount, txtTotalPromotion, txtTotalMoney;
	private TextView txtNotification;
	int page;
	List<JSONObject> lstJsonObjects = new ArrayList<JSONObject>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.vi21_layout_customer_list2, container, false);
		grid = (ListView) rootView.findViewById(R.id.grid_Customer);
		txtTotalCount = (TextView) rootView.findViewById(R.id.txtTotalCount);
		txtTotalPromotion = (TextView) rootView.findViewById(R.id.txtTotalPromotion);
		txtTotalMoney = (TextView) rootView.findViewById(R.id.txtTotalMoney);
		txtNotification = (TextView) rootView.findViewById(R.id.txtNotification);
		pd = new ProgressDialog(getActivity());

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
		if (adapter == null) {
			return;
		} else
			adapter.getFilter().filter(args.getString(ARG_OBJECT));
	}

	@Override
	public void process(String key, int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void startLoadData() {

		// TODO Auto-generated method stub
		// ServiceManager.factoryData().getDataRaw(
		// Util.SERVER_URL + "/getGeneralProduct.aspx?type=get&memberid=" +
		// Util.memberid + "&",
		// "zip=1&token=abc12345&p=" + String.valueOf(page),
		// DataOrder.CACHE_THEN_NETWORK, this);
		pd.setMessage("Đang tải dữ liệu!");
		pd.show();
		ServiceManager.factoryData()
				.getDataRaw(Util.SERVER_URL + "data={\"ActionType\":\"STORE\"" + ",\"UserName\":\""
						+ ShareMemManager.getInstance().readFromDB(getContext(), "username") + "\",\"Password\":\""
						+ ShareMemManager.getInstance().readFromDB(getContext(), "password") + "\"}", "",
				DataOrder.NETWORK_THEN_CACHE, this);

		// ServiceManager.factoryData()
		// .getDataRaw(Util.SERVER_URL + "data={\"ActionType\":\"REPORT_SALE2\""
		// + ",\"UserName\":\""
		// + ShareMemManager.getInstance().readFromDB(getContext(), "username")
		// + "\",\"Password\":\""
		// + ShareMemManager.getInstance().readFromDB(getContext(), "password")
		// + "\",\"Page\":" + 0
		// + ",\"Size\":" + 10 + ",\"FromDate\":\"" + "20151201" + "000000" +
		// "\",\"ToDate\":\""
		// + "20151222" + "235959" + "\" }", "", DataOrder.NETWORK_THEN_CACHE,
		// this);

		// CalculateTotal(totalCount,totalPromotion);
	}

	@SuppressWarnings("unused")
	private void CalculateTotal() {
		// TODO Auto-generated method stub
		int totalCount = 0;
		int totalPromotion = 0;
		int totalMoney = 0;
		for (int jx = 0; jx <= lstJsonObjects.size() - 1; jx++) {
			try {
				totalCount = totalCount + lstJsonObjects.get(jx).getInt("Quantity");
				totalPromotion = totalPromotion + lstJsonObjects.get(jx).getInt("Promotion");
				Log.e("sl", String.valueOf(lstJsonObjects.get(jx).getInt("Quantity")));
				Log.e("gia", String.valueOf(lstJsonObjects.get(jx).getInt("Price")));
				int tien = lstJsonObjects.get(jx).getInt("Quantity") * lstJsonObjects.get(jx).getInt("Price");
				totalMoney = totalMoney + tien;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		txtTotalCount.setText(String.valueOf(totalCount));
		txtTotalPromotion.setText(String.valueOf(totalPromotion));
		txtTotalMoney.setText(String.valueOf(FormatUtil.formatCurrency((double) totalMoney)));

	}

	protected void refreshData() {
		// pd.setMessage("Đang tải dữ liệu!");
		// pd.show();
		// data={"ActionType":"login","UserName":"khotongbtcom","Password":"123467"}

		// nextToFragment(new Fragment_Home_List(), null);
		page = 0;
		// TODO Auto-generated method stub
		// ServiceManager.factoryData().getDataRaw(
		// Util.SERVER_URL +
		// "data={\"ActionType\":\"login\",\"UserName\":\"khotongbtcom\",\"Password\":\"123467\"}","",
		// DataOrder.NETWORK_THEN_CACHE, this);
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
					pd.dismiss();

					return;
				}

				// // displayloading with json is percentage of loading data
				if (status == 200) {
					pd.dismiss();
					grid.setVisibility(View.VISIBLE);

					JSONObject oJson;

					try {
						oJson = new JSONObject(json);
						String arr = oJson.getString("ResponseMessage");
						lstJsonObjects = TransformDataManager.convertArrayToListJSON(new JSONArray(arr));
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					ShareMemManager.getInstance().saveToDB(getContext(), "product", json.toString());
					// List<JSONObject> lstJsonObjects =
					// TransformDataManager.getListJsonByXPath(g,"ResponseMessage");//''.convertStringToListJSON(json1);
					if (adapter == null) {
						// TODO Auto-generated method stu
						adapter = new CustomGridAndFilter(getActivity(), lstJsonObjects,
								R.layout.layout_customer_list_product,
								new String[] { "ProductCode", "ProductName", "BarCode" });
						// TicketNumber_TradeType_Currency_TimeFrame
						adapter.bindFields(new BindDataUI[] { BindDataUI.createNew(R.id.customer_title, "ProductCode",
								TypeUI.COMPLEX, null, new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o, BindDataUI ui) {
										// TODO Auto-generated method stub
										try {
											return o.getString("ProductCode") + "." + o.getString("ProductName");
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											return e.toString();
										}
									}
								}), BindDataUI.createNew(R.id.customer_address, "ProductName", TypeUI.COMPLEX, null,
										new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o, BindDataUI ui) {
										// TODO Auto-generated method stub
										try {
											Double price = Double.parseDouble(o.getString("Price"));
											return "Khuyến mại:" + o.getString("Promotion");
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											return e.toString();
										}
									}
								})

								, BindDataUI.createNew(R.id.customer_kho, "ProductName", TypeUI.COMPLEX, null,
										new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o, BindDataUI ui) {
										// TODO Auto-generated method stub
										try {
											Double price = Double.parseDouble(o.getString("Price"));
											return "Kho:" + o.getString("Quantity");
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											return e.toString();
										}
									}
								}), BindDataUI.createNew(R.id.customer_gia, "ProductName", TypeUI.COMPLEX, null,
										new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o, BindDataUI ui) {
										// TODO Auto-generated method stub
										try {
											Double price = Double.parseDouble(o.getString("Price"));
											return "Giá tiền: " + FormatUtil.formatCurrency(price);
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											return e.toString();
										}
									}
								}) });
						// uiCallBack.onParamsFromFragment(paramsToActivity);
						// grid=(ListView)rootView.findViewById(R.id.grid);
						// footerGrid =
						// getActivity().getLayoutInflater().inflate(R.layout.fragment_search_grid_loadmore,
						// null);
						// grid.addFooterView(footerGrid);
						// ((Button) footerGrid.findViewById(R.id.grid_button))
						// .setOnClickListener(new View.OnClickListener() {
						// @Override
						// public void onClick(View v) {
						// // TODO Auto-generated method stub
						// page++;
						// startLoadData();
						// }
						// });
						// grid.setOnItemClickListener(new
						// AdapterView.OnItemClickListener() {
						// @Override
						// public void onItemClick(AdapterView<?> parent, View
						// view, int position, long id) {
						// // Toast.makeText(getActivity(), "You Clicked at
						// // " +(position), Toast.LENGTH_SHORT).show();
						// // LinearLayout vwParentRow =
						// // (LinearLayout)view.getParent();
						// // View child =
						// // (View)view.findViewById(R.id.detailCustomer);
						// // if(child.getVisibility()==View.GONE)
						// // child.setVisibility(View.VISIBLE);
						// // else child.setVisibility(View.GONE);
						// // nextToFragment(new FragmentDetailIdt(),
						// // null);
						// }
						// });
						grid.setAdapter(adapter);
						CalculateTotal();
					} else {
						if (page == 0)
							adapter.setDataSource(lstJsonObjects);
						else
							adapter.getDataSource().addAll(lstJsonObjects);
						adapter.notifyDataSetChanged();
					}

					// if (json.equals("[]") || lstJsonObjects.size() <= 10)
					// footerGrid.setVisibility(View.GONE);
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
		case R.id.action_add:
			nextToFragment(new Fragment_Customer_Edit(), null);
			return true;
		case R.id.action_upload:
			// nextToFragment(new Fragment_Customer_UploadOffline(), null);
			return true;
		case R.id.search:
			nextToFragment(new FragmentBarCode(), null);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void setupMenuItem(Menu menu) {
		menu.findItem(R.id.action_add).setVisible(false);
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