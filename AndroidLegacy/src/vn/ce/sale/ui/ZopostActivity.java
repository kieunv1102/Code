package vn.ce.sale.ui;

import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public abstract class ZopostActivity extends ActionBarActivity {

	final public static String DATA_KEY = "DATA-IDT";
	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	protected CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	protected void restoreActionBar() {
		// ActionBar actionBar = getSupportActionBar();
		// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		// actionBar.setDisplayShowTitleEnabled(true);
		// actionBar.setTitle(mTitle);
	}

	protected void replaceFragment(Fragment frag, Bundle args, int container) {
		if (args != null)
			frag.setArguments(args);
		// if(frag.isAdded()) return;
		getSupportFragmentManager().beginTransaction().replace(container, frag, frag.getClass().getName())
				.commitAllowingStateLoss();
		/*
		 * FragmentManager fragmentManager = getSupportFragmentManager();
		 * FragmentTransaction fragmentTransaction =
		 * fragmentManager.beginTransaction();
		 * fragmentTransaction.replace(container,frag);
		 * fragmentTransaction.commit();
		 */
	}

	protected void replaceFragment(Fragment frag, Bundle args, int container, boolean isBackStack) {
		// if(args!=null) frag.setArguments(args);
		// if(isBackStack)
		// {
		// getSupportFragmentManager()
		// .beginTransaction()
		// .replace(container,
		// frag).commit();
		// //fragTransaction.addToBackStack(null);
		// }
		// else
		replaceFragment(frag, args, container);
	}

	protected void nextToActivity(Class<?> activity, Bundle args) {
		nextToActivity(activity, args, false);
	}

	protected void nextToActivity(Class<?> activity, Bundle args, boolean isFinish) {
		Intent intent = new Intent(this, activity);
		// if(args!=null) intent.putExtras(args);
		if (args != null)
			intent.putExtra(DATA_KEY, args);
		startActivity(intent);
		if (isFinish)
			finish();
	}

	protected void nextToActivityResult(Class<?> activity, Bundle args, int requestCode) {
		Intent intent = new Intent(this, activity);
		// if(args!=null) intent.putExtras(args);
		if (args != null)
			intent.putExtra(DATA_KEY, args);
		startActivityForResult(intent, requestCode);
		// finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void showDialogIdt(final String msg) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// Use the Builder class for convenient dialog construction
				AlertDialog.Builder builder = new AlertDialog.Builder(getCurrentFocus().getContext());
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

	protected Bundle getParams() {
		if (getIntent().getExtras() == null)
			return new Bundle();
		return getIntent().getExtras().getBundle(DATA_KEY);
	}

	// public void abstract setupNavigator();
	public void showDialogGridIdt(String msg, View view, OnClickListener okClick, OnClickListener cancelClick) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		builder.setTitle("Alert").setMessage(msg).setPositiveButton("OK", okClick).setNegativeButton("Cancel",
				cancelClick);
		// Create the AlertDialog object and return it
		builder.setView(view);
		builder.create().show();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// No call for super(). Bug on API Level > 11.
	}

	protected void showDialogFragment(DialogZopostFragment dialog, Bundle bundle) {
		String tag = dialog.getClass().getName();
		FragmentManager manager = getSupportFragmentManager();
		Fragment frag = manager.findFragmentByTag(tag);
		if (frag != null) {
			manager.beginTransaction().remove(frag).commit();
		}

		// Fragment_Location_EditDlg editNameDialog = new
		// Fragment_Location_EditDlg();
		dialog.setArguments(bundle);
		dialog.show(getSupportFragmentManager(), tag);
	}
}
