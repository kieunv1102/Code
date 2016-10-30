package vn.ce.sale.fragment.bee;

//import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.adapter.CustomGrid;
import vn.ce.sale.adapter.CustomGridAndFilter;
import vn.ce.sale.adapter.CustomList;
import vn.ce.sale.adapter.DisplayOrderProductGrid;
import vn.ce.sale.adapter.bee.BeeAdapterReportOrder;
import vn.ce.sale.adapter.bee.DisplayOrderEcomProductGrid;
import vn.ce.sale.adapter.bee.DisplayProductGrid;
import vn.ce.sale.data.DBManager;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.ICallBack;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.DownloadImageTask;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ImageLoadingHolder;
import vn.ce.sale.ui.TypeUI;
import vn.ce.sale.ui.ZopostActivity;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.ui.ZopostValue;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.HashData;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.R.id;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BeeFragmentPayment extends ZopostFragment implements OnClickListener {
	EditText edtFromDay, edtFromMonth, edtFromYear;
	EditText edtToDay, edtToMonth, edtToYear;
	ImageView imvFromTime, imvToTime;
	Spinner spnListStatus;
	Button btnSearch;
	ListView lvListOrder;
	BeeAdapterReportOrder adapter;
	private ProgressDialog pd;
	private int mYear, mMonth, mDay;
	String arrStatus[] = { "Điểm Bán Vi21", "NPP Cấp 1", "NPP Cấp 2"};
	List<JSONObject> lstJsonObjects = new ArrayList<JSONObject>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActivity().setTitle("Nộp Tiền");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// setup ui
		rootView = inflater.inflate(R.layout.bee_fragment_payment, container, false);
//		edtFromDay = (EditText) rootView.findViewById(R.id.edtFromDay);
//		edtFromMonth = (EditText) rootView.findViewById(R.id.edtFromMonth);
//		edtFromYear = (EditText) rootView.findViewById(R.id.edtFromYear);
//		edtToDay = (EditText) rootView.findViewById(R.id.edtToDay);
//		edtToMonth = (EditText) rootView.findViewById(R.id.edtToMonth);
//		edtToYear = (EditText) rootView.findViewById(R.id.edtToYear);
//		imvFromTime = (ImageView) rootView.findViewById(R.id.imvFromTime);
//		imvToTime = (ImageView) rootView.findViewById(R.id.imvToTimeaaa);
//		spnListStatus = (Spinner) rootView.findViewById(R.id.spnStatus);
//		btnSearch = (Button) rootView.findViewById(R.id.btnSearchOrder);
//		lvListOrder = (ListView) rootView.findViewById(R.id.lvListOrder);
//
//		final Calendar cal = Calendar.getInstance();
//		mDay = cal.get(Calendar.DAY_OF_MONTH);
//		mMonth = cal.get(Calendar.MONTH);
//		mYear = cal.get(Calendar.YEAR);
//
//		imvFromTime.setOnClickListener(this);
//		imvToTime.setOnClickListener(this);
//		btnSearch.setOnClickListener(this);
//
//		ArrayAdapter<String> adapterSpn = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
//				arrStatus);
//		adapterSpn.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
//		spnListStatus.setAdapter(adapterSpn);
//		spnListStatus.setOnItemSelectedListener(new StatusProcessEvent());
//		pd = new ProgressDialog(getActivity());
//		loadData();
		return rootView;
	}

	private void loadData() {
		// TODO Auto-generated method stub
		pd.setMessage("Đang tải dữ liệu!");
		pd.show();
		ServiceManager.factoryData().getDataRaw(
				Util.SERVER_URL + "system=bee&data={\"ActionType\":\"REPORT_ORDER\"" + ",\"UserName\":\"" + "tienbvv21"
						+ "\",\"Password\":\"" + ShareMemManager.getInstance().readFromDB(getContext(), "password")
						+ "\",\"FromDate\":\"" + "20150115000000" + "\",\"ToDate\":\"" + "20161216235959" + "\"}",
				"", DataOrder.NETWORK_THEN_CACHE, new ICallBackUI() {

					@Override
					public void processRaw(String key, final int status, final String json) {
						// TODO Auto-generated method stub
						runOnUiThreadX(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (status == 1001) {
									pd.dismiss();
									return;
								}
								if (status == 200) {
									pd.dismiss();

									JSONObject oJson;

									try {
										oJson = new JSONObject(json);
										String arr = oJson.getString("ResponseMessage");
										lstJsonObjects = TransformDataManager
												.convertArrayToListJSON(new JSONArray(arr));
										ShareMemManager.getInstance().saveToDB(getActivity(), "dataOrderProduct", lstJsonObjects.toString());
									} catch (JSONException e1) {
										// TODO 11223-generated catch block
										e1.printStackTrace();
									}
									adapter = new BeeAdapterReportOrder(getActivity(), lstJsonObjects,4);
									
									lvListOrder.setAdapter(adapter);
									lvListOrder.setOnItemClickListener(new OnItemClickListener() {
										@SuppressWarnings("static-access")
										@Override
										public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
											// TODO Auto-generated method stub
											try {
												FragmentTransaction fmdetail = getActivity().getSupportFragmentManager().beginTransaction();
												fmdetail.replace(R.id.container,new Bee_Fragment_Order_Product_Details().newInstance(lstJsonObjects.get(arg2).getString("OrderCode")));
												fmdetail.addToBackStack("tag");
												fmdetail.commit();
												Toast.makeText(getActivity(), String.valueOf(arg2), Toast.LENGTH_SHORT).show();
											} catch (JSONException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}
										}
									});
									adapter.notifyDataSetChanged();

								}
							}

						});
					}

					@Override
					public void process(String key, int status, List<JSONObject> lst) {
						// TODO Auto-generated method stub

					}
				});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imvFromTime:
			DatePickerDialog dpFromTime = new DatePickerDialog(getActivity(), dateListenerFromTime, mYear, mMonth,
					mDay);
			dpFromTime.show();
			break;
		case R.id.imvToTimeaaa:
			DatePickerDialog dpToTime = new DatePickerDialog(getActivity(), dateListenerToTime, mYear, mMonth, mDay);
			dpToTime.show();
			break;
		case R.id.btnSearchOrder:

			break;

		default:
			break;
		}
	}

	private class StatusProcessEvent implements android.widget.AdapterView.OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			// txtDisplayNhaMang.setText(arrNhaMang[0]);
		}

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
			edtToDay.setText(new StringBuilder().append(mDay));
			edtToMonth.setText(new StringBuilder().append(mMonth + 1));
			if (new StringBuilder().append(mDay).length() < 2) {
				edtToDay.setText(new StringBuilder().append("0" + mDay));
			}
			if (new StringBuilder().append(mMonth + 1).length() < 2) {
				edtToMonth.setText(new StringBuilder().append("0" + month));
			}
			edtToYear.setText(new StringBuilder().append(mYear));
		}
	};

	@Override
	protected void setupUI() {
		super.setupUI();
		setHasOptionsMenu(true);
	}

	@Override
	protected void initParamsForFragment() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void startLoadData() {

	}

	@Override
	protected void fillDataSource(int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

}