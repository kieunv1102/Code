package vn.ce.sale.fragment.vi21;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.HomeActivity1;
import vn.ce.sale.LoginActivity;
import vn.ce.sale.adapter.ProductGrid;
import vn.ce.sale.adapter.ProductGridChange;
import vn.ce.sale.adapter.ProductGridNoEdit;
import vn.ce.sale.data.DBManager;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.IData;
import vn.ce.sale.data.IDataCheck;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.HashData;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.TimeUtil;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.R.integer;
import android.annotation.SuppressLint;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentOrderProduct extends ZopostFragment implements IData, IDataCheck {
	private ListView grid;
	private EditText edtOrderBarcode;
	private ProgressDialog pd;
	String json;
	String barCode;
	List<JSONObject> listData;
	JSONObject oOrder;
	// CustomGridAndFilter adapter;
	ProductGrid adapter;
	View footerGrid;
	int page;
	// private View rootView ;
	// start
	private LinearLayout bottomPanel;
	private ImageView imgView;
	private TextView txtContent;
	private EditText edtSoLuong;
	private EditText edtName;
	private EditText edtPhone;
	private EditText edtAddress;
	private boolean modeEdit = false;
	private boolean isOnlyView = false;

	private int tien;
	List<JSONObject> _dataSource = new ArrayList<JSONObject>();

	protected void initCreatedView() {

	}

	View footer;
	View header;
	View rowListview;
	private Button btnSearch, btnSave;
	public static Date dStart;
	private Timer timer = new Timer();
	private final long DELAY = 300; // in ms

	public void onCreated(Bundle savedInstanceState) {

	}

	@SuppressLint("UseValueOf")
	@SuppressWarnings("unused")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// setup ui
		rootView = inflater.inflate(R.layout.vi21_fragment_order_product, container, false);
		// ShareMemManager.getInstance().saveToDB(getActivity(), "aSaleProduct",
		// "Vào bán hàng");
		// String tSaleProduct = new Long((new Date().getTime())).toString();
		// ShareMemManager.getInstance().saveToDB(getActivity(), "tSaleProduct",
		// tSaleProduct);
		bottomPanel = (LinearLayout) rootView.findViewById(R.id.bottomPanel);
		Util.saveActionUser(getActivity(), "VÀO-BÁN-HÀNG", (new Date()).getTime());

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

		edtPhone = (EditText) rootView.findViewById(R.id.edtphone);
		edtAddress = (EditText) rootView.findViewById(R.id.edtadress);
		edtName = (EditText) rootView.findViewById(R.id.edtname);

		btnSearch = (Button) rootView.findViewById(R.id.btnSearchProduct);
		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				FragmentManager fm = getActivity().getSupportFragmentManager();
				Fragment_Product_ListDlg editNameDialog = new Fragment_Product_ListDlg();

				editNameDialog.setHandleData(FragmentOrderProduct.this);
				editNameDialog.show(fm, "fragment_edit_name");

			}
		});
		btnSave = (Button) rootView.findViewById(R.id.btnSaveProduct);
		btnSave.clearFocus();
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (_dataSource.size() == 0) {
					Toast.makeText(getActivity(), "Bạn phải chọn sản phẩm trước khi lưu", Toast.LENGTH_SHORT).show();
				} else {
					AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
					b.setMessage("Bạn chắc chắn muốn xác nhận đơn bán hàng?");
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
		grid = (ListView) rootView.findViewById(R.id.grid);
		header = inflater.inflate(R.layout.header_order, null);
		grid.addHeaderView(header);
		removeDataTmp();
		footer = inflater.inflate(R.layout.footer_order, null);
		grid.addFooterView(footer);// cong tong tien set cho no

		setupUI();
		// Util.saveActionUser(getActivity(),"ACCESS-DH-SALE",(new
		// Date()).getTime());
		ShareMemManager.getInstance().saveToDB(getActivity(), "datasource", _dataSource.toString());
		// ShareMemManager.get
		// saveToServer();
		// start load data in other thread

		return rootView;
	}

	@Override
	protected void setupUI() {
		super.setupUI();
		setHasOptionsMenu(true);
	}

	public void updateDataSource() {
		for (int jx = 0; jx <= _dataSource.size() - 1; jx++) {
			try {

				// if(_dataSource.get(jx).getInt("isspecial")==1){
				// _dataSource.get(jx).put("ProductName", "L:
				// "+_dataSource.get(jx).getInt("Quantity")+"."+_dataSource.get(jx).getInt("ProductName"));
				// }
				if (_dataSource.get(jx).getString("KM").equalsIgnoreCase("1")) {
					_dataSource.get(jx).put("TT", "0");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
					Toast toast = Toast.makeText(getContext(), "Không tìm thấy sản phẩm hoặc sản phẩm hết hàng",
							Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.TOP | Gravity.CENTER, 10, 10);
					toast.show();
				}
			});

			return;
		}
		addToProduct(o);

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
			// nếu tìm ra barcode trong đống sản phẩm đã có trên grid
			for (int jx = 0; jx <= _dataSource.size() - 1; jx++) {
				// nếu tìm ra thì tăng số lượng lên 1- chỉnh số text

				if (_dataSource.get(jx).getString("ProductID").equalsIgnoreCase(o.getString("ProductID"))) {
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

	@SuppressWarnings("unused")
	JSONObject findBarCodeInDatabase(String barcode) {
		String allProduct = ShareMemManager.getInstance().readFromDB(getContext(), "product");
		JSONObject obj;
		String listPro = "";
		try {
			obj = new JSONObject(allProduct);
			listPro = obj.getString("ResponseMessage");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.toString();
		}
		List<JSONObject> lstAllProduct = TransformDataManager.convertStringToListJSON(listPro);
		boolean isOutOfStock = false;
		for (JSONObject o : lstAllProduct) {
			try {
				// nếu tìm ra barcode trong đống sản phẩm
				if (o.getString("BarCode").equalsIgnoreCase(barcode)) {
					if (o.getInt("Quantity") > 0 || o.getInt("Promotion") > 0) {
						return o;
					} else
						isOutOfStock = false;

				} else {
					// edtOrderBarcode.setText("");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// if(isOutOfStock) Toast.makeText(getActivity(), "Sản phẩm này đã hết
		// hàng!", Toast.LENGTH_SHORT).show();
		// else Toast.makeText(getActivity(), "Không có sản phẩm này:"+barcode,
		// Toast.LENGTH_SHORT).show();
		return null;
	}

	void bindDataToGrid() {
		updateDataSource();
		if (adapter == null) {

			adapter = new ProductGrid(getActivity(), _dataSource);
			try {
				if (oOrder != null && oOrder.getInt("status") != 0) {
					pd.dismiss();
					adapter = new ProductGrid(getActivity(), _dataSource);
					adapter.setLayoutRow(R.layout.layout_customer_list_row2_noedit);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			adapter.setFooter(footer);
			adapter.setHeader(header);

			grid.setAdapter(adapter);
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
		Util.saveActionUser(getActivity(), "DH-SALE-SAVE-ON", (new Date()).getTime());
		String sJsonObject = "";

		for (int jx = 0; jx <= adapter.getDataSource().size() - 1; jx++) {
			sJsonObject += "{";
			try {
				String valueKM = adapter.getDataSource().get(jx).getString("KM");
				if (valueKM.equalsIgnoreCase("1"))
					valueKM = "2";
				else
					valueKM = "1";

				if (adapter.getDataSource().get(jx).getString("IsLot").equalsIgnoreCase("true"))
					valueKM = "3";

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
		String params = "{\"ActionType\":\"SALE\",\"UserName\":\""
				+ ShareMemManager.getInstance().readFromDB(getContext(), "username") + "\",\"Password\":\""
				+ ShareMemManager.getInstance().readFromDB(getContext(), "password") + "\",\"OrderItems\":["
				+ sJsonObject + "]}";// data=
		Log.e("u", String.valueOf(sJsonObject.length()));
		final ProgressDialog pd = new ProgressDialog(getContext());
		pd.setMessage("Đang gửi dữ liệu xác nhận!");
		pd.show();
		String u = Util.SERVER_URL + params;

		ServiceManager.factoryData().postDataRaw(Util.SERVER_URL, HashData.createNew().putString("data", params).data(),
				new ICallBackUI() {

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
									showDialogConfirmToSale();
								}
								// phân tích để tiếp tục
								if (status == 200) {
									pd.dismiss();
									try {
										JSONObject o = new JSONObject(json);
										// ((TextView)
										// rootView.findViewById(R.id.txtDetail1))
										// .setText(o.getString("ResponseMessage"));
										// showMessageProgress(o.getString("ResponseMessage"));
										JSONObject object = buildOrderFromUI();
										object.put("server", json);

										if (o.getString("ResponseCode").equalsIgnoreCase("00")) {
											object.put("status", 3);
											showMessage(o.getString("ResponseMessage"));

										}
										if (o.getString("ResponseCode").equalsIgnoreCase("01")) {
											// startLoadProduct(DataOrder.ONLY_NETWORK);
											object.put("status", 1);
											header.setVisibility(View.GONE);
											footer.setVisibility(View.GONE);
											showMessageProgress("Đơn hàng thực hiện xác nhận thành công!");
											removeDataTmp();
											_dataSource.clear();
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
	public void onSaveInstanceState(Bundle outState) {
		Log.d("lamdaica", "onSaveInstanceState saving: ");
		outState.putString("data", _dataSource.toString());
		super.onSaveInstanceState(outState);
	}

	private void removeDataTmp() {
		// TODO Auto-generated method stub
		ShareMemManager.getInstance().deleteFromDB(getContext(), "ispause");
		ShareMemManager.getInstance().deleteFromDB(getContext(), "datatmp");
	}

	private void saveServerOffline() {

		Util.saveActionUser(getActivity(), "DH-SALE-SAVE-OFF", (new Date()).getTime());
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
		showMessageProgress("Lưu thành công");
		removeDataTmp();
		setVisibleForButtonToEdit(View.GONE);
		_dataSource = new ArrayList<JSONObject>();
	}

	private JSONObject buildOrderFromUI() throws JSONException {
		JSONObject oOrder = new JSONObject();
		oOrder.put("name", edtName.getText());
		oOrder.put("address", edtAddress.getText());
		oOrder.put("phone", edtPhone.getText());

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
		oOrder.put("tongtien", String.valueOf(sumAmount()));
		return oOrder;
	}

	private void setVisibleForButtonToEdit(int visible) {
		// TODO Auto-generated method stub
		((LinearLayout) rootView.findViewById(R.id.bottomPanel)).setVisibility(visible);
	}

	private void showMessage(String msg) {
		((LinearLayout) rootView.findViewById(R.id.progressContent)).setVisibility(View.GONE);
		((TextView) rootView.findViewById(R.id.txtDetail1)).setText(msg);
	}

	private void showMessageProgress(String msg) {
		setVisibleForButtonToEdit(View.GONE);
		header.setVisibility(View.GONE);
		footer.setVisibility(View.GONE);
		grid.setVisibility(View.GONE);
		((LinearLayout) rootView.findViewById(R.id.mainContent)).setVisibility(View.GONE);

		((LinearLayout) rootView.findViewById(R.id.progressContent)).setVisibility(View.VISIBLE);
		((TextView) rootView.findViewById(R.id.txtProgress)).setText(msg);
	}

	// put thoi gian

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

				bindDataToDetailTextAndOther();
				isOnlyView = (oOrder.getInt("status") == 1 || oOrder.getInt("status") == 2);

				edtAddress.setText(oOrder.getString("address"));
				edtName.setText(oOrder.getString("name"));
				edtPhone.setText(oOrder.getString("phone"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		startLoadProduct(DataOrder.ONLY_NETWORK);
		edtOrderBarcode.requestFocus();

	}

	private void startLoadProduct(final DataOrder typeLoad) {
		ServiceManager.factoryData()
				.getDataRaw(Util.SERVER_URL + "data={\"ActionType\":\"STORE\",\"UserName\":\""
						+ ShareMemManager.getInstance().readFromDB(getContext(), "username") + "\",\"Password\":\""
						+ ShareMemManager.getInstance().readFromDB(getContext(), "password") + "\"}", "", typeLoad,
				new ICallBackUI() {

					@Override
					public void processRaw(String key, int status, String json) {
						if (status == vn.ce.sale.util.Util.ERROR_NETWORK) {
							pd.dismiss();
							showDialogConfirm();
							return;

						}
						if (typeLoad != DataOrder.ONLY_CACHE)
							Util.checkOffline = false;
						if (status == 200) {
							pd.dismiss();
							try {
								// dummy data
//								 json = json.replaceAll("8935005801111",
//								 "8936014823767");
//								 json = json.replaceAll("8938507589693",
//								 "8935227312327");
//								 json = json.replaceAll("8938507589716",
//								 "8936036829112");
//								 json = json.replaceAll("8938507589952",
//								 "8938507589846");

								JSONObject o = new JSONObject(json);
								// TODO Auto-generated method stub
								ShareMemManager.getInstance().saveToDB(getContext(), "product", json);
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

				b.setMessage(
						"Không thể kết nối tới máy chủ, vui lòng kiểm tra lại hoặc chọn BÁN HÀNG để sử dụng chức năng bán hàng không có internet!");
				b.setCancelable(false);
				b.setPositiveButton("BÁN HÀNG", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Util.checkOffline = true;
						dialog.dismiss();
						startLoadProduct(DataOrder.ONLY_CACHE);

					}
				});
				b.setNegativeButton("THỬ LẠI", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						startLoadProduct(DataOrder.ONLY_NETWORK);
					}
				});
				b.create().show();

			}
		});
	}

	private void showDialogConfirmToSale() {
		runOnUiThreadX(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
				AlertDialog.Builder b = new AlertDialog.Builder(getActivity());

				b.setMessage(
						"Không thể kết nối tới máy chủ, vui lòng kiểm tra lại mạng và THỬ LẠI hoặc chọn LƯU TRÊN MÁY để lưu đơn hàng trên máy và xử lý sau!");
				b.setCancelable(false);
				b.setPositiveButton("LƯU TRÊN MÁY", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Util.checkOffline = true;
						dialog.dismiss();
						saveServerOffline();

					}
				});
				b.setNegativeButton("THỬ LẠI", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						saveToServer();
					}
				});
				b.setNeutralButton("ĐÓNG", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				b.create().show();

			}
		});
	}

	@SuppressWarnings("deprecation")
	private void bindDataToDetailTextAndOther() throws JSONException {

		((LinearLayout) rootView.findViewById(R.id.detailPanel)).setVisibility(View.VISIBLE);
		((TextView) rootView.findViewById(R.id.txtDateCreate)).setText(
				"Thời gian:" + TimeUtil.dateToString(new Date(oOrder.getLong("timestamp")), "dd/MM/yyyy HH:mm:ss"));

		// TODO Auto-generated method stub
		// allow edit
		if (oOrder.getString("status").equals("0")) {
			setVisibleForButtonToEdit(View.VISIBLE);

			((TextView) rootView.findViewById(R.id.txtDateCreate))
					.append("\nMã đơn hàng:" + oOrder.getString("timestamp"));
			((TextView) rootView.findViewById(R.id.txtDateCreate)).append("\nTrạng thái: Chưa cập nhật");
		} else {
			setVisibleForButtonToEdit(View.GONE);
			grid.setEnabled(true);

			((TextView) rootView.findViewById(R.id.txtDateCreate)).setText("\nThời gian:"
					+ TimeUtil.dateToString(new Date(oOrder.getLong("timestamp")), "dd/MM/yyyy HH:mm:ss"));

			// Toast.makeText(getActivity(), TimeUtil.dateToString(new
			// Date(ShareMemManager.getInstance().readFromDB(getActivity(),
			// "tSaleProduct")), "dd/MM/yyyy HH:mm:ss"),
			// Toast.LENGTH_SHORT).show();
			String sOrderCode = extractOrderCode(oOrder);
			((TextView) rootView.findViewById(R.id.txtDateCreate)).append("\nMã đơn hàng:" + sOrderCode);
			((TextView) rootView.findViewById(R.id.txtDateCreate)).append("\nTrạng thái: Đã cập nhật");
		}
	}

	private String extractOrderCode(JSONObject oOrder2) {
		try {
			// TODO Auto-generated method stub
			String msg = oOrder2.getString("server");
			JSONObject jsonObject = new JSONObject(msg);
			String msgOrder = jsonObject.getString("ResponseMessage");
			String sMsg = new StringTokenizer(msgOrder, "|").nextToken();// .split("\\|")[0];
			return sMsg;

		} catch (Exception ex) {
			return ex.toString();
		}
		// return "";
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.menu_order, menu);
		setupMenuItem(menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.saveOrder: {

			if (_dataSource.size() == 0) {
				Toast.makeText(getActivity(), "Bạn phải chọn sản phẩm trước khi lưu", Toast.LENGTH_SHORT).show();
			} else {
				saveToServer();
			}
			return true;
		}
		case R.id.saveOrderOffline: {
			if (_dataSource.size() == 0) {
				Toast.makeText(getActivity(), "Bạn phải chọn sản phẩm trước khi lưu", Toast.LENGTH_SHORT).show();
			} else
				saveServerOffline();
			return true;
		}
		case R.id.action_settings:
			if (_dataSource.size() > 0 && !isOnlyView) {
				if (Util.checkSaveOffline == true) {
					Util.checkSaveOffline = false;
					nextToFragmentAndKeepState(new Fragment_Order_ListTabViewer(), null, true);
				} else {
					AlertDialog.Builder b = new AlertDialog.Builder(getContext());

					b.setMessage("Bạn có muốn lưu đơn hàng này?");
					b.setCancelable(false);
					b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							saveServerOffline();
							_dataSource = new ArrayList<JSONObject>();
						}
					});
					b.setNegativeButton("Không", new DialogInterface.OnClickListener() {

						@Override

						public void onClick(DialogInterface dialog, int which)

						{
							_dataSource = new ArrayList<JSONObject>();
							nextToFragmentAndKeepState(new Fragment_Order_ListTabViewer(), null, true);

						}

					});

					b.create().show();
				}
			} else

				nextToFragmentAndKeepState(new Fragment_Order_ListTabViewer(), null, true);
			return true;
		case R.id.newOrder: {
			if (_dataSource.size() > 0 && !isOnlyView) {
				if (Util.checkSaveOffline == true) {
					newOrder();
				} else {
					AlertDialog.Builder b = new AlertDialog.Builder(getContext());

					b.setMessage("Bạn có muốn lưu đơn hàng này?");
					b.setCancelable(false);
					b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							saveServerOffline();
							_dataSource = new ArrayList<JSONObject>();

						}
					});
					b.setNegativeButton("Không", new DialogInterface.OnClickListener() {

						@Override

						public void onClick(DialogInterface dialog, int which)

						{

							newOrder();
							_dataSource = new ArrayList<JSONObject>();

						}

					});

					b.create().show();
				}
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
		removeDataTmp();
		_dataSource = new ArrayList<JSONObject>();
		// TODO Auto-generated method stub
		sendDataToActivity(BundleData.createNew().putString("screen", Util.SCREEN_ORDER).data());
	}

	public void setupMenuItem(Menu menu) {

		if (!modeEdit) {

			menu.findItem(R.id.saveOrderOffline).setVisible(true);
			menu.findItem(R.id.newOrder).setVisible(true);
		} else {
			try {
				if (oOrder.getInt("status") == Util.ORDER_OK || oOrder.getInt("status") == Util.ORDER_FAILED) {
					menu.findItem(R.id.saveOrder).setVisible(false);
					menu.findItem(R.id.saveOrderOffline).setVisible(false);
					menu.findItem(R.id.newOrder).setVisible(true);
				}
				if (oOrder.getInt("status") == Util.ORDER_OFFLINE) {
					menu.findItem(R.id.saveOrder).setVisible(false);
					menu.findItem(R.id.saveOrderOffline).setVisible(true);
					menu.findItem(R.id.newOrder).setVisible(true);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		menu.findItem(R.id.saveOrder).setVisible(false);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		int x = 0;
		x++;
		super.onResume();
		if (ShareMemManager.getInstance().readFromDB(getContext(), "ispause").equalsIgnoreCase("1")) {
			String string = ShareMemManager.getInstance().readFromDB(getContext(), "datatmp");
			if (!string.equalsIgnoreCase("")) {
				JSONArray jsonArrays;
				try {
					jsonArrays = new JSONArray(string);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				_dataSource = TransformDataManager.convertArrayToListJSON(jsonArrays);
				bindDataToGrid();
			}
		}

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		int x = 0;
		x++;
		super.onPause();
		if (_dataSource.size() > 0) {
			ShareMemManager.getInstance().saveToDB(getContext(), "ispause", "1");
			ShareMemManager.getInstance().saveToDB(getContext(), "datatmp", _dataSource.toString());
		}
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