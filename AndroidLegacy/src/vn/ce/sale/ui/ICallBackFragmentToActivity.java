package vn.ce.sale.ui;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;

public interface ICallBackFragmentToActivity {
	void onParamsFromFragment(Bundle data);

	void setSelectedFragment(ZopostFragment selectedFragment);
}