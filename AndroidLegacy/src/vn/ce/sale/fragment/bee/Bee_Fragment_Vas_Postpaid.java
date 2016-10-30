package vn.ce.sale.fragment.bee;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

@SuppressWarnings("unused")
public class Bee_Fragment_Vas_Postpaid extends ZopostFragment implements IData, IDataCheck, OnClickListener, TextWatcher {

	String timeNow = String.valueOf((new Date()).getTime());
	// TextView txtDisplayNhaMang, txtDisplayMoney;
	TextView txtThongBao;
	Spinner spnDichVu;
	LinearLayout lnlDisplayAll;
	EditText edtNumberBill, edtThongtin, edtTienThanhToan;
	Button btnThanhToan, btnCheck;
	String arrDichVu[] = { "Viettel trả sau", "Mobifone trả sau", "Vinaphone trả sau" };
	String arrMoney[] = { "-----Chọn-----","5.000", "10.000", "20.000", "30.000", "50.000", "100.000", "200.000", "300.000",
	"500.000" };
	int dichvu;
	Spinner spnMoneyPostpaid;
	protected void initCreatedView() {

	}

	View footer;
	View header;

	public static Bee_Fragment_Vas_Postpaid newInstance() {
		Bee_Fragment_Vas_Postpaid fragment = new Bee_Fragment_Vas_Postpaid();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	// List<JSONObject> _dataSource = new ArrayList<JSONObject>();
	public void onCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getActivity().setTitle(ShareMemManager.getInstance().readFromDB(getActivity(),
		// "username"));
	}

	@SuppressLint("UseValueOf")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// setup ui
		rootView = inflater.inflate(R.layout.bee_fragment_vas_postpaid, container, false);
		lnlDisplayAll = (LinearLayout) rootView.findViewById(R.id.lnlDisplayAll);
		txtThongBao = (TextView) rootView.findViewById(R.id.txtThongBaoVbill);
		spnDichVu = (Spinner) rootView.findViewById(R.id.spnDichVu);
		spnMoneyPostpaid = (Spinner)rootView.findViewById(R.id.spnMoneyPostpaid);
		edtNumberBill = (EditText) rootView.findViewById(R.id.edtNumberBill);
		edtThongtin = (EditText) rootView.findViewById(R.id.edtThongtin);
		edtTienThanhToan = (EditText) rootView.findViewById(R.id.edtTienThanhToan);
		edtNumberBill.addTextChangedListener((TextWatcher) this);
		edtThongtin.addTextChangedListener((TextWatcher) this);
		edtTienThanhToan.addTextChangedListener((TextWatcher) this);
		btnThanhToan = (Button) rootView.findViewById(R.id.btnThanhToan);
		btnCheck = (Button) rootView.findViewById(R.id.btnCheck);
		btnCheck.setOnClickListener(this);
		btnThanhToan.setOnClickListener(this);

		ArrayAdapter<String> adapterDichVu = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, arrDichVu);
		adapterDichVu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnDichVu.setAdapter(adapterDichVu);
		spnDichVu.setOnItemSelectedListener(new DichVuProcessEvent());
		ArrayAdapter<String> adapterMoney = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, arrMoney);
		adapterMoney.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnMoneyPostpaid.setAdapter(adapterMoney);
		spnMoneyPostpaid.setOnItemSelectedListener(new MoneyProcessEvent());

