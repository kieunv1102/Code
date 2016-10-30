package vn.ce.sale.util;

import java.util.HashMap;

import org.json.JSONObject;

import android.R.integer;
import android.os.Bundle;
import android.os.Message;

///use builder design partern to write code clearer and easier
public class MessageData {
	Message instance;

	public MessageData() {
		instance = new Message();
	}

	public static MessageData createNew() {
		// TODO Auto-generated constructor stub
		return new MessageData();
	}

	public MessageData putObj(Object value) {
		// TODO Auto-generated constructor stub
		instance.obj = value;
		return this;
	}

	public MessageData put(Object obj, int what) {
		this.putObj(obj).putWhat(what);
		return this;
	}

	public MessageData putWhat(int what) {
		// TODO Auto-generated constructor stub
		instance.what = what;
		return this;
	}

	public Message data() {
		return instance;
	}

}
