package vn.ce.sale.fragment.vi21;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.adapter.CustomGridAndFilter;
import vn.ce.sale.data.DataOrder;
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

public class Fragment_Report_Sale_List extends ZopostFragment
		implements ICallBackActivityToFragment, IData, IDBundleData {

	public static final String ARG_OBJECT = "object";
	CustomGridAndFilter adapter = null;
	View rootView;
	View footerGrid;
	int page;
	List<JSONObject> lstJsonObjects = new ArrayList<JSONObject>();
	private ProgressDialog pd;
	private TextView txttotalSale, txtTotalPromotion, txtTotalMoney;
	private TextView txtNotification;
	ListView grid;
	LinearLayout lnlReportTotal;
	String getFromDate;
	String getToDate;
	String getProductCode;
	private int mYear, mMonth, mDay;
	String datenow;

	// DatePicker datePickerFromTime,dataPickerTotime;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.vi21_layout_report_detail, container, false);
		grid = (ListView) rootView.findViewById(R.id.grid_Customer);

		// log action report sale
		// ShareMemManager.getInstance().saveToDB(getActivity(), "aReportSale",
		// "Vào báo cáo bán hàng");
		// String tSaleProduct = new Long((new Date().getTime())).toString();
		// ShareMemManager.getInstance().saveToDB(getActivity(), "tReportSale",
		// tSaleProduct);

		Util.saveActionUser(getActivity(), "VÀO-BC-BÁN-HÀNG", (new Date()).getTime());
		
		final Calendar cal = Calendar.getInstance();
		mYear = cal.get(Calendar.YEAR);
		mMonth = cal.get(Calendar.MONTH);
		mDay = cal.get(Calendar.DAY_OF_MONTH);
		String mday =String.valueOf(mDay);
		String mmonth =String.valueOf(mMonth + 1);
		if (String.valueOf(mDay).length() < 2) {
			mday = "0" + String.valueOf(mDay);
		}
		if (String.valueOf(mMonth + 1).length() < 2) {
			mmonth = "0" + String.valueOf(mMonth + 1);
		}
		datenow = String.valueOf(mYear) + mmonth + mday;
		txttotalSale = (TextView) rootView.findViewById(R.id.txtTotalCount);
		txtTotalPromotion = (TextView) rootView.findViewById(R.id.txtTotalPromotion);
		txtTotalMoney = (TextView) rootView.findViewById(R.id.txtTotalMoney);
		txtNotification = (TextView) rootView.findViewById(R.id.txtNotification);

		// lnlSearchReportSale = (LinearLayout)
		// rootView.findViewById(R.id.lnl_search_report_sale);
		lnlReportTotal = (LinearLayout) rootView.findViewById(R.id.lnl_report_total);

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
	protected void startLoadData() {
		pd.setMessage("Đang tải dữ liệu!");
		pd.show();
		// if (Util.checkSearchReport == true) {
		//
		// // bundleDataDate(getFromDate, getToDate);
		//
		// String setFromDate = getFromDate + "000000";
		// String setToDate = getToDate + "235959";
		//
		// ServiceManager.factoryData()
		// .getDataRaw(Util.SERVER_URL + "data={\"ActionType\":\"REPORT_SALE2\""
		// + ",\"UserName\":\""
		// + ShareMemManager.getInstance().readFromDB(getContext(), "username")
		// + "\",\"Password\":\""
		// + ShareMemManager.getInstance().readFromDB(getContext(), "password")
		// + "\",\"Page\":" + 0
		// + ",\"Size\":" + 10 + ",\"FromDate\":\"" + setFromDate +
		// "\",\"ToDate\":\"" + setToDate
		// + "\",\"ProductCode\":\"" + getProductCode + "\"}", "",
		// DataOrder.NETWORK_THEN_CACHE, this);
		//
		// } else {
		String dateFromNow = datenow + "000000";
		String dateToNow = datenow + "235959";
		ServiceManager.factoryData()
				.getDataRaw(Util.SERVER_URL + "data={\"ActionType\":\"REPORT_SALE2\"" + ",\"UserName\":\""
						+ ShareMemManager.getInstance().readFromDB(getContext(), "username") + "\",\"Password\":\""
						+ ShareMemManager.getInstance().readFromDB(getContext(), "password") + "\",\"Page\":" + 0
						+ ",\"Size\":" + 1000 + ",\"FromDate\":\"" + dateFromNow + "\",\"ToDate\":\"" + dateToNow
						+ "\",\"ProductCode\":\"" + "\"}", "", DataOrder.NETWORK_THEN_CACHE, new ICallBackUI() {

							@Override
							public void process(String key, int status, List<JSONObject> lst) {
								// TODO Auto-generated method stub

							}

							@Override
							public void processRaw(String key, final int status, final String json) {
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

										// // displayloading with json is
										// percentage of loading data
										if (status == 200) {
											pd.dismiss();
											grid.setVisibility(View.VISIBLE);
											txtNotification.setVisibility(View.GONE);
											lnlReportTotal.setVisibility(View.VISIBLE);
											JSONObject oJson;

											try {
												oJson = new JSONObject(json);
												String arr = oJson.getString("ResponseMessage");
												lstJsonObjects = TransformDataManager
														.convertArrayToListJSON(new JSONArray(arr));
												/*
												 * if(lstJsonObjects.size()>=2)
												 * { for (int i = 0; i <
												 * lstJsonObjects.size()-1; i++)
												 * { JSONObject o=
												 * lstJsonObjects.get(i); String
												 * id=String.valueOf(o.getInt(
												 * "ProductID"))+"-"+String.
												 * valueOf(o.getInt("DetailType"
												 * )==3?3:1);
												 * if(o.getInt("DetailType")==3)
												 * id=id+"-"+String.valueOf(o.
												 * getInt("Quantity")/o.
												 * getInt("QuantityLot"));
												 * 
												 * o.put("idok",id);
												 * 
												 * for (int j = i+1; j <
												 * lstJsonObjects.size()-1; j++)
												 * { if
												 * (lstJsonObjects.get(i).getInt
												 * ("ProductID")==
												 * lstJsonObjects.get(j).getInt(
												 * "ProductID") && ) {
												 * 
												 * } }
												 * 
												 * } ArrayList<JSONObject>
												 * lstTmp= new
												 * ArrayList<JSONObject>(); for
												 * (int i = 0; i <=
												 * lstJsonObjects.size()-2; i++)
												 * { JSONObject o1=
												 * lstJsonObjects.get(i); int
												 * j=i+1;
												 * while(j<=lstJsonObjects.size(
												 * )-2) {
												 * 
												 * //neu thang dang duyet trung
												 * voi thang khac thi +- tuong
												 * ung if(lstJsonObjects.get(j).
												 * getString("idok").
												 * equalsIgnoreCase(o1.getString
												 * ("idok"))) {
												 * 
												 * if(o1.getInt("DetailType")==
												 * 3) o1.put("Quantity",
												 * o1.getInt("Quantity")+
												 * lstJsonObjects.get(j).getInt(
												 * "Quantity")); else
												 * o1.put("QuantityLot",
												 * o1.getInt("QuantityLot")+
												 * lstJsonObjects.get(j).getInt
												 * ("QuantityLot"));
												 * o1.put("TotalProducValue",
												 * o1.getInt("TotalProducValue")
												 * +lstJsonObjects.get(j).
												 * getInt("TotalProducValue"));
												 * 
												 * } } for(JSONObject o:lstTmp)
												 * { if(lstJsonObjects.get(j).
												 * getString("idok").
												 * equalsIgnoreCase(o1.getString
												 * ("idok"))) continue; }
												 * lstTmp.add(o1); } }
												 */
											} catch (JSONException e1) {
												// TODO Auto-generated catch
												// block
												e1.printStackTrace();
											}

											ShareMemManager.getInstance().saveToDB(getContext(), "product_store2",
													json.toString());
											// List<JSONObject> lstJsonObjects =
											// TransformDataManager.getListJsonByXPath(g,"ResponseMessage");//''.convertStringToListJSON(json1);
											if (adapter == null) {
												// TODO Auto-generated method
												// stu
												adapter = new CustomGridAndFilter(getActivity(), lstJsonObjects,
														R.layout.layout_customer_report_sale_list);
												// TicketNumber_TradeType_Currency_TimeFrame
												adapter.bindFields(
														new BindDataUI[] { BindDataUI.createNew(R.id.customer_title,
																"ProductCode", TypeUI.COMPLEX, null, new ZopostValue() {

															@Override
															public String parseFromSource(JSONObject o, BindDataUI ui) {
																// TODO
																// Auto-generated
																// method stub
																try {
																	if (o.getInt("DetailType") == 3) {
																		// int
																		// loItem
																		// =
																		// (int)
																		// (o.getInt("Quantity")
																		// /
																		// o.getInt("QuantityLot"));
																		// return
																		// "L"+loItem+"-"+o.getString("ProductCode")
																		// + "."
																		// +
																		// o.getString("ProductName");
																		return o.getString("ProductCode") + "."
																				+ o.getString("ProductName");
																	} else
																		return o.getString("ProductCode") + "."
																				+ o.getString("ProductName");
																} catch (JSONException e) {
																	// TODO
																	// Auto-generated
																	// catch
																	// block
																	return e.toString();
																}
															}
														}), BindDataUI.createNew(R.id.customer_address, "ProductName",
																TypeUI.COMPLEX, null, new ZopostValue() {

															@Override
															public String parseFromSource(JSONObject o, BindDataUI ui) {
																// TODO
																// Auto-generated
																// method stub
																try {
																	if (o.getInt("DetailType") == 1
																			&& o.getInt("QuantityLot") > 0) {
																		return "Khuyến mại: "
																				+ o.getString("QuantityLot");
																	} else
																		return "Khuyến mại: " + "0";

																} catch (JSONException e) {
																	// TODO
																	// Auto-generated
																	// catch
																	// block
																	return e.toString();
																}
															}
														})
														// ,
														// BindDataUI.createNew(R.id.customer_lo,
														// "ProductName",
														// TypeUI.COMPLEX, null,
														// new ZopostValue() {
														//
														// @Override
														// public String
														// parseFromSource(JSONObject
														// o,
														// BindDataUI ui) {
														// // TODO
														// // Auto-generated
														// // method stub
														// try {
														// return "Bán theo lô:
														// " +
														// o.getString("QuantityLot");
														// } catch
														// (JSONException e) {
														// // TODO
														// // Auto-generated
														// // catch
														// // block
														// return e.toString();
														// }
														// }
														// })
														, BindDataUI.createNew(R.id.customer_kho, "ProductName",
																TypeUI.COMPLEX, null, new ZopostValue() {

															@Override
															public String parseFromSource(JSONObject o, BindDataUI ui) {
																// TODO
																// Auto-generated
																// method stub
																try {
																	return "Bán: " + o.getString("Quantity");
																} catch (JSONException e) {
																	// TODO
																	// Auto-generated
																	// catch
																	// block
																	return e.toString();
																}
															}
														}), BindDataUI.createNew(R.id.customer_gia, "ProductName",
																TypeUI.COMPLEX, null, new ZopostValue() {

															@Override
															public String parseFromSource(JSONObject o, BindDataUI ui) {
																// TODO
																// Auto-generated
																// method stub
																try {
																	// Double
																	// tien1 =
																	// Double.parseDouble(o.getString("TotalProducValue"));
																	// return
																	// "Tổng
																	// tiền: " +
																	// FormatUtil.formatCurrency(tien1)+"
																	// vnd";
																	return "Tổng tiền: "
																			+ FormatUtil.formatCurrency(
																					o.getDouble("TotalProducValue"))
																			+ " vnd";
																} catch (JSONException e) {
																	// TODO
																	// Auto-generated
																	// catch
																	// block
																	return e.toString();
																}
															}
														}) });

												grid.setAdapter(adapter);
												CalculateTotal2();
											} else {
												if (page == 0) {
													adapter.setDataSource(lstJsonObjects);
													CalculateTotal();
												} else {
													adapter.getDataSource().addAll(lstJsonObjects);
													CalculateTotal();
												}
												adapter.notifyDataSetChanged();
											}

											// if (json.equals("[]") ||
											// lstJsonObjects.size() <= 10)
											// footerGrid.setVisibility(View.GONE);
											Util.checkSearchReport = false;
										}
									}
								});
							}

						});
		// }
	}

	@SuppressWarnings("unused")
	protected void SearchReportSale() {
		pd.setMessage("Đang tải dữ liệu!");
		pd.show();
		// if (Util.checkSearchReport == true) {

		// bundleDataDate(getFromDate, getToDate);

		String setFromDate = getFromDate + "000000";
		String setToDate = getToDate + "235959";

		ServiceManager.factoryData()
				.getDataRaw(Util.SERVER_URL + "data={\"ActionType\":\"REPORT_SALE2\"" + ",\"UserName\":\""
						+ ShareMemManager.getInstance().readFromDB(getContext(), "username") + "\",\"Password\":\""
						+ ShareMemManager.getInstance().readFromDB(getContext(), "password") + "\",\"Page\":" + 0
						+ ",\"Size\":" + 10 + ",\"FromDate\":\"" + setFromDate + "\",\"ToDate\":\"" + setToDate
						+ "\",\"ProductCode\":\"" + getProductCode + "\"}", "", DataOrder.NETWORK_THEN_CACHE,
				new ICallBackUI() {

					// @Override
					// public void processRaw(String key, int status, String
					// json) {
					// // TODO Auto-generated method stub
					//
					// }
					//
					// @Override
					// public void process(String key, int status,
					// List<JSONObject> lst) {
					// // TODO Auto-generated method stub
					//
					// }
					// });
					// }
					// String dateFromNow = datenow + "000000";
					// String dateToNow = datenow + "235959";
					// ServiceManager.factoryData()
					// .getDataRaw(Util.SERVER_URL +
					// "data={\"ActionType\":\"REPORT_SALE2\"" +
					// ",\"UserName\":\""
					// + ShareMemManager.getInstance().readFromDB(getContext(),
					// "username") + "\",\"Password\":\""
					// + ShareMemManager.getInstance().readFromDB(getContext(),
					// "password") + "\",\"Page\":" + 0
					// + ",\"Size\":" + 10 + ",\"FromDate\":\"" + dateFromNow +
					// "\",\"ToDate\":\"" + dateToNow
					// + "\"}", "", DataOrder.NETWORK_THEN_CACHE, new
					// ICallBackUI() {

					@Override
					public void processRaw(String key, final int status, final String json) {

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

								// // displayloading with json is
								// percentage of loading data
								if (status == 200) {
									pd.dismiss();
									grid.setVisibility(View.VISIBLE);
									txtNotification.setVisibility(View.GONE);
									lnlReportTotal.setVisibility(View.VISIBLE);
									JSONObject oJson;

									try {
										oJson = new JSONObject(json);
										String arr = oJson.getString("ResponseMessage");
										lstJsonObjects = TransformDataManager
												.convertArrayToListJSON(new JSONArray(arr));
										/*
										 * if(lstJsonObjects.size()>=2) { for
										 * (int i = 0; i <
										 * lstJsonObjects.size()-1; i++) {
										 * JSONObject o= lstJsonObjects.get(i);
										 * String id=String.valueOf(o.getInt(
										 * "ProductID"))+"-"+String.
										 * valueOf(o.getInt("DetailType")==3?3:1
										 * ); if(o.getInt("DetailType")==3)
										 * id=id+"-"+String.valueOf(o.getInt(
										 * "Quantity")/o.
										 * getInt("QuantityLot"));
										 * 
										 * o.put("idok",id);
										 * 
										 * for (int j = i+1; j <
										 * lstJsonObjects.size()-1; j++) { if
										 * (lstJsonObjects.get(i).getInt(
										 * "ProductID")==
										 * lstJsonObjects.get(j).getInt(
										 * "ProductID") && ) {
										 * 
										 * } }
										 * 
										 * } ArrayList<JSONObject> lstTmp= new
										 * ArrayList<JSONObject>(); for (int i =
										 * 0; i <= lstJsonObjects.size()-2; i++)
										 * { JSONObject o1=
										 * lstJsonObjects.get(i); int j=i+1;
										 * while(j<=lstJsonObjects.size()-2) {
										 * 
										 * //neu thang dang duyet trung voi
										 * thang khac thi +- tuong ung
										 * if(lstJsonObjects.get(j).getString(
										 * "idok").
										 * equalsIgnoreCase(o1.getString("idok")
										 * )) {
										 * 
										 * if(o1.getInt("DetailType")==3)
										 * o1.put("Quantity",
										 * o1.getInt("Quantity")+lstJsonObjects.
										 * get(j).getInt( "Quantity")); else
										 * o1.put("QuantityLot",
										 * o1.getInt("QuantityLot")+
										 * lstJsonObjects.get(j).getInt
										 * ("QuantityLot"));
										 * o1.put("TotalProducValue",
										 * o1.getInt("TotalProducValue")+
										 * lstJsonObjects.get(j).
										 * getInt("TotalProducValue"));
										 * 
										 * } } for(JSONObject o:lstTmp) {
										 * if(lstJsonObjects.get(j).getString(
										 * "idok").
										 * equalsIgnoreCase(o1.getString("idok")
										 * )) continue; } lstTmp.add(o1); } }
										 */
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

									ShareMemManager.getInstance().saveToDB(getContext(), "product_store2",
											json.toString());
									// List<JSONObject> lstJsonObjects =
									// TransformDataManager.getListJsonByXPath(g,"ResponseMessage");//''.convertStringToListJSON(json1);
									if (adapter == null) {
										// TODO Auto-generated method
										// stu
										adapter = new CustomGridAndFilter(getActivity(), lstJsonObjects,
												R.layout.layout_customer_report_sale_list);
										// TicketNumber_TradeType_Currency_TimeFrame
										adapter.bindFields(new BindDataUI[] { BindDataUI.createNew(R.id.customer_title,
												"ProductCode", TypeUI.COMPLEX, null, new ZopostValue() {

													@Override
													public String parseFromSource(JSONObject o, BindDataUI ui) {
														// TODO
														// Auto-generated
														// method stub
														try {
															if (o.getInt("DetailType") == 3) {
																// int loItem =
																// (int)
																// (o.getInt("Quantity")
																// /
																// o.getInt("QuantityLot"));
																// return
																// "L"+loItem+"-"+o.getString("ProductCode")
																// + "." +
																// o.getString("ProductName");
																return "L" + o.getInt("QuantityLot") + "-"
																		+ o.getString("ProductCode") + "."
																		+ o.getString("ProductName");
															} else
																return o.getString("ProductCode") + "."
																		+ o.getString("ProductName");
														} catch (JSONException e) {
															// TODO
															// Auto-generated
															// catch
															// block
															return e.toString();
														}
													}
												}), BindDataUI.createNew(R.id.customer_address, "ProductName",
														TypeUI.COMPLEX, null, new ZopostValue() {

													@Override
													public String parseFromSource(JSONObject o, BindDataUI ui) {
														// TODO
														// Auto-generated
														// method stub
														try {
															if (o.getInt("DetailType") == 1
																	&& o.getInt("QuantityLot") > 0) {
																return "Khuyến mại: " + o.getString("QuantityLot");
															} else
																return "Khuyến mại: " + "0";

														} catch (JSONException e) {
															// TODO
															// Auto-generated
															// catch
															// block
															return e.toString();
														}
													}
												})
												// ,
												// BindDataUI.createNew(R.id.customer_lo,
												// "ProductName",
												// TypeUI.COMPLEX, null,
												// new ZopostValue() {
												//
												// @Override
												// public String
												// parseFromSource(JSONObject o,
												// BindDataUI ui) {
												// // TODO
												// // Auto-generated
												// // method stub
												// try {
												// return "Bán theo lô: " +
												// o.getString("QuantityLot");
												// } catch (JSONException e) {
												// // TODO
												// // Auto-generated
												// // catch
												// // block
												// return e.toString();
												// }
												// }
												// })
												, BindDataUI.createNew(R.id.customer_kho, "ProductName", TypeUI.COMPLEX,
														null, new ZopostValue() {

													@Override
													public String parseFromSource(JSONObject o, BindDataUI ui) {
														// TODO
														// Auto-generated
														// method stub
														try {
															return "Bán: " + o.getString("Quantity");
														} catch (JSONException e) {
															// TODO
															// Auto-generated
															// catch
															// block
															return e.toString();
														}
													}
												}), BindDataUI.createNew(R.id.customer_gia, "ProductName",
														TypeUI.COMPLEX, null, new ZopostValue() {

													@Override
													public String parseFromSource(JSONObject o, BindDataUI ui) {
														// TODO
														// Auto-generated
														// method stub
														try {
															// Double tien1 =
															// Double.parseDouble(o.getString("TotalProducValue"));
															// return "Tổng
															// tiền: " +
															// FormatUtil.formatCurrency(tien1)+"
															// vnd";
															return "Tổng tiền: " + FormatUtil.formatCurrency(
																	o.getDouble("TotalProducValue")) + " vnd";
														} catch (JSONException e) {
															// TODO
															// Auto-generated
															// catch
															// block
															return e.toString();
														}
													}
												}) });

										grid.setAdapter(adapter);
										CalculateTotal();
									} else {
										if (page == 0) {
											adapter.setDataSource(lstJsonObjects);
											CalculateTotal();
										} else {
											adapter.getDataSource().addAll(lstJsonObjects);
											CalculateTotal();
										}
										adapter.notifyDataSetChanged();
									}

									// if (json.equals("[]") ||
									// lstJsonObjects.size() <= 10)
									// footerGrid.setVisibility(View.GONE);
									Util.checkSearchReport = false;
								}
							}
						});

						// TODO Auto-generated method stub

					}

					@Override
					public void process(String key, int status, List<JSONObject> lst) {
						// TODO Auto-generated method stub

					}
				});
	}

	// }

	

	private void CalculateTotal() {
		// TODO Auto-generated method stub
		int totalSale = 0;
		int totalPromotion = 0;
		int totalMoney = 0;
		int iteamLo = 0;
		for (int jx = 0; jx <= lstJsonObjects.size() - 1; jx++) {
			try {
				if (lstJsonObjects.get(jx).getInt("DetailType") == 3) {
					iteamLo = lstJsonObjects.get(jx).getInt("Quantity") * lstJsonObjects.get(jx).getInt("QuantityLot");
					totalSale = totalSale + iteamLo;
				} else {
					totalSale = totalSale + lstJsonObjects.get(jx).getInt("Quantity");
				}
				if (lstJsonObjects.get(jx).getInt("DetailType") == 1
						&& lstJsonObjects.get(jx).getInt("QuantityLot") > 0) {
					totalPromotion = totalPromotion+lstJsonObjects.get(jx).getInt("QuantityLot");
				}
				int tien = lstJsonObjects.get(jx).getInt("TotalProducValue");
				totalMoney = totalMoney + tien;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		txttotalSale.setText(String.valueOf(totalPromotion));
		txtTotalPromotion.setText(String.valueOf(totalSale));
		txtTotalMoney.setText(String.valueOf(FormatUtil.formatCurrency((double) totalMoney)) + " vnd");
		if (lstJsonObjects.size() == 0) {
			txtNotification.setVisibility(View.VISIBLE);
			if (Util.checkSearchReport == true) {
				String reverseFD = TimeUtil.dateToString(TimeUtil.stringToDate(getFromDate, "yyyyMMdd"), "dd/MM/yyyy");
				String reverseTD = TimeUtil.dateToString(TimeUtil.stringToDate(getToDate, "yyyyMMdd"), "dd/MM/yyyy");
				txtNotification.setText("Từ ngày " + reverseFD + " đến ngày " + reverseTD + " đơn vị không bán hàng.");
			}
			// txtNotification.setText("Hôm nay đơn vị chưa bán hàng");

		}
	}

	private void CalculateTotal2() {
		// TODO Auto-generated method stub
		int totalSale = 0;
		int totalPromotion = 0;
		int totalMoney = 0;
		int iteamLo = 0;
		for (int jx = 0; jx <= lstJsonObjects.size() - 1; jx++) {
			try {
				if (lstJsonObjects.get(jx).getInt("DetailType") == 3) {
					iteamLo = lstJsonObjects.get(jx).getInt("Quantity") * lstJsonObjects.get(jx).getInt("QuantityLot");
					totalSale = totalSale + iteamLo;
				} else {
					totalSale = totalSale + lstJsonObjects.get(jx).getInt("Quantity");
				}
				if (lstJsonObjects.get(jx).getInt("DetailType") == 1
						&& lstJsonObjects.get(jx).getInt("QuantityLot") > 0) {
					totalPromotion =totalPromotion+ lstJsonObjects.get(jx).getInt("QuantityLot");
				}
				int tien = lstJsonObjects.get(jx).getInt("TotalProducValue");
				totalMoney = totalMoney + tien;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		txttotalSale.setText(String.valueOf(totalPromotion));
		txtTotalPromotion.setText(String.valueOf(totalSale));
		txtTotalMoney.setText(String.valueOf(FormatUtil.formatCurrency((double) totalMoney)) + " vnd");
		if (lstJsonObjects.size() == 0) {
			txtNotification.setVisibility(View.VISIBLE);
			// if (Util.checkSearchReport == true) {
			// String reverseFD =
			// TimeUtil.dateToString(TimeUtil.stringToDate(getFromDate,
			// "yyyyMMdd"), "dd/MM/yyyy");
			// String reverseTD =
			// TimeUtil.dateToString(TimeUtil.stringToDate(getToDate,
			// "yyyyMMdd"), "dd/MM/yyyy");
			// txtNotification.setText("Từ ngày " + reverseFD + " đến ngày " +
			// reverseTD + " đơn vị không bán hàng.");
			// }
			txtNotification.setText("Hôm nay đơn vị chưa bán hàng");

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
			Fragment_Report_Sale_Detail_List editNameDialog = new Fragment_Report_Sale_Detail_List();

			editNameDialog.setDataBundle(Fragment_Report_Sale_List.this);
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
	public void bundleDataDate(String fromDate, String toDate, String productCode) {
		// TODO Auto-generated method stub
		getProductCode = productCode;
		getFromDate = fromDate;
		getToDate = toDate;
		SearchReportSale();
	}

}