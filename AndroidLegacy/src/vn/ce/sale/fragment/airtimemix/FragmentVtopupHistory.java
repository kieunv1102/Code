package vn.ce.sale.fragment.airtimemix;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.adapter.ChangeAdapter;
import vn.ce.sale.adapter.DisplayHistoryGrid;
import vn.ce.sale.adapter.DisplayOrderProductGrid;
import vn.ce.sale.data.DBManager;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.IData;
import vn.ce.sale.data.IDataCheck;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.TimeUtil;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentVtopupHistory extends ZopostFragment implements IData, IDataCheck{

	ListView gridHistory;
	DisplayHistoryGrid adapterHistory;
	List<JSONObject> lstObj = new ArrayList<JSONObject>();
	protected void initCreatedView() {

	}


	// List<JSONObject> _dataSource = new ArrayList<JSONObject>();
	public void onCreated(Bundle savedInstanceState) {

	}

	@SuppressLint("UseValueOf")
	@SuppressWarnings("unused")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// setup ui
		rootView = inflater.inflate(R.layout.vi21_fragment_vtopup_history, container, false);
		gridHistory = (ListView) rootView.findViewById(R.id.grid_history);
		
		return rootView;
	}



	@Override
	protected void setupUI() {
		super.setupUI();
		setHasOptionsMenu(true);
	}

	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return super.getView();
	}




	@Override
	protected void startLoadData() {
		Util.checkChangeOrderProduct = true;
		String his = ShareMemManager.getInstance().readFromDB(getActivity(), "history_vtopup");
		try {
			lstObj= TransformDataManager.convertArrayToListJSON(new JSONArray(his));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<JSONObject> lstObjCost = new ArrayList<JSONObject>();
		for (int i = lstObj.size() - 1; i >= 0; --i) {
		    lstObjCost.add(lstObj.get(i));
		}
		adapterHistory = new DisplayHistoryGrid(getActivity(), lstObjCost);
		gridHistory.setAdapter(adapterHistory);
		adapterHistory.notifyDataSetChanged();

	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasDataSource() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void saveDataSource() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendData(JSONObject o) {
		// TODO Auto-generated method stub

	}

	@Override
	public JSONObject getData(JSONObject o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initParamsForFragment() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void fillDataSource(int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

}