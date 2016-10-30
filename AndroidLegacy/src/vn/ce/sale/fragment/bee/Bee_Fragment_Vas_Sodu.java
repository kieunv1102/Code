package vn.ce.sale.fragment.bee;

import java.text.SimpleDateFormat;
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
import vn.ce.sale.fragment.airtimemix.Fragment_Report_Vtopup_Detail_List;
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

public class Bee_Fragment_Vas_Sodu extends ZopostFragment implements ICallBackActivityToFragment, IData {
	TextView txtVasSodu, txtVasSoduText, txtVasSoduUser, txtVasSoduDate;
	int tien, tsd;

	public static Bee_Fragment_Vas_Sodu newInstance() {
		Bee_Fragment_Vas_Sodu fragment = new Bee_Fragment_Vas_Sodu();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.bee_fragment_vas_sodu, container, false);
		txtVasSodu = (TextView) rootView.findViewById(R.id.txtVasSodu);
		txtVasSoduText = (TextView) rootView.findViewById(R.id.txtVasSoduText);
		txtVasSoduUser = (TextView) rootView.findViewById(R.id.txtVasSoduUser);
		txtVasSoduDate = (TextView) rootView.findViewById(R.id.txtVasSoduDate);
		getBalance();
		return rootView;
	}

	private void getBalance() {
		String user = ShareMemManager.getInstance().readFromDB(getActivity(), "username");
		String params = "API/GetBalance?username=" + user;
		final ProgressDialog pd = new ProgressDialog(getContext());
		pd.setMessage("Đang gửi dữ liệu xác nhận!");
		pd.show();
		ServiceManager.factoryData().getDataRaw("http://callapi.chimen.xyz/", params, new ICallBackUI() {

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
							// txtVasSodu.setVisibility(View.VISIBLE);
							// txtVasSodu.setText("Vui lòng kiểm tra lại kết
							// nối.");
						}
						// phân tích để tiếp tục
						if (status == 200) {
							pd.dismiss();
							try {
								JSONObject o = new JSONObject(json);
								String data = o.getString("data");
								JSONObject obj = new JSONObject(data);
								txtVasSoduUser
										.setText(ShareMemManager.getInstance().readFromDB(getActivity(), "username"));
								// tien = obj.getInt("Amount");
								tien = 193935146;
								// ShareMemManager.getInstance().saveToDB(getActivity(),
								// "sodu", String.valueOf(tien));
								if (Util.checkThanhtoan) {
									@SuppressWarnings("unused")
									String ststt = ShareMemManager.getInstance().readFromDB(getActivity(), "sodu");
									if (ststt == null || ststt.equalsIgnoreCase("")) {
										String tttt = ShareMemManager.getInstance().readFromDB(getActivity(),
												"TienThanhToan");
										//int tstt = Integer.parseInt(ststt);
										txtVasSodu.setText(
												FormatUtil.formatCurrency((double) (tien - Integer.parseInt(tttt))));
										txtVasSoduText
												.setText(FormatUtil.numberToString(tien - Integer.parseInt(tttt)));
										ShareMemManager.getInstance().saveToDB(getActivity(), "sodu",
												String.valueOf(tien - Integer.parseInt(tttt)));
										Util.checkThanhtoan = false;
									} else {
										String tttt = ShareMemManager.getInstance().readFromDB(getActivity(),
												"TienThanhToan");
										int tstt = Integer.parseInt(ststt);
										txtVasSodu.setText(
												FormatUtil.formatCurrency((double) (tstt - Integer.parseInt(tttt))));
										txtVasSoduText
												.setText(FormatUtil.numberToString(tstt - Integer.parseInt(tttt)));
										ShareMemManager.getInstance().saveToDB(getActivity(), "sodu",
												String.valueOf(tstt - Integer.parseInt(tttt)));
										Util.checkThanhtoan = false;
									}
								} else {
									String tsdd = ShareMemManager.getInstance().readFromDB(getActivity(), "sodu");
									if (tsdd == null || tsdd.equalsIgnoreCase("")) {
										tsd = tien;
									} else {
										tsd = Integer.parseInt(tsdd);
									}
									txtVasSodu.setText(FormatUtil.formatCurrency((double) tsd));
									txtVasSoduText.setText(FormatUtil.numberToString(tsd));
								}
								String d = obj.getString("CreateDate");
								txtVasSoduDate.setText(TimeUtil.dateToString(
										new Date(Util.extractDateFromServerOrder(d)), "dd/MM/yyyy HH:mm:ss"));

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
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
	}

	@Override
	protected void startLoadData() {
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

}