package vn.ce.sale.fragment.bee;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.TimeUtil;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Bee_Fragment_CashIn_Off_Swallow extends ZopostFragment implements ICallBackActivityToFragment {
	String a, data, moneyAccount;
	Button btnOkCashIn;
	EditText edtAccountCIO, edtCashinSurplus, edtCashinMoney, edtCashinOffAccount, edtCashinOffMoneyAccount;
	Button btnCheck;
	TextView txtCashinSurplus, txtCashinOffMoneyAccount, txtNotifi;

	public static Bee_Fragment_CashIn_Off_Swallow newInstance(String param1) {
		Bee_Fragment_CashIn_Off_Swallow fragment = new Bee_Fragment_CashIn_Off_Swallow();
		Bundle args = new Bundle();
		args.putString("ARG_PARAM1", param1);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// ((Home) getActivity()).getSupportActionBar().show();
		// getActivity().setTitle("Chim Én");
		if (getArguments() != null) {
			a = getArguments().getString("ARG_PARAM1");
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.bee_fragment_cashin_off_swallow, container, false);
		btnOkCashIn = (Button) rootView.findViewById(R.id.btnOkCashIn);
		btnCheck = (Button) rootView.findViewById(R.id.btnCheckCIOAccount);
		edtAccountCIO = (EditText) rootView.findViewById(R.id.edtAccountCIO);
		edtCashinOffAccount = (EditText) rootView.findViewById(R.id.edtCashinOffAccount);
		edtCashinOffMoneyAccount = (EditText) rootView.findViewById(R.id.edtCashinOffMoneyAccount);
		edtCashinSurplus = (EditText) rootView.findViewById(R.id.edtCashinSurplus);
		edtCashinMoney = (EditText) rootView.findViewById(R.id.edtCashinMoney);
		txtCashinSurplus = (TextView) rootView.findViewById(R.id.txtCashinSurplus);
		txtCashinOffMoneyAccount = (TextView) rootView.findViewById(R.id.txtCashinOffMoneyAccount);
		txtNotifi = (TextView) rootView.findViewById(R.id.txtNotifi);
		btnOkCashIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				data = edtCashinSurplus.getText().toString() + "&" + edtCashinMoney.getText().toString() + "&"
						+ edtCashinOffAccount.getText().toString() + "&" + moneyAccount;
				FragmentTransaction fm = getFragmentManager().beginTransaction();
				fm.replace(R.id.container, new Bee_Fragment_Confirm_CashIn_Off().newInstance(data));
				fm.addToBackStack("tag");
				fm.commit();
			}
		});
		btnCheck.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getBalanceCheck(edtCashinOffAccount.getText().toString());
			}
		});
		getBalance();
		return rootView;
	}

	private void getBalance() {
		String user = ShareMemManager.getInstance().readFromDB(getActivity(), "username");
		edtAccountCIO.setText(user);
		String params = "API/GetBalance?username=" + user;
		final ProgressDialog pd = new ProgressDialog(getContext());
		pd.setMessage("Đang gửi dữ liệu xác nhận!");
		pd.show();
		ServiceManager.factoryData().getDataRaw("http://callapi.chimen.xyz/", params, new ICallBackUI() {

			@Override
			public void processRaw(String key, final int statusx, final String json) {
				runOnUiThreadX(new Runnable() {

					@SuppressWarnings("unused")
					@Override
					public void run() {
						// TODO Auto-generated method stub
						int status = statusx;
						// TODO Auto-generated method stub
						if (status == Util.ERROR_NETWORK) {
							pd.dismiss();
							txtNotifi.setVisibility(View.VISIBLE);
							txtNotifi.setText("Vui lòng kiểm tra lại kết nối.");
						}
						// phân tích để tiếp tục
						if (status == 200) {
							pd.dismiss();
							try {
								JSONObject o = new JSONObject(json);
								if (o.getString("code").equalsIgnoreCase("01")) {
									String data = o.getString("data");
									JSONObject obj = new JSONObject(data);
									String d = obj.getString("CreateDate");
									edtCashinSurplus.setText(FormatUtil.formatCurrency(obj.getDouble("Amount")));
									txtCashinSurplus.setText(FormatUtil.numberToString(obj.getDouble("Amount")));
								} else {
									txtNotifi.setVisibility(View.VISIBLE);
									txtNotifi.setText(o.getString("message"));
								}
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

	private void getBalanceCheck(String a) {
		String params = "API/GetBalance?username=" + a + "";
		final ProgressDialog pd = new ProgressDialog(getContext());
		pd.setMessage("Đang gửi dữ liệu xác nhận!");
		pd.show();
		ServiceManager.factoryData().getDataRaw("http://callapi.chimen.xyz/", params, new ICallBackUI() {

			@Override
			public void processRaw(String key, final int statusx, final String json) {
				runOnUiThreadX(new Runnable() {

					@SuppressWarnings("unused")
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
								String d = obj.getString("CreateDate");
								moneyAccount = String.valueOf(obj.getDouble("Amount"));
								edtCashinOffMoneyAccount.setText(FormatUtil.formatCurrency(obj.getDouble("Amount")));
								txtCashinOffMoneyAccount.setText(FormatUtil.numberToString(obj.getDouble("Amount")));
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
	protected void fillDataSource(int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void startLoadData() {
	}

	protected void refreshData() {
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void initParamsForFragment() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onParamsFromActivity(Bundle data) {
		// TODO Auto-generated method stub

	}

}