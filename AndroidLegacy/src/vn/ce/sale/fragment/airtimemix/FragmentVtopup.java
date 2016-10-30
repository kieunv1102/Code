package vn.ce.sale.fragment.airtimemix;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.Home;
import vn.ce.sale.adapter.ChangeAdapter;
import vn.ce.sale.adapter.DisplayOrderProductGrid;
import vn.ce.sale.data.DBManager;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.IData;
import vn.ce.sale.data.IDataCheck;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.fragment.bee.Bee_Fragment_Product_List;
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
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class FragmentVtopup extends ZopostFragment implements IData, IDataCheck, ICallBackUI {

	// TextView txtDisplayNhaMang, txtDisplayMoney;\
	LinearLayout lnlMoney, lnlDisplayAll;
	TextView txtThongBao, txtUserPhone;
	Spinner spnNhaMang, spnMoney;
	EditText edtPhone;
	Button btnSaveAction;
	String arrNhaMang[] = { "Điện Thoại", "Zing Xu (VNG)", "Vcoin", "On-Cash Đa Năng", "Gate(Bạc)" };
	String arrMoney[] = {};
	String arrMoneyDT[] = { "5.000", "10.000", "20.000", "30.000", "50.000", "100.000", "200.000", "300.000",
			"500.000" };
	String arrMoneyZing[] = { "10.000", "20.000", "60.000", "120.000" };
	String arrMoneyVcoin[] = { "10.000", "20.000", "50.000", "100.000", "200.000" };
	String arrMoneyOnCash[] = { "20.000", "60.000", "80.000", "100.000", "200.000" };
	String arrMoneyGate[] = { "10.000", "20.000", "30.000", "50.000", "80.000", "100.000", "200.000", "500.000" };

	int dichvu;
	int sotien;
	String d = null;

	protected void initCreatedView() {

	}

	View footer;
	View header;

	public static FragmentVtopup newInstance() {
		FragmentVtopup fragment = new FragmentVtopup();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	// List<JSONObject> _dataSource = new ArrayList<JSONObject>();
	public void onCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		// ((Home) getActivity()).getSupportActionBar().show();
		// getActivity().setTitle(ShareMemManager.getInstance().readFromDB(getActivity(),
		// "username"));

	}

	@SuppressLint("UseValueOf")
	@SuppressWarnings("unused")
	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// setup ui
		rootView = inflater.inflate(R.layout.airtime_fragment_vtopup, container, false);
		lnlMoney = (LinearLayout) rootView.findViewById(R.id.lnlMoney);
		lnlDisplayAll = (LinearLayout) rootView.findViewById(R.id.lnlDisplayAll);
		txtThongBao = (TextView) rootView.findViewById(R.id.txtThongBaoVtop);
		txtUserPhone = (TextView) rootView.findViewById(R.id.txtUserPhone);
		spnNhaMang = (Spinner) rootView.findViewById(R.id.spnMang);
		spnMoney = (Spinner) rootView.findViewById(R.id.spnMoney);
		edtPhone = (EditText) rootView.findViewById(R.id.edtPhone);
		edtPhone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				ShareMemManager.getInstance().saveToDB(getActivity(), "vtopup", s.toString());
			}
		});
		btnSaveAction = (Button) rootView.findViewById(R.id.btnSaveAction);
		btnSaveAction.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String phone = edtPhone.getText().toString();
				if (edtPhone.getText().toString().equals("")) {
					if (dichvu == 0) {
						txtThongBao.setVisibility(View.VISIBLE);
						txtThongBao.setText("Bạn phải nhập số điện thoại");
						// Toast.makeText(getActivity(), "Bạn phải nhập số điện
						// thoại", Toast.LENGTH_SHORT).show();
					} else {
						txtThongBao.setVisibility(View.VISIBLE);
						txtThongBao.setText("Bạn phải nhập tài khoản");
						// Toast.makeText(getActivity(), "Bạn phải nhập tài
						// khoản", Toast.LENGTH_SHORT).show();
					}

				} else {
					validatePhoneNumber(phone);
				}
			}
		});

		ArrayAdapter<String> adapterNhaMang = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, arrNhaMang);
		adapterNhaMang.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		spnNhaMang.setAdapter(adapterNhaMang);
		spnNhaMang.setOnItemSelectedListener(new NhaMangProcessEvent());

		return rootView;
	}

	private class NhaMangProcessEvent implements android.widget.AdapterView.OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub

			dichvu = position;
			if (position == 0) {
				txtUserPhone.setText("Số điện thoại:");
				edtPhone.setInputType(InputType.TYPE_CLASS_NUMBER);
				// lnlMoney.setVisibility(View.VISIBLE);
			} else {
				// lnlMoney.setVisibility(View.GONE);
				edtPhone.setInputType(InputType.TYPE_CLASS_TEXT);
				txtUserPhone.setText("Tài khoản:");
			}
			if (position == 0) {
				arrMoney = arrMoneyDT;
			} else if (position == 1) {
				arrMoney = arrMoneyZing;
			} else if (position == 2) {
				arrMoney = arrMoneyVcoin;
			} else if (position == 3) {
				arrMoney = arrMoneyOnCash;
			} else if (position == 4) {
				arrMoney = arrMoneyGate;
			}

			ArrayAdapter<String> adapterMoney = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_spinner_item, arrMoney);
			adapterMoney.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
			spnMoney.setAdapter(adapterMoney);
			spnMoney.setOnItemSelectedListener(new MoneyProcessEvent());
			edtPhone.setText("");
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			// txtDisplayNhaMang.setText(arrNhaMang[0]);
		}

	}

	private class MoneyProcessEvent implements android.widget.AdapterView.OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			// txtDisplayNhaMang.setText(arrNhaMang[position]);
			sotien = position;
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			// txtDisplayNhaMang.setText(arrNhaMang[0]);
		}

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

	private void addChangeOrderProduct(String mkc2) {
		// TODO Auto-generated method stub
		String a = ShareMemManager.getInstance().readFromDB(getContext(), "username");
		String b = String.valueOf(edtPhone.getText().toString());
		String tien = arrMoney[sotien];
		String[] parts = tien.split("\\.");
		String part1 = parts[0];
		String c = part1 + "000";
		String s = "|" + a + "|" + b + "|" + c + "|" + d + "";

		String params = "system=AirtimeMix&Airtime=GatewayApi/"
				+ ShareMemManager.getInstance().readFromDB(getContext(), "username") + "/" + mkc2 + ""
				+ "/APP/AIRTIMEMIX/AIRTIME" + s;
		final ProgressDialog pd = new ProgressDialog(getContext());
		pd.setMessage("Đang gửi dữ liệu xác nhận!");
		pd.show();
		ServiceManager.factoryData().getDataRaw(Util.SERVER_URL, params, new ICallBackUI() {

			@Override
			public void processRaw(String key, final int statusx, final String json) {
				runOnUiThreadX(new Runnable() {

					@SuppressWarnings("unused")
					@Override
					public void run() {
						// TODO Auto-generated method stub
						int status = statusx;
						// TODO Auto-generated method stub
						if (status == Util.ERROR_NETWORK) {
							pd.dismiss();
							Toast.makeText(getActivity(), "Không có kết nối mạng,Bạn vui lòng kiểm tra lại",
									Toast.LENGTH_SHORT).show();
						}
						// phân tích để tiếp tục
						if (status == 200) {
							pd.dismiss();
							try {
								JSONObject o = new JSONObject(json);

								if (o.getString("ResponseCode").equalsIgnoreCase("00")) {
									txtThongBao.setVisibility(View.VISIBLE);
									txtThongBao.setText(o.getString("ResponseMessage"));
								}
								if (o.getString("ResponseCode").equalsIgnoreCase("01")) {

									txtThongBao.setVisibility(View.VISIBLE);
									String tb = o.getString("ResponseMessage");
									String[] parts = tb.split("\\|");
									String part1 = parts[2];
									txtThongBao.setText("Nạp tiền thành công cho " + edtPhone.getText().toString()
											+ ", số tiền trừ tài khoản "
											+ String.valueOf(FormatUtil.formatCurrency(Double.parseDouble(parts[1])))
											+ " Vtop, số dư sau giao dịch "
											+ String.valueOf(FormatUtil.formatCurrency(Double.parseDouble(part1)))
											+ " Vtop");
									// lnlDisplayAll.setVisibility(View.GONE);

									long currentDate = System.currentTimeMillis();
									DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									String sdt = edtPhone.getText().toString();
									String tien = arrMoney[sotien];
									Util.saveHistoryVtopup(getActivity(), sdt, tien, String.valueOf(currentDate));
									edtPhone.setText("");
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

	@SuppressWarnings("unused")
	private void validatePhoneNumber(String phone) {

		// validatephonenumber
		if (dichvu == 0) {

			String regex11 = "01?[0-9]{9}$";
			String regex10 = "09?[0-9]{8}$";
			String regexViettel1 = "016?[3-9]{1}?[0-9]{7}$";
			String regexViettel2 = "09?[6-8]{1}?[0-9]{7}$";
			String regexMobi1 = "012?[0-2]{1}?[0-9]{7}$";
			String regexMobi2 = "0126?[0-9]{7}$";
			String regexMobi3 = "0128?[0-9]{7}$";
			String regexMobi4 = "090?[0-9]{7}$";
			String regexMobi5 = "093?[0-9]{7}$";
			String regexVina1 = "012?[3-5]{1}?[0-9]{7}$";
			String regexVina2 = "0127?[0-9]{7}$";
			String regexVina3 = "0129?[0-9]{7}$";
			String regexVina4 = "091?[0-9]{7}$";
			String regexVina5 = "094?[0-9]{7}$";
			String regexVnMobi1 = "0188?[0-9]{7}$";
			String regexVnMobi2 = "094?[0-9]{7}$";
			String regexBeeline = "099?[3-6]{1}?[0-9]{6}$";
			String regexE1 = "092?[0-9]{7}$";
			String regexE2 = "095?[0-9]{7}$";

			String arr10[] = { regexViettel2, regexMobi4, regexMobi5, regexVina4, regexVina5, regexVnMobi2,
					regexBeeline };
			String arr11[] = { regexViettel1, regexMobi1, regexMobi2, regexMobi3, regexVina1, regexVina2, regexVina3,
					regexVnMobi1 };
			String arr[] = { regexViettel1, regexViettel2, regexMobi1, regexMobi2, regexMobi3, regexMobi4, regexMobi5,
					regexVina1, regexVina2, regexVina3, regexVina4, regexVina5, regexVnMobi1, regexVnMobi2 };

			if (phone.length() < 10 || phone.length() > 11) {
				txtThongBao.setVisibility(View.VISIBLE);
				txtThongBao.setText("Định dạng số điện thoại không đúng");
			}
			else
			{
				if (phone.length() == 10) {

					for (int i = 0; i <= arr10.length - 1; i++) {
						Pattern pattern = Pattern.compile(arr10[i]);
						Matcher matcher = pattern.matcher(phone);
						if (matcher.matches()) {
							if (i == 0) {
								d = "VIETTEL";
								dialogPhone();
							} else if (i == 1 || i == 2) {
								d = "VMS";
								dialogPhone();
							} else if (i == 3 || i == 4) {
								d = "VINAPHONE";
								dialogPhone();
							} else if (i == 5) {
								d = "VNM";
								dialogPhone();
							} else if (i == 6) {
								d = "BEELINE";
								dialogPhone();
							}
							//
							// } else {
							//
							// txtThongBao.setVisibility(View.VISIBLE);
							// txtThongBao.setText("Số điện thoại không đúng!");
							// }
							// }
							Pattern pattern10 = Pattern.compile(regex10);
							Matcher matcher10 = pattern10.matcher(phone);
							if (matcher10.matches()) {
								for (int x = 0; x <= arr10.length - 1; x++) {
									Pattern p10 = Pattern.compile(arr10[x]);
									Matcher m10 = p10.matcher(phone);
									if (m10.matches()) {
										if (x == 0) {
											d = "VIETTEL";
											dialogPhone();
										} else if (x == 1 || x == 2) {
											d = "VMS";
											dialogPhone();
										} else if (x == 3 || x == 4) {
											d = "VINAPHONE";
											dialogPhone();
										} else if (x == 5) {
											d = "VNM";
											dialogPhone();
										} else if (i == 6) {
											d = "BEELINE";
											dialogPhone();
										}

									}
									// else {
									// if (arr10.length - 1 - i == 0) {
									// txtThongBao.setVisibility(View.VISIBLE);
									// txtThongBao.setText("Số điện thoại không
									// đúng!");
									// }
									// }

								}

							} else {
								txtThongBao.setVisibility(View.VISIBLE);
								txtThongBao.setText("Định dạng số điện thoại không đúng");
							}

						}
					}
				}
				else if (phone.length() == 11) {

					// for (int i = 0; i <= arr11.length - 1; i++) {
					// Pattern pattern = Pattern.compile(arr11[i]);
					// Matcher matcher = pattern.matcher(phone);
					// int p = i;
					// if (matcher.matches()) {
					// if (i == 0) {
					// d = "VIETTEL";
					// dialogPhone();
					// } else if (i == 1 || i == 2 || i == 3) {
					// d = "VMS";
					// dialogPhone();
					// } else if (i == 4 || i == 5 || i == 6) {
					// d = "VINAPHONE";
					// dialogPhone();
					// } else if (i == 7) {
					// d = "VNM";
					// dialogPhone();
					// }
					//
					// } else {
					// if (arr11.length - 1 - i == 0) {
					// txtThongBao.setVisibility(View.VISIBLE);
					// txtThongBao.setText("Số điện thoại không đúng!");
					// }
					// }
					Pattern pattern11 = Pattern.compile(regex11);
					Matcher matcher11 = pattern11.matcher(phone);
					if (matcher11.matches()) {
						for (int i = 0; i <= arr11.length - 1; i++) {
							Pattern pattern = Pattern.compile(arr11[i]);
							Matcher matcher = pattern.matcher(phone);
							int p = i;
							if (matcher.matches()) {
								if (i == 0) {
									d = "VIETTEL";
									dialogPhone();
								} else if (i == 1 || i == 2 || i == 3) {
									d = "VMS";
									dialogPhone();
								} else if (i == 4 || i == 5 || i == 6) {
									d = "VINAPHONE";
									dialogPhone();
								} else if (i == 7) {
									d = "VNM";
									dialogPhone();
								}

							}
							// else {
							// if (arr11.length - 1 - i == 0) {
							// txtThongBao.setVisibility(View.VISIBLE);
							// txtThongBao.setText("Số điện thoại không đúng!");
							// }
							// }

						}

					} else {
						txtThongBao.setVisibility(View.VISIBLE);
						txtThongBao.setText("Định dạng số điện thoại không đúng");
					}
				}

			
			}
		} else if (dichvu == 1) {
			d = "ZING_XU";
			validateTaiKhoan(phone);
		} else if (dichvu == 2) {
			d = "VTC_VCOIN";
			validateTaiKhoan(phone);
		} else if (dichvu == 3) {
			d = "ONCASH";
			validateTaiKhoan(phone);
		} else if (dichvu == 4) {
			d = "GATE";
			validateTaiKhoan(phone);
		}

	}

	private void validateTaiKhoan(String s) {
		Pattern p = Pattern.compile("[^A-Za-z0-9@]");
		Matcher m = p.matcher(s);
		// boolean b = m.matches();
		boolean b = m.find();
		if (b == true) {
			txtThongBao.setVisibility(View.VISIBLE);
			txtThongBao.setText("Bạn xem lại tài khoản vừa nhập");
		} else {
			dialog();
		}
	}

	private void dialogPhone() {
		txtThongBao.setText("aaaaaaaaaaaaaaa");
		txtThongBao.setVisibility(View.GONE);
		final LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
		b.setCancelable(false);
		b.setTitle("Xác nhận!");
		b.setMessage(
				"Bạn chắc chắn muốn nạp cho số " + edtPhone.getText().toString() + " " + arrMoney[sotien] + " đồng");
		b.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				final Dialog dlMK2 = new Dialog(getActivity());
				dlMK2.setTitle("Nhập mật khẩu cấp 2");
				final View view = inflater.inflate(R.layout.custom_dialog_vtopup, null);
				final EditText editText1 = (EditText) view.findViewById(R.id.edit_txt);
				final TextView txtNotification = (TextView) view.findViewById(R.id.txtNotification);
				dlMK2.setContentView(view);
				Button btn = (Button) view.findViewById(R.id.btn_ok);
				btn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if (editText1.getText().toString().equals("")) {
							txtNotification.setVisibility(View.VISIBLE);
							txtNotification.setText("Bạn chưa nhập mật khẩu cấp 2");
						} else {
							dlMK2.dismiss();
							addChangeOrderProduct(editText1.getText().toString());
						}

					}
				});
				Button btn1 = (Button) view.findViewById(R.id.btn_canel_order);
				btn1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dlMK2.dismiss();
					}
				});

				dlMK2.show();
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

	private void dialog() {
		final LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
		b.setCancelable(false);
		b.setTitle("Xác nhận!");
		b.setMessage("Bạn chắc chắn muốn nạp cho tài khoản " + edtPhone.getText().toString() + " " + arrMoney[sotien]
				+ " đồng");
		b.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				final Dialog dlMK2 = new Dialog(getActivity());
				dlMK2.setTitle("Nhập mật khẩu cấp 2");
				final View view = inflater.inflate(R.layout.custom_dialog_vtopup, null);
				final EditText editText1 = (EditText) view.findViewById(R.id.edit_txt);
				final TextView txtNotification = (TextView) view.findViewById(R.id.txtNotification);
				dlMK2.setContentView(view);
				Button btn = (Button) view.findViewById(R.id.btn_ok);
				btn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if (editText1.getText().toString().equals("")) {
							txtNotification.setVisibility(View.VISIBLE);
							txtNotification.setText("Bạn chưa nhập mật khẩu cấp 2");
						} else {
							dlMK2.dismiss();
							addChangeOrderProduct(editText1.getText().toString());
						}

					}
				});
				Button btn1 = (Button) view.findViewById(R.id.btn_canel_order);
				btn1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dlMK2.dismiss();
					}
				});

				dlMK2.show();
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

	@Override
	public void process(String key, int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processRaw(String key, int status, String json) {
		// TODO Auto-generated method stub

	}

}