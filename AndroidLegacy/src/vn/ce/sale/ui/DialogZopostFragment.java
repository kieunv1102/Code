package vn.ce.sale.ui;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.util.TimeUtil;
import vn.ce.sale.util.Util;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import vn.ce.sale.R;
public abstract class DialogZopostFragment extends DialogFragment {

	protected int idLayout;
	protected ICallBackFragmentToActivity uiCallBack;
	protected Bundle params;
	protected Bundle paramsToActivity;

	public DialogZopostFragment() {
		super();
		initParamsForFragment();
	}

	// order 1
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		uiCallBack = (ICallBackFragmentToActivity) ((ZopostActivity) activity);// .onParamsFromFragment(paramsToActivity);
		// uiCallBack.onParamsFromFragment(paramsToActivity);

	}

	// order 2
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getParams();
	}

	// order 3
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	// order 4
	@Override
	public void onStart() {
		// getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onStart();
		// ready for fragment
		startLoadData();
	}

	public void reloadData() {
		startLoadData();
	}

	protected void sendDataToActivity(Bundle bundle) {
		((ICallBackFragmentToActivity) (ZopostActivity) getActivity()).onParamsFromFragment(bundle);
	}

	protected void sendDataToNavigateScreen(String screenID, Bundle bundle) {
		if (bundle == null)
			bundle = new Bundle();
		bundle.putString("screen", screenID);
		sendDataToActivity(bundle);
	}

	protected abstract void startLoadData();

	// method to process data on ui background
	protected void runOnUiThreadX(Runnable irunable) {
		Log.v("lamlt", "Current activity On IDTFragment:" + getActivity());
		if (isAdded())
			this.getActivity().runOnUiThread(irunable);
	}

	// need to implements if there is need to use
	protected abstract void initParamsForFragment();

	// implement for UI common
	protected View rootView;
	protected ProgressBar progress;

	protected void setupUI() {
		progress = (ProgressBar) rootView.findViewById(R.id.form_pb);
	}

	protected void freeUI() {

	}

	protected void getParams() {
		params = getArguments();
		if (params == null)
			params = new Bundle();
	}

	protected void nextToActivity(Class<?> activity, Bundle args, boolean isFinish) {
		Intent intent = new Intent(getActivity(), activity);
		if (args != null)
			intent.putExtra(ZopostActivity.DATA_KEY, args);
		startActivity(intent);
		if (isFinish)
			getActivity().finish();
	}

	protected abstract void fillDataSource(int status, List<JSONObject> lst);

	protected void showLoading() {
		this.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (progress != null)
					progress.setVisibility(View.VISIBLE);
			}
		});
	}

	protected void dismissLoading() {
		if (this.getActivity() == null)
			return;
		this.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (progress != null)
					progress.setVisibility(View.GONE);
			}
		});
	}

	protected void nextToFragment(DialogZopostFragment frag, Bundle args) {
		nextToFragment(frag, args, R.id.container);// error here
	}

	protected void nextToFragment(Fragment frag, Bundle args, int container) {
		if (args != null)
			frag.setArguments(args);
		FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();// error
																											// here:
																											// on
																											// a
																											// null
																											// object
		transaction.replace(R.id.container, frag, frag.getClass().getName());
		transaction.commitAllowingStateLoss();
	}

	public void showDialogGridIdt(String msg, View view, OnClickListener okClick, OnClickListener cancelClick) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
				android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		builder.setTitle("Alert").setMessage(msg).setPositiveButton("OK", okClick).setNegativeButton("Cancel",
				cancelClick);
		// Create the AlertDialog object and return it
		builder.setView(view);
		builder.create().show();
	}

	public void showDialogIdt(final String msg) {
		runOnUiThreadX(new Runnable() {

			@Override
			public void run() {
				// Use the Builder class for convenient dialog construction
				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
				builder.setTitle("Alert").setMessage(msg)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// FIRE ZE MISSILES!
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User cancelled the dialog
					}
				});
				// Create the AlertDialog object and return it
				builder.create().show();

			}
		});
	}

	public static class ViewHolder {
		public TextView text;
		public ImageView icon;
		public ProgressBar pb;
	}

	protected String getValueStringDateTimePicker(DatePicker datePicker) {
		int day = datePicker.getDayOfMonth();
		int month = datePicker.getMonth();
		int year = datePicker.getYear();

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);

		return TimeUtil.dateToString(calendar.getTime(), "yyyyMMddHHmmss");
	}

	protected void setValueStringDatePicker(DatePicker dp, String string) {
		// TODO Auto-generated method stub
		Date dt = TimeUtil.stringToDate(string, "yyyyMMddHHmmss");
		// dp.init(dt.getYear(), dt.getMonth(), dt.getDay(),null);
		dp.updateDate(dt.getYear(), dt.getMonth(), dt.getDay());
	}

	protected CharSequence lookupTitleByValueInArrayJson(JSONArray jsonArray, String string) throws JSONException {
		// TODO Auto-generated method stub
		for (int j = 0; j <= jsonArray.length() - 1; j++) {
			JSONObject o = (JSONObject) jsonArray.get(j);
			if (o.getString("id").equalsIgnoreCase(string)) {
				return o.getString("title");
			}
		}
		return null;
	}

	protected void backToHome() {
		sendDataToActivity(BundleData.createNew().putString("screen", Util.SCREEN_HOME).data());

	}

}
