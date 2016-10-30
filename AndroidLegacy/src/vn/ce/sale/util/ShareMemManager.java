package vn.ce.sale.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

public class ShareMemManager {
	private Context _currentContextx;
	private static ShareMemManager _instance;

	/**
	 * 
	 */
	public static ShareMemManager getInstance() {
		if (_instance == null)
			_instance = new ShareMemManager();
		return _instance;
	}

	public static ShareMemManager i() {
		return getInstance();
	}

	public ShareMemManager() {

	}

	public void setCurrentContext(Context currentContext) {
		_currentContextx = currentContext;
	}

	public Context getCurrentContext() {
		return _currentContextx;
	}

	public boolean isConnectedGPRS(Context context) {
		if (_currentContextx == null)
			return false;
		ConnectivityManager conMan = (ConnectivityManager) _currentContextx
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		// mobile
		State mobile = conMan.getNetworkInfo(0).getState();

		return (mobile == android.net.NetworkInfo.State.CONNECTED);

	}

	public boolean isConnectedWIFI(Context _currentContext) {
		if (_currentContextx == null)
			return false;
		ConnectivityManager conMan = (ConnectivityManager) _currentContextx
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		// wifi
		State wifi = conMan.getNetworkInfo(1).getState();

		return (wifi == android.net.NetworkInfo.State.CONNECTED);
	}

	public boolean isConnected(Context _currentContext) {
		if (_currentContextx == null)
			return false;

		ConnectivityManager connectivity = (ConnectivityManager) _currentContextx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	static String KEY_TO_SAVE = "DATA-DMOBILE";

	public void saveToDB(Context _currentContext, String key, String value) {
		if (_currentContext == null)
			_currentContext = _currentContextx;
		SharedPreferences settings = _currentContext.getSharedPreferences(KEY_TO_SAVE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);

		editor.commit();

	}

	public String readFromDB(Context _currentContext, String key) {
		if (_currentContext == null)
			_currentContext = _currentContextx;
		SharedPreferences settings = _currentContext.getSharedPreferences(KEY_TO_SAVE, 0);
		String pKey = settings.getString(key, "");
		return pKey;
	}

	public void deleteFromDB(Context _currentContext, String key) {
		if (_currentContext == null)
			_currentContext = _currentContextx;
		SharedPreferences settings = _currentContext.getSharedPreferences(KEY_TO_SAVE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.remove(key);
		editor.commit();
	}

	public String readFromSetting(Context _currentContext, String key) {
		if (_currentContext == null)
			_currentContext = _currentContextx;
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(_currentContext);
		String pKey = settings.getString(key, "");
		return pKey;
	}

	public String readIMEI(Context _currentContext) {
		if (_currentContext == null)
			_currentContext = _currentContextx;
		String imei = readFromDB(_currentContext, "IMEI");
		if (imei.equalsIgnoreCase("")) {
			TelephonyManager tm = (TelephonyManager) _currentContext.getSystemService(Context.TELEPHONY_SERVICE);
			imei = tm.getDeviceId();
			saveToDB(_currentContext, "IMEI", imei);
		}
		return imei;
	}

	public String readLastActive(Context _currentContext) {
		return readFromDB(_currentContext, Util.KEY_LAST_ICD);
	}

}
