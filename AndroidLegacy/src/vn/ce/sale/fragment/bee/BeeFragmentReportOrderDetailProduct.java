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
import vn.ce.sale.adapter.bee.BeeAdapterReportOrderDetail;
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
import vn.ce.sale.ui.ICallBackActivityToFragment;
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

public class BeeFragmentReportOrderDetailProduct extends ZopostFragment {
	BeeAdapterReportOrderDetail adapter;
	List<JSONObject> lstJsonObjects,lstitem;
	TextView txtOrderCode,txtStatus;
	ListView lvDisplayProductOrder;
	String pos;
	View footer;
	TextView txtHelloUser;
	Button btnThanhtoan;
	public static BeeFragmentReportOrderDetailProduct newInstance(String p) {
		BeeFragmentReportOrderDetailProduct fragment = new BeeFragmentReportOrderDetailProduct();
		Bundle arg = new Bundle();
		arg.putString("PARAM1", p);
		fragment.setArguments(arg);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// getActivity().setTitle("Danh Sách Đơn Hàng");
		if (getArguments()!=null) {
			pos = getArguments().getString("PARAM1");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// setup ui
		rootView = inflater.inflate(R.layout.bee_fragment_report_order_detail_product, container, false);
		footer = inflater.inflate(R.layout.header, null);
		txtHelloUser = (TextView) footer.findViewById(R.id.txtHelloUser);
		btnThanhtoan = (Button) rootView.findViewById(R.id.btnThanhtoan);
		btnThanhtoan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				long totalMoney = sumAmount();
				Util.checkThanhtoan =true;
				ShareMemManager.getInstance().saveToDB(getActivity(), "TienThanhToan", String.valueOf((int)totalMoney));
				Toast.makeText(getActivity(), "Đa thanh toán cho đơn hàng", Toast.LENGTH_SHORT).show();
			}
		});
		ShareMemManager.getInstance().saveToDB(getActivity(), "posSpinner", pos);
		loadData();
		txtOrderCode = (TextView)rootView.findViewById(R.id.txtOrderCode);
		txtStatus = (TextView)rootView.findViewById(R.id.txtStatus);
		try {
			txtOrderCode.setText("Mã đơn hàng: "+lstJsonObjects.get(Integer.parseInt(pos)).getString("MaDH"));
			txtStatus.setText("Trạng thái: "+lstJsonObjects.get(Integer.parseInt(pos)).getString("TrangThai"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lvDisplayProductOrder =(ListView)rootView.findViewById(R.id.lvDisplayProductOrder);
		adapter = new BeeAdapterReportOrderDetail(getActivity(), lstitem);
		lvDisplayProductOrder.setAdapter(adapter);
		lvDisplayProductOrder.addFooterView(footer);
		totalMoney();
		adapter.notifyDataSetChanged();
		
		return rootView;
	}

	private void totalMoney() {
		// TODO Auto-generated method stub
		long totalMoney = sumAmount();
		txtHelloUser.setText("Tổng tiền: " + String.valueOf(FormatUtil.formatCurrency((double) totalMoney)));
	}

	private Long sumAmount() {

		Long totalMoneyNew = (long) 0;
		for (int i = 0; i < lstitem.size(); i++) {
			try {
				int tt = lstitem.get(i).getInt("TT");
				totalMoneyNew = totalMoneyNew + tt;

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return totalMoneyNew;
	}
	@SuppressWarnings("unused")
	private void loadData() {
		try {
			String arr = ShareMemManager.getInstance().readFromDB(getActivity(), "orderdatalocal");
			lstJsonObjects = TransformDataManager.convertArrayToListJSON(new JSONArray(arr));
			String item = lstJsonObjects.get(Integer.parseInt(pos)).getString("Item");
			lstitem = TransformDataManager
					.convertArrayToListJSON(new JSONArray(item));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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