package vn.ce.sale.fragment.vi21;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.adapter.ChangeAdapter;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentChangeOrderProduct extends ZopostFragment implements IData, IDataCheck, OnClickListener {

	ListView grid;
	DisplayOrderProductGrid adapter;
	TextView txtTotalMoney, txtTotalMoneyNew;
	Button btnSaveProductNew, btnCancel;

	String orderCode;
	String timeNow = String.valueOf((new Date()).getTime());

	RelativeLayout rllAllDisplay;
	TextView txtProgress;

	protected void initCreatedView() {

	}

	View footer;
	View header;
	JSONArray arrayChange = new JSONArray();
	JSONObject obj = new JSONObject();
	List<JSONObject> lstJsonItemList = new ArrayList<JSONObject>();
	List<JSONObject> lstJsonReportSale = new ArrayList<JSONObject>();
	List<JSONObject> lstJsonChange = new ArrayList<JSONObject>();

	// List<JSONObject> _dataSource = new ArrayList<JSONObject>();
	public void onCreated(Bundle savedInstanceState) {

	}

	@SuppressLint("UseValueOf")
	@SuppressWarnings("unused")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// setup ui
		rootView = inflater.inflate(R.layout.vi21_fragment_change_order_product, container, false);
		grid = (ListView) rootView.findViewById(R.id.grid);
		header = inflater.inflate(R.layout.header_order, null);
		grid.addHeaderView(header);
		footer = inflater.inflate(R.layout.footer_change_order, null);
		grid.addFooterView(footer);

		rllAllDisplay = (RelativeLayout) rootView.findViewById(R.id.rllAllDisplay);
		txtProgress = (TextView) rootView.findViewById(R.id.txtProgress);

		btnSaveProductNew = (Button) rootView.findViewById(R.id.btnSaveProductNew);
		btnCancel = (Button) rootView.findViewById(R.id.btnCancel);
		btnSaveProductNew.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		
		displayAllDataRePortSale();
		setupSLFromGrid();
		totalMoney();
		return rootView;
	}

	@SuppressWarnings("deprecation")
	private void displayAllDataRePortSale() {
		// TODO Auto-generated method stub
		JSONObject oJson;

		int position = Integer.parseInt(ShareMemManager.getInstance().readFromDB(getActivity(), "positionReportSale"));
		try {
			oJson = new JSONObject(ShareMemManager.getInstance().readFromDB(getActivity(), "report_sale"));
			String arr = oJson.getString("ResponseMessage");
			lstJsonReportSale = TransformDataManager.convertArrayToListJSON(new JSONArray(arr));

			obj = lstJsonReportSale.get(position);
			String itemList = obj.getString("ItemList");
			orderCode = obj.getString("OrderCode");
			lstJsonItemList = TransformDataManager.convertArrayToListJSON(new JSONArray(itemList));
			for (int i = 0; i < lstJsonItemList.size(); i++) {
				lstJsonChange.add(lstJsonItemList.get(i));
			}
			
			ShareMemManager.getInstance().saveToDB(getContext(), "data1", lstJsonChange.toString());
			bindDataToDetailTextAndOther();
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		adapter = new DisplayOrderProductGrid(getActivity(), lstJsonChange);
		adapter.setFooter(footer);
		adapter.setHeader(header);
		grid.setAdapter(adapter);
	}

	private void setupSLFromGrid() {
		if (lstJsonChange == null)
			return;
		if (lstJsonChange.size() == 0)
			return;
		if (adapter == null)
			return;
		for (int jx = 0; jx <= lstJsonChange.size() - 1; jx++) {
			View row = adapter.getView(jx, null, null);
			EditText txt1 = (EditText) row.findViewById(R.id.edtSoLuongNew);

			try {
				lstJsonChange.get(jx).put("SL", Integer.parseInt(txt1.toString()));
				lstJsonChange.get(jx).put("TT",
						Integer.parseInt(txt1.toString()) * lstJsonChange.get(jx).getDouble("ProducValue"));

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void totalMoney() {
		// TODO Auto-generated method stub
		Long totalMoney = (long) 0;
		long totalMoneyNew = sumAmount();
		for (int i = 0; i < lstJsonChange.size(); i++) {
			try {
				Long money = lstJsonChange.get(i).getLong("TotalProducValue");
				totalMoney = totalMoney + money;
				txtTotalMoney = (TextView) footer.findViewById(R.id.txtTotalMoney);
				txtTotalMoney.setText(String.valueOf(FormatUtil.formatCurrency((double) totalMoney)));

				txtTotalMoneyNew = (TextView) footer.findViewById(R.id.txtTotalMoneyNew);

				txtTotalMoneyNew.setText(String.valueOf(FormatUtil.formatCurrency((double) totalMoneyNew)));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private Long sumAmount() {

		Long totalMoneyNew = (long) 0;
		for (int i = 0; i < lstJsonChange.size(); i++) {
			try {
				totalMoneyNew = totalMoneyNew + lstJsonChange.get(i).getLong("TT");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return totalMoneyNew;
	}

	private void bindDataToDetailTextAndOther() throws JSONException {

		((LinearLayout) rootView.findViewById(R.id.detailPanel)).setVisibility(View.VISIBLE);
		((TextView) rootView.findViewById(R.id.txtDateCreate)).setText("Thời gian:" + TimeUtil.dateToString(
				new Date(Util.extractDateFromServerOrder(obj.getString("CreatedDate"))), "dd/MM/yyyy HH:mm:ss"));

		((TextView) rootView.findViewById(R.id.txtDateCreate)).append("\nMã đơn hàng:" + orderCode);
		((TextView) rootView.findViewById(R.id.txtDateCreate)).append("\nTrạng thái: Đã cập nhật");

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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnSaveProductNew:
			AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
			b.setMessage("Bạn chắc chắn muốn thay đổi đơn bán hàng?");
			b.setCancelable(false);
			b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					addChangeOrderProduct();
				}
			});
			b.setNegativeButton("Không", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			b.create().show();

			break;
		case R.id.btnCancel:
			nextToFragmentAndKeepState(new Fragment_Order_ListTabViewer(), null, true);
			break;
		default:
			break;
		}

	}

	private void addChangeOrderProduct() {
		// TODO Auto-generated method stub
		String sJsonObject = "";

		for (int jx = 0; jx <= adapter.getDataSource().size() - 1; jx++) {
			sJsonObject += "{";
			try {
				String valueKM = adapter.getDataSource().get(jx).getString("KM");
				if (valueKM.equalsIgnoreCase("0"))
					valueKM = "2";
				else
					valueKM = "1";

				// if
				// (adapter.getDataSource().get(jx).getString("IsLot").equalsIgnoreCase("true"))
				// valueKM = "3";

				// sJsonObject += "\"orderCode\":" + orderCode;
				// sJsonObject += ",\"CreatedDate\":" + timeNow;
				// sJsonObject += ",\"TotalPrice\":" +
				// String.valueOf(sumAmount());
				sJsonObject += "\"Quantity\":" + adapter.getDataSource().get(jx).get("SL");
				sJsonObject += ",\"ProductID\":" + adapter.getDataSource().get(jx).get("ProductID");
				sJsonObject += ",\"DetailType\":" + valueKM;

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sJsonObject += "}";
			if (jx != adapter.getDataSource().size() - 1)
				sJsonObject = sJsonObject + ",";
		}
		String params = "data={\"ActionType\":\"SALE\",\"UserName\":\""
				+ ShareMemManager.getInstance().readFromDB(getContext(), "username") + "\",\"Password\":\""
				+ ShareMemManager.getInstance().readFromDB(getContext(), "password") + "\",\"orderCode\":\"" + orderCode
				+ "\",\"OrderItems\":[" + sJsonObject + "]}";
		final ProgressDialog pd = new ProgressDialog(getContext());
		pd.setMessage("Đang gửi dữ liệu xác nhận!");
		pd.show();
		ServiceManager.factoryData().getDataRaw(Util.SERVER_URL, params, new ICallBackUI() {

			@Override
			public void processRaw(String key, final int statusx, final String json) {
				runOnUiThreadX(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						int status = statusx;
						// TODO Auto-generated method stub
						if (status == Util.ERROR_NETWORK) {
							pd.dismiss();
						}
						// phân tích để tiếp tục
						if (status == 200) {
							pd.dismiss();
							try {
								JSONObject o = new JSONObject(json);

								if (o.getString("ResponseCode").equalsIgnoreCase("00")) {
									txtProgress.setVisibility(View.VISIBLE);
									txtProgress.setText(o.getString("ResponseMessage"));

								}
								if (o.getString("ResponseCode").equalsIgnoreCase("01")) {
									// startLoadProduct(DataOrder.ONLY_NETWORK);
									txtProgress.setVisibility(View.VISIBLE);
									txtProgress.setText("Thay đổi đơn hàng thành công!");
									rllAllDisplay.setVisibility(View.GONE);
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
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
	protected void startLoadData() {
		Util.checkChangeOrderProduct = true;
		
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