		return rootView;
	}

	private class DichVuProcessEvent implements android.widget.AdapterView.OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			dichvu = position;
			edtNumberBill.setText("");
			edtTienThanhToan.setText("");
			edtThongtin.setText("");
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
			edtTienThanhToan.setText(arrMoney[position]);
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			// txtDisplayNhaMang.setText(arrNhaMang[0]);
			spnMoneyPostpaid.setSelection(0);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnCheck:
			edtTienThanhToan.setText("");
			edtThongtin.setText("");
			if (edtNumberBill.getText().toString().equals("")) {
				// Toast.makeText(getActivity(), "Bạn phải nhập số điện thoại để
				// kiểm tra!", Toast.LENGTH_SHORT).show();
				txtThongBao.setVisibility(View.VISIBLE);
				txtThongBao.setText("Bạn phải nhập số điện thoại để kiểm tra!");
			} else
				validatePhoneNumber(edtNumberBill.getText().toString());
			break;
		case R.id.btnThanhToan:
			if (edtNumberBill.getText().toString().equals("") || edtTienThanhToan.getText().toString().equals("")) {
				// Toast.makeText(getActivity(), "Không đủ thông tin để thanh
				// toán!", Toast.LENGTH_SHORT).show();
				txtThongBao.setVisibility(View.VISIBLE);
				txtThongBao.setText("Không đủ thông tin để thanh toán!");
			} else if (Integer.parseInt(edtTienThanhToan.getText().toString()) < 5000
					|| (Integer.parseInt(edtTienThanhToan.getText().toString()) % 1000) > 0) {
				// Toast.makeText(getActivity(), "Bạn phải nhập giá trị >=5000
				// và chia hết cho 1000!", Toast.LENGTH_SHORT)
				// .show();
				txtThongBao.setVisibility(View.VISIBLE);
				txtThongBao.setText("Bạn phải nhập giá trị >=5000 và chia hết cho 1000!");
			} else {
				validatePhoneNumberThanhToan(edtNumberBill.getText().toString());
			}

			break;
		default:
			break;
		}

	}

	private void addQUERYVBILL(String mkc2) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		String a = ShareMemManager.getInstance().readFromDB(getContext(), "username");
		String b = String.valueOf(edtNumberBill.getText().toString());
		String c = String.valueOf(edtTienThanhToan.getText().toString());
		String d = "";
		// validatephonenumber
		if (dichvu == 0) {
			d = "VIETTEL_TRASAU";
		} else if (dichvu == 1) {
			d = "VMS_TRASAU";
		} else if (dichvu == 2) {
			d = "VINA_TRASAU";
		}
		String s = "|" + a + "|" + b + "|" + c + "|" + d + "";

		String params = "system=AirtimeMix&Airtime=GatewayApi/"
				+ ShareMemManager.getInstance().readFromDB(getContext(), "username") + "/" + mkc2 + ""
				+ "/APP/AIRTIMEMIX/PAYBILL" + s;
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
									String t = FormatUtil
											.formatCurrency(Double.parseDouble(edtTienThanhToan.getText().toString()));
									String sodu = FormatUtil.formatCurrency(Double.parseDouble(part1));
									txtThongBao.setText("Nạp tiền thành công cho " + edtNumberBill.getText().toString()
											+ ", số tiền trừ tài khoản "
											+ FormatUtil.formatCurrency(Double.parseDouble(parts[1]))
											+ " Vtop, số dư sau giao dịch " + sodu + " Vtop");
									edtNumberBill.setText("");
									edtThongtin.setText("");
									edtTienThanhToan.setText("");
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

	private void validatePhoneNumber(String phone) {

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

		String arr10[] = { regexViettel2, regexMobi4, regexMobi5, regexVina4, regexVina5 };
		String arr11[] = { regexViettel1, regexMobi1, regexMobi2, regexMobi3, regexVina1, regexVina2, regexVina3 };
		String arr[] = { regexViettel1, regexViettel2, regexMobi1, regexMobi2, regexMobi3, regexMobi4, regexMobi5,
				regexVina1, regexVina2, regexVina3, regexVina4, regexVina5 };

		if (phone.length() < 10 || phone.length() > 11) {
			// Toast.makeText(getActivity(), "Định dạng số điện thoại không
			// đúng", Toast.LENGTH_SHORT).show();
			txtThongBao.setVisibility(View.VISIBLE);
			txtThongBao.setText("Định dạng số điện thoại không đúng");

		} else {
			if (phone.length() == 10) {
				Pattern pattern10 = Pattern.compile(regex10);
				Matcher matcher10 = pattern10.matcher(phone);
				if (matcher10.matches()) {
					for (int i = 0; i <= arr10.length - 1; i++) {
						Pattern pattern = Pattern.compile(arr10[i]);
						Matcher matcher = pattern.matcher(phone);
						if (matcher.matches()) {
							if (dichvu == 0) {
								if (i == 0) {
									checkQUERYVBILL();
								} else {
									txtThongBao.setVisibility(View.VISIBLE);
									txtThongBao.setText("Đây không phải số Viettel");
								}
							}
							if (dichvu == 1) {
								if (i == 1 || i == 2) {
									checkQUERYVBILL();
								} else {
									txtThongBao.setVisibility(View.VISIBLE);
									txtThongBao.setText("Đây không phải số Mobi");
								}
							}
							if (dichvu == 2) {
								if (i == 3 || i == 4) {
									checkQUERYVBILL();
								} else {
									txtThongBao.setVisibility(View.VISIBLE);
									txtThongBao.setText("Đây không phải số Vina");
								}
							}

						}

					}
				} else {
					txtThongBao.setVisibility(View.VISIBLE);
					txtThongBao.setText("Định dạng số điện thoại không đúng");
				}

			} else if (phone.length() == 11) {
				Pattern pattern11 = Pattern.compile(regex11);
				Matcher matcher11 = pattern11.matcher(phone);
				if (matcher11.matches()) {
					for (int i = 0; i <= arr11.length - 1; i++) {
						Pattern pattern = Pattern.compile(arr11[i]);
						Matcher matcher = pattern.matcher(phone);
						int p = i;
						if (matcher.matches()) {
							if (dichvu == 0) {
								if (i == 0) {
									checkQUERYVBILL();
								} else {
									txtThongBao.setVisibility(View.VISIBLE);
									txtThongBao.setText("Đây không phải số Viettel");
								}
							} else if (dichvu == 1) {
								if (i == 1 || i == 2 || i == 3) {
									checkQUERYVBILL();
								} else {
									txtThongBao.setVisibility(View.VISIBLE);
									txtThongBao.setText("Đây không phải số Mobi");
								}
							} else if (dichvu == 2) {
								if (i == 4 || i == 5 || i == 6) {
									checkQUERYVBILL();
								} else {
									txtThongBao.setVisibility(View.VISIBLE);
									txtThongBao.setText("Đây không phải số Vina");
								}
							}
						}
					}
				} else {
					txtThongBao.setVisibility(View.VISIBLE);
					txtThongBao.setText("Định dạng số điện thoại không đúng");
				}
			}

		}
	}

	private void validatePhoneNumberThanhToan(String phone) {

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

		String arr10[] = { regexViettel2, regexMobi4, regexMobi5, regexVina4, regexVina5 };
		String arr11[] = { regexViettel1, regexMobi1, regexMobi2, regexMobi3, regexVina1, regexVina2, regexVina3 };
		String arr[] = { regexViettel1, regexViettel2, regexMobi1, regexMobi2, regexMobi3, regexMobi4, regexMobi5,
				regexVina1, regexVina2, regexVina3, regexVina4, regexVina5 };

		if (phone.length() < 10 || phone.length() > 11) {
			// Toast.makeText(getActivity(), "Định dạng số điện thoại không
			// đúng", Toast.LENGTH_SHORT).show();
			txtThongBao.setVisibility(View.VISIBLE);
			txtThongBao.setText("Định dạng số điện thoại không đúng!");
		} else {
			if (phone.length() == 10) {
				Pattern pattern10 = Pattern.compile(regex10);
				Matcher matcher10 = pattern10.matcher(phone);
				if (matcher10.matches()) {
					for (int i = 0; i <= arr10.length - 1; i++) {
						Pattern pattern = Pattern.compile(arr10[i]);
						Matcher matcher = pattern.matcher(phone);
						if (matcher.matches()) {
							if (dichvu == 0) {
								if (i == 0) {
									thanhToan();
								} else {
									txtThongBao.setVisibility(View.VISIBLE);
									txtThongBao.setText("Đây không phải số Viettel");
								}
							}
							if (dichvu == 1) {
								if (i == 1 || i == 2) {
									thanhToan();
								} else {
									txtThongBao.setVisibility(View.VISIBLE);
									txtThongBao.setText("Đây không phải số Mobi");
								}
							}
							if (dichvu == 2) {
								if (i == 3 || i == 4) {
									thanhToan();
								} else {
									txtThongBao.setVisibility(View.VISIBLE);
									txtThongBao.setText("Đây không phải số Vina");
								}
							}

						}
						// else{
						// if (arr10.length-1-i==0) {
						// txtThongBao.setVisibility(View.VISIBLE);
						// txtThongBao.setText("Số điện thoại không đúng!");
						// }
						// }

					}
				} else {
					txtThongBao.setVisibility(View.VISIBLE);
					txtThongBao.setText("Định dạng số điện thoại không đúng");
				}

			} else if (phone.length() == 11) {
				Pattern pattern11 = Pattern.compile(regex11);
				Matcher matcher11 = pattern11.matcher(phone);
				if (matcher11.matches()) {
					for (int i = 0; i <= arr11.length - 1; i++) {
						Pattern pattern = Pattern.compile(arr11[i]);
						Matcher matcher = pattern.matcher(phone);
						int p = i;
						if (matcher.matches()) {
							if (dichvu == 0) {
								if (i == 0) {
									thanhToan();
								} else {
									txtThongBao.setVisibility(View.VISIBLE);
									txtThongBao.setText("Đây không phải số Viettel");
								}
							} else if (dichvu == 1) {
								if (i == 1 || i == 2 || i == 3) {
									thanhToan();
								} else {
									txtThongBao.setVisibility(View.VISIBLE);
									txtThongBao.setText("Đây không phải số Mobi");
								}
							} else if (dichvu == 2) {
								if (i == 4 || i == 5 || i == 6) {
									thanhToan();
								} else {
									txtThongBao.setVisibility(View.VISIBLE);
									txtThongBao.setText("Đây không phải số Vina");
								}
							}
						}
						// else{
						// if (arr11.length-1-i==0) {
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
	}

	private void thanhToan() {
		txtThongBao.setVisibility(View.GONE);
		final LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
		b.setCancelable(false);
		b.setTitle("Xác nhận!");
		b.setMessage("Bạn chắc chắn muốn thanh toán cho số " + edtNumberBill.getText().toString() + " "
				+ String.valueOf(FormatUtil.formatCurrency(Double.parseDouble(edtTienThanhToan.getText().toString())))
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
							addQUERYVBILL(editText1.getText().toString());
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

	private void checkQUERYVBILL() {
		// TODO Auto-generated method stub
		txtThongBao.setVisibility(View.GONE);
		String a = "";
		String b = edtNumberBill.getText().toString();
		if (dichvu == 0) {
			a = "VIETTEL";
		} else if (dichvu == 1) {
			a = "VINA";
		} else if (dichvu == 2) {
			a = "MOBI";
		}
		String regex11 = "01?[0-9]{9}$";
		String regex10 = "09?[0-9]{8}$";
		if (b.length() < 10 || b.length() > 11) {
			// Toast.makeText(getActivity(), "Định dạng số điện thoại không
			// đúng", Toast.LENGTH_SHORT).show();
			txtThongBao.setVisibility(View.VISIBLE);
			txtThongBao.setText("Định dạng số điện thoại không đúng!");
		} else {
			if (b.length() == 10) {

				Pattern pattern = Pattern.compile(regex10);
				Matcher matcher = pattern.matcher(b);
				if (matcher.matches()) {
				} else {
					// Toast.makeText(getActivity(), "Bạn xem lại số điện thoại
					// vừa nhập", Toast.LENGTH_SHORT).show();
					txtThongBao.setVisibility(View.VISIBLE);
					txtThongBao.setText("Bạn xem lại số điện thoại vừa nhập");
				}
			} else if (b.length() == 11) {
				Pattern pattern = Pattern.compile(regex11);
				Matcher matcher = pattern.matcher(b);
				if (matcher.matches()) {
				} else {
					// Toast.makeText(getActivity(), "Bạn xem lại số điện thoại
					// vừa nhập", Toast.LENGTH_SHORT).show();
					txtThongBao.setVisibility(View.VISIBLE);
					txtThongBao.setText("Bạn xem lại số điện thoại vừa nhập!");
				}
			}
		}
		String s = a + "|" + b;
		String params = "system=AirtimeMix&Airtime=GatewayApi/"
				+ ShareMemManager.getInstance().readFromDB(getContext(), "username") + "/"
				+ ShareMemManager.getInstance().readFromDB(getContext(), "password") + "" + "/APP/QUERYVBILL/" + s;
		final ProgressDialog pd = new ProgressDialog(getContext());
		pd.setMessage("Đang kiểm tra công nợ!");
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
							JSONObject oJson;

							try {
								oJson = new JSONObject(json);
								if (oJson.getString("ResponseCode").equalsIgnoreCase("00")) {
									edtTienThanhToan.setEnabled(true);
									edtThongtin.setEnabled(true);
									txtThongBao.setVisibility(View.VISIBLE);
									txtThongBao.setText("Không kiểm tra được công nợ, Bạn vui lòng tự nhập thông tin");
								}
								if (oJson.getString("ResponseCode").equalsIgnoreCase("01")) {
									String arr = "[" + oJson.getString("ResponseMessage") + "]";
									List<JSONObject> listObj = TransformDataManager
											.convertArrayToListJSON(new JSONArray(arr));
									for (int i = 0; i < listObj.size(); i++) {
										if (listObj.get(i).getInt("TotalPayment") == 0) {
											txtThongBao.setVisibility(View.VISIBLE);
											txtThongBao.setText("Không có công nợ");
										} else {
											edtTienThanhToan.setText(listObj.get(i).getString("TotalPayment"));
											edtThongtin.setText(listObj.get(i).getString("CustomerName") + " - "
													+ listObj.get(i).getString("Address"));
										}
									}
								}

							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
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

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

		// if (s.length() > 0) {
		// edtTienThanhToan.removeTextChangedListener(this);
		// double f = Double.parseDouble(s.toString());
		//
		// // String b =
		// // FormatUtil.formatCurrency(Double.parseDouble(s.toString()));
		// long c = parseCurrency(s.toString());
		// DecimalFormat df = new DecimalFormat("###,###,###.00");
		// String a = df.format(c);
		// // long d = Long.parseLong(s.toString());
		// String b = FormatUtil.formatCurrency((double) c);
		//
		// String b = NumberFormat.getInstance(Locale.GERMANY).format(f);
		// edtTienThanhToan.setText(b);
		// edtTienThanhToan.addTextChangedListener(this);

		// }

		ShareMemManager.getInstance().saveToDB(getActivity(), "vbill", s.toString());

	}

	public static long parseCurrency(String currency) {
		if (currency == null || currency.isEmpty()) {
			return 0;
		}
		char[] cs = currency.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : cs) {
			if (c == '.' || c == ',') {
				continue;
			}
			sb.append(c);
		}
		try {
			return Long.parseLong(sb.toString());
		} catch (Exception ex) {
			return 0;
		}
		// return Long.parseLong(sb.toString());
	}

}