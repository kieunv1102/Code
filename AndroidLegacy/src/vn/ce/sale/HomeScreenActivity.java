package vn.ce.sale;

import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackFragmentToActivity;
import vn.ce.sale.ui.ZopostActivity;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.R;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;

public class HomeScreenActivity extends ZopostActivity implements SearchView.OnQueryTextListener,
		NavigationDrawerFragment.NavigationDrawerCallbacks, ICallBackFragmentToActivity {
	public static boolean isFirstTime = false;
	private NavigationDrawerFragment mNavigationDrawerFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.activity_register1);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

		init_UI(this);
	}

	private void init_UI(Activity activity) {
		Bundle bx = getParams();
		String screenString = bx.getString("screen");
		if (screenString == null)
			screenString = "";

		else if (screenString.equalsIgnoreCase("save")) {
			// fragMain= new Fragment_Location_Edit();
			replaceFragment(fragMain, BundleData.createNew().putString("latlng", bx.getString("latlng")).data(),
					R.id.container, true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_action, menu);

		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

		// auto search on client
		searchView.setOnQueryTextListener(this);
		// final int resource_edit_text =
		// searchView.getContext().getResources().getIdentifier("android:id/search_src_text",
		// null, null);
		// EditText editText=(EditText)
		// searchView.findViewById(resource_edit_text);
		// editText.addTextChangedListener(this);

		return true;

	}
	/*
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { switch
	 * (item.getItemId()) { case R.id.menu_item1: // save it here
	 * //if(validateUserRegister()){saveOffline();} return true; case
	 * R.id.menu_item2:
	 * //if(validateUserRegister()){callToServer(edtEmail.getText().toString(),
	 * edtPass.getText().toString());} return true; default: return
	 * super.onOptionsItemSelected(item); } }
	 */

	@Override
	public void onParamsFromFragment(Bundle data) {
		if (data == null)
			return;
		// TODO Auto-generated method stub
		String type = (data.getString("type"));
		if (type == null)
			return;
		if (type.equalsIgnoreCase("showlocation")) {
			Bundle conData = new Bundle();
			conData.putDouble("lat", data.getDouble("lat"));
			conData.putDouble("lng", data.getDouble("lng"));

			Intent intent = new Intent();
			intent.putExtras(conData);
			setResult(1001, intent);
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		if (getFragmentManager().getBackStackEntryCount() == 0) {
			this.finish();
		} else {
			getFragmentManager().popBackStack();
		}
	}

	public void onNavigationDrawerItemSelected(int position) {
		if (!isFirstTime)
			isFirstTime = true;
		else
			isFirstTime = false;

		// TODO Auto-generated method stub
		if (position == 0) {
			if (!isFirstTime)
				finish();
		} // me

		if (position == 3) {
			// nextToActivity(SettingsActivity.class, null);
		}
		if (position == 4) {
			finish();
		}

	}

	@Override
	protected void onNewIntent(Intent intent) {

		handleIntent(intent);
	}

	ZopostFragment fragMain;

	private void handleIntent(Intent intent) {
		// Toast.makeText(this,"Receive data:"+
		// intent.getStringExtra(SearchManager.QUERY),
		// Toast.LENGTH_LONG).show();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			// Toast.makeText(this,"Receive data:"+query,
			// Toast.LENGTH_LONG).show();
			// use the query to search your data somehow
			((ICallBackActivityToFragment) fragMain)
					.onParamsFromActivity(BundleData.createNew().putString("object", "" + query).data());
		}
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		((ICallBackActivityToFragment) fragMain)
				.onParamsFromActivity(BundleData.createNew().putString("object", "" + arg0).data());
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSelectedFragment(ZopostFragment selectedFragment) {
		// TODO Auto-generated method stub

	}


}
