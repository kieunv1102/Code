package vn.ce.sale.fragment.airtimemix;

import java.util.List;

import org.json.JSONObject;

import vn.ce.sale.Home;
import vn.ce.sale.fragment.bee.Bee_Fragment_Product_List;
import vn.ce.sale.fragment.vi21.FragmentOrderProduct;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackFragmentToActivity;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Button;

public class Fragment_Home_Air extends ZopostFragment implements OnClickListener,ICallBackActivityToFragment{
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
		((Home) getActivity()).getSupportActionBar().show();
		// getActivity().setTitle("AirTimeMix");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.air_homescreen, container, false);
		txtStatusUserName = (TextView) rootView.findViewById(R.id.txtStatusUserName);
		txtStatusUserName.setText("AirTimeMix");
		btnAppBongSen = (Button) rootView.findViewById(R.id.btnAppBongSen);
		btnAppBongSen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			}
		});
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
		// sendDataToNavigateScreen(Util.SCREEN_HOME_AIR,
		// BundleData.createNew().putString("screen", "").data());
		// sendDataToActivity();
		return rootView;
	}

	@SuppressWarnings("static-access")
	@Override
	public void onClick(View v) {
		Util.checkMenuHome = true;
		if (v.getId() == R.id.lnlBeeProduct) {

			// return Util.SCREEN_AIR_PURCHASE_VTOP;
			final Dialog dialog = new Dialog(getActivity());
			dialog.setTitle("Bạn chọn phương thức thanh toán!");
			// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setCancelable(false);
			dialog.setContentView(R.layout.air_custom_dialog_purchase_vtop);
			// dialog.getWindow().getAttributes().windowAnimations =
			// R.style.dialog_animation_user_top;
			final RadioButton radioButtonBank = (RadioButton) dialog.findViewById(R.id.radioButtonBank);
//			final RadioButton radioButtonBankNet = (RadioButton) dialog.findViewById(R.id.radioButtonBankNet);
			final RadioButton radioButtonSwallow = (RadioButton) dialog.findViewById(R.id.radioButtonSwallow);
			Button btnOK = (Button) dialog.findViewById(R.id.btnOKAir);
			btnOK.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// if (radioButtonBank.isChecked() == true) {
					// FragmentTransaction fm = ((FragmentActivity)
					// getActivity()).getSupportFragmentManager()
					// .beginTransaction();
					// fm.replace(R.id.container, new
					// FragmentPurchaseVtopBank().newInstance("Tài Khoản Ngân
					// Hàng"));
					// fm.addToBackStack("tag");
					// fm.commit();
					// } else {
					// FragmentTransaction fm = ((FragmentActivity)
					// getActivity()).getSupportFragmentManager()
					// .beginTransaction();
					// fm.replace(R.id.container, new
					// FragmentPurchaseVtopBank().newInstance("Tài Khoản Chim
					// Én"));
					// fm.addToBackStack("tag");
					// fm.commit();
					// }
					if (radioButtonSwallow.isChecked() == true) {
						ShareMemManager.getInstance().saveToDB(getActivity(), "checkRadio", "1");
					}
//					if (radioButtonBankNet.isChecked() == true) {
//						ShareMemManager.getInstance().saveToDB(getActivity(), "checkRadio", "3");
//					}
					if (radioButtonBank.isChecked() == true) {
						ShareMemManager.getInstance().saveToDB(getActivity(), "checkRadio", "2");
					}
					sendDataToActivity(
							BundleData.createNew().putString("screen", Util.SCREEN_AIR_PURCHASE_VTOP_BANK).data());
					dialog.dismiss();
				}
			});
			Button btnCancel = (Button) dialog.findViewById(R.id.btnCancelAir);
			btnCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			dialog.show();
		} else
			sendDataToActivity(BundleData.createNew().putString("screen", getScreenByIndex(v.getId())).data());
	}

	@SuppressWarnings("unused")
	private void ReplaceFragment(Fragment fragment) {
		FragmentTransaction fm = ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction();
		fm.replace(R.id.container, fragment);
		fm.addToBackStack("tag");
		fm.commit();
	}

	private String getScreenByIndex(int position) {
		if (position == R.id.lnlAirVtop) {
			return Util.SCREEN_VTOPUP;
		}
		if (position == R.id.lnlAirVbill) {
			return Util.SCREEN_VBILL;
		}
		if (position == R.id.lnlAirReport) {
			return Util.SCREEN_AIRTIMEMIX;
		}

		return String.valueOf(position);
	}

	@Override
	protected void fillDataSource(int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void startLoadData() {
		final ProgressDialog pd = new ProgressDialog(getContext());
		pd.show();
		pd.dismiss();
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