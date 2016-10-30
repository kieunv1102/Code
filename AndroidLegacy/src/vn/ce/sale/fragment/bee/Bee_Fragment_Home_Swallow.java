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
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class Bee_Fragment_Home_Swallow extends ZopostFragment implements ICallBackActivityToFragment, OnClickListener {
	LinearLayout lnlSwallowCashIn, lnlSwallowCashOut, lnlSwallowTransfer, lnlSwallowVAS,lnlSodu;
	int a;

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
		rootView = inflater.inflate(R.layout.swallow_homescreen, container, false);
		lnlSwallowCashIn = (LinearLayout) rootView.findViewById(R.id.lnlSwallowCashIn);
		lnlSwallowCashOut = (LinearLayout) rootView.findViewById(R.id.lnlSwallowCashOut);
		lnlSwallowTransfer = (LinearLayout) rootView.findViewById(R.id.lnlSwallowTransfer);
		lnlSwallowVAS = (LinearLayout) rootView.findViewById(R.id.lnlSwallowVAS);
		lnlSodu = (LinearLayout) rootView.findViewById(R.id.lnlSodu);
		lnlSwallowCashIn.setOnClickListener(this);
		lnlSwallowCashOut.setOnClickListener(this);
		lnlSwallowTransfer.setOnClickListener(this);
		lnlSwallowVAS.setOnClickListener(this);
		lnlSodu.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.lnlSwallowCashIn) {
			final Dialog dialog = new Dialog(getActivity());
			dialog.setTitle("Bạn chọn hình thức nạp tiền!");
			// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			dialog.setCancelable(false);
			dialog.setContentView(R.layout.bee_custom_dialog_cashin_swallow);
			// dialog.getWindow().getAttributes().windowAnimations =
			// R.style.dialog_animation_user_top;
			final RadioButton rdbCashInOnline = (RadioButton) dialog.findViewById(R.id.rdbCashInOnline);
			final RadioButton rdbCashInOffline = (RadioButton) dialog.findViewById(R.id.rdbCashInOffline);
			
			Button btnOk = (Button) dialog.findViewById(R.id.btnOkCashIn);
			Button btnCancel = (Button) dialog.findViewById(R.id.btnCancelCashIn);
			btnOk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					dialog.dismiss();
					if (rdbCashInOnline.isChecked() == true) {
//						ShareMemManager.getInstance().saveToDB(getActivity(), "cashinOn", "2");
						sendDataToActivity(BundleData.createNew().putString("screen", Util.SCREEN_CASHIN_SWALLOW).data());
					}
					if (rdbCashInOffline.isChecked() == true) {
//						ShareMemManager.getInstance().saveToDB(getActivity(), "cashinOff", "1");
						sendDataToActivity(BundleData.createNew().putString("screen", Util.SCREEN_CASHIN_OFF_SWALLOW).data());
					}
					
					// FragmentTransaction fm = ((FragmentActivity)
					// getActivity()).getSupportFragmentManager()
					// .beginTransaction();
					// fm.replace(R.id.container, new
					// Bee_Fragment_CashIn_Swallow().newInstance(String.valueOf(a)));
					// fm.addToBackStack("tag");
					// fm.commit();
				}
			});
			btnCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();

				}
			});
			dialog.show();
		} else if (v.getId() == R.id.lnlSwallowTransfer) {
			final Dialog dialog = new Dialog(getActivity());
			dialog.setTitle("Bạn chọn hình thức chuyển tiền!");
			// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			dialog.setCancelable(false);
			dialog.setContentView(R.layout.bee_custom_dialog_transfer_swallow);
			// dialog.getWindow().getAttributes().windowAnimations =
			// R.style.dialog_animation_user_top;
			final RadioButton rdbTransfer1 = (RadioButton) dialog.findViewById(R.id.rdbTransfer1);
			final RadioButton rdbTransfer2 = (RadioButton) dialog.findViewById(R.id.rdbTransfer2);
			
			Button btnOk = (Button) dialog.findViewById(R.id.btnOkTransfer);
			Button btnCancel = (Button) dialog.findViewById(R.id.btnCancelTransfer);
			btnOk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					dialog.dismiss();
					if (rdbTransfer1.isChecked() == true) {
						sendDataToActivity(BundleData.createNew().putString("screen", Util.SCREEN_TRANSFER_SWALLOW).data());
					}
					if (rdbTransfer2.isChecked() == true) {
						sendDataToActivity(BundleData.createNew().putString("screen", Util.SCREEN_TRANSFER_SWALLOW2).data());
					}
					
					// FragmentTransaction fm = ((FragmentActivity)
					// getActivity()).getSupportFragmentManager()
					// .beginTransaction();
					// fm.replace(R.id.container, new
					// Bee_Fragment_CashIn_Swallow().newInstance(String.valueOf(a)));
					// fm.addToBackStack("tag");
					// fm.commit();
				}
			});
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

	private String getScreenByIndex(int position) {
		// if (position == R.id.lnlSwallowCashIn) {
		// return Util.SCREEN_CASHIN_SWALLOW;
		// }
		if (position == R.id.lnlSwallowCashOut) {
			return Util.SCREEN_CASHOUT_SWALLOW;
		}
		if (position == R.id.lnlSwallowTransfer) {
			return Util.SCREEN_TRANSFER_SWALLOW;
		}
		if (position == R.id.lnlSwallowVAS) {
			return Util.SCREEN_VAS_SWALLOW;
		}
		if (position == R.id.lnlSodu) {
			return Util.SCREEN_VAS_SODU;
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