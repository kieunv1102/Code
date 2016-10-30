package vn.ce.sale.fragment;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.adapter.CustomGrid;
import vn.ce.sale.adapter.CustomGridAndFilter;
import vn.ce.sale.adapter.CustomGridCatalog;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.TypeUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.R;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DemoListViewCatalogFragment extends ZopostFragment implements ICallBackActivityToFragment, ICallBackUI {

	public static final String ARG_OBJECT = "object";
	CustomGridCatalog adapter = null;
	View rootView;
	ListView grid;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_search_grid, container, false);
		grid = (ListView) rootView.findViewById(R.id.grid);
		Bundle args = getArguments();
		// ((TextView)
		// rootView.findViewById(android.R.id.text1)).setText(args.getString(ARG_OBJECT));
		// ((TextView) rootView.findViewById(android.R.id.text1)).setText("Demo
		// me...");
		return rootView;
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
	public void onParamsFromActivity(Bundle args) {
		// TODO Auto-generated method stub
		// ((TextView)
		// rootView.findViewById(android.R.id.text1)).setText(args.getString(ARG_OBJECT));
		// adapter.getFilter().filter(args.getString(ARG_OBJECT));
	}

	@Override
	public void process(String key, int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void startLoadData() {
		// TODO Auto-generated method stub
		ServiceManager.factoryData().getDataRaw("http://watchingvietnam.vn/api/app/getDataHome.aspx?",
				"zip=1&token=mp4maxpro@idtvn&c=20", this);

	}

	@Override
	public void processRaw(String key, int status, final String json) {
		if (status == 1001) {
			Log.v("DemoRealTime", "Value RealTime:" + json);
			return;
		}
		runOnUiThreadX(new Runnable() {

			@Override
			public void run() {
				List<JSONObject> lstJsonObjects = TransformDataManager.convertStringToListJSON2Level(
						TransformDataManager.getListJsonByXPath(json, "data").toString());// ,
																							// "data"
				if (adapter == null) {
					// TODO Auto-generated method stub
					// JSONObject= new JSONObject(json);
					// TODO Auto-generated method stub
					adapter = new CustomGridCatalog(getActivity(), lstJsonObjects, R.layout.fragment_search_grid_header,
							R.layout.fragment_search_grid_row, "data");
					// TicketNumber_TradeType_Currency_TimeFrame
					adapter.bindFieldCatalog(new BindDataUI[] {
							BindDataUI.createNew(R.id.grid_text, "catname", TypeUI.TEXT, new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									int position = (Integer) v.getTag();
									// update on ui
									LinearLayout vwParentRow = (LinearLayout) v.getParent();
									TextView child = (TextView) vwParentRow.findViewById(R.id.grid_text);
									child.setText(child.getText() + " ...");

									// update on datasource
									JSONObject o = adapter.getDataSource().get(position);
									try {
										o.put("catname", o.get("catname") + "...");
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									adapter.getDataSource().set(position, o);

									int c = Color.CYAN;
									vwParentRow.setBackgroundColor(c);
									vwParentRow.refreshDrawableState();

								}
							}) });
					adapter.bindFields(new BindDataUI[] { BindDataUI.createNew(R.id.grid_text, "title", TypeUI.TEXT),
							BindDataUI.createNew(R.id.grid_button, "idfile", TypeUI.BUTTON, new View.OnClickListener() {

								@Override
								public void onClick(View v) {

									// TODO Auto-generated method stub
									int position = (Integer) v.getTag();
									// update on ui
									LinearLayout vwParentRow = (LinearLayout) v.getParent();
									Button child = (Button) vwParentRow.findViewById(R.id.grid_button);
									child.setText(child.getText() + " ...");

									// update on datasource
									JSONObject o = adapter.getDataSource().get(position);
									try {
										o.put("idfile", o.get("idfile") + "...");
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									adapter.getDataSource().set(position, o);

									int c = Color.CYAN;
									vwParentRow.setBackgroundColor(c);
									vwParentRow.refreshDrawableState();
									/**/
									PopupMenu popup = new PopupMenu(getActivity(), child);
									// Inflating the Popup using xml file
									popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

									// registering popup with
									// OnMenuItemClickListener
									popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
										public boolean onMenuItemClick(MenuItem item) {
											Toast.makeText(getActivity(), "You Clicked : " + item.getTitle(),
													Toast.LENGTH_SHORT).show();
											return true;
										}
									});

									popup.show();// showing popup menu

								}
							}) });
					grid = (ListView) rootView.findViewById(R.id.grid);
					/*
					 * View
					 * v=getActivity().getLayoutInflater().inflate(R.layout.
					 * fragment_search_grid_loadmore,null);
					 * grid.addFooterView(v);
					 * ((Button)v.findViewById(R.id.grid_button)).
					 * setOnClickListener(new View.OnClickListener() {
					 * 
					 * @Override public void onClick(View v) { // TODO
					 * Auto-generated method stub startLoadData(); } });
					 */
					grid.setAdapter(adapter);
				}
				/*
				 * else { adapter.getDataSource().addAll(lstJsonObjects);
				 * adapter.notifyDataSetChanged(); }
				 */
			}
		});

	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

}