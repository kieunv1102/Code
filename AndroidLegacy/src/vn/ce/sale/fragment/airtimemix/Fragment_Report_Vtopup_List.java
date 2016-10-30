package vn.ce.sale.fragment.airtimemix;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.Home;
import vn.ce.sale.adapter.CustomGridAndFilter;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.IDBundleAirTimeMix;
import vn.ce.sale.data.IDBundleData;
import vn.ce.sale.data.IData;
import vn.ce.sale.data.IDataCheck;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.DialogZopostFragment;
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
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;

public class Fragment_Report_Vtopup_List extends ZopostFragment
		implements ICallBackActivityToFragment, IData, IDBundleAirTimeMix, ICallBackUI {

	public static final String ARG_OBJECT = "object";
	CustomGridAndFilter adapter = null;
	View rootView;
	View footerGrid;
	int page;
	List<JSONObject> lstJsonObjects = new ArrayList<JSONObject>();
	List<JSONObject> lstJsonObjectsCost = new ArrayList<JSONObject>();
	private ProgressDialog pd;
	private TextView txtTotalPromotion, txtTotalMoney;
	private TextView txtNotification;
	ListView grid;
	LinearLayout lnlReportTotal;
	String getFromDate;
	String getToDate;
	String getTaiKhoan;
	String getChanel;
	private int mYear, mMonth, mDay;
	String datenow;

	public static Fragment_Report_Vtopup_List newInstance() {
		Fragment_Report_Vtopup_List fragment = new Fragment_Report_Vtopup_List();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		((Home) getActivity()).getSupportActionBar().show();
		setHasOptionsMenu(true);
		// ActionBar mActionBar = ((Home) getActivity()).getSupportActionBar();
		// mActionBar.show();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.vi21_layout_report_detail, container, false);

		grid = (ListView) rootView.findViewById(R.id.grid_Customer);

		final Calendar cal = Calendar.getInstance();
		mYear = cal.get(Calendar.YEAR);
		mMonth = cal.get(Calendar.MONTH);
		mDay = cal.get(Calendar.DAY_OF_MONTH);
		String mday = String.valueOf(mDay);
		String mmonth = String.valueOf(mMonth + 1);
		if (String.valueOf(mDay).length() < 2) {
			mday = "0" + String.valueOf(mDay);
		}
		if (String.valueOf(mMonth + 1).length() < 2) {
			mmonth = "0" + String.valueOf(mMonth + 1);
		}
		datenow = String.valueOf(mYear) + mmonth + mday;
		txtTotalPromotion = (TextView) rootView.findViewById(R.id.txtTotalPromotion);
		txtTotalMoney = (TextView) rootView.findViewById(R.id.txtTotalMoney);
		txtNotification = (TextView) rootView.findViewById(R.id.txtNotification);

		lnlReportTotal = (LinearLayout) rootView.findViewById(R.id.lnl_report_total);

		pd = new ProgressDialog(getActivity());

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
	protected void startLoadData() {
		lstJsonObjects.clear();
		lstJsonObjectsCost.clear();
		pd.setMessage("Đang tải dữ liệu!");
		pd.show();
		String dateFromNow = datenow + "000000";
		String dateToNow = datenow + "235959";

		String s = ShareMemManager.getInstance().readFromDB(getContext(), "username") + "|" + dateFromNow + "|"
				+ dateToNow;

		ServiceManager.factoryData()
				.getDataRaw(Util.SERVER_URL + "system=AirtimeMix&Airtime=GatewayApi/"
						+ ShareMemManager.getInstance().readFromDB(getContext(), "username") + "/"
						+ ShareMemManager.getInstance().readFromDB(getContext(), "passwork") + "/APP/REPORT/" + s, "",
				DataOrder.NETWORK_THEN_CACHE, this);
//		ServiceManager.factoryData().getDataRaw(Util.SERVER_URL + "system=AirtimeMix&Airtime=GatewayApi/"
//				+ ShareMemManager.getInstance().readFromDB(getContext(), "username") + "/"
//				+ ShareMemManager.getInstance().readFromDB(getContext(), "passwork") + "/APP/QUERYVBILL/BALANCE|"
//				+ ShareMemManager.getInstance().readFromDB(getContext(), "username"), "", DataOrder.NETWORK_THEN_CACHE,
//				new ICallBackUI() {
//
//					@Override
//					public void processRaw(String key, final int status, final String json) {
//						// TODO Auto-generated method stub
//						runOnUiThreadX(new Runnable() {
//							public void run() {
//								if (status == -1001) {
//									Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
//									pd.dismiss();
//									return;
//								}
//								if (status == 200) {
//									JSONObject oJson;
//
//									try {
//										oJson = new JSONObject(json);
//										String arr = oJson.getString("ResponseMessage");
//										String[] parts = arr.split("\\|");
//										String part1 = parts[1];
//										txtTotalPromotion.setText(
//												FormatUtil.formatCurrency(Double.parseDouble(part1)) + " vtop");
//									} catch (JSONException e1) {
//										// TODO Auto-generated catch
//										// block
//										e1.printStackTrace();
//									}
//								}
//							}
//						});
//					}
//
//					@Override
//					public void process(String key, int status, List<JSONObject> lst) {
//						// TODO Auto-generated method stub
//
//					}
//				});
	}

	protected void SearchReportSale() {
		lstJsonObjectsCost.clear();
		pd.setMessage("Đang tải dữ liệu!");
		pd.show();

		String setFromDate = getFromDate + "000000";
		String setToDate = getToDate + "235959";
		String setTaiKhoan = getTaiKhoan;
		String setChanel = getChanel;
		String s = ShareMemManager.getInstance().readFromDB(getContext(), "username") + "|" + setFromDate + "|"
				+ setToDate + "|" + setTaiKhoan + "|" + setChanel;
		ServiceManager.factoryData()
				.getDataRaw(Util.SERVER_URL + "system=AirtimeMix&Airtime=GatewayApi/"
						+ ShareMemManager.getInstance().readFromDB(getContext(), "username") + "/"
						+ ShareMemManager.getInstance().readFromDB(getContext(), "passwork") + "/APP/REPORT/" + s, "",
				DataOrder.NETWORK_THEN_CACHE, this);
	}

	private void CalculateTotal() {
		// TODO Auto-generated method stub
		int totalMoney = 0;
		for (int jx = 0; jx <= lstJsonObjects.size() - 1; jx++) {
			try {
				int tien = lstJsonObjects.get(jx).getInt("SalePrice");
				totalMoney = totalMoney + tien;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		txtTotalMoney.setText(String.valueOf(FormatUtil.formatCurrency((double) totalMoney)) + " vtop");
		if (lstJsonObjects.size() == 0) {
			txtNotification.setVisibility(View.VISIBLE);

			if (Util.checkSearchReport == true) {
				int fd = Integer.parseInt(getFromDate);
				int td = Integer.parseInt(getToDate);
				String reverseFD = TimeUtil.dateToString(TimeUtil.stringToDate(getFromDate, "yyyyMMdd"), "dd/MM/yyyy");
				String reverseTD = TimeUtil.dateToString(TimeUtil.stringToDate(getToDate, "yyyyMMdd"), "dd/MM/yyyy");

				if (fd == td) {
					txtNotification.setText("Trong ngày " + reverseFD + " đơn vị không giao dịch.");
				} else
					txtNotification
							.setText("Từ ngày " + reverseFD + " đến ngày " + reverseTD + " đơn vị không giao dịch.");
			} else {
				txtNotification.setText("Hôm nay đơn vị chưa giao dịch.");
			}

		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.menu_report_sale, menu);

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.actionSearchReport:
			FragmentManager fm = getActivity().getSupportFragmentManager();
			Fragment_Report_Vtopup_Detail_List editNameDialog = new Fragment_Report_Vtopup_Detail_List();

			editNameDialog.setDataBundle(Fragment_Report_Vtopup_List.this);
			editNameDialog.show(fm, "fragment_edit_name");
			return true;
		case R.id.actionRefresh:
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

	@Override
	public void sendData(JSONObject o) {
		// TODO Auto-generated method stub

	}

	@Override
	public JSONObject getData(JSONObject o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void process(String key, int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processRaw(String key, final int status, final String json) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		runOnUiThreadX(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (status == -1001) {
					Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
					pd.dismiss();
					return;
				}

				if (status == 200) {
					pd.dismiss();
					grid.setVisibility(View.VISIBLE);
					txtNotification.setVisibility(View.GONE);
					lnlReportTotal.setVisibility(View.VISIBLE);
					JSONObject oJson;

					try {
						oJson = new JSONObject(json);
						String arr = oJson.getString("ResponseMessage");
						lstJsonObjects = TransformDataManager.convertArrayToListJSON(new JSONArray(arr));

						for (int i = lstJsonObjects.size() - 1; i >= 0; --i) {
							JSONObject a = lstJsonObjects.get(i);
							lstJsonObjectsCost.add(a);

						}

					} catch (JSONException e1) {
						// TODO Auto-generated catch
						// block
						e1.printStackTrace();
					}

					ShareMemManager.getInstance().saveToDB(getContext(), "report_airtimemix", json.toString());
					// List<JSONObject> lstJsonObjects =
					// TransformDataManager.getListJsonByXPath(g,"ResponseMessage");//''.convertStringToListJSON(json1);
					if (adapter == null) {
						// TODO Auto-generated method
						// stu
						adapter = new CustomGridAndFilter(getActivity(), lstJsonObjectsCost,
								R.layout.layout_customer_report_vtopup_list);
						// TicketNumber_TradeType_Currency_TimeFrame
						adapter.bindFields(new BindDataUI[] { BindDataUI.createNew(R.id.customer_title, "ProductCode",
								TypeUI.COMPLEX, null, new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o, BindDataUI ui) {
										try {
											return o.getString("CategoryName");
										} catch (JSONException e) {
											return e.toString();
										}
									}
								}), BindDataUI.createNew(R.id.customer_address, "ProductName", TypeUI.COMPLEX, null,
										new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o, BindDataUI ui) {
										try {
											return TimeUtil.dateToString(
													new Date(Util
															.extractDateFromServerOrder(o.getString("CreatedDate"))),
													"dd/MM/yyyy HH:mm:ss");
										} catch (JSONException e) {
											return e.toString();
										}
									}
								}), BindDataUI.createNew(R.id.customer_kho, "ProductName", TypeUI.COMPLEX, null,
										new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o, BindDataUI ui) {
										try {
											return String.valueOf(FormatUtil
													.formatCurrency(Double.parseDouble(o.getString("SalePrice"))));
										} catch (JSONException e) {
											return e.toString();
										}
									}
								}), BindDataUI.createNew(R.id.customer_gia, "ProductName", TypeUI.COMPLEX, null,
										new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o, BindDataUI ui) {
										try {
											return o.getString("Account");
										} catch (JSONException e) {
											return e.toString();
										}
									}
								}), BindDataUI.createNew(R.id.customer_chanel, "ProductName", TypeUI.COMPLEX, null,
										new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o, BindDataUI ui) {
										try {
											return o.getString("Channel");
										} catch (JSONException e) {
											return e.toString();
										}
									}
								}) });

						grid.setAdapter(adapter);
						CalculateTotal();
					} else {
						if (page == 0) {
							adapter.setDataSource(lstJsonObjectsCost);
							CalculateTotal();
						} else {
							adapter.getDataSource().addAll(lstJsonObjectsCost);
							CalculateTotal();
						}
						adapter.notifyDataSetChanged();
					}

					Util.checkSearchReport = false;
				}
			}
		});

	}

	@Override
	public void bundleAirTimeMix(String fromDate, String toDate, String taiKhoan, String chanel) {
		// TODO Auto-generated method stub
		getTaiKhoan = taiKhoan;
		getFromDate = fromDate;
		getToDate = toDate;
		getChanel = chanel;
		SearchReportSale();
	}

}