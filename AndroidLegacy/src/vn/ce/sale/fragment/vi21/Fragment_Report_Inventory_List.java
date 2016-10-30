package vn.ce.sale.fragment.vi21;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import vn.ce.sale.ui.ZopostActivity;
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

public class Fragment_Report_Inventory_List extends ZopostFragment implements ICallBackActivityToFragment, ICallBackUI {

	public static final String ARG_OBJECT = "object";
	CustomGridAndFilter adapter = null;
	View rootView;
	View footerGrid;
	private ProgressDialog pd;
	ListView grid;
	private TextView txtTotalCount, txtTotalPromotion, txtTotalMoney;
	private TextView txtNotification;
	int page;
	List<JSONObject> lstJsonDisplay = new ArrayList<JSONObject>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.vi21_layout_customer_list2, container, false);

		Util.saveActionUser(getActivity(), "VÀO-BC-TỒN-KHO", (new Date()).getTime());

		grid = (ListView) rootView.findViewById(R.id.grid_Customer);
		txtTotalCount = (TextView) rootView.findViewById(R.id.txtTotalCount);
		txtTotalPromotion = (TextView) rootView.findViewById(R.id.txtTotalPromotion);
		txtTotalMoney = (TextView) rootView.findViewById(R.id.txtTotalMoney);
		txtNotification = (TextView) rootView.findViewById(R.id.txtNotification);

		pd = new ProgressDialog(getActivity());
		// refreshData();
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
		if (args == null) {
			refreshData();
		}
		if (adapter == null) {
			refreshData();
		} else
			adapter.getFilter().filter(args.getString(ARG_OBJECT));

	}

	@Override
	public void process(String key, int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void startLoadData() {
		lstJsonDisplay.clear();
		// ZopostActivity a;
		// ((ZopostActivity)getActivity()).getSupportActionBar().show();
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

		// CalculateTotal(totalCount,totalPromotion);
	}

	@SuppressWarnings("unused")
	private void CalculateTotal() {
		// TODO Auto-generated method stub
		int totalCount = 0;
		int totalPromotion = 0;
		int totalMoney = 0;
		int tien = 0;
		for (int jx = 0; jx <= lstJsonDisplay.size() - 1; jx++) {
			try {
				totalCount = totalCount + lstJsonDisplay.get(jx).getInt("Quantity");
				totalPromotion = totalPromotion + lstJsonDisplay.get(jx).getInt("Promotion");
				Log.e("sl", String.valueOf(lstJsonDisplay.get(jx).getInt("Quantity")));
				Log.e("gia", String.valueOf(lstJsonDisplay.get(jx).getInt("Price")));
				tien = lstJsonDisplay.get(jx).getInt("Quantity") * lstJsonDisplay.get(jx).getInt("Price");
				totalMoney = totalMoney + tien;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		txtTotalCount.setText(String.valueOf(totalCount));
		txtTotalPromotion.setText(String.valueOf(totalPromotion));
		txtTotalMoney.setText(String.valueOf(FormatUtil.formatCurrency((double) totalMoney)) + " vnd");
		if (lstJsonDisplay.size() == 0) {
			txtNotification.setVisibility(View.VISIBLE);
			txtNotification.setText("Không còn tồn kho theo điều kiện tìm kiếm");
		}
	}

	protected void refreshData() {
		lstJsonDisplay.clear();
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
				DataOrder.ONLY_CACHE, this);
		// string sData={"a":"Vao don hang","t":(new DateTime.getTIme())}
		// user:, iddevice:
	}

	@Override
	public void processRaw(String key, final int status, final String json) {

		runOnUiThreadX(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (status == -1001) {
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
						List<JSONObject> lstJsonObjects = new ArrayList<JSONObject>();
						lstJsonObjects = TransformDataManager.convertArrayToListJSON(new JSONArray(arr));

						for (int i = 0; i < lstJsonObjects.size(); i++) {
							if (((lstJsonObjects.get(i).getInt("Quantity")
									+ lstJsonObjects.get(i).getInt("Promotion")) > 0)
									&& (lstJsonObjects.get(i).getString("IsLot").equalsIgnoreCase("false"))) {

								lstJsonDisplay.add(lstJsonObjects.get(i));

							}

						}

					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					ShareMemManager.getInstance().saveToDB(getContext(), "product", json.toString());
					// List<JSONObject> lstJsonObjects =
					// TransformDataManager.getListJsonByXPath(g,"ResponseMessage");//''.convertStringToListJSON(json1);
					if (adapter == null) {
						// TODO Auto-generated method stu
						adapter = new CustomGridAndFilter(getActivity(), lstJsonDisplay,
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
											return "Tồn khuyến mại: " + o.getString("Promotion");
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
											return "Tồn bán: " + o.getString("Quantity");
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
											int quantity = Integer.parseInt(o.getString("Quantity"));
											Double tongTien = (double) (price * quantity);
											return "Tổng tiền: " + FormatUtil.formatCurrency(tongTien) + " vnd";
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											return e.toString();
										}
									}
								}) });

						grid.setAdapter(adapter);
						adapter.notifyDataSetChanged();
						CalculateTotal();
					} else {
						if (page == 0) {
							CalculateTotal();
							adapter.setDataSource(lstJsonDisplay);
						} else {
							CalculateTotal();
							adapter.getDataSource().addAll(lstJsonDisplay);
						}
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