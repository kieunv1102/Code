package vn.ce.sale.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.data.DataType;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.fragment.bee.Bee_Fragment_Product_List;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.R;
import android.R.bool;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import java.lang.String;

public final class Util {

	public static final String SERVER_URL = "http://uat.zopost.vn:6690/?";
	// public static final String SERVER_URL = "http://test.btcom.vn:6690/?"; //
	// UAT
	// public static final String SERVER_URL = "http://btcom.vn:6868/?";//REAL
	// WORLD
	public static final String IMAGE_URL = "http://www.hungviethmp.com.vn/userupload/products/21122011165820937.png";
	public static final String HOME = "homeapp";
	public static final String SCREEN_HOME_BEE = "homebee";
	public static final String SCREEN_HOME_TAB = "hometab";
	public static final String SCREEN_HOME = "home";
	public static final String SCREEN_HOME1 = "home1";
	public static final String SCREEN_ORDER = "order";
	public static final String SCREEN_SETTING = "11";
	public static final String SCREEN_QRCODE = "9";
	public static final String SCREEN_ORDER_LIST = "9";
	public static final String SCREEN_REPORT_LIST = "17";
	public static final String SCREEN_REPORT_INVENTORY_LIST = "15";
	public static final String SCREEN_REPORT_SALE_LIST = "16";
	public static final String SCREEN_CHANGE_ORDER_PORDUCT = "20";
	public static final String SCREEN_VTOPUP = "21";
	public static final String SCREEN_VBILL = "22";
	public static final String SCREEN_VTOPUP_TAB = "23";
	public static final String SCREEN_AIRTIMEMIX = "24";
	public static final String SCREEN_PRODUCT_ECOM = "25";
	public static final String SCREEN_ORDER_ECOM = "26";
	public static final String SCREEN_REPORT_ECOM = "27";
	public static final String SCREEN_ALL_PRODUCT_ECOM = "28";
	public static final String SCREEN_HOME_AIR = "29";
	public static final String SCREEN_HOME_VNPOST = "30";
	public static final String SCREEN_HOME_SWALLOW = "31";
	public static final String SCREEN_AIR_PURCHASE_VTOP = "32";
	public static final String SCREEN_AIR_PURCHASE_VTOP_BANK = "33";
	public static final String SCREEN_AIR_PURCHASE_VTOP_SWALLOW = "34";
	public static final String SCREEN_CASHIN_SWALLOW = "35";
	public static final String SCREEN_CASHOUT_SWALLOW = "36";
	public static final String SCREEN_TRANSFER_SWALLOW = "37";
	public static final String SCREEN_VAS_SWALLOW = "38";
	public static final String SCREEN_PAYMENT_ECOM = "39";
	public static final String SCREEN_CONFIRM_CASHIN = "40";
	public static final String SCREEN_TRANSFER_SWALLOW2 = "41";
	public static final String SCREEN_VAS_Prepay = "42";
	public static final String SCREEN_VAS_POSTPAID = "43";
	public static final String SCREEN_VAS_REPORT = "44";
	public static final String SCREEN_CASHIN_OFF_SWALLOW = "45";
	public static final String SCREEN_CONFIRM_CASHIN_OFF = "46";
	public static final String SCREEN_VAS_SODU = "47";
	public static final String SCREEN_Web_View = "48";
	public static final String SCREEN_CONFIRM_CASHOUT = "49";
	public static final String SCREEN_CONFIRM_TRANSFER = "50";
	public static final String SCREEN_QRCODE_SCAN = "qrcode";
	public static final String SCREEN_KIEMHANG = "10";

	public static final String SCREEN_OFFLINE = "8";

	public static final String SCREEN_LOCATION = "12";
	public static final String SCREEN_LOCATION_EDIT = "save";

	public static final int ERROR_NETWORK = -1001;// vn.zopost.sale.util.Util.ERROR_NETWORK;
	public static final int OK_NETWORK = 200;
	public static final int NOTIFY_NETWORK = 1001;

	public static final String SCREEN_PRODUCT = "PRODUCT";
	public static final String SCREEN_ORDER_CREATE = "ORDER_CREATE";

	public static final int ORDER_FAILED = 2;
	public static final int ORDER_OK = 1;
	public static final int ORDER_OFFLINE = 0;

	public static final String SCREEN_ORDER_CREATE_VIEW = "PURCHASE_VIEW";

