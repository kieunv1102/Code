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

public class Bee_Fragment_Confirm_Transfer_Swallow extends ZopostFragment implements ICallBackActivityToFragment {
	String dt, ct,amount,user;
	TextView txtNotifi, txtSurplusFromTransfer, txtSurplusToTransfer;
	EditText edtAccountFromTransfer, edtSurplusFromTransfer, edtAccountToTransfer, edtSurplusToTransfer,
			edtContentTransfer, edtMoneyTransfer,edtInputOTP;
	Button btnOkConfirmTransfer,btnOKOTP;
	LinearLayout lnHeader,lnlOTP;
	public static Bee_Fragment_Confirm_Transfer_Swallow newInstance(String param1, String param2) {
		Bee_Fragment_Confirm_Transfer_Swallow fragment = new Bee_Fragment_Confirm_Transfer_Swallow();
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
		rootView = inflater.inflate(R.layout.bee_fragment_confirm_transfer_swallow, container, false);
		txtNotifi = (TextView) rootView.findViewById(R.id.txtNotifi);
		txtSurplusFromTransfer = (TextView) rootView.findViewById(R.id.txtSurplusFromTransfer);
		txtSurplusToTransfer = (TextView) rootView.findViewById(R.id.txtSurplusToTransfer);
		edtAccountFromTransfer = (EditText) rootView.findViewById(R.id.edtAccountFromTransfer);
		edtSurplusFromTransfer = (EditText) rootView.findViewById(R.id.edtSurplusFromTransfer);
		edtAccountToTransfer = (EditText) rootView.findViewById(R.id.edtAccountToTransfer);
		edtSurplusToTransfer = (EditText) rootView.findViewById(R.id.edtSurplusToTransfer);
		edtContentTransfer = (EditText) rootView.findViewById(R.id.edtContentTransfer);
		edtMoneyTransfer = (EditText) rootView.findViewById(R.id.edtMoneyTransfer);
		edtInputOTP = (EditText) rootView.findViewById(R.id.edtInputOTP);
		btnOkConfirmTransfer = (Button) rootView.findViewById(R.id.btnOkConfirmTransfer);
		btnOKOTP = (Button) rootView.findViewById(R.id.btnOKOTP);
		lnlOTP = (LinearLayout)rootView.findViewById(R.id.lnlOTP);
		lnHeader  = (LinearLayout)rootView.findViewById(R.id.lnlHeader);
		String p[] = dt.split("&");
		amount = p[4];
		edtAccountFromTransfer.setText(p[0]);
		edtSurplusFromTransfer.setText(FormatUtil.formatCurrency(Double.parseDouble(p[1])));
		txtSurplusFromTransfer.setText(FormatUtil.numberToString(Double.parseDouble(p[1])));
		edtAccountToTransfer.setText(p[2]);
//		edtSurplusToTransfer.setText(FormatUtil.formatCurrency(Double.parseDouble(p[3])));
//		txtSurplusFromTransfer.setText(FormatUtil.numberToString(Double.parseDouble(p[3])));
		edtMoneyTransfer.setText(FormatUtil.formatCurrency(Double.parseDouble(p[4])));
		edtContentTransfer.setText(ct);
		btnOkConfirmTransfer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				payment();
			}
		});
		btnOKOTP.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (edtInputOTP.getText().toString().equalsIgnoreCase("OTP")) {
					lnlOTP.setVisibility(View.GONE);
					txtNotifi.setVisibility(View.VISIBLE);
					txtNotifi.setText("Giao dịch thành công");
				}
			}
		});
		return rootView;
	}

	private void payment() {
		user = ShareMemManager.getInstance().readFromDB(getActivity(), "username");
		String params = "API/TransferToWallet?fromUser=" + user + "&toUser=" + edtAccountToTransfer.getText().toString() + "&amount="+amount;
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
		String params = "API/confirmPayment?username=" + user + "&idtransaction=" + data;
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
									txtNotifi.setVisibility(View.GONE);
									lnlOTP.setVisibility(View.VISIBLE);
									lnHeader.setVisibility(View.GONE);
									btnOkConfirmTransfer.setVisibility(View.GONE);
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