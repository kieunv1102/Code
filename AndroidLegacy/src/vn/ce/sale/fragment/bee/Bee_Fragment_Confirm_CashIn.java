package vn.ce.sale.fragment.bee;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.swing.text.AbstractDocument.Content;

import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.fragment.airtimemix.FragmentVtopupPurchase;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Bee_Fragment_Confirm_CashIn extends ZopostFragment implements ICallBackActivityToFragment {
	String a, amount;
	String user;
	EditText edtMoneyConfirm, edtMoney, edtTotalMoney, edtContent, edtAccConfirmCIO, edtSoDuConfirmCION;
	TextView txtMoneyConfirmText, txtMoneyText, txtTotalMoneyText, txtPTTT, txtSoDuConfirmCION;
	Button btnOkCashIn;
	TextView txtNotifi;

	public static Bee_Fragment_Confirm_CashIn newInstance(String param1) {
		Bee_Fragment_Confirm_CashIn fragment = new Bee_Fragment_Confirm_CashIn();
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
//		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.bee_fragment_confirm_cashin, container, false);
		edtMoneyConfirm = (EditText) rootView.findViewById(R.id.edtMoneyConfirm);
		edtMoney = (EditText) rootView.findViewById(R.id.edtMoney);
		edtTotalMoney = (EditText) rootView.findViewById(R.id.edtTotalMoney);
		edtContent = (EditText) rootView.findViewById(R.id.edtContent);
		edtAccConfirmCIO = (EditText) rootView.findViewById(R.id.edtAccConfirmCIO);
		edtSoDuConfirmCION = (EditText) rootView.findViewById(R.id.edtSoDuConfirmCION);
		txtSoDuConfirmCION = (TextView) rootView.findViewById(R.id.txtSoDuConfirmCION);
		txtMoneyConfirmText = (TextView) rootView.findViewById(R.id.txtMoneyConfirmText);
		txtMoneyText = (TextView) rootView.findViewById(R.id.txtMoneyText);
		txtTotalMoneyText = (TextView) rootView.findViewById(R.id.txtTotalMoneyText);
		txtNotifi = (TextView) rootView.findViewById(R.id.txtNotifi);
		String[] p = a.split("\\&");
		amount = p[2];
		edtAccConfirmCIO.setText(p[0]);
		edtSoDuConfirmCION.setText(FormatUtil.formatCurrency(Double.parseDouble(p[1]))+" (VND)");
		txtSoDuConfirmCION.setText(FormatUtil.numberToString(Double.parseDouble(p[1])));
		edtMoneyConfirm.setText(FormatUtil.formatCurrency(Double.parseDouble(p[2]))+" (VND)");
		edtMoney.setText(FormatUtil.formatCurrency(Double.parseDouble(p[2]))+" (VND)");
		edtTotalMoney.setText(FormatUtil.formatCurrency(Double.parseDouble(p[2]))+" (VND)");
		txtMoneyConfirmText.setText(FormatUtil.numberToString(Double.parseDouble(p[2])));
		txtMoneyText.setText(FormatUtil.numberToString(Double.parseDouble(p[2])));
		txtTotalMoneyText.setText(FormatUtil.numberToString(Double.parseDouble(p[2])));
		btnOkCashIn = (Button) rootView.findViewById(R.id.btnOkCashIn);
		btnOkCashIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				payment();
			}
		});
		return rootView;
	}

	private void payment() {
		String type = ShareMemManager.getInstance().readFromDB(getActivity(), "cashinOn");
		user = ShareMemManager.getInstance().readFromDB(getActivity(), "username");
		String params = "API/cashin?username=" + user + "&amount=" + amount + "&paymenttype=2";
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
								if (o.getString("code").equalsIgnoreCase("00")) {
									txtNotifi.setVisibility(View.VISIBLE);
									txtNotifi.setText(o.getString("message"));
								}
								if (o.getString("code").equalsIgnoreCase("01")) {
									txtNotifi.setVisibility(View.GONE);
									FragmentTransaction fm = getFragmentManager().beginTransaction();
									fm.replace(R.id.container, new Bee_Fragment_Web_View()
											.newInstance(o.getString("data"), edtContent.getText().toString()));
									fm.addToBackStack("tag");
									fm.commit();
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