package vn.ce.sale;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.adapter.bee.BeeAutoAdapterSearchProduct;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.fragment.Fragment_Product_List;
import vn.ce.sale.fragment.airtimemix.FragmentConfirmPurchaseVtop;
import vn.ce.sale.fragment.airtimemix.FragmentPurchaseVtopBank;
import vn.ce.sale.fragment.airtimemix.FragmentPurchaseVtopSwallow;
import vn.ce.sale.fragment.airtimemix.FragmentVbill;
import vn.ce.sale.fragment.airtimemix.FragmentVtopup;
import vn.ce.sale.fragment.airtimemix.FragmentVtopupPurchase;
import vn.ce.sale.fragment.airtimemix.Fragment_Home_Air;
import vn.ce.sale.fragment.airtimemix.Fragment_Report_Vtopup_List;
import vn.ce.sale.fragment.airtimemix.Fragment_VTOPUP_ListTabViewer;
import vn.ce.sale.fragment.bee.BeeFragmentOrderProduct;
import vn.ce.sale.fragment.bee.BeeFragmentPayment;
import vn.ce.sale.fragment.bee.BeeFragmentReportOrderDetailProduct;
import vn.ce.sale.fragment.bee.BeeFragmentReportOrderProduct;
import vn.ce.sale.fragment.bee.Bee_Fragment_CashIn_Off_Swallow;
import vn.ce.sale.fragment.bee.Bee_Fragment_CashIn_Swallow;
import vn.ce.sale.fragment.bee.Bee_Fragment_CashOut_Swallow;
import vn.ce.sale.fragment.bee.Bee_Fragment_Category_Product_List;
import vn.ce.sale.fragment.bee.Bee_Fragment_Confirm_CashIn;
import vn.ce.sale.fragment.bee.Bee_Fragment_Confirm_CashIn_Off;
import vn.ce.sale.fragment.bee.Bee_Fragment_Confirm_CashOut_Swallow;
import vn.ce.sale.fragment.bee.Bee_Fragment_Confirm_Transfer_Swallow;
import vn.ce.sale.fragment.bee.Bee_Fragment_Home_Swallow;
import vn.ce.sale.fragment.bee.Bee_Fragment_Home_TabIcons;
import vn.ce.sale.fragment.bee.Bee_Fragment_Home_TabViewer;
import vn.ce.sale.fragment.bee.Bee_Fragment_Home_Vnpost;
import vn.ce.sale.fragment.bee.Bee_Fragment_Product_List;
import vn.ce.sale.fragment.bee.Bee_Fragment_Product_List_Detail;
import vn.ce.sale.fragment.bee.Bee_Fragment_Transfer_Swallow;
import vn.ce.sale.fragment.bee.Bee_Fragment_Transfer_Swallow2;
import vn.ce.sale.fragment.bee.Bee_Fragment_VAS_Swallow;
import vn.ce.sale.fragment.bee.Bee_Fragment_Vas_Postpaid;
import vn.ce.sale.fragment.bee.Bee_Fragment_Vas_Prepay;
import vn.ce.sale.fragment.bee.Bee_Fragment_Vas_Report;
import vn.ce.sale.fragment.bee.Bee_Fragment_Vas_Sodu;
import vn.ce.sale.fragment.bee.Bee_Fragment_Web_View;
import vn.ce.sale.fragment.bee.Bee_Fragment_Web_View2;
import vn.ce.sale.fragment.bee.Bee_Fragment_Web_View3;
import vn.ce.sale.fragment.vi21.FragmentChangeOrderProduct;
import vn.ce.sale.fragment.vi21.FragmentOrderCreate;
import vn.ce.sale.fragment.vi21.FragmentOrderProduct;
import vn.ce.sale.fragment.vi21.Fragment_Order_ListTabViewer;
import vn.ce.sale.fragment.vi21.Fragment_Report_Inventory_List;
import vn.ce.sale.fragment.vi21.Fragment_Report_List;
import vn.ce.sale.fragment.vi21.Fragment_Report_Sale_List;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.CIDManager;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackFragmentToActivity;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ZopostActivity;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("InflateParams")
public class Home extends ZopostActivity implements SearchView.OnQueryTextListener,
		NavigationDrawerFragment.NavigationDrawerCallbacks, ICallBackFragmentToActivity, OnClickListener {
	public static Date dtLast = null;
	public static Date dtLastBefore = null;
	public static String screenIdLast = null;
	public static String screenIdBefore = null;

	public static int i = 0;

	public static boolean isFirstTime = false;
	private NavigationDrawerFragment mNavigationDrawerFragment;

	boolean checkDialog = false;
	AutoCompleteTextView autext;
	// back
	private ZopostFragment fragMain;
	String balance;
	// auto update
	Long lastUpdateTime;
	LinearLayout lnlMenuHome;
	RelativeLayout layoutHomeMenu, layoutOrderMenu, layoutOrderListMenu, layoutPaymentMenu, layoutReportMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_register1);
		lnlMenuHome = (LinearLayout) findViewById(R.id.lnlMenuHome);
		layoutHomeMenu = (RelativeLayout) findViewById(R.id.layoutHomeMenu);
		layoutOrderMenu = (RelativeLayout) findViewById(R.id.layoutOrderMenu);
		layoutOrderListMenu = (RelativeLayout) findViewById(R.id.layoutOrderListMenu);
		layoutPaymentMenu = (RelativeLayout) findViewById(R.id.layoutPaymentMenu);
		layoutReportMenu = (RelativeLayout) findViewById(R.id.layoutReportMenu);
		layoutHomeMenu.setOnClickListener(this);
		layoutOrderMenu.setOnClickListener(this);
		layoutOrderListMenu.setOnClickListener(this);
		layoutPaymentMenu.setOnClickListener(this);
		layoutReportMenu.setOnClickListener(this);
		init_UI(this);
		// if (Util.checkMenuHome2==true) {
		// lnlMenuHome.setVisibility(View.GONE);
		// autext.setVisibility(View.GONE);
		// Util.checkMenuHome2=false;
		// }
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.layoutHomeMenu:
			autext.setVisibility(View.VISIBLE);
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_HOME_TAB).data());
			break;
		case R.id.layoutOrderMenu:
			Util.checkMenuHome = true;
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_ORDER_ECOM).data());
			break;
		case R.id.layoutOrderListMenu:
			Util.checkMenuHome = true;
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_REPORT_ECOM).data());
			break;
		case R.id.layoutPaymentMenu:
			Util.checkMenuHome = true;
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_PAYMENT_ECOM).data());
			break;
		case R.id.layoutReportMenu:
			Util.checkMenuHome = true;
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_REPORT_ECOM).data());
			break;

		default:
			break;
		}
	}

	private void setupMenuNavigatorDrawer() {

		// TODO Auto-generated method stub
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

	}

	@SuppressWarnings("deprecation")
	private void init_UI(Activity activity) {
		// getBalance();
		pay();
		Util.saveActionUser(getApplicationContext(), "HOME-PAGE", (new Date()).getTime());
		// startRegisterCID();
		ColorDrawable colorDrawable = new ColorDrawable();
		int color = getResources().getColor(R.color.d_color_blue3);
		colorDrawable.setColor(color);
		getSupportActionBar().setBackgroundDrawable(colorDrawable);
		getSupportActionBar().setHomeButtonEnabled(true);

		ActionBar mActionBar = getSupportActionBar();
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);
		LayoutInflater mInflater = LayoutInflater.from(this);
		View mCustomView = mInflater.inflate(R.layout.bee_custom_actionbar, null);
		autext = (AutoCompleteTextView) mCustomView.findViewById(R.id.auSearch);
		// autext.setDropDownBackgroundDrawable(new
		// ColorDrawable(this.getResources().getColor(R.color.blue)));
		ImageView imvUser = (ImageView) mCustomView.findViewById(R.id.userAction);
		imvUser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dialog dialog = new Dialog(Home.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// dialog.setCancelable(false);
				dialog.setContentView(R.layout.bee_custom_dialog_home_tab);
				dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation_user_top;
				TextView txtAccountSwallow = (TextView) dialog.findViewById(R.id.txtAccountSwallow);
				// txtAccountSwallow.setText(balance);
				dialog.show();

			}
		});
		autoSearch();
		autext.setOnKeyListener(new View.OnKeyListener() {
			@SuppressWarnings("static-access")
			@Override
			public boolean onKey(View view, int key, KeyEvent keyEvent) {
				if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (key == KeyEvent.KEYCODE_ENTER)) {
					if (autext.getText().toString().equals("")) {
						Toast.makeText(getApplicationContext(), "Bạn phải nhập tên sản phẩm", Toast.LENGTH_LONG).show();
					} else {
						Util.checkSearch = true;
						FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
						ft.replace(R.id.container,
								new Bee_Fragment_Product_List().newInstance(autext.getText().toString(), ""));
						ft.addToBackStack("tag");
						ft.commit();
					}
				}
				return false;
			}
		});
		mActionBar.setCustomView(mCustomView);
		mActionBar.setDisplayShowCustomEnabled(true);

		setupMenuNavigatorDrawer();
		showNavigate(getParams());

		/**/
	}

	private void getBalance() {
		String user = ShareMemManager.getInstance().readFromDB(getApplicationContext(), "username");
		String params = "API/GetBalance?username=" + user;
		final ProgressDialog pd = new ProgressDialog(getApplicationContext());
		pd.setMessage("Đang gửi dữ liệu xác nhận!");
		pd.show();
		ServiceManager.factoryData().getDataRaw("http://192.168.10.214:1234/", params, new ICallBackUI() {

			@Override
			public void processRaw(String key, final int statusx, final String json) {
				runOnUiThread(new Runnable() {

					@SuppressWarnings("unused")
					@Override
					public void run() {
						// TODO Auto-generated method stub
						int status = statusx;
						// TODO Auto-generated method stub
						if (status == Util.ERROR_NETWORK) {
							pd.dismiss();
							// txtThongBao.setVisibility(View.VISIBLE);
							// txtThongBao.setText("Vui lòng kiểm tra lại kết
							// nối.");
						}
						// phân tích để tiếp tục
						if (status == 200) {
							pd.dismiss();
							try {
								JSONObject o = new JSONObject(json);
								if (o.getString("code").equalsIgnoreCase("00")) {
									// txtThongBao.setVisibility(View.VISIBLE);
									// txtThongBao.setText(o.getString("message"));
								}
								if (o.getString("code").equalsIgnoreCase("01")) {
									// txtThongBao.setVisibility(View.GONE);
									// txtPurchaseVtop.setText(o.getString("message"));
									balance = o.getString("data");
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

	private void autoSearch() {

		try {
			ArrayList<JSONObject> lJson = new ArrayList<JSONObject>();
			String arr = ShareMemManager.getInstance().readFromDB(getApplicationContext(), "ProductBee");
			JSONArray array = new JSONArray(arr);
			lJson = (ArrayList<JSONObject>) TransformDataManager.convertArrayToListJSON(array);
			ShareMemManager.getInstance().saveToDB(getApplicationContext(), "ProductBee", lJson.toString());
			BeeAutoAdapterSearchProduct adapter = new BeeAutoAdapterSearchProduct(getApplicationContext(), lJson,
					R.layout.layout_auto_list_row);
			autext.setThreshold(1);
			autext.setAdapter(adapter);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// TODO Auto-generated method stub

	}

	private void pay() {
		ServiceManager.factoryData()
				.getDataRaw("http://192.168.10.214:9981/mobile/GetAllProduct?categoryid=0&username="
						+ ShareMemManager.getInstance().readFromDB(getApplicationContext(), "username") + "&password="
						+ ShareMemManager.getInstance().readFromDB(getApplicationContext(), "password") + "\"}", "",
				DataOrder.NETWORK_THEN_CACHE, new ICallBackUI() {

					@Override
					public void processRaw(String key, final int status, final String json) {
						// TODO Auto-generated method stub
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (status == -1001) {
									return;
								}
								if (status == 200) {
									JSONObject object;
									ArrayList<JSONObject> lst = new ArrayList<JSONObject>();
									try {
										object = new JSONObject(json);
										JSONArray array = new JSONArray(object.getString("ResponseMessage"));
										lst = (ArrayList<JSONObject>) TransformDataManager
												.convertArrayToListJSON(array);
										ShareMemManager.getInstance().saveToDB(getApplicationContext(), "ProductBee",
												lst.toString());
										BeeAutoAdapterSearchProduct adapter = new BeeAutoAdapterSearchProduct(
												getApplicationContext(), lst, R.layout.layout_auto_list_row);
										autext.setThreshold(1);
										autext.setAdapter(adapter);
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

									// TODO Auto-generated method stub

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
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;

	}

	@Override
	public void onParamsFromFragment(Bundle data) {
		showNavigate(data);
		// if (ShareMemManager.getInstance().readFromDB(getApplicationContext(),
		// "pageview")!=null) {
		// if (ShareMemManager.getInstance().readFromDB(getApplicationContext(),
		// "pageview").equals("a")) {
		// autext.setVisibility(View.VISIBLE);
		// lnlMenuHome.setVisibility(View.VISIBLE);
		// }
		//
		// }
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (fragMain instanceof Bee_Fragment_Product_List) {
			autext.setVisibility(View.VISIBLE);
			lnlMenuHome.setVisibility(View.VISIBLE);
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_HOME_TAB).data());

		} 
		else if (fragMain instanceof Bee_Fragment_Confirm_Transfer_Swallow) {
			autext.setVisibility(View.GONE);
			lnlMenuHome.setVisibility(View.GONE);
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_TRANSFER_SWALLOW).data());

		} 
		else if (fragMain instanceof FragmentConfirmPurchaseVtop) {
			autext.setVisibility(View.GONE);
			lnlMenuHome.setVisibility(View.GONE);
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_AIR_PURCHASE_VTOP_BANK).data());

		} 
		else if (fragMain instanceof BeeFragmentOrderProduct) {
			autext.setVisibility(View.VISIBLE);
			lnlMenuHome.setVisibility(View.VISIBLE);
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_HOME_TAB).data());

		} else if (fragMain instanceof BeeFragmentReportOrderProduct) {
			autext.setVisibility(View.VISIBLE);
			lnlMenuHome.setVisibility(View.VISIBLE);
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_HOME_TAB).data());

		} else if (fragMain instanceof Bee_Fragment_Product_List_Detail) {
			autext.setVisibility(View.VISIBLE);
			lnlMenuHome.setVisibility(View.VISIBLE);
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_HOME_TAB).data());

		} else if (fragMain instanceof FragmentPurchaseVtopSwallow) {
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_AIR_PURCHASE_VTOP).data());

		} else if (fragMain instanceof FragmentPurchaseVtopBank) {
			// Util.checkSwallowPager = true;
			autext.setVisibility(View.VISIBLE);
			lnlMenuHome.setVisibility(View.VISIBLE);
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_VAS_SWALLOW).data());

		} else if (fragMain instanceof FragmentVtopup) {
			Util.checkViewPager = true;
			autext.setVisibility(View.VISIBLE);
			lnlMenuHome.setVisibility(View.VISIBLE);
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_HOME_TAB).data());

		} else if (fragMain instanceof FragmentVbill) {
			Util.checkViewPager = true;
			autext.setVisibility(View.VISIBLE);
			lnlMenuHome.setVisibility(View.VISIBLE);
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_HOME_TAB).data());

		} else if (fragMain instanceof Fragment_Report_Vtopup_List) {
			// Util.checkViewPager = true;
			autext.setVisibility(View.VISIBLE);
			lnlMenuHome.setVisibility(View.VISIBLE);
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_HOME_TAB).data());

		} else if (fragMain instanceof FragmentVtopupPurchase) {
			Util.checkSwallowPager = true;
			autext.setVisibility(View.VISIBLE);
			lnlMenuHome.setVisibility(View.VISIBLE);
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_VAS_SWALLOW).data());
		} else if (fragMain instanceof BeeFragmentReportOrderDetailProduct) {
			Util.checkViewPager = true;
			// autext.setVisibility(View.VISIBLE);
			// lnlMenuHome.setVisibility(View.VISIBLE);
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_REPORT_ECOM).data());
		} else if (fragMain instanceof Bee_Fragment_CashIn_Off_Swallow || fragMain instanceof Bee_Fragment_Vas_Sodu
				|| fragMain instanceof Bee_Fragment_Web_View || fragMain instanceof Bee_Fragment_CashIn_Swallow
				|| fragMain instanceof Bee_Fragment_CashOut_Swallow || fragMain instanceof Bee_Fragment_Transfer_Swallow
				|| fragMain instanceof Bee_Fragment_VAS_Swallow) {
			Util.checkSwallowPager = true;
			autext.setVisibility(View.VISIBLE);
			lnlMenuHome.setVisibility(View.VISIBLE);
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_HOME_TAB).data());

		} else if (fragMain instanceof Bee_Fragment_Confirm_CashIn_Off) {
			autext.setVisibility(View.GONE);
			lnlMenuHome.setVisibility(View.GONE);
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_CASHIN_OFF_SWALLOW).data());

		} else if (fragMain instanceof Bee_Fragment_Confirm_CashIn) {
			autext.setVisibility(View.GONE);
			lnlMenuHome.setVisibility(View.GONE);
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_CASHIN_SWALLOW).data());

		} else if (fragMain instanceof Bee_Fragment_Confirm_CashOut_Swallow) {
			autext.setVisibility(View.GONE);
			lnlMenuHome.setVisibility(View.GONE);
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_CASHOUT_SWALLOW).data());

		} else if (fragMain instanceof Bee_Fragment_Web_View3 || fragMain instanceof Bee_Fragment_Web_View2
				|| fragMain instanceof FragmentPurchaseVtopBank || fragMain instanceof Bee_Fragment_Vas_Prepay
				|| fragMain instanceof Bee_Fragment_Vas_Postpaid || fragMain instanceof Bee_Fragment_Vas_Report) {
			autext.setVisibility(View.VISIBLE);
			lnlMenuHome.setVisibility(View.VISIBLE);
			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_VAS_SWALLOW).data());

		} else {
			AlertDialog.Builder b = new AlertDialog.Builder(Home.this);
			b.setCancelable(false);
			b.setMessage("Bạn có muốn thoát chương trình?");
			b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
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
		// super.onBackPressed();
	}

	@SuppressWarnings("static-access")
	public void onNavigationDrawerItemSelected(int position) {
		// TODO Auto-generated method stub
		if (position == 0) {
			if (Util.checkMenuHome == true) {
				if (autext != null && lnlMenuHome != null) {
					autext.setVisibility(View.VISIBLE);
					lnlMenuHome.setVisibility(View.VISIBLE);
					Util.checkMenuHome = false;
				}
			}

			showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_HOME_TAB).data());
		} else {
			autext.setVisibility(View.VISIBLE);
			lnlMenuHome.setVisibility(View.VISIBLE);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.container, new Bee_Fragment_Product_List().newInstance("a", "b"));
			ft.addToBackStack("tag");
			ft.commit();
		}

	}

	@SuppressWarnings("unused")
	private void thoat() {
		// TODO Auto-generated method stub
		AlertDialog.Builder b = new AlertDialog.Builder(Home.this);
		b.setCancelable(false);
		b.setMessage("Bạn có muốn thoát chương trình?");
		b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
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
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		handleIntent(intent);
	}

	@Override
	protected void onResume() {
		// Toast.makeText(this,"OnReums", Toast.LENGTH_LONG).show();
		super.onResume();
		// showNavigate(getParams());
	}

	@SuppressWarnings("unused")
	@SuppressLint("NewApi")
	private void showNavigate(Bundle bx) {
		i++;
		// getSupportActionBar().setTitle(String.valueOf(i)+" times");
		if (bx == null)
			bx = new Bundle();
		String screenString = bx.getString("screen", "-1");

		if (i % 2 == 0)
			dtLast = new Date();
		if (i % 2 == 1)
			dtLastBefore = new Date();

		if (screenIdLast != null && screenIdBefore != null) {
			screenIdBefore = screenIdLast;
			screenIdLast = screenString;
		}
		if (screenIdLast == null)
			screenIdLast = screenString;
		if (screenIdBefore == null)
			screenIdBefore = screenString;

		// getSupportActionBar().setTitle(getSupportActionBar().getTitle()+":"+screenString);
		if (screenString.equalsIgnoreCase("-1"))
			return;

		if (dtLast != null && dtLastBefore != null) {
			if (Math.abs((dtLast.getTime() - dtLastBefore.getTime())) < 100
					&& (!screenIdBefore.equalsIgnoreCase("-1") && !screenIdBefore.equalsIgnoreCase("home"))) {
				return;
			}
		}
		boolean check = false;
		// if (screenString.equalsIgnoreCase("ccc")) {
		// lnlMenuHome.setVisibility(View.GONE);
		// autext.setVisibility(View.GONE);
		// } else if (screenString.equalsIgnoreCase("aaa")) {
		// lnlMenuHome.setVisibility(View.VISIBLE);
		// autext.setVisibility(View.VISIBLE);
		// } else
		if (screenString.equalsIgnoreCase(Util.SCREEN_SETTING)) {
			Util.saveActionUser(getApplicationContext(), "SETTING", (new Date()).getTime());
			nextToActivity(SettingsActivity.class, null);
		}
		else if (screenString.equalsIgnoreCase(Util.SCREEN_CONFIRM_TRANSFER)) {
			getSupportActionBar().show();
			Util.checkMenuHome = true;
			lnlMenuHome.setVisibility(View.GONE);
			autext.setVisibility(View.GONE);
			fragMain = new Bee_Fragment_Confirm_Transfer_Swallow();
			replaceFragment(fragMain, bx, R.id.container, true);
		} 
		else if (screenString.equalsIgnoreCase(Util.SCREEN_Web_View)) {
			getSupportActionBar().show();
			Util.checkMenuHome = true;
			lnlMenuHome.setVisibility(View.GONE);
			autext.setVisibility(View.GONE);
			fragMain = new Bee_Fragment_Web_View();
			replaceFragment(fragMain, bx, R.id.container, true);
		} 
		else if (screenString.equalsIgnoreCase(Util.SCREEN_CASHIN_SWALLOW)) {
			getSupportActionBar().show();
			Util.checkMenuHome = true;
			lnlMenuHome.setVisibility(View.GONE);
			autext.setVisibility(View.GONE);
			fragMain = new Bee_Fragment_CashIn_Swallow();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_CASHIN_OFF_SWALLOW)) {
			getSupportActionBar().show();
			Util.checkMenuHome = true;
			lnlMenuHome.setVisibility(View.GONE);
			autext.setVisibility(View.GONE);
			fragMain = new Bee_Fragment_CashIn_Off_Swallow();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_CASHOUT_SWALLOW)) {
			getSupportActionBar().show();
			Util.checkMenuHome = true;
			lnlMenuHome.setVisibility(View.GONE);
			autext.setVisibility(View.GONE);
			fragMain = new Bee_Fragment_CashOut_Swallow();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_CONFIRM_CASHOUT)) {
			getSupportActionBar().show();
			Util.checkMenuHome = true;
			lnlMenuHome.setVisibility(View.GONE);
			autext.setVisibility(View.GONE);
			fragMain = new Bee_Fragment_Confirm_CashOut_Swallow();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_TRANSFER_SWALLOW)) {
			getSupportActionBar().show();
			Util.checkMenuHome = true;
			lnlMenuHome.setVisibility(View.GONE);
			autext.setVisibility(View.GONE);
			fragMain = new Bee_Fragment_Transfer_Swallow();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_TRANSFER_SWALLOW2)) {
			getSupportActionBar().show();
			Util.checkMenuHome = true;
			lnlMenuHome.setVisibility(View.GONE);
			autext.setVisibility(View.GONE);
			fragMain = new Bee_Fragment_Transfer_Swallow2();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_VAS_SWALLOW)) {
			getSupportActionBar().show();
			Util.checkMenuHome = true;
			lnlMenuHome.setVisibility(View.GONE);
			autext.setVisibility(View.GONE);
			fragMain = new Bee_Fragment_VAS_Swallow();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_VAS_Prepay)) {
			getSupportActionBar().show();
			Util.checkMenuHome = true;
			lnlMenuHome.setVisibility(View.GONE);
			autext.setVisibility(View.GONE);
			fragMain = new Bee_Fragment_Vas_Prepay();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_VAS_POSTPAID)) {
			getSupportActionBar().show();
			Util.checkMenuHome = true;
			lnlMenuHome.setVisibility(View.GONE);
			autext.setVisibility(View.GONE);
			fragMain = new Bee_Fragment_Vas_Postpaid();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_VAS_REPORT)) {
			getSupportActionBar().show();
			Util.checkMenuHome = true;
			lnlMenuHome.setVisibility(View.GONE);
			autext.setVisibility(View.GONE);
			fragMain = new Bee_Fragment_Vas_Report();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_VAS_SODU)) {
			getSupportActionBar().show();
			Util.checkMenuHome = true;
			lnlMenuHome.setVisibility(View.GONE);
			autext.setVisibility(View.GONE);
			fragMain = new Bee_Fragment_Vas_Sodu();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_CONFIRM_CASHIN)) {
			getSupportActionBar().show();
			lnlMenuHome.setVisibility(View.GONE);
			autext.setVisibility(View.GONE);
			fragMain = new Bee_Fragment_Confirm_CashIn();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_CONFIRM_CASHIN_OFF)) {
			getSupportActionBar().show();
			lnlMenuHome.setVisibility(View.GONE);
			autext.setVisibility(View.GONE);
			fragMain = new Bee_Fragment_Confirm_CashIn_Off();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_REPORT_LIST)) {
			getSupportActionBar().hide();
			fragMain = new Fragment_Report_List();
			replaceFragment(fragMain, bx, R.id.container, true);
		}

		else if (screenString.equalsIgnoreCase(Util.SCREEN_VTOPUP_TAB)) {
			getSupportActionBar().show();
			fragMain = new Fragment_VTOPUP_ListTabViewer();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_VTOPUP)) {
			getSupportActionBar().show();
			lnlMenuHome.setVisibility(View.GONE);
			autext.setVisibility(View.GONE);
			fragMain = new FragmentVtopup();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_VBILL)) {
			getSupportActionBar().show();
			autext.setVisibility(View.GONE);
			lnlMenuHome.setVisibility(View.GONE);
			fragMain = new FragmentVbill();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_AIRTIMEMIX)) {
			getSupportActionBar().show();
			autext.setVisibility(View.GONE);
			lnlMenuHome.setVisibility(View.GONE);
			fragMain = new Fragment_Report_Vtopup_List();
			replaceFragment(fragMain, bx, R.id.container, true);
		}
		// else if
		// (screenString.equalsIgnoreCase(Util.SCREEN_AIR_PURCHASE_VTOP)) {
		// getSupportActionBar().show();
		// autext.setVisibility(View.GONE);
		// lnlMenuHome.setVisibility(View.GONE);
		// fragMain = new FragmentVtopupPurchase();
		// replaceFragment(fragMain, bx, R.id.container, true);
		// }
		else if (screenString.equalsIgnoreCase(Util.SCREEN_AIR_PURCHASE_VTOP_BANK)) {
			getSupportActionBar().show();
			autext.setVisibility(View.GONE);
			lnlMenuHome.setVisibility(View.GONE);
			fragMain = new FragmentPurchaseVtopBank();
			replaceFragment(fragMain, bx, R.id.container, true);
		}
		// else if
		// (screenString.equalsIgnoreCase(Util.SCREEN_AIR_PURCHASE_VTOP_SWALLOW))
		// {
		// getSupportActionBar().show();
		// autext.setVisibility(View.GONE);
		// lnlMenuHome.setVisibility(View.GONE);
		// fragMain = new FragmentPurchaseVtopSwallow();
		// replaceFragment(fragMain, bx, R.id.container, true);
		// }
		else if (screenString.equalsIgnoreCase(Util.SCREEN_ALL_PRODUCT_ECOM)) {
			getSupportActionBar().show();
			lnlMenuHome.setVisibility(View.VISIBLE);
			autext.setVisibility(View.VISIBLE);
			fragMain = new Bee_Fragment_Category_Product_List();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_PRODUCT_ECOM)) {
			getSupportActionBar().show();
			fragMain = new Bee_Fragment_Product_List();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_ORDER_ECOM)) {
			getSupportActionBar().show();
			autext.setVisibility(View.GONE);
			fragMain = new BeeFragmentOrderProduct();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_PAYMENT_ECOM)) {
			getSupportActionBar().show();
			autext.setVisibility(View.GONE);
			fragMain = new BeeFragmentPayment();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_REPORT_ECOM)) {
			getSupportActionBar().show();
			autext.setVisibility(View.GONE);
			fragMain = new BeeFragmentReportOrderProduct();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_CHANGE_ORDER_PORDUCT)) {
			// getSupportActionBar().hide();
			fragMain = new FragmentChangeOrderProduct();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_REPORT_INVENTORY_LIST)) {
			getSupportActionBar().show();
			fragMain = new Fragment_Report_Inventory_List();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_REPORT_SALE_LIST)) {
			getSupportActionBar().show();
			fragMain = new Fragment_Report_Sale_List();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_PRODUCT)) {

			getSupportActionBar().show();
			autext.setVisibility(View.VISIBLE);
			lnlMenuHome.setVisibility(View.VISIBLE);
			fragMain = new Fragment_Product_List();
			replaceFragment(fragMain, bx, R.id.container, true);
		}

		else if (screenString.equalsIgnoreCase(Util.SCREEN_ORDER)) {

			getSupportActionBar().show();
			fragMain = new FragmentOrderProduct();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_ORDER_CREATE)) {
			getSupportActionBar().show();
			fragMain = new FragmentOrderCreate();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_ORDER_LIST)) {

			getSupportActionBar().show();
			fragMain = new Fragment_Order_ListTabViewer();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_HOME_AIR)) {
			getSupportActionBar().show();
			autext.setVisibility(View.GONE);
			lnlMenuHome.setVisibility(View.GONE);
			fragMain = new Fragment_Home_Air();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_HOME_VNPOST)) {
			getSupportActionBar().show();
			autext.setVisibility(View.GONE);
			lnlMenuHome.setVisibility(View.GONE);
			fragMain = new Bee_Fragment_Home_Vnpost();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_HOME_SWALLOW)) {

			getSupportActionBar().show();
			lnlMenuHome.setVisibility(View.GONE);
			autext.setVisibility(View.GONE);
			fragMain = new Bee_Fragment_Home_Swallow();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_HOME_TAB)) {
			getSupportActionBar().show();
			// autext.setVisibility(View.VISIBLE);
			// lnlMenuHome.setVisibility(View.VISIBLE);
			fragMain = new Bee_Fragment_Home_TabIcons();
			replaceFragment(fragMain, bx, R.id.container, true);
		}
		// else {
		// getSupportActionBar().show();
		// autext.setVisibility(View.VISIBLE);
		// lnlMenuHome.setVisibility(View.VISIBLE);
		// fragMain = new Bee_Fragment_Home_TabViewer();
		// replaceFragment(fragMain, bx, R.id.container, true);
		//
		// }
		// getSupportActionBar().setTitle(getSupportActionBar().getTitle()+">>"+screenString);
	}

	private void handleIntent(Intent intent) {
		// Toast.makeText(this,"Single top intent", Toast.LENGTH_LONG).show();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			((ICallBackActivityToFragment) fragMain)
					.onParamsFromActivity(BundleData.createNew().putString("object", "" + query).data());
		} else
			showNavigate(getParams());
	}

	@Override
	public boolean onQueryTextChange(String arg0) {

		if (arg0 == null)
			return false;
		if (arg0.length() < 3) {
			((ICallBackActivityToFragment) fragMain)
					.onParamsFromActivity(BundleData.createNew().putString("object", "").data());
		}
		if (arg0.length() >= 3) {
			((ICallBackActivityToFragment) fragMain)
					.onParamsFromActivity(BundleData.createNew().putString("object", "" + arg0).data());
		}
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public static int REQUESTCODE_PICTURE = 1001;
	public static int REQUESTCODE_QRCODE = 1002;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// No call for super(). Bug on API Level > 11.
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Checks the orientation of the screen
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// setContentView(R.layout.homescreen1);
			// setupButton();
			Toast.makeText(this, "loading in landscape", Toast.LENGTH_SHORT).show();
			// replaceFragment(fragMain, getParams(), R.id.container);
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			Toast.makeText(this, "loading in portrait", Toast.LENGTH_SHORT).show();
			// setContentView(R.layout.homescreen2);
			// setupButton();
			// replaceFragment(fragMain, getParams(), R.id.container);
		}

	}

	@SuppressWarnings("unused")
	private void makeDirTempInSdCard() {
		String[] str = { "mkdir", Util.resourceDir };
		try {
			Process ps = Runtime.getRuntime().exec(str);
			try {
				ps.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private void startRegisterCID() {
		Util.activeKey = ShareMemManager.getInstance().readIMEI(getApplicationContext());
		android.telephony.TelephonyManager tg = (android.telephony.TelephonyManager) getSystemService(
				Context.TELEPHONY_SERVICE);
		tg.listen(new CIDManager(tg), CIDManager.EVENTS);
	}

	@Override
	public void setSelectedFragment(ZopostFragment selectedFragment) {
		// TODO Auto-generated method stub
		this.fragMain = selectedFragment;

	}

}
