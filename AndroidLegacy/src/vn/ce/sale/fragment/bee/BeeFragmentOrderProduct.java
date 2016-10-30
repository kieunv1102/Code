package vn.ce.sale.fragment.bee;

//import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.adapter.CustomGrid;
import vn.ce.sale.adapter.CustomGridAndFilter;
import vn.ce.sale.adapter.CustomList;
import vn.ce.sale.adapter.DisplayOrderProductGrid;
import vn.ce.sale.adapter.bee.DisplayOrderEcomProductGrid;
import vn.ce.sale.data.DBManager;
import vn.ce.sale.data.ICallBack;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.DownloadImageTask;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ImageLoadingHolder;
import vn.ce.sale.ui.TypeUI;
import vn.ce.sale.ui.ZopostActivity;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.ui.ZopostValue;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.HashData;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.R.id;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BeeFragmentOrderProduct extends ZopostFragment {
	private ListView grid;
	Button btnOrderProductServer;
	TextView txtHelloUser, txtNotification;
	RelativeLayout rllOrderProduct;
	private DisplayOrderEcomProductGrid adapter;
	int page;
	View footer;
	View header;
	int total = 0;
	int p;
	List<JSONObject> lstJsonOrder = new ArrayList<JSONObject>();

	protected void initCreatedView() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// getActivity().setTitle("Giỏ Hàng");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// setup ui
		rootView = inflater.inflate(R.layout.bee_fragment_order_product, container, false);
		footer = inflater.inflate(R.layout.header, null);
		txtHelloUser = (TextView) footer.findViewById(R.id.txtHelloUser);
		txtNotification = (TextView) rootView.findViewById(R.id.txtNotification);
		rllOrderProduct = (RelativeLayout) rootView.findViewById(R.id.rllOrderProduct);
		btnOrderProductServer = (Button) rootView.findViewById(R.id.btnOrderProductServer);
		btnOrderProductServer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
				b.setCancelable(false);
				b.setMessage("Bạn chắc chắn muốn đặt hàng số sản phẩm này?");
				b.setNegativeButton("Có", new DialogInterface.OnClickListener() {

					@SuppressWarnings("unused")
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						LayoutInflater inflater = (LayoutInflater) getActivity()
								.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						final Dialog dialogCustomer = new Dialog(getActivity());
						dialogCustomer.setCancelable(true);
						dialogCustomer.setTitle("Nhập thông tin khách hàng!");
						View view = inflater.inflate(R.layout.bee_dialog_customer_information, null);
						final TextView txtNotiOrder = (TextView) view.findViewById(R.id.txtNotiOrder);
						final AutoCompleteTextView nameCustomer = (AutoCompleteTextView) view
								.findViewById(R.id.edtNameCustomer);
						final EditText phoneCustomer = (EditText) view.findViewById(R.id.edtPhoneCustomer);
						final EditText addressCustomer = (EditText) view.findViewById(R.id.edtAddressCustomer);
						Button btnOk = (Button) view.findViewById(R.id.btnOkCustomerInformation);
						Button btnCancel = (Button) view.findViewById(R.id.btnCancelCustomerInformation);
						dialogCustomer.setContentView(view);
						btnOk.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								String regex11 = "01?[0-9]{9}$";
								String regex10 = "09?[0-9]{8}$";

								if (phoneCustomer.getText().toString().equals("")
										|| nameCustomer.getText().toString().equals("")
										|| addressCustomer.getText().toString().equals("")) {
									txtNotiOrder.setVisibility(View.VISIBLE);
									txtNotiOrder.setText("Bạn phải nhập đầy đủ thông tin cần thiết!");
								} else {
									if (phoneCustomer.getText().toString().length() < 10
											|| phoneCustomer.getText().toString().length() > 11) {
										txtNotiOrder.setVisibility(View.VISIBLE);
										txtNotiOrder.setText("Định dạng số điện thoại không đúng!");
									} else {
										if (phoneCustomer.getText().toString().length() == 10) {
											Pattern pattern10 = Pattern.compile(regex10);
											Matcher matcher10 = pattern10.matcher(phoneCustomer.getText().toString());
											if (matcher10.matches()) {
												orderProduct2(phoneCustomer.getText().toString(),
														nameCustomer.getText().toString(),
														addressCustomer.getText().toString());
												dialogCustomer.dismiss();
											} else {
												txtNotiOrder.setVisibility(View.VISIBLE);
												txtNotiOrder.setText("Định dạng số điện thoại không đúng");
											}

										} else if (phoneCustomer.getText().toString().length() == 11) {
											Pattern pattern11 = Pattern.compile(regex11);
											Matcher matcher11 = pattern11.matcher(phoneCustomer.getText().toString());
											if (matcher11.matches()) {
												orderProduct2(phoneCustomer.getText().toString(),
														nameCustomer.getText().toString(),
														addressCustomer.getText().toString());
												dialogCustomer.dismiss();
											} else {
												txtNotiOrder.setVisibility(View.VISIBLE);
												txtNotiOrder.setText("Định dạng số điện thoại không đúng");
											}

										}
									}

								}
							}
						});
						btnCancel.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialogCustomer.dismiss();
							}
						});
						dialogCustomer.show();
						dialog.dismiss();
					}
				});
				b.setPositiveButton("Không", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				b.show();

			}
		});
		grid = (ListView) rootView.findViewById(R.id.grid);
		grid.addFooterView(footer);
		String arr = ShareMemManager.getInstance().readFromDB(getActivity(), "productOrder");
		List<JSONObject> lstJson = new ArrayList<JSONObject>();
		try {
			lstJson = TransformDataManager.convertArrayToListJSON(new JSONArray(arr));
			for (int i = lstJson.size() - 1; i >= 0; --i) {
				lstJsonOrder.add(lstJson.get(i));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		adapter = new DisplayOrderEcomProductGrid(getActivity(), lstJsonOrder);
		adapter.setFooter(footer);
		if (adapter.getCount() > 0) {
			rllOrderProduct.setVisibility(View.VISIBLE);
		} else {
			txtNotification.setVisibility(View.VISIBLE);
			txtNotification.setText("Chưa có sản phẩm trong giỏ hàng!");
			rllOrderProduct.setVisibility(View.GONE);
		}
		grid.setAdapter(adapter);

		totalMoney();
		adapter.notifyDataSetChanged();
		setupUI();

		return rootView;// return super.onCreateView(inflater, container,
						// savedInstanceState);
	}

	private void orderProduct2(String phone, String name, String address) {
		String sJsonObject = "";

		for (int jx = 0; jx <= adapter.getDataSource().size() - 1; jx++) {

			sJsonObject += "{";
			try {

				sJsonObject += "\"Quantity\":\"" + adapter.getDataSource().get(jx).get("SL");
				sJsonObject += "\",\"ProductID\":\"" + adapter.getDataSource().get(jx).get("Id");
				sJsonObject += "\",\"ProductName\":\"" + adapter.getDataSource().get(jx).get("Name");
				sJsonObject += "\",\"ProductPrice\":\"" + adapter.getDataSource().get(jx).get("PRICE");
				sJsonObject += "\",\"UnitName\":\"" + adapter.getDataSource().get(jx).get("UNAME");
				sJsonObject += "\",\"UnitID\":\"" + adapter.getDataSource().get(jx).get("UID");
				sJsonObject += "\",\"TT\":\"" + adapter.getDataSource().get(jx).get("TT");
				sJsonObject += "\",\"DetailType\":" + 1;

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sJsonObject += "}";
			if (jx != adapter.getDataSource().size() - 1)
				sJsonObject = sJsonObject + ",";
		}
		String data = "[" + sJsonObject + "]";
		Random rand = new Random();
		int n = rand.nextInt(50) + 1;
		try {
			JSONObject o = new JSONObject();
			o.put("MaDH", "DH" + n + "" + n);
			o.put("TrangThai", "DH moi");
			o.put("HoTen", name);
			o.put("Phone", phone);
			o.put("Item", data);
			Util.orderDataLocal(getActivity(), o);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ShareMemManager.getInstance().deleteFromDB(getActivity(), "productOrder");
		txtNotification.setVisibility(View.VISIBLE);
		txtNotification.setText("Đặt hàng thành công!");
		btnOrderProductServer.setVisibility(View.GONE);
		adapter.notifyDataSetChanged();
	}

	private void orderProduct(String phone, String name, String address) {
		// Toast.makeText(getActivity(), phone + name + address,
		// Toast.LENGTH_SHORT).show();
		String sJsonObject = "";

		for (int jx = 0; jx <= adapter.getDataSource().size() - 1; jx++) {
			sJsonObject += "{";
			try {
				sJsonObject += "\"Quantity\":" + adapter.getDataSource().get(jx).get("SL");
				sJsonObject += ",\"ProductID\":" + adapter.getDataSource().get(jx).get("Id");
				sJsonObject += ",\"UnitName\":" + adapter.getDataSource().get(jx).get("UNAME");
				sJsonObject += ",\"UnitID\":" + adapter.getDataSource().get(jx).get("UID");
				sJsonObject += ",\"DetailType\":" + 1;

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sJsonObject += "}";
			if (jx != adapter.getDataSource().size() - 1)
				sJsonObject = sJsonObject + ",";
		}

		String params = "system=bee&data={\"ActionType\":\"ORDER\"" + ",\"UserName\":\"" + "tienbvv21"
				+ "\",\"Password\":\"" + ShareMemManager.getInstance().readFromDB(getContext(), "password")
				+ "\",\"FromDate\":\"" + "20150115000000" + "\",\"ToDate\":\"" + "20151216235959"
				+ "\",\"OrderItems\":[" + sJsonObject + "]" + ",\"AppInfo\":{\"Mobile\":\"" + phone + "\",\"Name\":\""
				+ name + "\",\"Address\":\"" + address + "\"}}";

		final ProgressDialog pd = new ProgressDialog(getContext());
		pd.setMessage("Đang gửi dữ liệu xác nhận!");
		pd.show();
		String u = Util.SERVER_URL + params;

		ServiceManager.factoryData().getDataRaw(Util.SERVER_URL, params, new ICallBackUI() {

			@Override
			public void processRaw(String key, final int statusx, final String json) {
				runOnUiThreadX(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						int status = statusx;
						// TODO Auto-generated method stub
						if (status == Util.ERROR_NETWORK) {
							pd.dismiss();
						}
						// phân tích để tiếp tục
						if (status == 200) {
							pd.dismiss();
							try {
								JSONObject o = new JSONObject(json);

								if (o.getString("ResponseCode").equalsIgnoreCase("00")) {
									String mess = o.getString("ResponseMessage");
									txtNotification.setVisibility(View.VISIBLE);
									txtNotification.setText(mess);
									// Toast.makeText(getActivity(), mess,
									// Toast.LENGTH_SHORT).show();

								}
								if (o.getString("ResponseCode").equalsIgnoreCase("01")) {
									String mess = o.getString("ResponseMessage");
									rllOrderProduct.setVisibility(View.GONE);
									txtNotification.setVisibility(View.VISIBLE);
									txtNotification.setText(mess);
									ShareMemManager.getInstance().deleteFromDB(getActivity(), "productOrder");
									// Toast.makeText(getActivity(), "Thành
									// công", Toast.LENGTH_SHORT).show();

								}

							} catch (Exception ex) {
								ex.printStackTrace();
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

	private void totalMoney() {
		// TODO Auto-generated method stub
		long totalMoney = sumAmount();
		txtHelloUser.setText("Tổng tiền: " + String.valueOf(FormatUtil.formatCurrency((double) totalMoney)));
	}

	private Long sumAmount() {

		Long totalMoneyNew = (long) 0;
		for (int i = 0; i < lstJsonOrder.size(); i++) {
			View row = adapter.getView(i, null, null);
			EditText txt1 = (EditText) row.findViewById(R.id.edtQuantityProduct);

			try {
				int tt = lstJsonOrder.get(i).getInt("TT");
				totalMoneyNew = totalMoneyNew + tt;

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return totalMoneyNew;
	}

	@Override
	protected void setupUI() {
		super.setupUI();
		setHasOptionsMenu(true);
	}

	@Override
	protected void initParamsForFragment() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void startLoadData() {
		params.getString("mabarcode");
	}

	@Override
	protected void fillDataSource(int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

}