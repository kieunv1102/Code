package vn.ce.sale.fragment.bee;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.service.ServiceManager;
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
import android.widget.LinearLayout;
import android.widget.TextView;

public class Bee_Fragment_CashOut_Swallow extends ZopostFragment implements ICallBackActivityToFragment {
	Button btnOkCashIn, btnCheckCashOut;
	EditText edtMerchantPhone, edtMerchantName, edtSurplusUser, edtMoneyPay, edtContent, edtCashOutNamePay,
			edtAccountCO;
	TextView txtSurplusUser, txtMerchantName, txtNotifi;
	String data, MerchantName, SurplusUser;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.bee_fragment_cashout_swallow, container, false);
		edtMerchantName = (EditText) rootView.findViewById(R.id.edtMerchantName);
		edtMerchantPhone = (EditText) rootView.findViewById(R.id.edtMerchantPhone);
		edtSurplusUser = (EditText) rootView.findViewById(R.id.edtSurplusUser);
		txtSurplusUser = (TextView) rootView.findViewById(R.id.txtSurplusUser);
		txtMerchantName = (TextView) rootView.findViewById(R.id.txtMerchantName);
		txtNotifi = (TextView) rootView.findViewById(R.id.txtNotifi);
		edtMoneyPay = (EditText) rootView.findViewById(R.id.edtMoneyPay);
		edtContent = (EditText) rootView.findViewById(R.id.edtContent);
		edtCashOutNamePay = (EditText) rootView.findViewById(R.id.edtCashOutNamePay);
		edtAccountCO = (EditText) rootView.findViewById(R.id.edtAccountCO);
		btnOkCashIn = (Button) rootView.findViewById(R.id.btnOkCashIn);
		btnCheckCashOut = (Button) rootView.findViewById(R.id.btnCheckCashOut);

		getBalanceMerchant();

		btnCheckCashOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (edtAccountCO.getText().toString().equalsIgnoreCase("")) {
					txtNotifi.setVisibility(View.VISIBLE);
					txtNotifi.setText("Bạn phải nhập tài khoản");
				} else
					getBalanceUser();
			}
		});
		btnOkCashIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (edtMoneyPay.getText().toString().equalsIgnoreCase("")
						&& edtAccountCO.getText().toString().equalsIgnoreCase("")) {
					txtNotifi.setVisibility(View.VISIBLE);
					txtNotifi.setText("Bạn phải nhập thông tin cần thiết để giao dịch");
				} else if (edtMoneyPay.getText().toString().equalsIgnoreCase("")) {
					txtNotifi.setVisibility(View.VISIBLE);
					txtNotifi.setText("Bạn phải nhập số tiền");
				} else if (edtAccountCO.getText().toString().equalsIgnoreCase("")) {
					txtNotifi.setVisibility(View.VISIBLE);
					txtNotifi.setText("Bạn phải nhập tài khoản rút tiền");
				} else {
					data = edtMerchantPhone.getText().toString() + "&" + MerchantName + "&"
							+ edtAccountCO.getText().toString() + "&" + SurplusUser + "&"
							+ edtMoneyPay.getText().toString();
					FragmentTransaction fm = getFragmentManager().beginTransaction();
					fm.replace(R.id.container, new Bee_Fragment_Confirm_CashOut_Swallow().newInstance(data,
							edtContent.getText().toString()));
					fm.addToBackStack("tag");
					fm.commit();
				}
			}
		});

		return rootView;
	}

	private void getBalanceMerchant() {
		String user = ShareMemManager.getInstance().readFromDB(getActivity(), "username");
		edtMerchantPhone.setText(user);
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
									MerchantName = String.valueOf(obj.getInt("Amount"));
									edtMerchantName.setText(FormatUtil.formatCurrency(obj.getDouble("Amount")));
									txtMerchantName.setText(FormatUtil.numberToString(obj.getDouble("Amount")));
								}else{
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

	private void getBalanceUser() {
		String u = edtAccountCO.getText().toString();
		String params = "API/GetBalance?username=" + u;
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
							txtNotifi.setVisibility(View.VISIBLE);
							txtNotifi.setText("Vui lòng kiểm tra lại kết nối.");
						}
						// phân tích để tiếp tục
						if (status == 200) {
							pd.dismiss();
							try {
								JSONObject o = new JSONObject(json);
								String data = o.getString("data");
								JSONObject obj = new JSONObject(data);
								SurplusUser = String.valueOf(obj.getInt("Amount"));
								edtSurplusUser.setText(FormatUtil.formatCurrency(obj.getDouble("Amount")));
								txtSurplusUser.setText(FormatUtil.numberToString(obj.getDouble("Amount")));
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

	public String encryptMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
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