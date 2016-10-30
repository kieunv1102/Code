package vn.ce.sale.fragment.vi21;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.HomeActivity1;
import vn.ce.sale.SettingsActivity;
import vn.ce.sale.adapter.ProductGridPurchase;
import vn.ce.sale.data.DBManager;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.IData;
import vn.ce.sale.data.IDataCheck;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.fragment.Fragment_Home_List;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.HashData;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentOrderCreate extends ZopostFragment implements IData, IDataCheck {
	private ListView grid;
	String json;
	String barCode;
	List<JSONObject> listData;
	JSONObject oOrder;
	ProductGridPurchase adapter;
	View footerGrid;
	int page;
	LinearLayout bottomPanel;
	private ProgressDialog pd;
	// private View rootView ;
	// start
	private boolean isOnlyView = false;
	private ImageView imgView;
	private TextView txtContent;
	private EditText edtSoLuong;
	private boolean modeEdit = false;
	private int tien;
	List<JSONObject> _dataSource = new ArrayList<JSONObject>();

	protected void initCreatedView() {
	}

	private final long DELAY = 300;
	View footer;
	View header;
	View rowListview;
	private Button btnSearch;
	private Button btnSave;
	private EditText edtOrderBarcode;
	private Timer timer = new Timer();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// setup ui
		rootView = inflater.inflate(R.layout.vi21_fragment_orderpurchase_product, container, false);
		// ShareMemManager.getInstance().saveToDB(getActivity(),
		// "aOrderProduct", "Vào đặt hàng");
		// String tSaleProduct = new Long((new Date().getTime())).toString();
		// ShareMemManager.getInstance().saveToDB(getActivity(),
		// "tOrderProductProduct", tSaleProduct);
		Util.saveActionUser(getActivity(), "VÀO-ĐẶT-HÀNG", (new Date()).getTime());

		pd = new ProgressDialog(getActivity());
		edtOrderBarcode = (EditText) rootView.findViewById(R.id.edtOrderBarcode);
		edtOrderBarcode.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				if (timer != null)
					timer.cancel();

			}

			@Override
			public void afterTextChanged(Editable s) {

				/*
				 * String zzz = s.toString().replace("\n", ""); // TODO
				 * Auto-generated method stub if (zzz.length() >= 13) {
				 * setupBarCode(zzz); // new BarCode().execute(s.toString()); }
				 */
				final String zzz = s.toString().replace("\n", "");
				if (zzz.length() >= 13) {
					timer = new Timer();

					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							setupBarCode(zzz);
						}

					}, DELAY);
				}
			}
		});
		bottomPanel = (LinearLayout) rootView.findViewById(R.id.bottomPanel);
		btnSearch = (Button) rootView.findViewById(R.id.btnSearchProduct);
		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				FragmentManager fm = getActivity().getSupportFragmentManager();
				Fragment_Order_Create_ListDlg editNameDialog = new Fragment_Order_Create_ListDlg();
				editNameDialog.setHandleData(FragmentOrderCreate.this);
				editNameDialog.show(fm, "fragment_edit_name");
			}
		});
		grid = (ListView) rootView.findViewById(R.id.grid);
		header = inflater.inflate(R.layout.header_order, null);
		grid.addHeaderView(header);

		footer = inflater.inflate(R.layout.footer_order, null);
		grid.addFooterView(footer);// cong tong tien set cho no

		btnSave = (Button) rootView.findViewById(R.id.btnSaveProduct);
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (_dataSource.size() == 0) {
					Toast.makeText(getActivity(), "Bạn phải chọn sản phẩm trước khi xác nhận", Toast.LENGTH_SHORT)
							.show();
				} else {
					AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
					b.setMessage("Bạn chắc chắn muốn xác nhận đơn đặt hàng?");
					b.setCancelable(false);
					b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							saveToServer();
						}
					});
					b.setNegativeButton("Không", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
					b.create().show();
				}
			}
		});
		Util.saveActionUser(getActivity(), "ACCESS-DH-ORDER", (new Date()).getTime());
		Log.e("long", String.valueOf(Long.MAX_VALUE));
		Log.e("flo", String.valueOf(Float.MAX_VALUE));
		setupUI();

		return rootView;// return super.onCreateView(inflater, container,
						// savedInstanceState);
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

	public void setupBarCode(String barcode) {
		barcode = barcode.replace("\n", "");
		// edtAddress.setText("barcode");
		// tìm trong database
		JSONObject o = findBarCodeInDatabase(barcode);
		if (o == null) {
			clearBarCodeContent();
			runOnUiThreadX(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast toast = Toast.makeText(getContext(), "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.TOP | Gravity.CENTER, 10, 10);
					toast.show();
				}
			});
			return;
		}
		// try {
		// setupSLFromGrid();
		//
		// boolean isFound = false;
		// // nếu tìm ra barcode trong đống sản phẩm đã có trên grid
		// for (int jx = 0; jx <= _dataSource.size() - 1; jx++) {
		// // nếu tìm ra thì tăng số lượng lên 1- chỉnh số text
		// if
		// (_dataSource.get(jx).getString("BarCode").equalsIgnoreCase(barcode))
		// {
		// _dataSource.get(jx).put("SL", _dataSource.get(jx).getInt("SL") + 1);
		// _dataSource.get(jx).put("TT",
		// _dataSource.get(jx).getInt("SL") *
		// _dataSource.get(jx).getDouble("Price"));
		//
		// isFound = true;
		// break;
		// }
		// }
		// if (!isFound) {
		// o.put("SL", "1");
		// o.put("KM", "2");
		// o.put("TT", o.getInt("SL") * o.getDouble("Price"));
		// _dataSource.add(o); // nếu ko tìm ra thì add vào
		// }
		//
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		addToProduct(o);
		// bind lại trong grid
		// bindDataToGrid();
		clearBarCodeContent();
	}

	private void clearBarCodeContent() {
		runOnUiThreadX(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				edtOrderBarcode.setText("");
			}
		});
	}

	private void addToProduct(JSONObject o) {
		// TODO Auto-generated method stub
		// thực setup barcode vào grid
		try {
			setupSLFromGrid();

			boolean isFound = false;
			// nếu tìm ra barcode trong đống sản phẩm đã có trên grid có sản
			// phẩm khuyến mại hay ko?
			for (int jx = 0; jx <= _dataSource.size() - 1; jx++) {
				// nếu tìm ra thì tăng số lượng lên 1- chỉnh số text
				if (_dataSource.get(jx).getString("ProductID").equalsIgnoreCase(o.getString("ProductID"))) {
					// nếu là khuyến mại thì mark là thêm mới để tách ra
					if (_dataSource.get(jx).has("KM") && _dataSource.get(jx).getString("KM").equalsIgnoreCase("1")) {
						isFound = false;
						break;
					}
					_dataSource.get(jx).put("SL", _dataSource.get(jx).getInt("SL") + 1);
					_dataSource.get(jx).put("TT",
							_dataSource.get(jx).getInt("SL") * _dataSource.get(jx).getDouble("Price"));

					isFound = true;
					break;
				}
			}
			// thêm rồi thì ko thêm nữa....
			if (!isFound) {
				// nếu tìm ra barcode trong đống sản phẩm đã có sản phẩm bán hay
				// ko?
				for (int jx = 0; jx <= _dataSource.size() - 1; jx++) {
					// nếu tìm ra thì tăng số lượng lên 1- chỉnh số text
					if (_dataSource.get(jx).getString("ProductID").equalsIgnoreCase(o.getString("ProductID"))) {
						// nếu là có sản phẩm ko phải khuyến mại thì update SL
						// cho nó
						if (!_dataSource.get(jx).has("KM")
								|| !_dataSource.get(jx).getString("KM").equalsIgnoreCase("1")) {
							_dataSource.get(jx).put("SL", _dataSource.get(jx).getInt("SL") + 1);
							_dataSource.get(jx).put("TT",
									_dataSource.get(jx).getInt("SL") * _dataSource.get(jx).getDouble("Price"));

							// đánh dấu là thêm rồi để ko thêm ở cuối
							isFound = true;
							break;
						}
					}
				}
			}
			if (!isFound) {
				o.put("SL", "1");
				o.put("KM", "2");
				o.put("TT", o.getInt("SL") * o.getDouble("Price"));
				_dataSource.add(o); // nếu ko tìm ra thì add vào
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// bind lại trong grid
		runOnUiThreadX(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				bindDataToGrid();
			}
		});
	}

	public static List<JSONObject> lstAllProduct = new ArrayList<JSONObject>();

	public JSONObject findBarCodeInDatabase(String barcode) {
		if (lstAllProduct.size() == 0) {
			String allProduct = ShareMemManager.getInstance().readFromDB(getContext(), "product_store");
			lstAllProduct = TransformDataManager.convertStringToListJSON(allProduct);
		}
		for (JSONObject o : lstAllProduct) {
			try {
				// nếu tìm ra barcode trong đống sản phẩm
				if (o.getString("BarCode").equalsIgnoreCase(barcode)) {
					return o;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	void bindDataToGrid() {
		updateDataSource();
		if (adapter == null) {
			// TODO Auto-generated method stu
			adapter = new ProductGridPurchase(getActivity(), _dataSource);
			// TicketNumber_TradeType_Currency_TimeFrame

			grid.setAdapter(adapter);
			adapter.setFooter(footer);
			adapter.setHeader(header);
			// adapter.notifyDataSetChanged();
			caculateTotalOfAmount();
		} else {
			// _dataSource.add(new JSONObject());
			adapter.setDataSource(_dataSource);
			// adapter.notifyDataSetChanged();
			caculateTotalOfAmount();
		}
	}

	// txtDetail=((TextView)rootView.findViewById(R.id.detail_title));
	// imgView=((ImageView)rootView.findViewById(R.id.detail_image));
	// txtContent=((TextView)rootView.findViewById(R.id.detail_content));
	public void updateDataSource() {
		for (int jx = 0; jx <= _dataSource.size() - 1; jx++) {
			try {
				if (_dataSource.get(jx).getString("KM").equalsIgnoreCase("1")) {
					_dataSource.get(jx).put("TT", "0");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private EditText txt1;
	private TextView txt2;
	private TextView txtTien;
	private TextView stt;
	private int gia;
	private long timestamp;

	private void caculateTotalOfAmount() {
		Long total = sumAmount();
		TextView txtTotal = (TextView) footer.findViewById(R.id.txtTotalMoney);
		txtTotal.setText(
				String.valueOf(FormatUtil.formatCurrency((double) total)) + "\n" + FormatUtil.numberToString(total));
		adapter.setDataSource(_dataSource);
		adapter.notifyDataSetChanged();
		if (_dataSource.size() > 0) {
			footer.setVisibility(View.VISIBLE);
			header.setVisibility(View.VISIBLE);
		} else {

			footer.setVisibility(View.GONE);
			header.setVisibility(View.GONE);
		}
	}

	private Long sumAmount() {

		Long total = (long) 0;
		for (int jx = 0; jx <= _dataSource.size() - 1; jx++) {
			try {
				_dataSource.get(jx).put("STT", String.valueOf(jx + 1));
				if (_dataSource.get(jx).getString("KM").equals("1")) {
					_dataSource.get(jx).put("TT", 0);
				} else {
					Long tt = (long) (_dataSource.get(jx).getDouble("Price") * _dataSource.get(jx).getInt("SL"));
					_dataSource.get(jx).put("TT", tt);
					total = (Long) (total + tt);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return total;
	}

	private void setupSLFromGrid() {
		if (_dataSource == null)
			return;
		if (_dataSource.size() == 0)
			return;
		if (adapter == null)
			return;
		for (int jx = 0; jx <= _dataSource.size() - 1; jx++) {
			View row = adapter.getView(jx, null, null);
			EditText txt1 = (EditText) row.findViewById(R.id.edtSoLuong);

			try {
				_dataSource.get(jx).put("SL", Integer.parseInt(txt1.toString()));
				_dataSource.get(jx).put("TT",
						Integer.parseInt(txt1.toString()) * _dataSource.get(jx).getDouble("Price"));

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void saveToServer() {
		// data={\"ActionType\":\"SALE\",\"UserName\":\"khotongbtcom\",\"Password\":\"1234567\",\"OrderItems\":[{quantity:1,productid:2,DetailType:0},}]}
		Util.saveActionUser(getActivity(), "DH-ORDER-SAVE-ON", (new Date()).getTime());
		String sJsonObject = "";

		for (int jx = 0; jx <= adapter.getDataSource().size() - 1; jx++) {
			sJsonObject += "{";
			try {
				String valueKM = adapter.getDataSource().get(jx).getString("KM");
				if (valueKM.equalsIgnoreCase("1"))
					valueKM = "2";
				else
					valueKM = "1";

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
		String params = "{\"ActionType\":\"ORDER\",\"UserName\":\""
				+ ShareMemManager.getInstance().readFromDB(getContext(), "username") + "\",\"Password\":\""
				+ ShareMemManager.getInstance().readFromDB(getContext(), "password") + "\",\"OrderItems\":["
				+ sJsonObject + "]}";

		final ProgressDialog pd = new ProgressDialog(getContext());
		pd.setMessage("Đang gửi dữ liệu tạo đơn đặt hàng!");
		pd.show();

		ServiceManager.factoryData().postDataRaw(Util.SERVER_URL, HashData.createNew().putString("data", params).data(),
				new ICallBackUI() {

					@Override
					public void processRaw(String key, final int status, final String json) {
						runOnUiThreadX(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub

								// TODO Auto-generated method stub
								// phân tích để tiếp tục
								if (status == 200) {
									try {
										JSONObject o = new JSONObject(json);
										((TextView) rootView.findViewById(R.id.txtDetail1))
												.setText(o.getString("ResponseMessage"));
										JSONObject object = buildOrderFromUI();
										object.put("server", json);
										if (o.getString("ResponseCode").equalsIgnoreCase("00")) {
											showMessage(o.getString("ResponseMessage"));
											object.put("status", 2);
										}
										if (o.getString("ResponseCode").equalsIgnoreCase("01")) {
											// showMessageProgress(o.getString("ResponseMessage"));
											showMessageProgress("Đặt hàng thành công");
											bottomPanel.setVisibility(View.GONE);
											object.put("status", 1);
											_dataSource = new ArrayList<JSONObject>();
										}
										if (modeEdit)
											DBManager.getInstance(getContext()).updateOrder(object);
										else
											DBManager.getInstance(getContext()).saveOrder(object);
									} catch (Exception ex) {
										showMessage(ex.toString());
									}

								}
								if (status == -Util.ERROR_NETWORK)
									showMessage("Có lỗi do mạng, xin vui lòng thử lại....");
								pd.dismiss();
							}
						});
					}

					@Override
					public void process(String key, int status, List<JSONObject> lst) {
						// TODO Auto-generated method stub

					}
				});

	}

	private void saveServerOffline() {
		Util.saveActionUser(getActivity(), "DH-ORDER-SAVE-OFF", (new Date()).getTime());
		// data={\"ActionType\":\"SALE\",\"UserName\":\"khotongbtcom\",\"Password\":\"1234567\",\"OrderItems\":[{quantity:1,productid:2,DetailType:0},}]}
		try {
			JSONObject object = buildOrderFromUI();
			object.put("status", 0);
			if (!modeEdit)
				DBManager.getInstance(getContext()).saveOrder(object);
			else
				DBManager.getInstance(getContext()).updateOrder(object);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hideButton();
		showMessageProgress("Lưu thành công");
		_dataSource = new ArrayList<JSONObject>();
	}

	private void hideButton() {
		((TextView) rootView.findViewById(R.id.btnSaveProduct)).setVisibility(View.GONE);
		((TextView) rootView.findViewById(R.id.btnSearchProduct)).setVisibility(View.GONE);
	}

	private void showButton() {
		((TextView) rootView.findViewById(R.id.btnSaveProduct)).setVisibility(View.VISIBLE);
		((TextView) rootView.findViewById(R.id.btnSearchProduct)).setVisibility(View.VISIBLE);
	}

	private void showMessage(String msg) {
		((LinearLayout) rootView.findViewById(R.id.progressContent)).setVisibility(View.GONE);
		((TextView) rootView.findViewById(R.id.txtDetail1)).setText(msg);
	}

	private void showMessageProgress(String msg) {
		((LinearLayout) rootView.findViewById(R.id.mainContent)).setVisibility(View.GONE);

		((LinearLayout) rootView.findViewById(R.id.progressContent)).setVisibility(View.VISIBLE);
		((TextView) rootView.findViewById(R.id.txtProgress)).setText(msg);
	}

	private JSONObject buildOrderFromUI() throws JSONException {
		JSONObject oOrder = new JSONObject();

		JSONArray jsonArray = new JSONArray();

		for (int jx = 0; jx <= adapter.getDataSource().size() - 1; jx++) {
			jsonArray.put(adapter.getDataSource().get(jx));
		}
		oOrder.put("OrderItems", jsonArray);
		if (!modeEdit)
			oOrder.put("timestamp", (new Date()).getTime());
		else {
			oOrder.put("timestamp", timestamp);
		}
		return oOrder;
	}

	protected void fillDataSource(int status, final List<JSONObject> lst) {

	}

	@Override
	protected void initParamsForFragment() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void startLoadData() {
		pd.setMessage("Đang tải dữ liệu!");
		pd.show();
		modeEdit = false;
		// load product
		if (params.getString("data-order") != null) {
			modeEdit = params.getString("mode-edit") != null;

			// setup for ui

			try {
				oOrder = new JSONObject(params.getString("data-order"));

				JSONArray jsonArray = oOrder.getJSONArray("OrderItems");

				timestamp = oOrder.getLong("timestamp");
				_dataSource = TransformDataManager.convertArrayToListJSON(jsonArray);
				bindDataToGrid();

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		startLoadProductCreate(DataOrder.ONLY_NETWORK);
	}

	private void startLoadProductCreate(DataOrder typeLoad) {
		ServiceManager.factoryData()
				.getDataRaw(Util.SERVER_URL + "data={\"ActionType\":\"PRODUCT\",\"UserName\":\""
						+ ShareMemManager.getInstance().readFromDB(getActivity(), "username") + "\",\"Password\":\""
						+ ShareMemManager.getInstance().readFromDB(getActivity(), "password") + "\"}", "", typeLoad,
				new ICallBackUI() {

					@Override
					public void processRaw(String key, int status, final String json) {

						if (status == Util.ERROR_NETWORK) {
							pd.dismiss();
							showDialogConfirm();
						}
						if (status == 200) {
							pd.dismiss();
							try {
								// dummy data

								// json.replaceAll("8938507589693",
								// "8936036829112");
								// // json =
								// json.replaceAll("8938507589167",
								// "8936014823774");
								//
								JSONObject o = new JSONObject(json);
								Log.v("LCP", json);
								// TODO Auto-generated method stub
								ShareMemManager.getInstance().saveToDB(getActivity(), "product_store",
										o.getString("ResponseMessage"));
							} catch (Exception ex) {
							}

						}
					}

					@Override
					public void process(String key, int status, List<JSONObject> lst) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void showDialogConfirm() {
		runOnUiThreadX(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
				AlertDialog.Builder b = new AlertDialog.Builder(getActivity());

				b.setMessage("Bạn phải kết nối mạng để sử dụng chức năng này!");
				b.setCancelable(false);
				b.setNegativeButton("Thử lại", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which)

					{

						startLoadProductCreate(DataOrder.ONLY_NETWORK);
					}

				});
				b.setPositiveButton("Quay lại trang chủ", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						nextToFragmentAndKeepState(new Fragment_Home_List(), null, true);

					}
				});
				b.create().show();

			}
		});
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.menu_order_create, menu);
		setupMenuItem(menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.saveOrder: {
			if (_dataSource.size() == 0) {
				Toast.makeText(getActivity(), "Bạn phải chọn sản phẩm trước khi xác nhận", Toast.LENGTH_SHORT).show();
			} else {
				AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
				b.setMessage("Bạn chắc chắn muốn xác nhận đơn đặt hàng?");
				b.setCancelable(false);
				b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						saveToServer();

					}
				});
				b.setNegativeButton("Không", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				b.create().show();
			}
			return true;
		}
		case R.id.dsOrder:
			if (_dataSource.size() > 0 && !isOnlyView) {
				// AlertDialog.Builder b = new
				// AlertDialog.Builder(getContext());
				//
				// b.setMessage("Bạn có muốn lưu đơn hàng này?");
				// b.setCancelable(false);
				// b.setPositiveButton("Có", new
				// DialogInterface.OnClickListener() {
				// @Override
				// public void onClick(DialogInterface dialog, int which) {
				// saveServerOffline();
				// _dataSource.clear();
				// _dataSource = new ArrayList<JSONObject>();
				// }
				// });
				// b.setNegativeButton("Không", new
				// DialogInterface.OnClickListener() {
				//
				// @Override
				//
				// public void onClick(DialogInterface dialog, int which)
				//
				// {
				// _dataSource = new ArrayList<JSONObject>();
				// nextToFragmentAndKeepState(new
				// Fragment_Order_Create_ListTabViewer(), null, true);
				//
				// }
				//
				// });
				//
				// b.create().show();
				nextToFragmentAndKeepState(new Fragment_Order_Create_ListTabViewer(), null, true);
			} else {
				isOnlyView = false;
				nextToFragmentAndKeepState(new Fragment_Order_Create_ListTabViewer(), null, true);
			}

			return true;
		case R.id.newOrder: {
			if (_dataSource.size() > 0) {
				// AlertDialog.Builder b = new
				// AlertDialog.Builder(getContext());
				//
				// b.setMessage("Bạn có muốn lưu số sản phẩm đã chọn?");
				// b.setCancelable(false);
				// b.setPositiveButton("Có", new
				// DialogInterface.OnClickListener() {
				// @Override
				// public void onClick(DialogInterface dialog, int which) {
				// saveServerOffline();
				// _dataSource = new ArrayList<JSONObject>();
				//
				// }
				// });
				// b.setNegativeButton("Không", new
				// DialogInterface.OnClickListener() {
				//
				// @Override
				//
				// public void onClick(DialogInterface dialog, int which) {
				// newOrder();
				// _dataSource = new ArrayList<JSONObject>();
				//
				// }
				// });
				//
				// b.create().show();
				newOrder();
			} else
				newOrder();
			return true;
		}

		default: {
			return super.onOptionsItemSelected(item);
		}
		}

	}
	/**/

	private void newOrder() {
		// TODO Auto-generated method stub
		sendDataToActivity(BundleData.createNew().putString("screen", Util.SCREEN_ORDER_CREATE).data());
	}

	public void setupMenuItem(Menu menu) {
		menu.findItem(R.id.saveOrder).setVisible(false);
		if (!modeEdit) {
			// menu.findItem(R.id.saveOrder).setVisible(true);
			menu.findItem(R.id.newOrder).setVisible(true);
		} else {
			try {
				if (oOrder.getInt("status") == Util.ORDER_OK || oOrder.getInt("status") == Util.ORDER_FAILED) {
					// menu.findItem(R.id.saveOrder).setVisible(false);
					menu.findItem(R.id.newOrder).setVisible(true);
				}
				if (oOrder.getInt("status") == Util.ORDER_OFFLINE) {
					// menu.findItem(R.id.saveOrder).setVisible(true);
					menu.findItem(R.id.newOrder).setVisible(true);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		int x = 0;
		x++;
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		int x = 0;
		x++;
		super.onPause();
	}

	@Override
	public void sendData(JSONObject o) {
		// TODO Auto-generated method stub
		addToProduct(o);
	}

	@Override
	public JSONObject getData(JSONObject o) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public boolean hasDataSource() {
		// TODO Auto-generated method stub
		return _dataSource.size() > 0;
	}

	@Override
	public void saveDataSource() {
		// TODO Auto-generated method stub
		saveServerOffline();
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

}