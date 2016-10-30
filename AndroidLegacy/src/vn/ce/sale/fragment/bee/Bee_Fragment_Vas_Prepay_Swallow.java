package vn.ce.sale.fragment.bee;

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
import vn.ce.sale.ui.ICallBackActivityToFragment;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class Bee_Fragment_Vas_Prepay_Swallow extends ZopostFragment implements ICallBackActivityToFragment   {
	TextView txtService,txtPhone,txtMoney;
	RadioButton rdbBank;
	String service,phone,money,bank;
	public static Bee_Fragment_Vas_Prepay_Swallow newInstance(String a,String b,String c,String d) {
		Bee_Fragment_Vas_Prepay_Swallow fragment = new Bee_Fragment_Vas_Prepay_Swallow();
		Bundle args = new Bundle();
		args.putString("param1", a);
		args.putString("param2", b);
		args.putString("param3", c);
		args.putString("param4", d);
		fragment.setArguments(args);
		return fragment;
	}

	public void onCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (getArguments() != null) {
			service = getArguments().getString("param1");
			phone = getArguments().getString("param2");
			money = getArguments().getString("param3");
			bank = getArguments().getString("param4");
		}

	}

	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.bee_fragment_vas_prepay_swallow, container, false);
		txtService = (TextView)rootView.findViewById(R.id.txtService);
		txtPhone = (TextView)rootView.findViewById(R.id.txtPhone);
		txtMoney =(TextView)rootView.findViewById(R.id.txtMoney);
		rdbBank =(RadioButton)rootView.findViewById(R.id.rdbBank);
		txtService.setText(service);
		txtPhone.setText(phone);
		txtMoney.setText(money);
//		if (Integer.parseInt(bank)==1) {
//			rdbBank.setText("Tài khoản chim én");
//		}
//		if (Integer.parseInt(bank)==2) {
//			rdbBank.setText("Tài khoản ngân hàng nội địa");
//		}
//		if (Integer.parseInt(bank)==3) {
//			rdbBank.setText("Tài khoản ngân hàng quốc tế");
//		}
		
		return rootView;
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
	public void onParamsFromActivity(Bundle data) {
		// TODO Auto-generated method stub
		
	}


}