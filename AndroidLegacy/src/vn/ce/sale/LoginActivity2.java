package vn.ce.sale;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.data.DataOrder;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ZopostActivity;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;//ok
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
// @SuppressWarnings("deprecation")
public class LoginActivity2 extends Activity {

	private EditText edtPassWord;
	private EditText edtEmail;
	private Button btLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.login);
		btLogin = (Button) findViewById(R.id.btnLogin);
		edtEmail = (EditText) findViewById(R.id.username);
		edtPassWord = (EditText) findViewById(R.id.password);
		btLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (edtEmail.getText().toString().equals("admin") || edtPassWord.getText().toString().equals("123")) {
					Intent i = new Intent(LoginActivity2.this, DetailActivity.class);
					startActivity(i);
				}
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

}