	public static String memberid = "0";
	public static String companyid = "26";
	public static String fullNamex = "";
	public static String companyName = "";
	public static String placeid = "0";

	public static String resourceDir = FileUtil.DIR_IN_SDCARD;

	public static String resolutionPicture = "0x0";

	public static int sizePictureWidth = 0;
	public static int sizePictureHeight = 0;

	public static int methodx = 1;

	public static boolean checkSearchReport = false;
	public static boolean checkReport = false;
	public static boolean autoUpload = false;
	public static boolean modeOffline = false;
	public static boolean checkOffline = false;
	public static boolean checkSaveOffline = false;
	public static boolean checkOrderOnline = false;
	public static boolean checkPage = false;
	public static boolean checkPage2 = false;
	public static boolean checkChangeOrderProduct = false;
	public static boolean checkMenuHome = false;
	public static boolean checkMenuHome2 = false;
	public static boolean checkViewPager = false;
	public static boolean checkSwallowPager = false;
	public static boolean checkAirPager = false;
	public static boolean checkSearch = false;
	public static boolean checkThanhtoan = false;

	public static int partStreamingPref = 5;

	// check Ecommerce
	public static boolean checkBanner = false;
	public static boolean checkItemCate = false;

	// config video
	public static String resolutionVideo = "0x0";
	public static int sizeVideoWidth = 320;
	public static int sizeVideoHeight = 240;

	public static int videoSource = 1;
	public static int audioSource = 1;

	public static int outputFormat = 2;

	public static int videoEncoder = 2;
	public static int audioEncoder = 3;

	public static int videoBitRate = 0;

	public static String formatStreaming = "mp4"; // extension file
	public static int frameRate = 0;

	public static String data = "";
	public static String email = "";
	public static String typeid = "-1";
	public static String fileNameCurrent;
	public static Date timeStarted = new Date();

	public static Date time = new Date();
	public static String typegame = "erp";
	public static boolean isHaveGps = false;

	// for cid util
	public static boolean isNewDataCID;
	public static String code_mnc;
	public static String code_mmc;
	public static String code_lac;
	public static String code_cellid;

	private static String typeConnect = "";

	private static String vesrsion = "1.0";

	private static String speedConnect = "";

	private static String stringCANDC = "";

	private static String GPS_NETWORK;

	private static String debug = "";

	private static String speed = "";

	private static String levelBattery = "0";

	private static int savepin = 1;

	private static int isAutoLogin = 0;

	private static int isFirstRun = 0;

	private static int liveaudio = 0;

	public static String activeKey = "";

	private static String GPS_SAT = "";

	public static String lat = "";
	private static String alt = "";
	private static String lon = "";

	private static String accuracy = "0";
	private static String course = "";

	public static String KEY_LAST_ICD = "LAST_CID_DATA";

	public static String fromHashToQueryString(HashMap<String, String> params) {
		// TODO Auto-generated method stub
		if (params == null)
			return "";
		StringBuilder sb = new StringBuilder();

		for (String keyParams : params.keySet()) {
			sb.append(keyParams + "=" + params.get(keyParams).toString() + "&");
		}
		String paramRequests = sb.toString();
		return paramRequests;
	}

