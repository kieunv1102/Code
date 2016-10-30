package vn.ce.sale.fragment;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.TypeUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.R;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DemoObjectTextFragment extends ZopostFragment// implements
															// ICallBackActivityToFragment,ICallBackUI
{

	public static final String ARG_OBJECT = "object";
	View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.demo_text, container, false);
		// grid=(ListView) rootView.findViewById(R.id.grid);
		// Bundle args = getArguments();
		// ((TextView)
		// rootView.findViewById(android.R.id.text1)).setText(args.getString(ARG_OBJECT));
		((TextView) rootView.findViewById(R.id.text1)).setText("Demo me...");
		return rootView;
	}

	@Override
	protected void startLoadData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initParamsForFragment() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void fillDataSource(int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}
}