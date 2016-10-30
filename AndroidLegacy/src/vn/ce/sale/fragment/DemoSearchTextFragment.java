package vn.ce.sale.fragment;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.adapter.CustomGrid;
import vn.ce.sale.adapter.CustomGridAndFilter;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DemoSearchTextFragment extends ZopostFragment implements ICallBackActivityToFragment, ICallBackUI {

	public static final String ARG_OBJECT = "object";
	CustomGridAndFilter adapter = null;
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
		adapter.getFilter().filter(args.getString(ARG_OBJECT));
	}

	@Override
	public void process(String key, int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void startLoadData() {
		// TODO Auto-generated method stub
		ServiceManager.factoryData().getDataRaw("http://mp4.sabi.vn/api/getDataChart.aspx?",
				"zip=1&token=mp4maxpro@idtvn&c=20", this);

	}

	@Override
	public void processRaw(String key, int status, final String json) {
		runOnUiThreadX(new Runnable() {

			@Override
			public void run() {
				List<JSONObject> lstJsonObjects = TransformDataManager.getListJsonByXPath(json, "data");
				if (adapter == null) {
					// TODO Auto-generated method stub
					// JSONObject= new JSONObject(json);
					// TODO Auto-generated method stub
					adapter = new CustomGridAndFilter(getActivity(), lstJsonObjects, R.layout.fragment_search_grid_row);
					// TicketNumber_TradeType_Currency_TimeFrame
					adapter.bindFields(new BindDataUI[] { BindDataUI.createNew(R.id.grid_text, "currency", TypeUI.TEXT),
							BindDataUI.createNew(R.id.grid_button, "lastupdate", TypeUI.BUTTON,
									new View.OnClickListener() {

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
										o.put("lastupdate", o.get("lastupdate") + "...");
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
					grid = (ListView) rootView.findViewById(R.id.grid);

					View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_search_grid_loadmore, null);
					grid.addFooterView(v);
					((Button) v.findViewById(R.id.grid_button)).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							startLoadData();
						}
					});
					grid.setAdapter(adapter);
				} else {
					adapter.getDataSource().addAll(lstJsonObjects);
					adapter.notifyDataSetChanged();
				}
			}
		});

	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

}