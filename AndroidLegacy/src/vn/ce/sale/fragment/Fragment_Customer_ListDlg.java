package vn.ce.sale.fragment;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.adapter.CustomGrid;
import vn.ce.sale.adapter.CustomGridAndFilter;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.DialogZopostFragment;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.TypeUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.ui.ZopostValue;
import vn.ce.sale.util.TimeUtil;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.R.integer;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.view.Menu;
import android.widget.AdapterView;
import android.view.MenuInflater;

public class Fragment_Customer_ListDlg extends DialogZopostFragment
		implements ICallBackActivityToFragment, ICallBackUI {

	public static final String ARG_OBJECT = "object";
	CustomGridAndFilter adapter = null;
	View rootView;
	View footerGrid;

	ListView grid;
	ProgressBar form_pb;
	int page;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.vi21_layout_customer_list, container, false);
		grid = (ListView) rootView.findViewById(R.id.grid_Customer);
		form_pb = (ProgressBar) rootView.findViewById(R.id.form_pb);
		// setHasOptionsMenu(true) ;
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
		if (args == null)
			return;
		if (adapter == null)
			return;
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
		ServiceManager.factoryData().getDataRaw(
				Util.SERVER_URL + "/agency.aspx?memberid=" + Util.memberid + "&type=get&",
				"zip=1&token=abc12345&p=" + String.valueOf(page), DataOrder.CACHE_THEN_NETWORK, this);

	}

	protected void refreshData() {
		page = 0;
		// TODO Auto-generated method stub
		ServiceManager.factoryData().getDataRaw(
				Util.SERVER_URL + "/agency.aspx?memberid=" + Util.memberid + "&type=get&",
				"zip=1&token=abc12345&p=" + String.valueOf(page), DataOrder.NETWORK_THEN_CACHE, this);
	}

	@Override
	public void processRaw(String key, final int status, final String json) {
		runOnUiThreadX(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (status == 1001) {
					form_pb.setVisibility(View.VISIBLE);
					return;
				}
				// displayloading with json is percentage of loading data
				if (status == 200) {
					form_pb.setVisibility(View.GONE);
					grid.setVisibility(View.VISIBLE);
					/*
					 * String json1=
					 * "[{\"id\":\"13\",\"title\":\" - dai ly D�ng Anh\",\"address\":\"544\"}"
					 * +
					 * ",{\"id\":\"13\",\"title\":\" - dai ly Li�n hoa\",\"address\":\"544\"}"
					 * +"]";
					 */
					List<JSONObject> lstJsonObjects = TransformDataManager.convertStringToListJSON(json);
					if (adapter == null) {
						// TODO Auto-generated method stub
						adapter = new CustomGridAndFilter(getActivity(), lstJsonObjects,
								R.layout.layout_customer_list_row);
						// TicketNumber_TradeType_Currency_TimeFrame
						adapter.bindFields(new BindDataUI[] { BindDataUI.createNew(R.id.customer_title, "title",
								TypeUI.COMPLEX, null, new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o, BindDataUI ui) {
										// TODO Auto-generated method stub
										try {
											return o.getString("id") + "." + o.getString("title");
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											return e.toString();
										}
									}
								})

								, BindDataUI.createNew(R.id.customer_address, "address", TypeUI.TEXT),
								BindDataUI.createNew(R.id.imgdetail, "id", TypeUI.IMAGE_STATIC, new OnClickListener() {
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										final int position = (Integer) v.getTag();

										try {
											final JSONObject oView = adapter.getDataSource().get(position);
											final String id = oView.getString("id");
											LinearLayout vwParentRow = (LinearLayout) v.getParent();
											ImageView child = (ImageView) vwParentRow.findViewById(R.id.imgdetail);
											PopupMenu popup = new PopupMenu(getActivity(), child);
											popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
											// registering popup with
											// OnMenuItemClickListener
											popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
												public boolean onMenuItemClick(MenuItem item) {
													sendDataToNavigateScreen(
															Util.getScreenIdByPositionInPopup(item.getOrder()),
															BundleData.createNew()
																	.putString("typeid", Util.PARENT_DSDAILY)
																	.putString("placeid", id)
																	.putString("datax", oView.toString()).data());
													return true;
												}
											});

											popup.show();// showing popup menu
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}, new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o, BindDataUI ui) {
										// TODO Auto-generated method stub
										return "...";
									}
								}), BindDataUI.createNew(R.id.customer_detail, "contactname", TypeUI.COMPLEX, null,
										new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o, BindDataUI ui) {
										try {
											// TODO Auto-generated method stub
											return o.getString("mail")
													+ (TextUtils.isEmpty(o.getString("mail")) ? "" : "\n")
													+ o.getString("tel")
													+ (TextUtils.isEmpty(o.getString("tel")) ? "" : "\n")
													+ o.getString("contactname")
													+ (TextUtils.isEmpty(o.getString("contactname")) ? "" : "\n")
													+ o.getString("contactnumber");
										} catch (Exception ex) {
											return "Error...";
										}
									}
								}) });
						// uiCallBack.onParamsFromFragment(paramsToActivity);
						// grid=(ListView)rootView.findViewById(R.id.grid);
						footerGrid = getActivity().getLayoutInflater().inflate(R.layout.fragment_search_grid_loadmore,
								null);
						grid.addFooterView(footerGrid);
						((Button) footerGrid.findViewById(R.id.grid_button))
								.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								page++;
								startLoadData();
							}
						});
						grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								// Toast.makeText(getActivity(), "You Clicked at
								// " +(position), Toast.LENGTH_SHORT).show();
								// LinearLayout vwParentRow =
								// (LinearLayout)view.getParent();
								View child = (View) view.findViewById(R.id.detailCustomer);
								if (child.getVisibility() == View.GONE)
									child.setVisibility(View.VISIBLE);
								else
									child.setVisibility(View.GONE);
								// dismiss();

							}
						});
						grid.setAdapter(adapter);
					} else {
						if (page == 0)
							adapter.setDataSource(lstJsonObjects);
						else
							adapter.getDataSource().addAll(lstJsonObjects);
						adapter.notifyDataSetChanged();
					}
					if (json.equals("[]") || lstJsonObjects.size() <= 10)
						footerGrid.setVisibility(View.GONE);
				}
			}
		});

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		// ((IdtActivity)
		// getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		inflater.inflate(R.menu.main_action, menu);
		SearchManager searchManager = (SearchManager) (getActivity().getSystemService(Context.SEARCH_SERVICE));
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo((getActivity().getComponentName())));

		// auto search on client
		searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) getActivity());
		setupMenuItem(menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			// save it here
			page = 0;
			startLoadData();
			return true;
		case R.id.action_add:
			// nextToFragment(new Fragment_Customer_Edit(), null);
			return true;
		case R.id.action_upload:
			// nextToFragment(new Fragment_Customer_UploadOffline(), null);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void setupMenuItem(Menu menu) {
		menu.findItem(R.id.action_add).setVisible(true);
		menu.findItem(R.id.action_search).setVisible(true);
		menu.findItem(R.id.action_save).setVisible(false);
		menu.findItem(R.id.action_edit).setVisible(false);
		menu.findItem(R.id.action_refresh).setVisible(true);
		menu.findItem(R.id.action_list).setVisible(false);
		menu.findItem(R.id.action_upload).setVisible(false);
		menu.findItem(R.id.action_exit).setVisible(false);
		menu.findItem(R.id.action_saveoffline).setVisible(false);
	}
}