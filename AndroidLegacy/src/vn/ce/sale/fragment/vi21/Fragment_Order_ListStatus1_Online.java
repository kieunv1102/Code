package vn.ce.sale.fragment.vi21;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.HomeActivity1;
import vn.ce.sale.adapter.CustomGrid;
import vn.ce.sale.adapter.CustomGridAndFilter;
import vn.ce.sale.adapter.ProductGrid;
import vn.ce.sale.adapter.ProductGridInline;
import vn.ce.sale.data.DBManager;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.fragment.Fragment_Customer_Edit;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.TypeUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.ui.ZopostValue;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.TimeUtil;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.R.integer;
import android.R.string;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.view.Menu;
import android.widget.AdapterView;
import android.view.MenuInflater;

public class Fragment_Order_ListStatus1_Online extends ZopostFragment
		implements ICallBackActivityToFragment, ICallBackUI {

	public static final String ARG_OBJECT = "object";
	CustomGrid adapter = null;
	View rootView;
	View footerGrid;

	ListView grid;
	ProgressBar form_pb;
	int page = 1;
	int totalRecord = 20;

	String status = "0";
	private HomeActivity1 mActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.vi21_layout_customer_list, container, false);
		grid = (ListView) rootView.findViewById(R.id.grid_Customer);
		// footerGrid= inflater.inflate(R.layout.fragment_search_grid_loadmore,
		// container, false);
		Util.saveActionUser(getActivity(), "DS-DON-SALE-ONLINE", (new Date()).getTime());
		try {
			Util.getActionUserToServer(getActivity());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pd = new ProgressDialog(getActivity());
		form_pb = (ProgressBar) rootView.findViewById(R.id.form_pb);
		setHasOptionsMenu(true);
		mActivity = (HomeActivity1) getActivity();
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
		if (adapter == null)
			return;
		// TODO Auto-generated method stub
		// ((TextView)
		// rootView.findViewById(android.R.id.text1)).setText(args.getString(ARG_OBJECT));
		// adapter.getFilter().filter(args.getString(ARG_OBJECT));
	}

	private ProgressDialog pd;

	@Override
	protected void startLoadData() {
		pd.setMessage("Đang tải dữ liệu!");
		pd.show();
		status = params.getString("status");
		String paramString = "data={\"ActionType\":\"REPORT_SALE\",\"UserName\":\""
				+ ShareMemManager.getInstance().readFromDB(getContext(), "username") + "\",\"Password\":\""
				+ ShareMemManager.getInstance().readFromDB(getContext(), "password") + "\",\"Page\":"
				+ (page - 1) * totalRecord + ",\"Size\":" + totalRecord + "}";
		// TODO Auto-generated method stub
		ServiceManager.factoryData().getDataRaw(Util.SERVER_URL + paramString, "", this);

	}

	protected void refreshData() {
		pd.setMessage("Đang tải dữ liệu!");
		pd.show();
		page = 1;
		adapter.getDataSource().clear();
		// TODO Auto-generated method stub
		String paramString = "data={\"ActionType\":\"REPORT_SALE\",\"UserName\":\""
				+ ShareMemManager.getInstance().readFromDB(getContext(), "username") + "\",\"Password\":\""
				+ ShareMemManager.getInstance().readFromDB(getContext(), "password") + "\",\"Page\":"
				+ (page - 1) * totalRecord + ",\"Size\":" + totalRecord + "}";
		// TODO Auto-generated method stub
		ServiceManager.factoryData().getDataRaw(Util.SERVER_URL + paramString, "", this);
	}

	@Override
	public void process(String key, int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processRaw(String key, final int status, final String json) {
		// if(1==1) return;
		runOnUiThreadX(new Runnable() {
			@Override
			public void run() {
				if (Util.checkOffline == true) {
					pd.dismiss();
					form_pb.setVisibility(View.GONE);
				}
				// TODO Auto-generated method stub
				if (status == 1001) {
					form_pb.setVisibility(View.VISIBLE);
					return;
				} // displayloading with json is percentage of loading data
				if (status == 200) {
					pd.dismiss();
					form_pb.setVisibility(View.GONE);
					grid.setVisibility(View.VISIBLE);
					/*
					 * String json1=
					 * "[{\"id\":\"13\",\"title\":\" - dai ly D�ng Anh\",\"address\":\"544\"}"
					 * +
					 * ",{\"id\":\"13\",\"title\":\" - dai ly Li�n hoa\",\"address\":\"544\"}"
					 * +"]";
					 */
					JSONObject object;
					List<JSONObject> lstJsonObjects = new ArrayList<JSONObject>();
					String jsonArray = "[]";
					;
					try {

						object = new JSONObject(json);
						jsonArray = object.getString("ResponseMessage");
						JSONArray array = new JSONArray(jsonArray);
						lstJsonObjects = TransformDataManager.convertArrayToListJSON(array);
						ShareMemManager.getInstance().saveToDB(getContext(), "report_sale", json);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (adapter == null) {
						// TODO Auto-generated method stub
						adapter = new CustomGridAndFilter(getActivity(), lstJsonObjects,
								R.layout.layout_order_list_row_online, new String[] { "code", "note" });
						// TicketNumber_TradeType_Currency_TimeFrame
						adapter.bindFields(new BindDataUI[] { BindDataUI.createNew(R.id.customer_madonhang, "server",
								TypeUI.COMPLEX, null, new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o, BindDataUI ui) {

										// TODO Auto-generated method stub
										try {
											return "Mã đơn hàng: " + o.getString("OrderCode") + "\n";
											// +"Thời gian:
											// "+TimeUtil.dateToString(new
											// Date(Util.extractDateFromServerOrder(o.getString("CreatedDate"))),
											// "dd/MM/yyyy HH:mm:ss");
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											return e.toString();
										}
									}
								})

								, BindDataUI.createNew(R.id.customer_tongtien, "server", TypeUI.COMPLEX, null,
										new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o, BindDataUI ui) {
										try {

											return // "Số lượng sản phẩm:
													// "+o.getJSONArray("ItemList").length()+"\n\n"+
											"Tổng tiền: " + FormatUtil.formatCurrency(o.getDouble("TotalPrice")) + "\n";

										} catch (JSONException e) {
											// TODO Auto-generated catch block
											return e.toString();
										}

									}
								}), BindDataUI.createNew(R.id.customer_thoigian, "server", TypeUI.COMPLEX, null,
										new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o, BindDataUI ui) {

										try {
											return "Thời gian: " + TimeUtil.dateToString(
													new Date(Util
															.extractDateFromServerOrder(o.getString("CreatedDate"))),
													"dd/MM/yyyy HH:mm:ss") + "\n";
											// String date =
											// o.getString("timestamp");
											// Date d = (Date)
											// TimeUtil.stringToDate(date,
											// "dd/MM HH:mm:ss");
											// return "Thời gian: "+
											// DateFormat.getTimeInstance().format(d)
											// +"\n";
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											return e.toString();
										}

									}
								}), BindDataUI.createNew(R.id.customer_capnhat, "server", TypeUI.COMPLEX, null,
										new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o, BindDataUI ui) {

										try {
											if (o.has("status") && o.getInt("status") == 0)
												return "Trạng thái: Chưa cập nhật";
											return "Trạng thái: Đã cập nhật";

										} catch (JSONException e) {
											// TODO Auto-generated catch block
											return e.toString();
										}

									}
								}), BindDataUI.createNew(R.id.customer_detail, "code", TypeUI.COMPLEX, null,
										new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o1, BindDataUI ui) {
										StringBuilder sBuffer = new StringBuilder();

										try {

											JSONArray array = o1.getJSONArray("ItemList");
											for (int jx = 0; jx <= array.length() - 1; jx++) {
												JSONObject o = array.getJSONObject(jx);

												// TODO Auto-generated method
												// stub
												// if (o.getInt("DetailType") ==
												// 3) {
												// sBuffer.append(String.valueOf(jx
												// + 1) + "L: " +
												// o.getInt("Quantity")
												// + "." +
												// o.getString("ProductCode") +
												// "."
												// + o.getString("ProductName")
												// + "\n");
												// } else {
												sBuffer.append(String.valueOf(jx + 1) + "." + o.getString("ProductCode")
														+ "." + o.getString("ProductName") + "  "
														+ o.getString("Quantity") + "  " + o.getString("QuantityLot")
														+ " " + o.getString("DetailType") + " "
														+ o.getString("TotalProducValue") + "\n");

											}
										} catch (Exception ex) {
											return "Error...";
										}
										return sBuffer.toString();
									}
								}) });

						// uiCallBack.onParamsFromFragment(paramsToActivity);
						// grid=(ListView)rootView.findViewById(R.id.grid);
						// footerGrid=(getActivity()).getLayoutInflater().inflate(R.layout.fragment_search_grid_loadmore,
						// null);

						// Activity mActivity=getActivity();
						footerGrid = ((LayoutInflater) (mActivity).getSystemService(Context.LAYOUT_INFLATER_SERVICE))
								.inflate(R.layout.fragment_search_grid_loadmore, null);

						((Button) footerGrid.findViewById(R.id.grid_button)).setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								page++;
								startLoadData();
							}
						});
						grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								Util.checkOrderOnline = true;
								try {
									ShareMemManager.getInstance().saveToDB(getActivity(), "positionReportSale",
											position + "");
									nextToFragment(new FragmentOrderProduct(),
											BundleData.createNew().putString("screen", Util.SCREEN_ORDER)
													.putString("mode-edit", "true")
													.putString("data-order",
															convertToOfflineJSON(adapter.getDataSource().get(position))
																	.toString())
													.data());

								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
						grid.setAdapter(adapter);
						grid.addFooterView(footerGrid);
						// footerGrid.setVisibility(View.GONE);
					} else {
						pd.dismiss();
						if (page == 1)
							adapter.setDataSource(lstJsonObjects);
						else
							adapter.getDataSource().addAll(lstJsonObjects);
						adapter.notifyDataSetChanged();
					}
					if (lstJsonObjects.size() <= totalRecord)
						footerGrid.setVisibility(View.GONE);
					// if(json.equals("[]")|| lstJsonObjects.size()<=10)
					// footerGrid.setVisibility(View.GONE);
				}
			}
		});

	}

	// {\"ProductName\":\"Nước lau sàn grid 15/10 ( sản phẩm mới ) 180
	// ml\",\"ProductCode\":\"H206A\",\"ProductID\":330,\"Price\":50000.0000,\"BarCode\":\"1512020000330\",\"Quantity\":0,\"Promotion\":97}
	JSONObject convertToOfflineJSON(JSONObject oNeedToConvert) throws JSONException {
		JSONObject oResult = new JSONObject();
		oResult.put("status", 1);
		oResult.put("OrderCode", oNeedToConvert.getString("OrderCode"));

		JSONObject oServer = new JSONObject();
		oServer.put("ResponseMessage", oNeedToConvert.getString("OrderCode") + "|");
		oResult.put("server", oServer);

		JSONArray oOrderItems = new JSONArray();
		JSONArray arrProduct = oNeedToConvert.getJSONArray("ItemList");
		for (int jx = 0; jx <= arrProduct.length() - 1; jx++) {
			// QuantityLot\":0,\"PromotionQuantity\":0,\"TotalProducValue\":30000.0000
			// ,\"TotalProducValueLot\":0,\"ProductCode\":\"0102A\",\"ProductName\":\"Tẩy
			// Okay Vàng từ 01/08/2014 ( 12Chai/Thùng)\",\"DetailType\":0}
			JSONObject oProduct = arrProduct.getJSONObject(jx);// lấy sản phẩm
			int price = 0;
			/*
			 * // tách sản phẩm bán if (oProduct.getInt("Quantity") > 0) {
			 * JSONObject oProductInOder = new JSONObject();
			 * oProductInOder.put("ProductCode",
			 * oProduct.getString("ProductCode"));
			 * oProductInOder.put("ProductID", oProduct.getString("ProductID"));
			 * oProductInOder.put("ProductName",
			 * oProduct.getString("ProductName"));
			 * 
			 * oProductInOder.put("SL", oProduct.getInt("Quantity"));
			 * oProductInOder.put("KM", "0"); oProductInOder.put("TT",
			 * oProduct.getInt("TotalProducValue")); price = (int)
			 * (oProduct.getInt("TotalProducValue") /
			 * oProduct.getInt("Quantity")); oProductInOder.put("Price", price);
			 * oOrderItems.put(oProductInOder); } // tách sản phẩm khuyến mại if
			 * (oProduct.getInt("PromotionQuantity") > 0) { JSONObject
			 * oProductInOder = new JSONObject();
			 * oProductInOder.put("ProductCode",
			 * oProduct.getString("ProductCode"));
			 * oProductInOder.put("ProductID", oProduct.getString("ProductID"));
			 * oProductInOder.put("ProductName",
			 * oProduct.getString("ProductName"));
			 * 
			 * oProductInOder.put("SL", oProduct.getInt("PromotionQuantity"));
			 * oProductInOder.put("KM", "1"); oProductInOder.put("TT",
			 * oProduct.getInt("TotalProducValue")); //
			 * price=(int)(oProduct.getInt("TotalProducValue")/oProduct.getInt(
			 * "Quantity")); oProductInOder.put("Price", price);
			 * oOrderItems.put(oProductInOder); } // tách sản phẩm bán theo lô
			 * if (oProduct.getInt("QuantityLot") > 0) { JSONObject
			 * oProductInOder = new JSONObject();
			 * oProductInOder.put("ProductCode",
			 * oProduct.getString("ProductCode"));
			 * oProductInOder.put("ProductID", oProduct.getString("ProductID"));
			 * oProductInOder.put("ProductName",
			 * oProduct.getString("ProductName"));
			 * 
			 * oProductInOder.put("SL", oProduct.getInt("QuantityLot"));
			 * oProductInOder.put("KM", "0"); oProductInOder.put("TT",
			 * oProduct.getInt("TotalProducValueLot")); price = (int)
			 * (oProduct.getInt("TotalProducValueLot") /
			 * oProduct.getInt("QuantityLot")); oProductInOder.put("Price",
			 * price); oOrderItems.put(oProductInOder); }
			 */

			JSONObject oProductInOder = new JSONObject();
			oProductInOder.put("ProductCode", oProduct.getString("ProductCode"));
			oProductInOder.put("ProductID", oProduct.getString("ProductID"));
			oProductInOder.put("ProductName", oProduct.getString("ProductName"));

			// detail=3:
			// set IsLot=true;
			// set SL: quantitySlot
			// KM: "0"
			if (oProduct.getInt("DetailType") == 1) {
				oProductInOder.put("SL", oProduct.getInt("Quantity"));
				oProductInOder.put("KM", "0");
				oProductInOder.put("TT", oProduct.getInt("TotalProducValue"));
				price = oProduct.getInt("ProducValue");
				oProductInOder.put("Price", price);
				oOrderItems.put(oProductInOder);
			}
			if (oProduct.getInt("DetailType") == 2) {
				oProductInOder.put("SL", oProduct.getInt("Quantity"));
				oProductInOder.put("KM", "1");
				oProductInOder.put("TT", "0");
				price = oProduct.getInt("ProducValue");
				oProductInOder.put("Price", price);
				oOrderItems.put(oProductInOder);
			}

			if (oProduct.getInt("DetailType") == 3) {
				int item = (int) (oProduct.getInt("Quantity") / oProduct.getInt("QuantityLot"));
				// oProductInOder.put("ProductName",
				// " Lô: " + String.valueOf(item) + "-" +
				// oProduct.getString("ProductName"));
				oProductInOder.put("isspecial", 1);
				oProductInOder.put("SL", oProduct.getInt("QuantityLot"));
				oProductInOder.put("KM", "0");
				oProductInOder.put("TT", oProduct.getInt("TotalProducValue"));
				if (oProduct.getInt("QuantityLot") > 0) {
					price = oProduct.getInt("ProducValue");
					oProductInOder.put("Price", price);
				}

				oProductInOder.put("IsLot", "true");
				oOrderItems.put(oProductInOder);
			}

		}
		oResult.put("OrderItems", oOrderItems);
		oResult.put("timestamp", Util.extractDateFromServerOrder(oNeedToConvert.getString("CreatedDate")));
		oResult.put("tongtien", oNeedToConvert.getLong("TotalPrice") + oNeedToConvert.getLong("TotalPrice"));
		return oResult;
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
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void setupMenuItem(Menu menu) {
		menu.findItem(R.id.action_add).setVisible(true);
		menu.findItem(R.id.action_search).setVisible(true);
		menu.findItem(R.id.action_save).setVisible(false);
		menu.findItem(R.id.action_edit).setVisible(false);
		menu.findItem(R.id.action_refresh).setVisible(true);
		menu.findItem(R.id.action_list).setVisible(false);
		menu.findItem(R.id.action_upload).setVisible(false);
		menu.findItem(R.id.action_saveoffline).setVisible(false);
		menu.findItem(R.id.action_exit).setVisible(false);
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

}