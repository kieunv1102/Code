package vn.ce.sale.fragment;

import java.util.List;

import org.json.JSONObject;

import vn.ce.sale.Home;
import vn.ce.sale.HomeActivity1;
import vn.ce.sale.data.IDataCheck;
import vn.ce.sale.fragment.airtimemix.Fragment_Home_Air;
import vn.ce.sale.fragment.bee.BeeFragmentOrderProduct;
import vn.ce.sale.fragment.bee.Bee_Fragment_Home;
import vn.ce.sale.fragment.bee.Bee_Fragment_Home_Swallow;
import vn.ce.sale.fragment.bee.Bee_Fragment_Home_Vnpost;
import vn.ce.sale.fragment.vi21.Fragment_Order_ListTabViewer;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

public class Fragment_Home_List2 extends ZopostFragment implements OnClickListener {
	TextView txtStatusUserName;
	LinearLayout lnlVi21, lnlAirTimeMix, lnlBee;
	LinearLayout lnlVi21Produt, lnlVi21OrderProdut, lnlVi21ReportProdut;
	LinearLayout lnlAirVtop, lnlAirVbill, lnlAirReport;
	LinearLayout lnlBeeProdut, lnlBeeCart, lnlBeeOrder;
	Button btnAppBongSen, btnAppAir, btnAppVnpost, btnAppBee;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		((Home) getActivity()).getSupportActionBar().show();
//		((Home) getActivity()).getSupportActionBar().setIcon(R.drawable.iconuser);
		getActivity().setTitle(ShareMemManager.getInstance().readFromDB(getActivity(), "username"));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.homescreennew2, container, false);

		txtStatusUserName = (TextView) rootView.findViewById(R.id.txtStatusUserName);
		txtStatusUserName.setText(ShareMemManager.getInstance().readFromDB(getContext(), "username"));
		btnAppBongSen = (Button) rootView.findViewById(R.id.btnAppBongSen);
		btnAppAir = (Button) rootView.findViewById(R.id.btnAppAir);
		btnAppVnpost = (Button) rootView.findViewById(R.id.btnAppVnpost);
		btnAppBee = (Button) rootView.findViewById(R.id.btnAppBee);

		btnAppBongSen.setOnClickListener(this);
		btnAppAir.setOnClickListener(this);
		btnAppVnpost.setOnClickListener(this);
		btnAppBee.setOnClickListener(this);

		lnlVi21 = (LinearLayout) rootView.findViewById(R.id.lnlVi21);
		lnlVi21.setVisibility(View.GONE);
		// lnlVi21.setBackgroundColor(Color.parseColor("#A5A4A4"));
		lnlVi21Produt = (LinearLayout) rootView.findViewById(R.id.lnlVi21Product);
		lnlVi21OrderProdut = (LinearLayout) rootView.findViewById(R.id.lnlVi21OrderProduct);
		lnlVi21ReportProdut = (LinearLayout) rootView.findViewById(R.id.lnlVi21ReportProduct);

		lnlAirTimeMix = (LinearLayout) rootView.findViewById(R.id.lnlAirTimeMix);
		// lnlAirTimeMix.setBackgroundColor(Color.parseColor("#A5A4A4"));
		lnlAirVtop = (LinearLayout) rootView.findViewById(R.id.lnlAirVtop);
		lnlAirVbill = (LinearLayout) rootView.findViewById(R.id.lnlAirVbill);
		lnlAirReport = (LinearLayout) rootView.findViewById(R.id.lnlAirReport);

		lnlBee = (LinearLayout) rootView.findViewById(R.id.lnlBee);
		lnlBeeProdut = (LinearLayout) rootView.findViewById(R.id.lnlBeeProduct);
		lnlBeeCart = (LinearLayout) rootView.findViewById(R.id.lnlBeeCart);
		lnlBeeOrder = (LinearLayout) rootView.findViewById(R.id.lnlBeeOrder);

		lnlVi21Produt.setOnClickListener(this);
		lnlVi21OrderProdut.setOnClickListener(this);
		lnlVi21ReportProdut.setOnClickListener(this);

		lnlAirVtop.setOnClickListener(this);
		lnlAirVbill.setOnClickListener(this);
		lnlAirReport.setOnClickListener(this);

		lnlBeeProdut.setOnClickListener(this);
		lnlBeeCart.setOnClickListener(this);
		lnlBeeOrder.setOnClickListener(this);

		setHasOptionsMenu(true);
		return rootView;
	}

	@Override
	public void onClick(View v) {
		// sendDataToActivity(BundleData.createNew().putString("screen",
		// getScreenByIndex(v.getId())).data());
		switch (v.getId()) {
		case R.id.btnAppBongSen:
			nextToFragmentAndKeepState(new Bee_Fragment_Home_Swallow(), null, true);
			break;
		case R.id.btnAppAir:
			nextToFragmentAndKeepState(new Fragment_Home_Air(), null, true);
			break;
		case R.id.btnAppBee:
			nextToFragmentAndKeepState(new Bee_Fragment_Home(), null, true);
			break;
		case R.id.btnAppVnpost:
			nextToFragmentAndKeepState(new Bee_Fragment_Home_Vnpost(), null, true);
			break;

		default:
			break;
		}
	}

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
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		// inflater.inflate(R.menu.bee_menu_product, menu);
		// SearchManager searchManager = (SearchManager)
		// (getActivity().getSystemService(Context.SEARCH_SERVICE));
		// SearchView searchView = (SearchView)
		// menu.findItem(R.id.actionSearchProductBee).getActionView();
		// searchView.setMaxWidth(650);
		// searchView.setIconifiedByDefault(false);
		//
		// ActionBar.LayoutParams params = new
		// ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
		// ActionBar.LayoutParams.MATCH_PARENT);
		// searchView.setLayoutParams(params);
		// getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//// ActionBar actionbar = getActivity().getActionBar();
		//// actionbar.setIcon(R.drawable.iconuser);
		//// actionbar.setCustomView(R.layout.custom_actionbar);

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// switch (item.getItemId()) {
		// case R.id.actionRefreshBee:
		// // save it here
		// nextToFragmentAndKeepState(new FragmentOrderBeeProduct(), null,
		// false);
		// return true;
		//
		// default:
		// return super.onOptionsItemSelected(item);
		// }
		return super.onOptionsItemSelected(item);
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
	protected void startLoadData() {
	}

	protected void refreshData() {
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
		b.setCancelable(false);
		b.setMessage("Bạn muốn thoát chương trình?");
		b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				getActivity().finish();
				dialog.dismiss();
			}
		});
		b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		b.create().show();
		return true;
	}

}