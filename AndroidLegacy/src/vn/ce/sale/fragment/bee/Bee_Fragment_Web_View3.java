package vn.ce.sale.fragment.bee;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.ShareMemManager;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class Bee_Fragment_Web_View3 extends ZopostFragment implements OnClickListener, ICallBackActivityToFragment {
	TextView txtService, txtPhone, txtMoney,txtNotification;
	RadioButton rdbBank;
	EditText edtOTP;
	Button btnOK;
	LinearLayout lnlOTP;
	String service, phone, money, bank;
	private Timer timer = new Timer();
	private final long DELAY = 300;

	public static Bee_Fragment_Web_View3 newInstance(String param1, String param2, String param3, String param4) {
		Bee_Fragment_Web_View3 fragment = new Bee_Fragment_Web_View3();
		Bundle args = new Bundle();
		args.putString("param1", param1);
		args.putString("param2", param2);
		args.putString("param3", param3);
		args.putString("param4", param4);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			service = getArguments().getString("param1");
			phone = getArguments().getString("param2");
			money = getArguments().getString("param3");
			bank = getArguments().getString("param4");
		}
	}

	@SuppressWarnings("unused")
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.bee_fragment_vas_prepay_swallow, container, false);
		txtService = (TextView)rootView.findViewById(R.id.txtService);
		txtPhone = (TextView)rootView.findViewById(R.id.txtPhone);
		txtMoney =(TextView)rootView.findViewById(R.id.txtMoney);
		txtNotification = (TextView)rootView.findViewById(R.id.txtNotification);
		rdbBank =(RadioButton)rootView.findViewById(R.id.rdbBank);
		edtOTP = (EditText)rootView.findViewById(R.id.edtOTP);
		lnlOTP = (LinearLayout)rootView.findViewById(R.id.lnlOTP);
		btnOK =(Button)rootView.findViewById(R.id.btnOKOTP);
		btnOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (edtOTP.getText().toString().equalsIgnoreCase("")) {
					txtNotification.setVisibility(View.VISIBLE);
					txtNotification.setText("Bạn phải nhập OTP");
				}else{
					if (edtOTP.getText().toString().equalsIgnoreCase("OTP")) {
						lnlOTP.setVisibility(View.GONE);
						txtNotification.setVisibility(View.VISIBLE);
						txtNotification.setText("Giao dịch thành công.");
					}else{
						txtNotification.setVisibility(View.VISIBLE);
						txtNotification.setText("Bạn nhập sai OTP");
					}
				}
			}
		});
		
		txtService.setText(service);
		txtPhone.setText(phone);
		txtMoney.setText(money);
		if (Integer.parseInt(bank)==1) {
			rdbBank.setText("Tài khoản chim én");
		}
		if (Integer.parseInt(bank)==2) {
			rdbBank.setText("Tài khoản ngân hàng nội địa");
		}
		if (Integer.parseInt(bank)==3) {
			rdbBank.setText("Tài khoản ngân hàng quốc tế");
		}
		return rootView;
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