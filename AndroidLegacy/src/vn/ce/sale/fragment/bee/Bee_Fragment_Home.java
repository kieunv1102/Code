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

public class Bee_Fragment_Home extends ZopostFragment implements OnClickListener, ICallBackActivityToFragment {
	TextView txtStatusUserName;
	LinearLayout lnlBeeProduct, lnlBeeOrderProduct, lnlBeeCateOrder,lnlBeePayment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		((Home) getActivity()).getSupportActionBar().show();
		getActivity().setTitle("Con Ong");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.bee_homescreen, container, false);
		txtStatusUserName = (TextView) rootView.findViewById(R.id.txtStatusUserName);
		txtStatusUserName.setText("Con Ong");

		lnlBeeProduct = (LinearLayout) rootView.findViewById(R.id.lnlBeeProduct);
		lnlBeeOrderProduct = (LinearLayout) rootView.findViewById(R.id.lnlBeeOrderProduct);
		lnlBeeCateOrder = (LinearLayout) rootView.findViewById(R.id.lnlBeeCateOrder);
		lnlBeePayment = (LinearLayout) rootView.findViewById(R.id.lnlBeePayment);

		lnlBeeProduct.setOnClickListener(this);
		lnlBeeOrderProduct.setOnClickListener(this);
		lnlBeeCateOrder.setOnClickListener(this);
		lnlBeePayment.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.lnlBeeProduct:
			nextToFragmentAndKeepState(new Bee_Fragment_Product_List(), null, true);
			break;
		case R.id.lnlBeeOrderProduct:
			nextToFragmentAndKeepState(new BeeFragmentOrderProduct(), null, true);
			break;
		case R.id.lnlBeeCateOrder:
			nextToFragmentAndKeepState(new BeeFragmentReportOrderProduct(), null, true);
			break;
		case R.id.lnlBeePayment:
			nextToFragmentAndKeepState(new BeeFragmentPayment(), null, true);
			break;
		default:
			break;
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