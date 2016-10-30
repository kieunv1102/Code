package vn.ce.sale.util;

import java.util.HashMap;

import org.json.JSONObject;

import android.os.Bundle;

///use builder design partern to write code clearer and easier
public class HashData {
	HashMap<String, String> instance;

	public HashData() {
		instance = new HashMap<String, String>();
	}

	public static HashData createNew() {
		// TODO Auto-generated constructor stub
		return new HashData();
	}

	public HashData putString(String key, String value) {
		// TODO Auto-generated constructor stub
		instance.put(key, value);
		return this;
	}

	public HashMap<String, String> data() {
		return instance;
	}

	public String toHashToQueryString() {
		// TODO Auto-generated method stub
		if (instance == null)
			return "";
		StringBuilder sb = new StringBuilder();

		for (String keyParams : instance.keySet()) {
			sb.append(keyParams + "=" + instance.get(keyParams).toString() + "&");
		}
		String paramRequests = sb.toString();
		return paramRequests;
	}
}
