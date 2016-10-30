package vn.ce.sale.fragment.bee;

import java.util.List;

import org.json.JSONObject;

import vn.ce.sale.Home;
import vn.ce.sale.fragment.vi21.FragmentOrderProduct;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

public class Bee_Fragment_Home_Vnpost extends ZopostFragment implements ICallBackActivityToFragment{
	TextView txtStatusUserName;
	LinearLayout lnlVi21, lnlAirTimeMix, lnlBee;
	LinearLayout lnlVi21Produt, lnlVi21OrderProdut, lnlVi21ReportProdut;
	LinearLayout lnlAirVtop, lnlAirVbill, lnlAirReport;
	LinearLayout lnlBeeProdut, lnlBeeCart, lnlBeeOrder;
	Button btnAppBongSen;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		((Home) getActivity()).getSupportActionBar().show();
//		getActivity().setTitle("VnPost");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.vnpost_homescreen, container, false);
		return rootView;
	}

//	@Override
//	public void onClick(View v) {
		// TODO Auto-generated method stub
		// if (v.getId() == R.id.lnlVi21Product || v.getId() ==
		// R.id.lnlVi21OrderProduct
		// || v.getId() == R.id.lnlVi21ReportProduct || v.getId() ==
		// R.id.lnlAirVtop
		// || v.getId() == R.id.lnlAirVbill || v.getId() == R.id.lnlAirReport) {
		// Toast.makeText(getActivity(), "Chức năng này không được kích hoạt với
		// tài khoản của bạn",
		// Toast.LENGTH_SHORT).show();
		// } else
//		sendDataToActivity(BundleData.createNew().putString("screen", getScreenByIndex(v.getId())).data());
//	}

	private String getScreenByIndex(int position) {
		if (position == R.id.lnlVi21Product)
			return Util.SCREEN_ORDER;
		if (position == R.id.lnlVi21OrderProduct)
			return Util.SCREEN_ORDER_CREATE;
		if (position == R.id.lnlVi21ReportProduct) {
			// sendDataToActivity(BundleData.createNew().putString("screen",
			// Util.SCREEN_REPORT_LIST).data());
			return Util.SCREEN_REPORT_LIST;
		}

		if (position == R.id.lnlAirVtop)
			return Util.SCREEN_VTOPUP;
		if (position == R.id.lnlAirVbill)
			return Util.SCREEN_VBILL;
		if (position == R.id.lnlAirReport)
			return Util.SCREEN_AIRTIMEMIX;

		if (position == R.id.lnlBeeProduct)
			return Util.SCREEN_ALL_PRODUCT_ECOM;
		if (position == R.id.lnlBeeCart)
			return Util.SCREEN_ORDER_ECOM;
		if (position == R.id.lnlBeeOrder)
			return Util.SCREEN_REPORT_ECOM;

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
		getActivity().setTitle(ShareMemManager.getInstance().readFromDB(getActivity(), "username"));
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