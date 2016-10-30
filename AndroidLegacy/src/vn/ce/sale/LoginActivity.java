package vn.ce.sale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ZopostActivity;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.TimeUtil;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;//ok
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
// @SuppressWarnings("deprecation")
public class LoginActivity extends ZopostActivity
		implements OnClickListener, OnEditorActionListener, OnFocusChangeListener, ICallBackUI {

	private EditText edtPassWord;
	private AutoCompleteTextView edtEmail;
	private CheckBox cbRememberPassWord;
	private CheckBox cbRememberOffline;

	private Button btLogin;
	private Button btnBongSen;
	// private TextView tvForgetPassWord;
	private TextView tvTitleActivity;
	private View formLogin;
	private ProgressBar formLodingData;
	public static int index = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
//		btnBongSen = (Button) findViewById(R.id.btnBongSen);
//		btnBongSen.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent(LoginActivity.this, MainActivityBongsen.class);
//				startActivity(i);
//			}
//		});
		init_UI(this);
	}

	private void init_UI(Activity activity) {
		btLogin = (Button) activity.findViewById(R.id.activity_login_bt_login);

		cbRememberPassWord = (CheckBox) activity.findViewById(R.id.activity_login_cb_remember_password);
		cbRememberOffline = (CheckBox) activity.findViewById(R.id.activity_login_cb_offline);

		edtPassWord = (EditText) activity.findViewById(R.id.activity_login_edt_password);
		edtEmail = (AutoCompleteTextView) activity.findViewById(R.id.activity_login_edt_email);
		tvTitleActivity = (TextView) activity.findViewById(R.id.activity_login_tv_title_activity);
		formLodingData = (ProgressBar) activity.findViewById(R.id.view_loading_data_form_loading);

		// btLogin.setText("Đăng nhập");
		edtPassWord.setHint("Nhập mật khẩu...");
		edtPassWord.setText(getPassWork());
		edtEmail.setHint("Nhập tên đăng nhập...");
		edtEmail.setText(getEmail());

		btLogin.setOnClickListener(this);

		edtEmail.setOnEditorActionListener(this);
		edtPassWord.setOnEditorActionListener(this);
		edtEmail.setOnFocusChangeListener(this);
		edtPassWord.setOnFocusChangeListener(this);
		// formLodingData.setVisibility(View.GONE);
		//
		((ImageView) findViewById(R.id.v21logo)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// nextToActivity(Home.class,
//				nextToActivity(HomeActivity1.class,
//						BundleData.createNew().putString("screen", Util.SCREEN_HOME).data(), true);
//				BundleData.createNew().putString("screen", Util.SCREEN_HOME).data(),true);
//				Intent t = new Intent(LoginActivity.this, HomeActivity.class);
//				startActivity(t);
				// userLogin(LoginActivity.this);
			}
		});
		/* */
		// load data
		// set upimei
		String imei = ShareMemManager.getInstance().readFromDB(getApplicationContext(), "iddevice");
		if (imei.equalsIgnoreCase("")) {
			TelephonyManager tm = (TelephonyManager) getApplicationContext()
					.getSystemService(Context.TELEPHONY_SERVICE);
			imei = tm.getDeviceId();
			ShareMemManager.getInstance().saveToDB(getApplicationContext(), "iddevice", imei);
		}
		if (ShareMemManager.getInstance().readFromDB(LoginActivity.this, "isRemember").equalsIgnoreCase("1")) {
			edtEmail.setText(ShareMemManager.getInstance().readFromDB(LoginActivity.this, "user"));
			edtPassWord.setText(ShareMemManager.getInstance().readFromDB(LoginActivity.this, "pass"));
			cbRememberPassWord.setChecked(true);
		}
