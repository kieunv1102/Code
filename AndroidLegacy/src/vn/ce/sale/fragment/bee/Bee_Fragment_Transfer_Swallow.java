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

public class Bee_Fragment_Transfer_Swallow extends ZopostFragment implements ICallBackActivityToFragment {
	TextView txtNotifi, txtSurplusFromTransfer, txtSurplusToTransfer;
	EditText edtAccountFromTransfer, edtSurplusFromTransfer, edtAccountToTransfer, edtSurplusToTransfer,
			edtContentTransfer, edtMoneyTransfer;
	Button btnCheckTransfer, btnOkTransfer;
	String m1, m2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.bee_fragment_transfer_swallow, container, false);
		txtNotifi = (TextView) rootView.findViewById(R.id.txtNotifi);
		txtSurplusFromTransfer = (TextView) rootView.findViewById(R.id.txtSurplusFromTransfer);
		txtSurplusToTransfer = (TextView) rootView.findViewById(R.id.txtSurplusToTransfer);
		edtAccountFromTransfer = (EditText) rootView.findViewById(R.id.edtAccountFromTransfer);
		edtSurplusFromTransfer = (EditText) rootView.findViewById(R.id.edtSurplusFromTransfer);
		edtAccountToTransfer = (EditText) rootView.findViewById(R.id.edtAccountToTransfer);
		// edtSurplusToTransfer = (EditText)
		// rootView.findViewById(R.id.edtSurplusToTransfer);
		edtContentTransfer = (EditText) rootView.findViewById(R.id.edtContentTransfer);
		edtMoneyTransfer = (EditText) rootView.findViewById(R.id.edtMoneyTransfer);
//		btnCheckTransfer = (Button) rootView.findViewById(R.id.btnCheckTransfer);
		btnOkTransfer = (Button) rootView.findViewById(R.id.btnOkTransfer);
		getBalance();
//		btnCheckTransfer.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				getBalanceCheck();
//			}
//		});
		btnOkTransfer.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String data = edtAccountFromTransfer.getText().toString() + "&" + m1 + "&"
						+ edtAccountToTransfer.getText().toString() + "&" + m2 + "&"
						+ edtMoneyTransfer.getText().toString();
				FragmentTransaction fm = getFragmentManager().beginTransaction();
				fm.replace(R.id.container, new Bee_Fragment_Confirm_Transfer_Swallow().newInstance(data,
						edtContentTransfer.getText().toString()));
				fm.addToBackStack("tag");
				fm.commit();

			}
		});
		return rootView;
	}

	private void getBalance() {
		String user = ShareMemManager.getInstance().readFromDB(getActivity(), "username");
		edtAccountFromTransfer.setText(user);
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
									m1 = String.valueOf(obj.getInt("Amount"));
									edtSurplusFromTransfer.setText(FormatUtil.formatCurrency(obj.getDouble("Amount")));
									txtSurplusFromTransfer.setText(FormatUtil.numberToString(obj.getDouble("Amount")));
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

	private void getBalanceCheck() {
		String user = ShareMemManager.getInstance().readFromDB(getActivity(), "username");
		edtAccountFromTransfer.setText(user);
		String params = "API/GetBalance?username=" + edtAccountToTransfer.getText().toString();
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
									m2 = String.valueOf(obj.getInt("Amount"));
									edtSurplusToTransfer.setText(FormatUtil.formatCurrency(obj.getDouble("Amount")));
									txtSurplusToTransfer.setText(FormatUtil.numberToString(obj.getDouble("Amount")));
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