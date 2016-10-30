package vn.ce.sale.fragment.bee;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Bee_Fragment_Web_View2 extends ZopostFragment implements OnClickListener, ICallBackActivityToFragment {
	private WebView browser;
	private TextView txtURL;
	LinearLayout lnlDisplayAll;
	TextView txtUserPhone, txtMoney;
	String href, phone;
	private Timer timer = new Timer();
	private final long DELAY = 300;

	public static Bee_Fragment_Web_View2 newInstance(String param1, String param2) {
		Bee_Fragment_Web_View2 fragment = new Bee_Fragment_Web_View2();
		Bundle args = new Bundle();
		args.putString("param1", param1);
		args.putString("param2", param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			href = getArguments().getString("param1");
			phone = getArguments().getString("param2");
		}
	}

	@SuppressWarnings("unused")
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.bee_swallow_web_view2, container, false);
		browser = (WebView) rootView.findViewById(R.id.webkit);
		txtURL = (TextView) rootView.findViewById(R.id.txtURL);
		lnlDisplayAll = (LinearLayout) rootView.findViewById(R.id.lnlDisplayAll);
		txtUserPhone = (TextView) rootView.findViewById(R.id.txtUserPhone);
		txtMoney = (TextView) rootView.findViewById(R.id.txtMoney);
		startWebView(href);
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
					progressDialog.setCancelable(false);
					progressDialog.show();
				}
				if (url.equalsIgnoreCase("http://listenapi.chimen.xyz/ReceiveResult/Payment/?t=banknet")) {
					sendDataToActivity(BundleData.createNew().putString("screen", Util.SCREEN_VAS_Prepay).data());
				} else {
					if (url.equalsIgnoreCase("http://paymentcert.smartlink.com.vn/gateway/processing")) {
						progressDialog.show();
					}
					String[] parts = url.split("\\&");
					if (parts[0].equals("http://listenapi.chimen.xyz/ReceiveResult/Payment/?t=banknet")) {
						progressDialog.show();
					}
					if (parts[0].equals("http://online.keypay.vn/smlresult?vpc_AdditionalData=970400")) {
						progressDialog.show();
					}
					if (parts[0].equals("http://listenapi.chimen.xyz/ReceiveResult/Payment/?t=keypay")) {
						progressDialog.show();
					}
				}

			}

			public void onPageFinished(WebView view, String url) {
				// txtURL.setText(url);
				// if (progressDialog.isShowing()) {
				// progressDialog.dismiss();
				// progressDialog = null;
				// }
				if (url.equalsIgnoreCase("http://listenapi.chimen.xyz/ReceiveResult/Payment/?t=banknet")) {
					sendDataToActivity(BundleData.createNew().putString("screen", Util.SCREEN_VAS_Prepay).data());
				} else {
					if (url.equals("http://paymentcert.smartlink.com.vn/gateway/atm")) {
						timer = new Timer();

						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								progressDialog.dismiss();
							}

						}, DELAY);
					}
					if (url.equals("http://online.keypay.vn/selectbank")) {
						timer = new Timer();

						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								progressDialog.dismiss();
							}

						}, DELAY);
					}
					String[] parts = url.split("\\&");
					// if
					// (parts[0].equals("http://online.keypay.vn/smlresult?vpc_AdditionalData=970400"))
					// {
					// timer = new Timer();
					//
					// timer.schedule(new TimerTask() {
					// @Override
					// public void run() {
					// progressDialog.dismiss();
					// }
					//
					// }, DELAY);
					// }
					if (parts[0].equals("http://listenapi.chimen.xyz/ReceiveResult/Payment/?t=banknet")) {

						timer = new Timer();

						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								progressDialog.dismiss();
							}

						}, DELAY);
						lnlDisplayAll.setVisibility(View.VISIBLE);
						browser.setVisibility(View.GONE);
						String responseCode = parts[10];
						String money = parts[2];
						String[] parts3 = responseCode.split("\\=");
						String[] parts7 = money.split("\\=");
						int t = Integer.parseInt(parts7[1]);
						int[] ara = new int[parts7[1].length()];
						ara[0] = t % 100;
						t = t / 100;
						txtUserPhone.setText(phone);
						txtMoney.setText(FormatUtil.formatCurrency(Double.parseDouble(String.valueOf(t)))+" (VND)");
						if (Integer.parseInt(parts3[1]) == 0) {
							txtURL.setText("Giao dịch thành công");
							browser.setVisibility(View.GONE);
						} else
							txtURL.setText("Giao dịch không thành công!");

					}

					if (parts[0].equals("http://listenapi.chimen.xyz/ReceiveResult/Payment/?t=keypay")) {

						timer = new Timer();

						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								progressDialog.dismiss();
							}

						}, DELAY);
						lnlDisplayAll.setVisibility(View.VISIBLE);
						browser.setVisibility(View.GONE);
						String responseCode = parts[4];
						String money = parts[7];
						String[] parts3 = responseCode.split("\\=");
						String[] parts7 = money.split("\\=");
						txtUserPhone.setText(phone);
						txtMoney.setText(parts7[1]);
						if (Integer.parseInt(parts3[1]) == 0) {
							txtURL.setVisibility(View.VISIBLE);
							txtURL.setText("Giao dịch thành công");
							browser.setVisibility(View.GONE);
						} else
							txtURL.setText("Giao dịch không thành công!");

					}
				}

			}

		});

		// Javascript inabled on webview
		browser.getSettings().setJavaScriptEnabled(true);
		browser.getSettings().setUserAgentString("Android");
		// Other webview options

		browser.getSettings().setLoadWithOverviewMode(true);
		browser.getSettings().setUseWideViewPort(true);
		browser.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		browser.setScrollbarFadingEnabled(false);
		browser.getSettings().setBuiltInZoomControls(true);

		/*
		 * String summary =
		 * "<html><body>You scored <b>192</b> points.</body></html>";
		 * webview.loadData(summary, "text/html", null);
		 */

		// Load url in webview
		browser.loadUrl(url);

	}

	@SuppressLint("SetJavaScriptEnabled")
	private void startWebView2(String url) {

		browser.setWebViewClient(new WebViewClient() {
			ProgressDialog progressDialog;

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			public void onLoadResource(WebView view, String url) {
				if (progressDialog == null) {
					progressDialog = new ProgressDialog(getActivity());
					progressDialog.setMessage("Loading2...");
					progressDialog.show();
				}
			}

			public void onPageFinished(WebView view, String url) {
				progressDialog.dismiss();
				try {
					if (progressDialog.isShowing()) {
						progressDialog.dismiss();
						progressDialog = null;
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				txtURL.setText(url);
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
	public void onParamsFromActivity(Bundle data) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initParamsForFragment() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}