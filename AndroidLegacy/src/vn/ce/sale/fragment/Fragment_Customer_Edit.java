package vn.ce.sale.fragment;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.adapter.CustomGrid;
import vn.ce.sale.adapter.ZopostSpinnerAdapter;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.ICallBack;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.DownloadImageTask;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ImageLoadingHolder;
import vn.ce.sale.ui.TypeUI;
import vn.ce.sale.ui.ZopostActivity;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.ui.ZopostValue;
import vn.ce.sale.util.FileUtil;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.TimeUtil;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import vn.ce.sale.R.layout;
import android.R.id;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CheckBox;

public class Fragment_Customer_Edit extends ZopostFragment implements ICallBackActivityToFragment, ICallBackUI {
	ProgressBar form_pb;

	protected void initCreatedView() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// setup ui
		rootView = inflater.inflate(R.layout.layout_customer_edit, container, false);
		setupUI();
		form_pb = (ProgressBar) rootView.findViewById(R.id.form_pb);
		setHasOptionsMenu(true);
		// start load data in other thread
		return rootView;// return super.onCreateView(inflater, container,
						// savedInstanceState);
	}

	// var in edit android class
	private EditText editId;
	private EditText editTitle;
	private EditText editNote;

	private Spinner editCatalogcustomerid;

	@Override
	protected void setupUI() {
		super.setupUI();
		setHasOptionsMenu(true);

		// var to link ui and xml

		editTitle = (EditText) rootView.findViewById(R.id.editTitle);
		editTitle.setHint("Nhập tên...");
		editNote = (EditText) rootView.findViewById(R.id.editNote);
		editNote.setHint("Nhập địa chỉ");
		editCatalogcustomerid = (Spinner) rootView.findViewById(R.id.editCatalogcustomerid);

		if (params != null && params.getString("latlng") != null) {
			((TextView) rootView.findViewById(R.id.viewLatLng)).setText("Tọa độ:" + params.getString("latlng"));
		}
	}

	@Override
	protected void initParamsForFragment() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void startLoadData() {
		// edit an item
		if (params != null && params.getString("id") != null) {
			ServiceManager.factoryData().getDataRaw(
					Util.SERVER_URL + "api/xagency.aspx?type=view&zip=1&id=" + params.getString("id"), "", this);
		}
		// load esstential item for combobox
		else {
			// load source data for spinner...
			ServiceManager.factoryData().getDataRaw(Util.SERVER_URL + "api/xagency.aspx?type=source&zip=1", "",
					DataOrder.CACHE_THEN_NETWORK, this);
		}
	}

	private boolean validateData() {
		// params in validate...

		if (TextUtils.isEmpty(editTitle.getText().toString())) {
			editTitle.setError(this.getString(R.string.text_error_title));
			return false;
		}
		if (TextUtils.isEmpty(editNote.getText().toString())) {
			editNote.setError(this.getString(R.string.text_error_note));
			return false;
		}
		return true;

	}

	public void callToServer(String... paramsString) {
		HashMap<String, String> paramToPost = new HashMap<String, String>();
		if (params != null && params.getString("id") != null)
			paramToPost.put("id", params.getString("id"));

		paramToPost.put("title", editTitle.getText().toString());
		paramToPost.put("note", editNote.getText().toString());
		paramToPost.put("catalogcustomerid", String.valueOf(((ZopostSpinnerAdapter) editCatalogcustomerid.getAdapter())
				.getItemId(editCatalogcustomerid.getSelectedItemPosition())));
		paramToPost.put("data", ShareMemManager.getInstance().readLastActive(getContext()));
		if (params != null && params.getString("latlng") != null) {
			String latlng = params.getString("latlng");
			String[] arrLatlng = latlng.split(",");
			paramToPost.put("lat", arrLatlng[0]);
			paramToPost.put("lng", arrLatlng[1]);
		}
		ServiceManager.factoryData().postDataRaw(Util.SERVER_URL + "api/xagency.aspx?zip=1&type=update&", paramToPost,
				this);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		// ((IdtActivity)
		// getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		inflater.inflate(R.menu.main_action, menu);
		setupMenuItem(menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_save:
			// save it here

			if (validateData()) {
				form_pb.setVisibility(View.VISIBLE);
				callToServer();
			}
			return true;
		case R.id.action_saveoffline:
			if (validateData()) {
				form_pb.setVisibility(View.VISIBLE);
				saveOffline();
			}
			return true;
		case R.id.action_list:
			nextToFragment(new Fragment_Customer_List(), null);

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onParamsFromActivity(Bundle args) {
		// TODO Auto-generated method stub
		// ((TextView)
		// rootView.findViewById(android.R.id.text1)).setText(args.getString(ARG_OBJECT));
		// adapter.getFilter().filter(args.getString(ARG_OBJECT));
	}

	/**/
	@Override
	protected void fillDataSource(int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(String key, int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processRaw(final String key, final int status, final String json) {
		runOnUiThreadX(new Runnable() {
			@Override
			public void run() {
				if (status == 1001) {
					form_pb.setVisibility(View.VISIBLE);
					return;

				} // displayloading with json is percentage of loading data
				if (status == 200) {
					form_pb.setVisibility(View.GONE);
					// is bind to edit
					if (key.indexOf("type=view") != -1) {
						ShareMemManager.getInstance().saveToDB(getActivity(), key, json);
						JSONObject o = null;
						try {
							o = new JSONObject(json);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						bindSpinnerByObject(o);
						bindEditByObject(o);
					}
					// show status update
					if (key.indexOf("type=source") != -1) {
						ShareMemManager.getInstance().saveToDB(getActivity(), key, json);
						JSONObject o = null;
						try {
							o = new JSONObject(json);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						bindSpinnerByObject(o);
					}
					// show status update
					if (key.indexOf("type=update") != -1) {
						nextToFragment(new Fragment_Customer_List(), null);
					}

				}
				if (status == vn.ce.sale.util.Util.ERROR_NETWORK) {

					((ZopostActivity) getActivity()).getSupportActionBar()
							.setTitle("Error Network:" + String.valueOf(status));
					form_pb.setVisibility(View.GONE);

				}
			}
		});
	}

	private void bindEditByObject(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		try {
			JSONObject oView = (JSONObject) jsonObject.get("data");
			{
				editTitle.setText(oView.getString("title"));
				editNote.setText(oView.getString("note"));
				editCatalogcustomerid.setSelection(((ZopostSpinnerAdapter) editCatalogcustomerid.getAdapter())
						.getPosition((oView.getString("catalogcustomerid"))));

				((TextView) rootView.findViewById(R.id.viewLatLng))
						.setText("Tọa độ:" + oView.getString("lat") + "," + oView.getString("lng"));

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void bindSpinnerByObject(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		try {
			JSONObject oView = jsonObject;// (JSONObject)
											// jsonObject.get("data");

			ZopostSpinnerAdapter adapterCatalogcustomerid = new ZopostSpinnerAdapter(getActivity(),
					oView.getJSONArray("catalogcustomerid"), new String[] { "id", "title" });
			editCatalogcustomerid.setAdapter(adapterCatalogcustomerid);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void saveOffline() {
		HashMap<String, String> paramToPost = new HashMap<String, String>();

		paramToPost.put("title", editTitle.getText().toString());
		paramToPost.put("note", editNote.getText().toString());
		paramToPost.put("catalogcustomerid", String.valueOf(((ZopostSpinnerAdapter) editCatalogcustomerid.getAdapter())
				.getItemId(editCatalogcustomerid.getSelectedItemPosition())));

		paramToPost.put("time", TimeUtil.getStringTimeNow("yyyyMMddHHmmss"));
		paramToPost.put("typeid", params.getString("typeid", "-1"));
		paramToPost.put("placeid", params.getString("placeid", "-1"));
		paramToPost.put("data", ShareMemManager.getInstance().readLastActive(getContext()));
		paramToPost.put("memberid", Util.memberid);

		if (params != null && params.getString("latlng") != null) {
			String latlng = params.getString("latlng");
			String[] arrLatlng = latlng.split(",");
			paramToPost.put("lat", arrLatlng[0]);
			paramToPost.put("lng", arrLatlng[1]);
		}

		// TODO Auto-generated method stub
		FileUtil.saveTextToMetaData("offline", "Agency", TimeUtil.getStringTimeNow("yyyyMMddHHmmss"), paramToPost);
		nextToFragment(new Fragment_Customer_List(), null);
	}

	public void setupMenuItem(Menu menu) {
		menu.findItem(R.id.action_add).setVisible(false);
		menu.findItem(R.id.action_search).setVisible(false);
		menu.findItem(R.id.action_save).setVisible(true);
		menu.findItem(R.id.action_edit).setVisible(false);
		menu.findItem(R.id.action_refresh).setVisible(false);
		menu.findItem(R.id.action_list).setVisible(true);
		menu.findItem(R.id.action_saveoffline).setVisible(true);
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

}