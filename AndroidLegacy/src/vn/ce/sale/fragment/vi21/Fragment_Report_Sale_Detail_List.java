package vn.ce.sale.fragment.vi21;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.adapter.AutoAdapter;
import vn.ce.sale.adapter.CustomGridAndFilter;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.IDBundleData;
import vn.ce.sale.data.IData;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.DialogZopostFragment;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.TypeUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.ui.ZopostValue;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;

public class Fragment_Report_Sale_Detail_List extends DialogZopostFragment
		implements OnClickListener, ICallBackActivityToFragment, ICallBackUI {

	EditText edtFromDay, edtFromMonth, edtFromYear;
	EditText edtToday, edtToMonth, edtToYear;
	ImageView imvSelectFromTime, imvSelectToTime;
	AutoCompleteTextView edtReportSearch;
	Button btnReportSearch;
	String fromDate, toDate;
	private int mYear, mMonth, mDay;
	IData handleData;

	String getFromDate, getToDate;

	IDBundleData dataBundle;

	public IDBundleData getDataBundle() {
		return dataBundle;
	}

	public void setDataBundle(IDBundleData dataBundle) {
		this.dataBundle = dataBundle;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.vi21_layout_report_sale_dialog, container, false);

		edtFromDay = (EditText) rootView.findViewById(R.id.edt_from_day);
		edtFromMonth = (EditText) rootView.findViewById(R.id.edt_from_month);
		edtFromYear = (EditText) rootView.findViewById(R.id.edt_from_year);

		edtToday = (EditText) rootView.findViewById(R.id.edt_to_day);
		edtToMonth = (EditText) rootView.findViewById(R.id.edt_to_month);
		edtToYear = (EditText) rootView.findViewById(R.id.edt_to_year);

		final Calendar cal = Calendar.getInstance();
		mDay = cal.get(Calendar.DAY_OF_MONTH);
		mMonth = cal.get(Calendar.MONTH);
		mYear = cal.get(Calendar.YEAR);

		edtFromDay.setText(String.valueOf(mDay));
		edtToday.setText(String.valueOf(mDay));
		edtFromMonth.setText(String.valueOf(mMonth + 1));
		edtToMonth.setText(String.valueOf(mMonth + 1));
		edtFromYear.setText(String.valueOf(mYear));
		edtToYear.setText(String.valueOf(mYear));

		imvSelectFromTime = (ImageView) rootView.findViewById(R.id.imv_select_from_time);
		imvSelectToTime = (ImageView) rootView.findViewById(R.id.imv_select_to_time);

		edtReportSearch = (AutoCompleteTextView) rootView.findViewById(R.id.edt_report_search);

		btnReportSearch = (Button) rootView.findViewById(R.id.btn_report_search);
		btnReportSearch.setOnClickListener(this);
		imvSelectFromTime.setOnClickListener(this);
		imvSelectToTime.setOnClickListener(this);
		setupAutoComplete();
		setHasOptionsMenu(true);
		return rootView;
	}

	private void setupAutoComplete() {
		// String s = ShareMemManager.getInstance().readFromDB(getContext(),
		// "product");// load

		// data

		JSONObject object;
		ArrayList<JSONObject> lst = new ArrayList<JSONObject>();
		try {
			object = new JSONObject(ShareMemManager.getInstance().readFromDB(getContext(), "product"));
			JSONArray array = new JSONArray(object.getString("ResponseMessage"));
			lst = (ArrayList<JSONObject>) TransformDataManager.convertArrayToListJSON(array);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// TODO Auto-generated method stub
		AutoAdapter adapter = new AutoAdapter(getContext(), lst, R.layout.layout_auto_list_row);
		edtReportSearch.setThreshold(1);

		edtReportSearch.setAdapter(adapter);
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
		// if (args == null)
		// return;
		// if (adapter == null) {
		// return;
		// } else
		// adapter.getFilter().filter(args.getString(ARG_OBJECT));
	}

	@Override
	protected void startLoadData() {
		getDialog().setTitle("Nhập thông tin báo cáo cần tìm");
	}

	@Override
	public void process(String key, int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processRaw(String key, final int status, final String json) {

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_report_search:

			fromDate = edtFromYear.getText().toString() + edtFromMonth.getText().toString()
					+ edtFromDay.getText().toString() + "000000";
			toDate = edtToYear.getText().toString() + edtToMonth.getText().toString() + edtToday.getText().toString()
					+ "000000";
			if (edtFromDay.getText().toString().equals("") || edtFromMonth.getText().toString().equals("")
					|| edtFromYear.getText().toString().equals("") || edtToday.getText().toString().equals("")
					|| edtToMonth.getText().toString().equals("") || edtToYear.getText().toString().equals("")) {
				showToast("Bạn phải nhập đầy đủ phần bắt buộc");
			}
			// bbdaaaaaaaaaaa
			// abd
			else if (fromDate.compareTo(toDate) > 0) {
				showToast("Bạn xem lại giá trị vừa nhập");
			} else {
				Util.checkSearchReport = true;
				String mfromday = edtFromDay.getText().toString();
				String mtoday = edtToday.getText().toString();
				String mfrommonth = edtFromMonth.getText().toString();
				String mtomonth = edtToMonth.getText().toString();

				if (edtFromDay.getText().toString().length() < 2) {
					mfromday = "0" + edtFromDay.getText().toString();
				}
				if (edtToday.getText().toString().length() < 2) {
					mtoday = "0" + edtToday.getText().toString();
				}
				if (edtFromMonth.getText().toString().length() < 2) {
					mfrommonth = "0" + edtFromMonth.getText().toString();
				}
				if (edtToMonth.getText().toString().length() < 2) {
					mtomonth = "0" + edtToMonth.getText().toString();
				}

				getFromDate = edtFromYear.getText().toString() + mfrommonth + mfromday;
				getToDate = edtToYear.getText().toString() + mtomonth + mtoday;
				dataBundle.bundleDataDate(getFromDate, getToDate, edtReportSearch.getText().toString());
				dismiss();

			}

			break;
		case R.id.imv_select_from_time:

			DatePickerDialog dpFromTime = new DatePickerDialog(getActivity(), dateListenerFromTime, mYear, mMonth,
					mDay);
			dpFromTime.show();
			break;
		case R.id.imv_select_to_time:

			DatePickerDialog dpToTime = new DatePickerDialog(getActivity(), dateListenerToTime, mYear, mMonth, mDay);
			dpToTime.show();
			break;
		default:
			break;
		}
	}

	private void showToast(String msg) {

		Toast toast = Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.TOP | Gravity.CENTER, 10, 10);
		toast.show();
	}

	private DatePickerDialog.OnDateSetListener dateListenerFromTime = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int yr, int monthOfYear, int dayOfMonth) {
			mYear = yr;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			int month = mMonth + 1;
			edtFromDay.setText(new StringBuilder().append(mDay));
			edtFromMonth.setText(new StringBuilder().append(mMonth + 1));
			if (new StringBuilder().append(mDay).length() < 2) {
				edtFromDay.setText(new StringBuilder().append("0" + mDay));
			}
			if (new StringBuilder().append(mMonth + 1).length() < 2) {
				edtFromMonth.setText(new StringBuilder().append("0" + month));
			}
			edtFromYear.setText(new StringBuilder().append(mYear));
		}
	};
	private DatePickerDialog.OnDateSetListener dateListenerToTime = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int yr, int monthOfYear, int dayOfMonth) {
			mYear = yr;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			int month = mMonth + 1;
			edtToday.setText(new StringBuilder().append(mDay));
			edtToMonth.setText(new StringBuilder().append(mMonth + 1));
			if (new StringBuilder().append(mDay).length() < 2) {
				edtToday.setText(new StringBuilder().append("0" + mDay));
			}
			if (new StringBuilder().append(mMonth + 1).length() < 2) {
				edtToMonth.setText(new StringBuilder().append("0" + month));
			}
			edtToYear.setText(new StringBuilder().append(mYear));
		}
	};

}