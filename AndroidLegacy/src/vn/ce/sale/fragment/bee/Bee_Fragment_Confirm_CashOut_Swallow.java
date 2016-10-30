package vn.ce.sale.fragment.bee;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.annotation.SuppressLint;
import android.app.Dialog;
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

public class Bee_Fragment_Confirm_CashOut_Swallow extends ZopostFragment implements ICallBackActivityToFragment {
	String user, amount, toUser;
	TextView txtNotifi, txtNameMerchant, txtBalanceConfirm;
	EditText edtMoneyPaymentCashOut, edtWithdrawalFeesCashOut, edtConfirmMoney, edtConfirmContent;
	EditText edtPhoneMerchant, edtNameMerchant, edtBalanceConfirm, edtAccConfirmCO;
	Button btnOkConfirmCashOut;
	LinearLayout lnlHeader;
	String dt,ct;

	public static Bee_Fragment_Confirm_CashOut_Swallow newInstance(String param1, String param2) {
		Bee_Fragment_Confirm_CashOut_Swallow fragment = new Bee_Fragment_Confirm_CashOut_Swallow();
		Bundle args = new Bundle();
		args.putString("ARG_PARAM1", param1);
		args.putString("ARG_PARAM2", param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			dt = getArguments().getString("ARG_PARAM1");
			ct = getArguments().getString("ARG_PARAM2");
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.bee_fragment_confirm_cashout_swallow, container, false);
		txtNotifi = (TextView) rootView.findViewById(R.id.txtNotifi);
		edtConfirmContent = (EditText) rootView.findViewById(R.id.edtConfirmContent);
		edtConfirmMoney = (EditText) rootView.findViewById(R.id.edtConfirmMoney);
		edtMoneyPaymentCashOut = (EditText) rootView.findViewById(R.id.edtMoneyPaymentCashOut);
		edtWithdrawalFeesCashOut = (EditText) rootView.findViewById(R.id.edtWithdrawalFeesCashOut);
		edtNameMerchant = (EditText) rootView.findViewById(R.id.edtNameMerchant);
		edtPhoneMerchant = (EditText) rootView.findViewById(R.id.edtPhoneMerchant);
		edtBalanceConfirm = (EditText) rootView.findViewById(R.id.edtBalanceConfirm);
		edtAccConfirmCO = (EditText) rootView.findViewById(R.id.edtAccConfirmCO);
		txtNameMerchant = (TextView) rootView.findViewById(R.id.txtNameMerchant);
		txtBalanceConfirm = (TextView) rootView.findViewById(R.id.txtBalanceConfirm);
		btnOkConfirmCashOut = (Button) rootView.findViewById(R.id.btnOkConfirmCashOut);
		lnlHeader = (LinearLayout) rootView.findViewById(R.id.lnlHeader);
		String[] parts = dt.split("\\&");
		edtPhoneMerchant.setText(parts[0]);
		edtNameMerchant.setText(FormatUtil.formatCurrency(Double.parseDouble(parts[1]))+" (VND)");
		txtNameMerchant.setText(FormatUtil.numberToString(Double.parseDouble(parts[1])));
		edtAccConfirmCO.setText(parts[2]);
		toUser = parts[2];
		edtBalanceConfirm.setText(FormatUtil.formatCurrency(Double.parseDouble(parts[3]))+" (VND)");
		txtBalanceConfirm.setText(FormatUtil.numberToString(Double.parseDouble(parts[3])));
		edtConfirmContent.setText(ct);

		edtConfirmMoney.setText(FormatUtil.formatCurrency(Double.parseDouble(parts[4]))+" (VND)");
		amount = parts[4];
		edtWithdrawalFeesCashOut.setText("0 (VND)");
		edtMoneyPaymentCashOut.setText(FormatUtil.formatCurrency(Double.parseDouble(parts[4]))+" (VND)");
		btnOkConfirmCashOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(getActivity());
				dialog.setTitle("Xác Nhận Mật Khẩu!");
				// dialog.setCancelable(false);
				dialog.setContentView(R.layout.bee_custom_dialog_cashin_off_swallow);
				final TextView txtNoti = (TextView) dialog.findViewById(R.id.txtNoti);
				final EditText edtPass = (EditText) dialog.findViewById(R.id.edtPassworkConfirm);
				Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
				Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
				btnOk.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (edtPass.getText().toString().equals("")) {
							txtNoti.setVisibility(View.VISIBLE);
							txtNoti.setText("Bạn phải nhập mật khẩu.");
						} else {
							String p = ShareMemManager.getInstance().readFromDB(getActivity(), "password");
							if (edtPass.getText().toString().equalsIgnoreCase(p)) {
								txtNoti.setVisibility(View.GONE);
								dialog.dismiss();
								payment();
							} else {
								txtNoti.setVisibility(View.VISIBLE);
								txtNoti.setText("Mật khẩu không đúng.");
							}
						}

					}
				});
				btnCancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		});
		return rootView;
	}

	private void payment() {
		user = ShareMemManager.getInstance().readFromDB(getActivity(), "username");
		String params = "API/cashout?username=" + user + "&amount=" + amount + "&toUserName=" + toUser;
		final ProgressDialog pd = new ProgressDialog(getContext());
		pd.setMessage("Đang gửi dữ liệu xác nhận!");
		pd.setCancelable(false);
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
									txtNotifi.setVisibility(View.GONE);
									confirm(o.getString("data"));
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

	private void confirm(String data) {
		String params = "API/confirmPayment?username=" + user + "&idtransaction=" + data + "";
		final ProgressDialog pd = new ProgressDialog(getContext());
		pd.setMessage("Đang gửi dữ liệu xác nhận!");
		pd.setCancelable(false);
		pd.show();
		ServiceManager.factoryData().getDataRaw("http://callapi.chimen.xyz/", params, new ICallBackUI() {

			@Override
			public void processRaw(String key, final int statusx, final String json) {
				runOnUiThreadX(new Runnable() {

					@SuppressWarnings({ "unused", "static-access" })
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
									txtNotifi.setVisibility(View.VISIBLE);
									txtNotifi.setText("Giao dịch thành công");
									btnOkConfirmCashOut.setVisibility(View.GONE);
									lnlHeader.setVisibility(View.GONE);
								}else{
									txtNotifi.setVisibility(View.VISIBLE);
									txtNotifi.setText(o.getString("message"));
									lnlHeader.setVisibility(View.GONE);
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