	public static String TrimVietnameseMark(String str) {
		str = str.replace(".", " ");
		str = str.replace(",", " ");
		str = str.replace("_", " ");
		str = str.replace(";", " ");
		str = str.replace("?", " ");
		str = str.replace("(", "");
		str = str.replace(")", "");
		str = str.replace("[", "");
		str = str.replace("]", "");
		str = str.replace("{", "");
		str = str.replace("}", "");
		str = str.replace("<", "");
		str = str.replace(">", "");
		str = str.replace("'", "");

		// while (str.indexOf(" ") > 0)
		// {
		str = str.replace("  ", " ");
		// }

		str = str.replace("ấ", "a");
		str = str.replace("ầ", "a");
		str = str.replace("ẩ", "a");
		str = str.replace("ẫ", "a");
		str = str.replace("ậ", "a");

		str = str.replace("Ấ", "A");
		str = str.replace("Ầ", "A");
		str = str.replace("Ẩ", "A");
		str = str.replace("Ẫ", "A");
		str = str.replace("Ậ", "A");

		str = str.replace("ắ", "a");
		str = str.replace("ằ", "a");
		str = str.replace("ẳ", "a");
		str = str.replace("ẵ", "a");
		str = str.replace("ặ", "a");

		str = str.replace("Ắ", "A");
		str = str.replace("Ằ", "A");
		str = str.replace("Ẳ", "A");
		str = str.replace("Ẵ", "A");
		str = str.replace("Ặ", "A");

		str = str.replace("á", "a");
		str = str.replace("à", "a");
		str = str.replace("ả", "a");
		str = str.replace("ã", "a");
		str = str.replace("ạ", "a");
		str = str.replace("â", "a");
		str = str.replace("ă", "a");

		str = str.replace("Á", "A");
		str = str.replace("À", "A");
		str = str.replace("Ả", "A");
		str = str.replace("Ã", "A");
		str = str.replace("Ạ", "A");
		str = str.replace("Â", "A");
		str = str.replace("Ă", "A");

		str = str.replace("ế", "e");
		str = str.replace("ề", "e");
		str = str.replace("ể", "e");
		str = str.replace("ễ", "e");
		str = str.replace("ệ", "e");

		str = str.replace("Ế", "E");
		str = str.replace("Ề", "E");
		str = str.replace("Ể", "E");
		str = str.replace("Ễ", "E");
		str = str.replace("Ệ", "E");

		str = str.replace("é", "e");
		str = str.replace("è", "e");
		str = str.replace("ẻ", "e");
		str = str.replace("ẽ", "e");
		str = str.replace("ẹ", "e");
		str = str.replace("ê", "e");

		str = str.replace("É", "E");
		str = str.replace("È", "E");
		str = str.replace("Ẻ", "E");
		str = str.replace("Ẽ", "E");
		str = str.replace("Ẹ", "E");
		str = str.replace("Ê", "E");

		str = str.replace("í", "i");
		str = str.replace("ì", "i");
		str = str.replace("ỉ", "i");
		str = str.replace("ĩ", "i");
		str = str.replace("ị", "i");

		str = str.replace("Í", "I");
		str = str.replace("Ì", "I");
		str = str.replace("Ỉ", "I");
		str = str.replace("Ĩ", "I");
		str = str.replace("Ị", "I");

		str = str.replace("ố", "o");
		str = str.replace("ồ", "o");
		str = str.replace("ổ", "o");
		str = str.replace("ỗ", "o");
		str = str.replace("ộ", "o");

		str = str.replace("Ố", "O");
		str = str.replace("Ồ", "O");
		str = str.replace("Ổ", "O");
		str = str.replace("Ô", "O");
		str = str.replace("Ộ", "O");

		str = str.replace("ớ", "o");
		str = str.replace("ờ", "o");
		str = str.replace("ở", "o");
		str = str.replace("ỡ", "o");
		str = str.replace("ợ", "o");

		str = str.replace("Ớ", "O");
		str = str.replace("Ờ", "O");
		str = str.replace("Ở", "O");
		str = str.replace("Ỡ", "O");
		str = str.replace("Ợ", "O");

		str = str.replace("ứ", "u");
		str = str.replace("ừ", "u");
		str = str.replace("ử", "u");
		str = str.replace("ữ", "u");
		str = str.replace("ự", "u");

		str = str.replace("Ứ", "U");
		str = str.replace("Ừ", "U");
		str = str.replace("Ử", "U");
		str = str.replace("Ữ", "U");
		str = str.replace("Ự", "U");

		str = str.replace("ý", "y");
		str = str.replace("ỳ", "y");
		str = str.replace("ỷ", "y");
		str = str.replace("ỹ", "y");
		str = str.replace("ỵ", "y");

		str = str.replace("Ý", "Y");
		str = str.replace("Ỳ", "Y");
		str = str.replace("Ỷ", "Y");
		str = str.replace("Ỹ", "Y");
		str = str.replace("Ỵ", "Y");

		str = str.replace("Đ", "D");
		str = str.replace("Đ", "D");
		str = str.replace("đ", "d");

		str = str.replace("ó", "o");
		str = str.replace("ò", "o");
		str = str.replace("ỏ", "o");
		str = str.replace("õ", "o");
		str = str.replace("ọ", "o");
		str = str.replace("ô", "o");
		str = str.replace("ơ", "o");

		str = str.replace("Ó", "O");
		str = str.replace("Ò", "O");
		str = str.replace("Ỏ", "O");
		str = str.replace("Õ", "O");
		str = str.replace("Ọ", "O");
		str = str.replace("Ô", "O");
		str = str.replace("Ơ", "O");

		str = str.replace("ú", "u");
		str = str.replace("ù", "u");
		str = str.replace("ủ", "u");
		str = str.replace("ũ", "u");
		str = str.replace("ụ", "u");
		str = str.replace("ư", "u");

		str = str.replace("Ú", "U");
		str = str.replace("Ù", "U");
		str = str.replace("Ủ", "U");
		str = str.replace("Ũ", "U");
		str = str.replace("Ụ", "U");
		str = str.replace("Ư", "U");

		// lê thanh lâm
		// str = str.ToLower();

		return str;
	}

