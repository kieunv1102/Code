package vn.ce.sale.ui;

import org.json.JSONObject;

import android.os.Bundle;

///use builder design partern to write code clearer and easier
public class BundleData {
	Bundle instance;

	public BundleData() {
		instance = new Bundle();
	}

	public static BundleData createNew() {
		// TODO Auto-generated constructor stub
		return new BundleData();
	}

	public BundleData putInt(String key, int value) {
		// TODO Auto-generated constructor stub
		instance.putInt(key, value);
		return this;
	}

	public BundleData putDouble(String key, double value) {
		// TODO Auto-generated constructor stub
		instance.putDouble(key, value);
		return this;
	}

	public BundleData putString(String key, String value) {
		// TODO Auto-generated constructor stub
		instance.putString(key, value);
		return this;
	}

	public Bundle data() {
		return instance;
	}

}
