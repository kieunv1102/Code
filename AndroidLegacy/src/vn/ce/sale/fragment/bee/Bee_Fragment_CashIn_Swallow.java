package vn.ce.sale.fragment.bee;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Bee_Fragment_CashIn_Swallow extends ZopostFragment implements ICallBackActivityToFragment {
	String a;
	Button btnOkCashIn;
	EditText edtMoneyCashIn, edtMoney, edtAccountCION, edtSoDuCION;
	TextView txtSoDuCION, txtNofiCIO;
	String user, sd;

	public static Bee_Fragment_CashIn_Swallow newInstance(String param1) {
		Bee_Fragment_CashIn_Swallow fragment = new Bee_Fragment_CashIn_Swallow();
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
		rootView = inflater.inflate(R.layout.bee_fragment_cashin_swallow, container, false);
		edtMoneyCashIn = (EditText) rootView.findViewById(R.id.edtMoneyCashIn);
		edtMoney = (EditText) rootView.findViewById(R.id.edtMoney);
		edtAccountCION = (EditText) rootView.findViewById(R.id.edtAccountCION);
		edtSoDuCION = (EditText) rootView.findViewById(R.id.edtSoDuCION);
		txtSoDuCION = (TextView) rootView.findViewById(R.id.txtSoDuCION);
		txtNofiCIO = (TextView) rootView.findViewById(R.id.txtNofiCIO);
		edtMoneyCashIn.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				edtMoney.setText(s);
			}
		});

		btnOkCashIn = (Button) rootView.findViewById(R.id.btnOkCashIn);
		btnOkCashIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (edtMoneyCashIn.getText().toString().equalsIgnoreCase("")) {
					txtNofiCIO.setVisibility(View.VISIBLE);
					txtNofiCIO.setText("Bạn phải nhập số tiền");
				} else {
					if (Integer.parseInt(edtMoneyCashIn.getText().toString()) < 10000) {
						txtNofiCIO.setVisibility(View.VISIBLE);
						txtNofiCIO.setText("Bạn phải nhập số tiền có giá trị lớn hơn 10000");
					} else {
						String data = user + "&" + sd + "&" + edtMoneyCashIn.getText().toString();
						FragmentTransaction fm = getFragmentManager().beginTransaction();
						fm.replace(R.id.container, new Bee_Fragment_Confirm_CashIn().newInstance(data));
						fm.addToBackStack("tag");
						fm.commit();
					}
				}
			}
		});
		getBalance();
		return rootView;
	}

	private void getBalance() {
		user = ShareMemManager.getInstance().readFromDB(getActivity(), "username");
		edtAccountCION.setText(user);
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
							txtNofiCIO.setVisibility(View.VISIBLE);
							txtNofiCIO.setText("Vui lòng kiểm tra lại kết nối.");
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
									sd = String.valueOf(obj.getInt("Amount"));
									edtSoDuCION.setText(FormatUtil.formatCurrency(obj.getDouble("Amount"))+" (VND)");
									txtSoDuCION.setText(FormatUtil.numberToString(obj.getDouble("Amount")));
								} else {
									txtNofiCIO.setVisibility(View.VISIBLE);
									txtNofiCIO.setText(o.getString("message"));
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