//		edtEmail.setText(ShareMemManager.getInstance().readFromDB(getApplicationContext(), "user"));
//		edtPassWord.setText(ShareMemManager.getInstance().readFromDB(getApplicationContext(), "pass"));

		index = 0;

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	public void onClick(View v) {
		// nextToActivity(HomeActivity1.class, null, true);
		// if (1 == 1) return;

		switch (v.getId()) {
		case R.id.activity_login_bt_login: {

			userLogin(this);

		}
			break;
		case R.id.activity_login_bt_canel: {
			// userLogin(this, KeyConfig.EMAIL_DEFAULT + "_" + timeLogin(),
			// KeyConfig.PASSWORD_DEFAULT);
			finish();
		}
			break;

		}

	}

	ProgressDialog pd;

	private boolean userLogin(Activity activity, String... paramstInput) {
		String userEmail = null;
		String passWord = null;
		// if (edtEmail.getText().toString().equals("") ||
		// edtPassWord.getText().toString().equals("")) {
		// tvTitleActivity.setText("Bạn phải nhập đầy đủ thông tin để đăng
		// nhập");
		// }
		if (paramstInput.length == 0) {
			userEmail = edtEmail.getText().toString();
			passWord = edtPassWord.getText().toString();

		} else {
			userEmail = paramstInput[0];
			passWord = paramstInput[1];
		}

		if (checkDataInput(userEmail, passWord)) {

			Util.saveActionUser(getApplicationContext(), "LOGIN", (new Date()).getTime());
			formLodingData.setVisibility(View.GONE);
			// formLogin.setVisibility(View.VISIBLE);
			// String paramString =
			// "?data={\"ActionType\":\"LOGIN\",\"UserName\":\"" + userEmail +
			// "\",\"Password\":\""
			// + passWord + "\"}";
			String paramString = "login=" + userEmail + "|" + passWord + "";
			// call to server
			pd = new ProgressDialog(LoginActivity.this);
			pd.setCancelable(false);
			pd.setMessage("Đang thực hiện đăng nhập!");
			pd.show();
			ServiceManager.factoryData().getDataRaw(Util.SERVER_URL + paramString, "", DataOrder.ONLY_NETWORK, this);
			// showDialogConfirm();
			return true;
		} else {
			return false;
		}
	}

	private boolean checkDataInput(String userEmail, String passWord) {

		if (TextUtils.isEmpty(userEmail)) {
			edtEmail.setError("Bạn chưa điền tên đăng nhập");
			edtEmail.requestFocus();
			return false;
		}
		if (TextUtils.isEmpty(passWord)) {
			edtPassWord.setError("Bạn chưa điền mật khẩu");
			edtPassWord.requestFocus();
			return false;
		}

		return true;
	}

	private String getPassWork() {
		return edtPassWord.getText().toString();
	}

	private String getEmail() {
		return edtEmail.getText().toString();
	}

	/*
	 * public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	 * return userLogin(this, new String[] {}); }
	 */
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.activity_login_edt_email: {

		}
			break;
		case R.id.activity_login_edt_password: {
			// if (hasFocus) {
			// String email = edtEmail.getText().toString();
			// if (!TextUtils.isEmpty(email)) {
			// edtPassWord.setText(getPassWork());
			// }
			// }
		}
			break;
		}
	}

	@Override
	public void process(String key, int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processRaw(String key, final int status, final String json) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				if (status == vn.ce.sale.util.Util.ERROR_NETWORK) {
					pd.dismiss();
					tvTitleActivity.setVisibility(View.VISIBLE);
					tvTitleActivity.setText("Không có kết nối mạng,Bạn vui lòng kiểm tra lại");
					// showDialogConfirm();
					return;

				}
				if (status == 200) {
					formLodingData.setVisibility(View.GONE);
					JSONObject oObject;
					try {
						oObject = new JSONObject(json);
						ShareMemManager.getInstance().saveToDB(getApplicationContext(), "login", json);
						if (oObject.getString("ResponseCode").equalsIgnoreCase("00")) {
							tvTitleActivity
									.setText("Tên đăng nhập hoặc mật khẩu không đúng.Bạn vui lòng kiểm tra lại!");
							hideProgress();

						} else if (oObject.getString("ResponseCode").equalsIgnoreCase("01")) {
							if (ShareMemManager.getInstance().readFromDB(getApplicationContext(), "checkTimeLogin")
									.equals("")) {
								ShareMemManager.getInstance().saveToDB(getApplicationContext(), "checkTimeLogin",
										String.valueOf((new Date()).getTime()));
							}
							String timeOld = ShareMemManager.getInstance().readFromDB(getApplicationContext(),
									"checkTimeLogin");
							long timeNow = (new Date()).getTime();
							long t = timeNow - Long.parseLong(timeOld);
							if (timeNow - Long.parseLong(timeOld) >= 43200000) {
								Util.getActionUserToServer(getApplicationContext());
								ShareMemManager.getInstance().deleteFromDB(getApplicationContext(), "checkTimeLogin");
							}
							
							ShareMemManager.getInstance().saveToDB(LoginActivity.this, "useroff", getEmail());
							ShareMemManager.getInstance().saveToDB(LoginActivity.this, "passoff", getPassWork());
							ShareMemManager.getInstance().saveToDB(LoginActivity.this, "username", getEmail());
							ShareMemManager.getInstance().saveToDB(LoginActivity.this, "password", getPassWork());
							if (cbRememberPassWord.isChecked()) {
								ShareMemManager.getInstance().saveToDB(LoginActivity.this, "isRemember", "1");
								// to remember password
								ShareMemManager.getInstance().saveToDB(LoginActivity.this, "user", getEmail());
								ShareMemManager.getInstance().saveToDB(LoginActivity.this, "pass", getPassWork());

								// to request API
								ShareMemManager.getInstance().saveToDB(LoginActivity.this, "username", getEmail());
								ShareMemManager.getInstance().saveToDB(LoginActivity.this, "password", getPassWork());

							} else {
								// to remember password
								ShareMemManager.getInstance().saveToDB(LoginActivity.this, "isRemember", "0");
								ShareMemManager.getInstance().deleteFromDB(LoginActivity.this, "user");
								ShareMemManager.getInstance().deleteFromDB(LoginActivity.this, "pass");

								// delete request API
								// ShareMemManager.getInstance().deleteFromDB(LoginActivity.this,
								// "username");
								// ShareMemManager.getInstance().deleteFromDB(LoginActivity.this,
								// "password");
							}

							formLodingData.setVisibility(View.GONE);
							//createMenu(ShareMemManager.getInstance().readFromDB(LoginActivity.this, "username"), ShareMemManager.getInstance().readFromDB(LoginActivity.this, "password"), json);
							LoadData();
							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						tvTitleActivity.setText(e.toString());
					}

				}
			}

		});

	}

	private void createMenu(String name, String pass,final String j) {
		ServiceManager.factoryData().getDataRaw("http://192.168.10.214:9981/mobile/GetAllCatagory?username=" + name + "&password=" + pass + "&checksum=", "", DataOrder.NETWORK_THEN_CACHE, new ICallBackUI() {

					@Override
					public void processRaw(String key, final int status, final String json) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								// TODO Auto-generated method stub
								if (status == -1001) {
									tvTitleActivity.setVisibility(View.VISIBLE);
									tvTitleActivity.setText("Bạn vui lòng kiểm tra lại kết nối");
									return;

								}
								if (status == 200) {
									JSONObject oJson;
									List<JSONObject> lstJsonObjects = new ArrayList<JSONObject>();
									try {
										oJson = new JSONObject(json);

										String arrw = oJson.getString("ResponseMessage");
										lstJsonObjects = TransformDataManager
												.convertArrayToListJSON(new JSONArray(arrw));
										ShareMemManager.getInstance().saveToDB(getApplicationContext(), "CategoryName",
												lstJsonObjects.toString());
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

									LoadData();
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
	private void showDialogConfirm() {
		// TODO Auto-generated method stub
		AlertDialog.Builder b = new AlertDialog.Builder(LoginActivity.this);

		b.setMessage(
				"Không thể kết nối tới máy chủ, vui lòng kiểm tra lại hoặc chọn BÁN HÀNG để sử dụng chức năng bán hàng không có internet!");
		b.setPositiveButton("BÁN HÀNG", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();

				if (ShareMemManager.getInstance().readFromDB(LoginActivity.this, "useroff").equalsIgnoreCase(getEmail())
						&& ShareMemManager.getInstance().readFromDB(LoginActivity.this, "passoff")
								.equalsIgnoreCase(getPassWork())) {
					nextToActivity(Home.class,
							BundleData.createNew().putString("screen", Util.HOME).data(), true);

				} else {

					tvTitleActivity.setText("Tên đăng nhập hoặc mật khẩu không đúng,Bạn vui lòng kiểm tra lại!");

				}

			}
		});
		b.setNegativeButton("THỬ LẠI", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				userLogin(LoginActivity.this);
			}
		});
		b.create().show();
	}

	

	private void LoadData() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (true)
				{
					hideProgress();
					nextToActivity(HomeSwallow.class,
							BundleData.createNew().putString("screen", Util.HOME).data(), true);
				}

			}

		});

	}

	private void hideProgress() {
		// TODO Auto-generated method stub
		if (pd != null)
			pd.dismiss();
	}

	@Override
	public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
		// TODO Auto-generated method stub
		return false;
	}
}
