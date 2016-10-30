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
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Bee_Fragment_Web_View extends ZopostFragment implements OnClickListener, ICallBackActivityToFragment {
	private WebView browser;
	private TextView txtURL;
	LinearLayout lnlConfirm;
	EditText edtMoneyConfirm, edtMoney, edtTotalMoney, edtContent;
	TextView txtMoneyConfirmText, txtMoneyText, txtTotalMoneyText;
	String href;
	private Timer timer = new Timer();
	private final long DELAY = 300;
	String content;

	public static Bee_Fragment_Web_View newInstance(String param1, String param2) {
		Bee_Fragment_Web_View fragment = new Bee_Fragment_Web_View();
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
			content = getArguments().getString("param2");
		}
	}

	@SuppressWarnings("unused")
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.bee_swallow_web_view, container, false);
		browser = (WebView) rootView.findViewById(R.id.webkit);
		txtURL = (TextView) rootView.findViewById(R.id.txtURL);
		lnlConfirm = (LinearLayout) rootView.findViewById(R.id.lnlConfirm);
		edtMoneyConfirm = (EditText) rootView.findViewById(R.id.edtMoneyConfirm);
		edtMoney = (EditText) rootView.findViewById(R.id.edtMoney);
		edtTotalMoney = (EditText) rootView.findViewById(R.id.edtTotalMoney);
		edtContent = (EditText) rootView.findViewById(R.id.edtContent);
		txtMoneyConfirmText = (TextView) rootView.findViewById(R.id.txtMoneyConfirmText);
		txtMoneyText = (TextView) rootView.findViewById(R.id.txtMoneyText);
		txtTotalMoneyText = (TextView) rootView.findViewById(R.id.txtTotalMoneyText);
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
					// sendDataToActivity(BundleData.createNew().putString("screen",
					// Util.SCREEN_CASHIN_SWALLOW).data());
					FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
					fm.replace(R.id.container, new Bee_Fragment_CashIn_Swallow());
					fm.addToBackStack("tag");
					fm.commit();
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
					// sendDataToActivity(BundleData.createNew().putString("screen",
					// Util.SCREEN_CASHIN_SWALLOW).data());
					FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
					fm.replace(R.id.container, new Bee_Fragment_CashIn_Swallow());
					fm.addToBackStack("tag");
					fm.commit();
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
					String x = parts[0];
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
					// lnlConfirm.setVisibility(View.VISIBLE);
					// browser.setVisibility(View.GONE);
					// edtMoney.setText(content);
					// edtMoneyConfirm.setText(content);
					// edtTotalMoney.setText(content);
					// txtMoneyConfirmText.setText(FormatUtil.numberToString(Double.parseDouble(content)));
					// txtMoneyText.setText(FormatUtil.numberToString(Double.parseDouble(content)));
					// txtTotalMoneyText.setText(FormatUtil.numberToString(Double.parseDouble(content)));
					// }
					// parts[0].equals("http://listenapi.chimen.xyz/ReceiveResult/Payment/?t=keypay||
					if (parts[0].equals("http://listenapi.chimen.xyz/ReceiveResult/Payment/?t=banknet")) {

						timer = new Timer();

						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								progressDialog.dismiss();
							}

						}, DELAY);
						lnlConfirm.setVisibility(View.VISIBLE);
						browser.setVisibility(View.GONE);
						String responseCode = parts[10];
						String money = parts[2];
						String[] parts3 = responseCode.split("\\=");
						String[] parts7 = money.split("\\=");
						int t = Integer.parseInt(parts7[1]);
						int[] ara = new int[parts7[1].length()];
						ara[0] = t % 100;
						t = t / 100;
						edtMoney.setText(FormatUtil.formatCurrency(Double.parseDouble(String.valueOf(t)))+" (VND)");
						edtMoneyConfirm.setText(FormatUtil.formatCurrency(Double.parseDouble(String.valueOf(t)))+" (VND)");
						edtTotalMoney.setText(FormatUtil.formatCurrency(Double.parseDouble(String.valueOf(t)))+" (VND)" );
						edtContent.setText(content);
						txtMoneyConfirmText.setText(FormatUtil.numberToString(Double.parseDouble(String.valueOf(t))));
						txtMoneyText.setText(FormatUtil.numberToString(Double.parseDouble(String.valueOf(t))));
						txtTotalMoneyText.setText(FormatUtil.numberToString(Double.parseDouble(String.valueOf(t))));
						if (Integer.parseInt(parts3[1]) == 0) {
							txtURL.setVisibility(View.VISIBLE);
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
						lnlConfirm.setVisibility(View.VISIBLE);
						browser.setVisibility(View.GONE);
						String responseCode = parts[4];
						String money = parts[7];
						String[] parts3 = responseCode.split("\\=");
						String[] parts7 = money.split("\\=");
						edtMoney.setText(parts7[1]);
						edtMoneyConfirm.setText(parts7[1]);
						edtTotalMoney.setText(parts7[1]);
						edtContent.setText(content);
						txtMoneyConfirmText.setText(FormatUtil.numberToString(Double.parseDouble(parts7[1])));
						txtMoneyText.setText(FormatUtil.numberToString(Double.parseDouble(parts7[1])));
						txtTotalMoneyText.setText(FormatUtil.numberToString(Double.parseDouble(parts7[1])));
						if (Integer.parseInt(parts3[1]) == 0) {
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