package vn.ce.sale.fragment.airtimemix;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.data.TransformDataManager;
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

public class FragmentPurchaseVtopBank extends ZopostFragment implements ICallBackActivityToFragment, OnClickListener {

	private static final String ARG_PARAM1 = "param1";
	TextView txtPurchaseVtop,edtDateBuyVtop,edtVtop;
	EditText edtMoney;
	Button btnOk;
	String user;
	String data;
	String dataUrl;
	String type;
	String account;
	long date = new Date().getTime();
	String amountVTop,balance;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// ((Home) getActivity()).getSupportActionBar().show();
		// getActivity().setTitle("VnPost");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.air_purchase_vtop_bank, container, false);
		txtPurchaseVtop = (TextView) rootView.findViewById(R.id.txtPurchaseVtop);
		edtMoney = (EditText) rootView.findViewById(R.id.edtMoney);
		edtDateBuyVtop = (TextView)rootView.findViewById(R.id.edtDateBuyVtop);
		edtVtop =(TextView)rootView.findViewById(R.id.edtVtop);
		btnOk = (Button) rootView.findViewById(R.id.btnOk);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		edtDateBuyVtop.setText(dateFormat.format(date));
		type = ShareMemManager.getInstance().readFromDB(getActivity(), "checkRadio");
		if (Integer.parseInt(type) == 1) {
			account = " bằng tài khoản Chim Én";
		}
		if (Integer.parseInt(type) == 2) {
			account = " bằng tài khoản Bank Net";
		}
		if (Integer.parseInt(type) == 3) {
			account = " bằng tài khoản Ngân Hàng";
		}
		btnOk.setOnClickListener(this);
		
		return rootView;
	}

	@SuppressWarnings("unused")
	@Override
	public void onClick(View v) {
		if (edtMoney.length() == 0) {
			txtPurchaseVtop.setVisibility(View.VISIBLE);
			txtPurchaseVtop.setText("Bạn phải nhập số tiền!");
		} else {
			txtPurchaseVtop.setVisibility(View.GONE);
			// TODO Auto-generated method stub
//			final Dialog dialog = new Dialog(getActivity());
//			// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			dialog.setCancelable(false);
//			dialog.setTitle("Xác nhận giao dịch");
//			dialog.setContentView(R.layout.air_custom_dialog_confirm_purchase_vtop);
//			TextView txtConfirm = (TextView) dialog.findViewById(R.id.txtConfirm);
//			TextView txtDateBuyVtop = (TextView) dialog.findViewById(R.id.txtDateBuyVtop);
//			TextView txtMoneyBuyVtop = (TextView) dialog.findViewById(R.id.txtMoneyBuyVtop);
//			TextView txtVtop = (TextView) dialog.findViewById(R.id.txtVtop);
//			TextView txtSurplusVtop = (TextView) dialog.findViewById(R.id.txtSurplusVtop);
//			TextView txtTransactionFees = (TextView) dialog.findViewById(R.id.txtTransactionFees);
//			TextView txtMoneyPayment = (TextView) dialog.findViewById(R.id.txtMoneyPayment);
//			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//			txtConfirm.setText("Bạn muốn mua "
//					+ FormatUtil.formatCurrency(Double.parseDouble(edtMoney.getText().toString())) + " Vtop" + account);
//			txtDateBuyVtop.setText(dateFormat.format(date));
//			txtMoneyBuyVtop.setText(FormatUtil.formatCurrency(Double.parseDouble(edtMoney.getText().toString())));
//			txtVtop.setText(FormatUtil.formatCurrency(Double.parseDouble(edtMoney.getText().toString())));
//			txtSurplusVtop.setText("500.000");
//			txtTransactionFees.setText("25.000");
//			txtMoneyPayment.setText("125.000");
//			Button btnDialogOk = (Button) dialog.findViewById(R.id.btnDialogOk);
//			Button btnDialogCancel = (Button) dialog.findViewById(R.id.btnDialogCancel);
//			btnDialogOk.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					dialog.dismiss();
//					payment();
//					// ShareMemManager.getInstance().saveToDB(getActivity(),
//					// "type", type);
//					// ReplaceFragment(new
//					// FragmentVtopupPurchase().newInstance(type));
//				}
//			});
//			btnDialogCancel.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					dialog.dismiss();
//				}
//			});
//			dialog.show();
			payment();
			
		}
	}


	@SuppressWarnings("unused")
	private void payment() {
		user = ShareMemManager.getInstance().readFromDB(getActivity(), "username");
		String money = edtMoney.getText().toString();
		String params = "API/topupmobile?username="+user+"&mobilenumber=0984945090"+"&amount="+money+"&paymenttype="+type+"";
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
							txtPurchaseVtop.setVisibility(View.VISIBLE);
							txtPurchaseVtop.setText("Vui lòng kiểm tra lại kết nối.");
						}
						// phân tích để tiếp tục
						if (status == 200) {
							pd.dismiss();
							try {
								JSONObject o = new JSONObject(json);
								if (o.getString("code").equalsIgnoreCase("00")) {
									txtPurchaseVtop.setVisibility(View.VISIBLE);
									txtPurchaseVtop.setText(o.getString("message"));
								}
								if (o.getString("code").equalsIgnoreCase("01")) {
									txtPurchaseVtop.setVisibility(View.GONE);
									// txtPurchaseVtop.setText(o.getString("message"));
									data = o.getString("data");
//									JSONObject obj = new JSONObject(o.getString("data"));
//									ShareMemManager.getInstance().saveToDB(getActivity(), "queryamountvtop", obj.toString());
////									List<JSONObject> lstJsonObjects = new ArrayList<JSONObject>();
////									lstJsonObjects = TransformDataManager.convertArrayToListJSON(new JSONArray(data));
//									amountVTop = obj.getString("AmountVTop");
//									balance = obj.getString("Balance");
//									FragmentTransaction fm = ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction();
//									fm.replace(R.id.container, new FragmentConfirmPurchaseVtop().newInstance(edtMoney.getText().toString(),type,amountVTop,balance));
//									fm.addToBackStack("tag");
//									fm.commit();
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
							txtPurchaseVtop.setVisibility(View.VISIBLE);
							txtPurchaseVtop.setText("Vui lòng kiểm tra lại kết nối.");
						}
						// phân tích để tiếp tục
						if (status == 200) {
							pd.dismiss();
							try {
								JSONObject o = new JSONObject(json);
								if (o.getString("code").equalsIgnoreCase("00")) {
									txtPurchaseVtop.setVisibility(View.VISIBLE);
									txtPurchaseVtop.setText(o.getString("message"));
								}
								if (o.getString("code").equalsIgnoreCase("01")) {
									txtPurchaseVtop.setVisibility(View.GONE);
									dataUrl = o.getString("data");
									String aa = dataUrl;
									ShareMemManager.getInstance().saveToDB(getActivity(), "urlWeb", dataUrl);
									ShareMemManager.getInstance().saveToDB(getActivity(), "dataUrl", dataUrl);
									ReplaceFragment(new FragmentVtopupPurchase().newInstance(type));
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