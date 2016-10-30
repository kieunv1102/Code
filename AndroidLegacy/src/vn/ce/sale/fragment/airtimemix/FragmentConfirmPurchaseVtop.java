package vn.ce.sale.fragment.airtimemix;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.MaHoa;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.TimeUtil;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;

public class FragmentConfirmPurchaseVtop extends ZopostFragment implements ICallBackActivityToFragment{

	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	private static final String ARG_PARAM3 = "param3";
	private static final String ARG_PARAM4 = "param4";
	String moneyPurchaseVtop,typePayment;
	TextView txtConfirm,txtNotification,txtDateBuyVtop,txtMoneyBuyVtop,txtVtop,txtSurplusVtop,txtTransactionFees,txtMoneyPayment;
	Button btnConfirmOk;
	long date = new Date().getTime();
	String user,data,dataUrl,amountVTop,balance;
	public static FragmentConfirmPurchaseVtop newInstance(String mpv,String tpm,String amv,String bl) {
		FragmentConfirmPurchaseVtop fragment = new FragmentConfirmPurchaseVtop();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, mpv);
		args.putString(ARG_PARAM2, tpm);
		args.putString(ARG_PARAM3, amv);
		args.putString(ARG_PARAM4, bl);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// ((Home) getActivity()).getSupportActionBar().show();
//		getActivity().setTitle("Xác nhận g");
		if (getArguments() != null) {
			moneyPurchaseVtop = getArguments().getString(ARG_PARAM1);
			typePayment = getArguments().getString(ARG_PARAM2);
			amountVTop = getArguments().getString(ARG_PARAM3);
			balance = getArguments().getString(ARG_PARAM4);;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.air_custom_dialog_confirm_purchase_vtop, container, false);
		txtConfirm = (TextView) rootView.findViewById(R.id.txtConfirm);
		txtNotification = (TextView) rootView.findViewById(R.id.txtNotification);
		txtDateBuyVtop = (TextView) rootView.findViewById(R.id.txtDateBuyVtop);
		txtMoneyBuyVtop = (TextView) rootView.findViewById(R.id.txtMoneyBuyVtop);
		txtVtop = (TextView) rootView.findViewById(R.id.txtVtop);
		txtSurplusVtop = (TextView) rootView.findViewById(R.id.txtSurplusVtop);
		txtTransactionFees = (TextView) rootView.findViewById(R.id.txtTransactionFees);
		txtMoneyPayment = (TextView) rootView.findViewById(R.id.txtMoneyPayment);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		txtDateBuyVtop.setText(dateFormat.format(date));
		txtMoneyBuyVtop.setText(FormatUtil.formatCurrency(Double.parseDouble(moneyPurchaseVtop)));
		txtVtop.setText(FormatUtil.formatCurrency(Double.parseDouble(amountVTop)));
		txtSurplusVtop.setText(FormatUtil.formatCurrency(Double.parseDouble(balance)));
		txtTransactionFees.setText("0");
		txtMoneyPayment.setText(FormatUtil.formatCurrency(Double.parseDouble(moneyPurchaseVtop)));
		btnConfirmOk = (Button) rootView.findViewById(R.id.btnConfirmOk);
		btnConfirmOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				payment();
			}
		});
		return rootView;
	}

	


	@SuppressWarnings("unused")
	private void payment() {
//		String money = moneyPurchaseVtop;
		user = ShareMemManager.getInstance().readFromDB(getActivity(), "username");
//		String txtmh = money + user + type;
//		String key = "AAAAAAAAAAAAAAAAAAAA";
//		user = "cuongnt";
		String md5 = encryptMD5(moneyPurchaseVtop + user + typePayment);
		String mh = MaHoa.encrypt("aaaaaaaaasyueie");
		String gm = MaHoa.decrypt(mh);
		String params = "API/payment?amount=" + moneyPurchaseVtop + "&username=" + user + "&signdata=" + md5 + "&typepayment="
				+ typePayment + "";
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
							txtConfirm.setVisibility(View.VISIBLE);
							txtConfirm.setText("Vui lòng kiểm tra lại kết nối.");
						}
						// phân tích để tiếp tục
						if (status == 200) {
							pd.dismiss();
							try {
								JSONObject o = new JSONObject(json);
								if (o.getString("code").equalsIgnoreCase("00")) {
									txtConfirm.setVisibility(View.VISIBLE);
									txtConfirm.setText(o.getString("message"));
								}
								if (o.getString("code").equalsIgnoreCase("01")) {
									txtConfirm.setVisibility(View.GONE);
									// txtPurchaseVtop.setText(o.getString("message"));
									data = o.getString("data");
									confirm();
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

	private void confirm() {
		String params = "API/confirmPayment?username=" + user + "&idtransaction=" + data + "";
		final ProgressDialog pd = new ProgressDialog(getContext());
		pd.setMessage("Đang gửi dữ liệu xác nhận!");
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
							txtConfirm.setVisibility(View.VISIBLE);
							txtConfirm.setText("Vui lòng kiểm tra lại kết nối.");
						}
						// phân tích để tiếp tục
						if (status == 200) {
							pd.dismiss();
							try {
								JSONObject o = new JSONObject(json);
								if (o.getString("code").equalsIgnoreCase("00")) {
									txtConfirm.setVisibility(View.VISIBLE);
									txtConfirm.setText(o.getString("message"));
								}
								if (o.getString("code").equalsIgnoreCase("01")) {
									txtConfirm.setVisibility(View.GONE);
									dataUrl = o.getString("data");
									String aa = dataUrl;
									ShareMemManager.getInstance().saveToDB(getActivity(), "urlWeb", dataUrl);
									ShareMemManager.getInstance().saveToDB(getActivity(), "dataUrl", dataUrl);
									ReplaceFragment(new FragmentVtopupPurchase().newInstance(""));
									// nextToFragmentAndKeepState(new
									// Bee_Fragment_Web_View(), null, true);
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

	// static String IV = "AAAAAAAAAAAAAAAA";
	// static String encryptionKey = "0123456789abcdef";
	// public static byte[] encryptaa(String plainText, String encryptionKey)
	// throws Exception {
	// Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
	// SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"),
	// "AES");
	// cipher.init(Cipher.ENCRYPT_MODE, key, new
	// IvParameterSpec(IV.getBytes("UTF-8")));
	// return cipher.doFinal(plainText.getBytes("UTF-8"));
	// }
	//
	// public static String decrypt(byte[] cipherText, String encryptionKey)
	// throws Exception {
	// Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
	// SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"),
	// "AES");
	// cipher.init(Cipher.DECRYPT_MODE, key, new
	// IvParameterSpec(IV.getBytes("UTF-8")));
	// return new String(cipher.doFinal(cipherText), "UTF-8");
	// }

	// Mã hóa md5 1 chiều
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

	@SuppressWarnings("unused")
	private void ReplaceFragment(Fragment fragment) {
		FragmentTransaction fm = ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction();
		fm.replace(R.id.container, fragment);
		fm.addToBackStack("tag");
		fm.commit();
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
		getActivity().setTitle(ShareMemManager.getInstance().readFromDB(getActivity(), "username"));
		return false;
	}

	@Override
	public void onParamsFromActivity(Bundle data) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initParamsForFragment() {
		// TODO Auto-generated method stub

	}

}