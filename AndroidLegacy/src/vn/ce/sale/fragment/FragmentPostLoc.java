package vn.ce.sale.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.FileUtil;
import vn.ce.sale.util.TimeUtil;
import vn.ce.sale.R;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentPostLoc extends ZopostFragment implements ICallBackUI {
	protected void initCreatedView() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// setup ui
		rootView = inflater.inflate(R.layout.fragment_register, container, false);
		setupUI();
		// start load data in other thread
		return rootView;// return super.onCreateView(inflater, container,
						// savedInstanceState);
	}

	private EditText edtEmail;
	private EditText edtPass;
	private String latlng = "";
	private TextView lblView;

	@Override
	protected void setupUI() {
		super.setupUI();
		setHasOptionsMenu(true);

		edtEmail = (EditText) rootView.findViewById(R.id.title);
		edtPass = (EditText) rootView.findViewById(R.id.note);

		Button btLogin = (Button) rootView.findViewById(R.id.activity_register_bt_login);
		Button btCanel = (Button) rootView.findViewById(R.id.activity_register_bt_canel);

		lblView = (TextView) rootView.findViewById(R.id.txtView);

		edtEmail.setHint(R.string.text_hint_email);
		edtPass.setHint(R.string.text_hint_password);

		btLogin.setText(R.string.text_label_login);
		btCanel.setText(R.string.text_label_canel);

		// btCanel.setOnClickListener(this);
		// btLogin.setOnClickListener(this);

		latlng = params.getString("latlng");
		lblView.setText(latlng);

		List<JSONObject> lstJsonObjects = new ArrayList<JSONObject>();
		try {
			lstJsonObjects.add(new JSONObject("{\"id\":1,\"title\":\"title 123\""));
			lstJsonObjects.add(new JSONObject("{\"id\":2,\"title\":\"title 1234\""));
		} catch (Exception e) {
		}
		final Spinner spinner = (Spinner) rootView.findViewById(R.id.catalog);
		ArrayAdapter<JSONObject> adapter = new ArrayAdapter<JSONObject>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, lstJsonObjects);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

	}

	@Override
	protected void initParamsForFragment() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void startLoadData() {

	}

	private boolean validateData() {
		if (TextUtils.isEmpty(edtEmail.getText().toString())) {
			edtEmail.setError(this.getString(R.string.text_error_email));
			return false;
		}
		if (TextUtils.isEmpty(edtPass.getText().toString())) {
			edtPass.setError(this.getString(R.string.text_error_password));
			return false;
		}

		return true;
	}

	private void saveOffline() {
		String email = edtEmail.getText().toString();
		String password = edtPass.getText().toString();
		// TODO Auto-generated method stub
		FileUtil.saveTextToMetaData("offline", TimeUtil.getStringTimeNow("yyyyMMddHHmmss"),
				new String[] { email, password, latlng });
	}

	public void saveToServer() {
		HashMap<String, String> params = new HashMap<String, String>();
		if (params.get("id") != null)
			params.put("title", params.get("id"));
		// String.v
		params.put("title", edtEmail.getText().toString());
		params.put("note", edtPass.getText().toString());
		params.put("latlng", latlng);
		ServiceManager.factoryData().postDataRaw("http://123.31.17.53:15000/serivces/api/postDataLocation.aspx", params,
				this);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.clear();
		inflater.inflate(R.menu.main_action, menu);
		// ((IdtActivity)
		// getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		// repl(Fra, args, isFinish);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add:
			// save it here
			if (validateData()) {
				saveToServer();
			}

			return true;
		case R.id.action_save:
			if (validateData()) {
				saveOffline();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**/
	@Override
	protected void fillDataSource(int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(String key, int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processRaw(String key, int status, String json) {
		// TODO Auto-generated method stub
		if (status == 200) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_LONG).show();
			// setResult(1001);
			// finish();
		} else {
			Toast.makeText(getActivity(), "Cập nhật ko thành công", Toast.LENGTH_LONG).show();
			// setResult(1001);
			// finish();
		}
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

}