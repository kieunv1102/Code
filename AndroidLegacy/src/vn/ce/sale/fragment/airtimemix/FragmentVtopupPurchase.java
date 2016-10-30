package vn.ce.sale.fragment.airtimemix;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentVtopupPurchase extends ZopostFragment implements ICallBackUI {
	private WebView browser;
	private TextView txtwebUrl;
	private EditText edtInputOTP;
	private Button btnOkOTP;
	private String mParam1;
	private static final String ARG_PARAM1 = "param1";
	private Timer timer = new Timer();
	private final long DELAY = 300;

	public static FragmentVtopupPurchase newInstance(String aa) {
		FragmentVtopupPurchase fragment = new FragmentVtopupPurchase();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, aa);
		fragment.setArguments(args);
		return fragment;
	}

	public void onCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
		}

	}

	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.purchasevtop_homescreen, container, false);
		browser = (WebView) rootView.findViewById(R.id.webkit);
		txtwebUrl = (TextView) rootView.findViewById(R.id.txtwebUrl);
		edtInputOTP = (EditText) rootView.findViewById(R.id.edtInputOTP);
		btnOkOTP = (Button) rootView.findViewById(R.id.btnOkOTP);
		String href = ShareMemManager.getInstance().readFromDB(getActivity(), "urlWeb");
		String type = ShareMemManager.getInstance().readFromDB(getActivity(), "checkRadio");
		String data = ShareMemManager.getInstance().readFromDB(getActivity(), "dataUrl");
		if (Integer.parseInt(type) == 1) {
			browser.setVisibility(View.GONE);
			txtwebUrl.setVisibility(View.GONE);
			edtInputOTP.setVisibility(View.VISIBLE);
			btnOkOTP.setVisibility(View.VISIBLE);
			btnOkOTP.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (edtInputOTP.getText().toString().equals("OTP")) {
						txtwebUrl.setVisibility(View.VISIBLE);
						txtwebUrl.setText("Giao dịch thành công!");
						edtInputOTP.setVisibility(View.GONE);
						btnOkOTP.setVisibility(View.GONE);
					}
				}
			});

		}
		if (Integer.parseInt(type) == 2) {
			browser.setVisibility(View.VISIBLE);
			txtwebUrl.setVisibility(View.VISIBLE);
			edtInputOTP.setVisibility(View.GONE);
			btnOkOTP.setVisibility(View.GONE);
			startWebView(href);
		}
		if (Integer.parseInt(type) == 3) {

		}

		return rootView;
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void startWebView(String url) {

		browser.setWebViewClient(new WebViewClient() {
			ProgressDialog progressDialog;

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			public void onLoadResource(WebView view, String url) {
				if (progressDialog == null) {
					progressDialog = new ProgressDialog(getActivity());
					progressDialog.setMessage("Loading...");
					progressDialog.show();
				}
				if (url.equals("http://payment.smartlink.com.vn/gateway/processing")) {
					progressDialog.show();
				}
				String[] parts = url.split("\\&");
				if (parts[0].equals("http://online.keypay.vn/smlresult?vpc_AdditionalData=970400")) {
					progressDialog.show();
				}
				if (parts[0].equals("http://callapi.chimen.xyz/ReceiveResult/Payment/?t=keypay")) {
					progressDialog.show();
				}

			}

			@SuppressWarnings("unused")
			public void onPageFinished(WebView view, String url) {
//				txtwebUrl.setText(url);
				String a = url;
				String[] parts = url.split("\\&");
				if (parts[0].equals("http://online.keypay.vn/smlresult?vpc_AdditionalData=970400")) {
					timer = new Timer();

					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							progressDialog.dismiss();
						}

					}, DELAY);
				}
				if (parts[0].equals("http://callapi.chimen.xyz/ReceiveResult/Payment/?t=keypay")) {
					timer = new Timer();

					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							progressDialog.dismiss();
						}

					}, DELAY);
					String responseCode = parts[4];
					String money = parts[7];
					String[] parts3 = responseCode.split("\\=");
					String[] parts7 = money.split("\\=");
					if (Integer.parseInt(parts3[1]) == 0) {
						txtwebUrl.setText("Giao dịch thành công.Số tiền nạp tài khoản "+parts7[1]);
//						txtwebUrl.setText("Bạn vừa nạp thành công " + parts[1] + " lúc "
//								 + dateFormat.format(new Date().getTime()) + " .Mã giao dịch: " + parts[1]
//								 + " .Số dư Vtop hiện tại " +
//								 FormatUtil.formatCurrency(Double.parseDouble(bb))
//								 + " .Số Vtop được nạp " +
//								 FormatUtil.formatCurrency(Double.parseDouble(aa)) + "");
						browser.setVisibility(View.GONE);
					} else
						txtwebUrl.setText("Giao dịch không thành công!");
					browser.setVisibility(View.GONE);
				}
				// txtwebUrl.setText(url);
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				// if (zzz.length() >= 13) {
				// timer = new Timer();
				//
				// timer.schedule(new TimerTask() {
				// @Override
				// public void run() {
				// progressDialog.show();
				// }
				//
				// }, DELAY);
				// }
				// String[] parts = url.split("\\&");
				/*if (parts[0].equals("http://192.168.10.211:1234/ReceiveResult/Payment/?t=keypay")) {
					String responseCode = parts[4];
					String[] parts3 = responseCode.split("\\=");
					if (Integer.parseInt(parts3[1]) == 0) {
						txtwebUrl.setText("Giao dịch thành công!");
						browser.setVisibility(View.GONE);
					} else
						txtwebUrl.setText("Giao dịch không thành công!");
					browser.setVisibility(View.GONE);
				}*/
				// if
				// (url.equals("http://paymentcert.smartlink.com.vn/gateway/atm"))
				// {
				// txtwebUrl.setVisibility(View.GONE);
				// }
				// String[] parts = url.split("\\&");
				// if
				// (parts[0].equals("http://192.168.10.202:1234/ReceiveResult/Payment/?t=keypay"))
				// {
				// txtwebUrl.setVisibility(View.VISIBLE);
				// txtwebUrl.setText("Giao dịch thành công!");
				// browser.setVisibility(View.GONE);
				// String aa = "", bb = "";
				// String responseCode = parts[10];
				// String transactionNo = parts[11];
				// String amount = parts[2];
				// String[] parts1 = amount.split("\\=");
				// String[] parts2 = transactionNo.split("\\=");
				// String[] parts3 = responseCode.split("\\=");
				// SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss
				// dd/MM/yyyy");
				// String queryamountvtop =
				// ShareMemManager.getInstance().readFromDB(getActivity(),
				// "queryamountvtop");
				//
				// try {
				// JSONObject obj = new JSONObject(queryamountvtop);
				// bb = obj.getString("AmountVTop");
				// aa = obj.getString("Balance");
				// } catch (JSONException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// if (Integer.parseInt(parts3[1]) == 0) {
				// txtwebUrl.setVisibility(View.VISIBLE);
				// txtwebUrl.setText("Bạn vừa nạp thành công " + parts1[1] + "
				// lúc "
				// + dateFormat.format(new Date().getTime()) + " .Mã giao dịch:
				// " + parts2[1]
				// + " .Số dư Vtop hiện tại " +
				// FormatUtil.formatCurrency(Double.parseDouble(bb))
				// + " .Số Vtop được nạp " +
				// FormatUtil.formatCurrency(Double.parseDouble(aa)) + "");
				// browser.setVisibility(View.GONE);
				// } else
				// txtwebUrl.setText("Giao dịch không thành công!");
				// }

			}

		});

		// Javascript inabled on webview
		browser.getSettings().setJavaScriptEnabled(true);

		// Other webview options
		/*
		 * webView.getSettings().setLoadWithOverviewMode(true);
		 * webView.getSettings().setUseWideViewPort(true);
		 * webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		 * webView.setScrollbarFadingEnabled(false);
		 * webView.getSettings().setBuiltInZoomControls(true);
		 */

		/*
		 * String summary =
		 * "<html><body>You scored <b>192</b> points.</body></html>";
		 * webview.loadData(summary, "text/html", null);
		 */

		// Load url in webview
		browser.loadUrl(url);

	}

	@Override
	protected void setupUI() {
		super.setupUI();
		setHasOptionsMenu(true);
	}

	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return super.getView();
	}

	@Override
	protected void startLoadData() {
		Util.checkChangeOrderProduct = true;

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
	protected void fillDataSource(int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(String key, int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processRaw(String key, int status, String json) {
		// TODO Auto-generated method stub

	}

}