	public static void setupCID() {

	}

	public static void sendData() {

	}

	private static int getGMT() {
		TimeZone tz = TimeZone.getDefault();
		Date now = new Date();
		int offsetFromUtc = tz.getOffset(now.getTime()) / 3600000;
		return offsetFromUtc;
		// TODO Auto-generated method stub
		// return null;
	}

	private static boolean isValidGPS(Date time2) {
		// TODO Auto-generated method stub
		return false;
	}

	public static String PARENT_CHAMCONG = "1";
	public static String PARENT_DSDAILY = "2";
	public static String PARENT_KHLV = "4";
	public static String PARENT_MESSAGE = "10";
	public static String PARENT_KIEMHANG = "11";

	public static Date dtLast = new Date();

	public static String getTypeIdByPosition(int position) {
		if (position == 0) {
		}
		// TODO Auto-generated method stub
		return null;
		/*
		 * old version public static String PARENT_CHAMCONG = "1"; public static
		 * String PARENT_DSDAILY = "2";//old version is 2 public static String
		 * PARENT_KHLV = "4"; public static String PARENT_MESSAGE = "10"; public
		 * static String PARENT_KIEMHANG_DAILY = "11";
		 */
		/**
		 * From ui: <item android:id="@+id/thongbaoanh" android:title=
		 * "Thông báo ảnh"/>
		 * 
		 * <item android:id="@+id/lapdonhang" android:title=
		 * "Lập đơn hàng" /> <item android:id="@+id/kiemhang" android:title=
		 * "Kiểm hàng" /> <item android:id="@+id/thongbaotext" android:title=
		 * "Thông báo text" /> <item android:id="@+id/thongbaoaudio"
		 * android:title=
		 * "Thông báo Audio" /> <item android:id="@+id/thongbaovideo"
		 * android:title= "Thông báo Video"/>
		 */
	}

	public static String getScreenIdByPositionInPopup(int position) {
		if (position == 2)
			return Util.SCREEN_KIEMHANG;
		// TODO Auto-generated method stub
		return String.valueOf(position);
	}

	// Date(1449720227197-0000)
	public static long extractDateFromServerOrder(String stringDateFromServer) {
		try {
			// TODO Auto-generated method stub
			stringDateFromServer = stringDateFromServer.substring(stringDateFromServer.indexOf("(") + 1);
			stringDateFromServer = stringDateFromServer.substring(0, stringDateFromServer.indexOf("-"));
			return Long.parseLong(stringDateFromServer);
		} catch (Exception ex) {
			return 0;
		}
	}

	public static void replaceFragment(Context mContext, Fragment fragment) {
		FragmentTransaction fmdetail = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
		fmdetail.replace(R.id.container, fragment);
		fmdetail.addToBackStack("tag");
		fmdetail.commit();
	}

