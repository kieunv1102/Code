package vn.ce.sale.fragment;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.HomeActivity1;
import vn.ce.sale.adapter.CustomGridAndFilter;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.TypeUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.ui.ZopostValue;
import vn.ce.sale.util.FileUtil;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.view.Menu;
import android.widget.AdapterView;
import android.view.MenuInflater;

public class Fragment_Order_ListStatus1_Off extends ZopostFragment implements ICallBackActivityToFragment, ICallBackUI {

	public static final String ARG_OBJECT = "object";
	CustomGridAndFilter adapter = null;
	View rootView;
	View footerGrid;

	ListView grid;
	ProgressBar form_pb;
	int page;
	String status = "0";
	private HomeActivity1 mActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.vi21_layout_customer_list, container, false);
		grid = (ListView) rootView.findViewById(R.id.grid_Customer);
		// footerGrid= inflater.inflate(R.layout.fragment_search_grid_loadmore,
		// container, false);
		Util.saveActionUser(getActivity(), "DS-DON-SALE-OFF", (new Date()).getTime());
		form_pb = (ProgressBar) rootView.findViewById(R.id.form_pb);
		setHasOptionsMenu(true);
		mActivity = (HomeActivity1) getActivity();
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
		// status=params.getString("status");
		// TODO Auto-generated method stub
		// ServiceManager.factoryData().getDataRaw(Util.SERVER_URL+"/xorders.aspx?memberid="+Util.memberid+"&type=get&status="+status,
		// "&zip=1&token=abc12345&p="+String.valueOf(page), this);
		List<JSONObject> arrData = FileUtil.readJsonFromMetaData("Order_Offline");
		bindDataOfflineToUI("", 200, arrData);
	}

	protected void refreshData() {
		startLoadData();
		// page=0;
		// TODO Auto-generated method stub
		// ServiceManager.factoryData().getDataRaw(Util.SERVER_URL+"/xorders.aspx?memberid="+Util.memberid+"&type=get&status="+status,
		// "&zip=1&token=abc12345&p="+String.valueOf(page),DataOrder.NETWORK_THEN_CACHE,
		// this);
	}

	@Override
	public void processRaw(String key, final int status, final String json) {

	}

	public void bindDataOfflineToUI(String key, final int status, final List<JSONObject> lstJsonObjects) {

		// if(1==1) return;
		runOnUiThreadX(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (status == 1001) {
					form_pb.setVisibility(View.VISIBLE);
					return;
				} // displayloading with json is percentage of loading data
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
					// List<JSONObject> lstJsonObjects=
					// TransformDataManager.convertStringToListJSON(json);
					if (adapter == null) {
						// TODO Auto-generated method stub
						adapter = new CustomGridAndFilter(getActivity(), lstJsonObjects,
								R.layout.layout_customer_list_row, new String[] { "code", "note" });
						// TicketNumber_TradeType_Currency_TimeFrame
						adapter.bindFields(new BindDataUI[] { BindDataUI.createNew(R.id.customer_title, "title",
								TypeUI.COMPLEX, null, new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o, BindDataUI ui) {
										// TODO Auto-generated method stub
										try {
											return o.getString("id") + "Code:" + o.getString("code") + "\n"
													+ o.getString("time");
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											return e.toString();
										}
									}
								})

								, BindDataUI.createNew(R.id.customer_address, "note", TypeUI.TEXT),
								BindDataUI.createNew(R.id.imgdetail, "id", TypeUI.IMAGE_STATIC, new OnClickListener() {
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										int position = (Integer) v.getTag();

										try {
											JSONObject oView = adapter.getDataSource().get(position);
											// uiCallBack.onParamsFromFragment(
											// BundleData.createNew().putDouble("lat",oView.getDouble("lat"))
											// .putDouble("lng",oView.getDouble("lng")).putString("type","showlocation").data());
											LinearLayout vwParentRow = (LinearLayout) v.getParent();
											ImageView child = (ImageView) vwParentRow.findViewById(R.id.imgdetail);
											PopupMenu popup = new PopupMenu(getActivity(), child);
											// Inflating the Popup using xml
											// file
											popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

											// registering popup with
											// OnMenuItemClickListener
											popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
												public boolean onMenuItemClick(MenuItem item) {
													// Toast.makeText(getActivity(),"You
													// Clicked : " +
													// item.getTitle(),Toast.LENGTH_SHORT).show();
													// nextToActivity(ReportPicture.class,
													// null, false);
													sendDataToActivity(BundleData.createNew()
															.putString("screen", "reportpicture").data());
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
								}), BindDataUI.createNew(R.id.customer_detail, "code", TypeUI.COMPLEX, null,
										new ZopostValue() {

									@Override
									public String parseFromSource(JSONObject o1, BindDataUI ui) {
										StringBuilder sBuffer = new StringBuilder();

										try {
											sBuffer.append("Thời gian:" + o1.getString("time") + "\n");
											sBuffer.append("Khách hàng:" + o1.getString("kh") + "\n");
											JSONArray array = o1.getJSONArray("pro");
											for (int jx = 0; jx <= array.length() - 1; jx++) {
												JSONObject o = array.getJSONObject(jx);

												// TODO Auto-generated method
												// stub
												sBuffer.append(String.valueOf(jx + 1) + "." + o.getString("title")
														+ "     " + o.getString("sl") + " " + o.getString("ut") + "\n");
											}
										} catch (Exception ex) {
											return "Error...";
										}
										return sBuffer.toString();
									}
								}) });

						// uiCallBack.onParamsFromFragment(paramsToActivity);
						// grid=(ListView)rootView.findViewById(R.id.grid);
						// footerGrid=(getActivity()).getLayoutInflater().inflate(R.layout.fragment_search_grid_loadmore,
						// null);

						// Activity mActivity=getActivity();
						/*
						 * footerGrid=((LayoutInflater)(mActivity).
						 * getSystemService(
						 * Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.
						 * fragment_search_grid_loadmore, null);
						 * 
						 * ((Button)footerGrid.findViewById(R.id.grid_button)).
						 * setOnClickListener(new View.OnClickListener() {
						 * 
						 * @Override public void onClick(View v) { // TODO
						 * Auto-generated method stub page++; startLoadData(); }
						 * });
						 */
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
								try {
									// nextToFragment(new
									// Fragment_customer_View(),
									// BundleData.createNew().putString("id",
									// adapter.getDataSource().get(position).getString("id")).data());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
						grid.setAdapter(adapter);
						// grid.addFooterView(footerGrid);
					} else {
						if (page == 0)
							adapter.setDataSource(lstJsonObjects);
						else
							adapter.getDataSource().addAll(lstJsonObjects);
						adapter.notifyDataSetChanged();
					}

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
			nextToFragment(new Fragment_Customer_Edit(), null);
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
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}
}