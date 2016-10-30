package vn.ce.sale.fragment.bee;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.json.JSONObject;

import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

public class Bee_Fragment_VAS_Swallow extends ZopostFragment implements ICallBackActivityToFragment, OnClickListener {
	LinearLayout lnlVasPrepay, lnlVasPostpaid, lnlVasReport;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// ((Home) getActivity()).getSupportActionBar().show();
		// getActivity().setTitle("Chim Én");
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.bee_fragment_vas_swallow, container, false);
		lnlVasPrepay = (LinearLayout) rootView.findViewById(R.id.lnlVasPrepay);
		lnlVasPostpaid = (LinearLayout) rootView.findViewById(R.id.lnlVasPostpaid);
		lnlVasReport = (LinearLayout) rootView.findViewById(R.id.lnlVasReport);
		lnlVasPrepay.setOnClickListener(this);
		lnlVasPostpaid.setOnClickListener(this);
		lnlVasReport.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.lnlVasPrepay:
			sendDataToActivity(BundleData.createNew().putString("screen", Util.SCREEN_VAS_Prepay).data());
			break;
		case R.id.lnlVasPostpaid:
			Toast.makeText(getActivity(), "Chức năng chưa được hỗ trợ", Toast.LENGTH_SHORT).show();
			break;
		case R.id.lnlVasReport:
			Toast.makeText(getActivity(), "Chức năng chưa được hỗ trợ", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
//		sendDataToActivity(BundleData.createNew().putString("screen", getScreenByIndex(v.getId())).data());
	}

	private String getScreenByIndex(int position) {

		if (position == R.id.lnlVasPrepay) {
			return Util.SCREEN_VAS_Prepay;
		}
		if (position == R.id.lnlVasPostpaid) {
			return Util.SCREEN_VAS_POSTPAID;
		}
		if (position == R.id.lnlVasReport) {
			return Util.SCREEN_VAS_REPORT;
		}
		return String.valueOf(position);
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