	public static void orderDataLocal(Context ctx, JSONObject obj) {
		try {
			String s = ShareMemManager.getInstance().readFromDB(ctx, "orderdatalocal");
			JSONArray jsonArray;
			if (s.equalsIgnoreCase(""))
				s = "[]";
			jsonArray = new JSONArray(s);
			jsonArray.put(obj);
			ShareMemManager.getInstance().saveToDB(ctx, "orderdatalocal", jsonArray.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void saveOrderProduct(Context ctx, JSONObject obj) {

		try {
			String s = ShareMemManager.getInstance().readFromDB(ctx, "productOrder");
			String lstPriceInfo = obj.getString("PriceInfo");
			List<JSONObject> lstJsonPriceInfo = TransformDataManager
					.convertArrayToListJSON(new JSONArray(lstPriceInfo));
			List<JSONObject> lstJson = new ArrayList<JSONObject>();

			if (s.equalsIgnoreCase("") || s.equalsIgnoreCase("[]")) {
				// s = "[]";
				lstJson.add(obj);
				ShareMemManager.getInstance().saveToDB(ctx, "productOrder", lstJson.toString());
			} else {
				lstJson = TransformDataManager.convertArrayToListJSON(new JSONArray(s));
				for (int i = 0; i < lstJson.size(); i++) {
					if (lstJson.get(i).getInt("Id") == obj.getInt("Id")) {
						if (lstJson.get(i).getInt("UID") == lstJsonPriceInfo.get(0).getInt("UnitID")) {
							lstJson.remove(i);
							lstJson.add(obj);
							ShareMemManager.getInstance().saveToDB(ctx, "productOrder", lstJson.toString());
						} else {
							if (lstJson.size() - 1 - i == 0) {
								lstJson.add(obj);
								ShareMemManager.getInstance().saveToDB(ctx, "productOrder", lstJson.toString());
							}
						}

					} else {
						if (lstJson.size() - 1 - i == 0) {
							lstJson.add(obj);
							ShareMemManager.getInstance().saveToDB(ctx, "productOrder", lstJson.toString());
						}

					}
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void saveHistoryVtopup(Context ctx, String phone, String money, String time) {

		try {
			String s = ShareMemManager.getInstance().readFromDB(ctx, "history_vtopup");
			JSONArray jsonArray;
			if (s.equalsIgnoreCase(""))
				s = "[]";
			jsonArray = new JSONArray(s);

			// công vào user
			JSONObject o = new JSONObject();
			o.put("phone", phone);
			o.put("money", money);
			o.put("time", time);
			jsonArray.put(o);

			// save vào db
			String sResult = jsonArray.toString();
			ShareMemManager.getInstance().saveToDB(ctx, "history_vtopup", sResult);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void saveActionUser(Context ctx, String a, long t) {

		try {
			String s = ShareMemManager.getInstance().readFromDB(ctx, "action-user");
			JSONArray jsonArray;
			if (s.equalsIgnoreCase(""))
				s = "[]";
			jsonArray = new JSONArray(s);

			// công vào user
			JSONObject o = new JSONObject();
			o.put("a", a);
			o.put("t", t);
			jsonArray.put(o);

			// save vào db
			String sResult = jsonArray.toString();
			ShareMemManager.getInstance().saveToDB(ctx, "action-user", sResult);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void savePosition(Context ctx, String pos) {

		try {
			String s = ShareMemManager.getInstance().readFromDB(ctx, "positionProduct");
			JSONArray jsonArray;
			if (s.equalsIgnoreCase(""))
				s = "[]";
			jsonArray = new JSONArray(s);

			// công vào user
			JSONObject o = new JSONObject();
			o.put("pos", pos);
			jsonArray.put(o);

			// save vào db
			String sResult = jsonArray.toString();
			ShareMemManager.getInstance().saveToDB(ctx, "positionProduct", sResult);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getActionUserToServer(Context ctx) throws JSONException {
		String s = ShareMemManager.getInstance().readFromDB(ctx, "action-user");
		JSONObject object = new JSONObject();
		object.put("id", ShareMemManager.getInstance().readIMEI(ctx));
		object.put("iddevice", ShareMemManager.getInstance().readIMEI(ctx));
		object.put("detail", new JSONArray(s));

		final String sFile = FileUtil.saveJsonToActionUser(ShareMemManager.getInstance().readFromDB(ctx, "username"),
				object);
		if (!sFile.equalsIgnoreCase("")) {
			ShareMemManager.getInstance().deleteFromDB(ctx, "action-user");
			String urlData = Util.SERVER_URL + "?data={\"ActionType\":\"SAVEFILE\",\"UserName\":\""
					+ ShareMemManager.getInstance().readFromDB(ctx, "username") + "\",\"Password\":\""
					+ ShareMemManager.getInstance().readFromDB(ctx, "password") + "\"}";
			ServiceManager.factoryData().postFileRaw(urlData, sFile, new ICallBackUI() {
				@Override
				public void processRaw(String key, int status, String json) {
					// TODO Auto-generated method stub
					if (status == Util.OK_NETWORK) {
						// FileUtil.deleteFile(sFile);
					}

				}

				@Override
				public void process(String key, int status, List<JSONObject> lst) {
					// TODO Auto-generated method stub

				}
			});
		}

	}

	// foatingactionbutton

	static int dpToPx(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return Math.round(dp * scale);
	}

	static boolean hasJellyBean() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}

	static boolean hasLollipop() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
	}
}
