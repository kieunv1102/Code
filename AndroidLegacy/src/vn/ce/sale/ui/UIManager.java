package vn.ce.sale.ui;

import android.content.Context;
import android.widget.Toast;
import vn.ce.sale.MyApp;

//implement add more base on convention code
public class UIManager {

	public static UIManager getInstance() {
		// TODO Auto-generated method stub
		return new UIManager();
	}

	public void showMsg(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(MyApp.getInstance().getApplicationContext(), string, Toast.LENGTH_LONG).show();
	}

	// need to implement for next version
	public void showDialog(int i) {
		// TODO Auto-generated method stub

	}

	// need to implement for next version
	public void setProgress(int parseInt) {
		// TODO Auto-generated method stub

	}

	// need to implement for next version
	public void dismissDialog() {
		// TODO Auto-generated method stub

	}

	// need to implement for next version
	public void stopShowProgress() {
		// TODO Auto-generated method stub

	}

	public void showMsg(Context context, String string) {
		// TODO Auto-generated method stub
		Toast.makeText(context, string, Toast.LENGTH_LONG).show();
	}
}
