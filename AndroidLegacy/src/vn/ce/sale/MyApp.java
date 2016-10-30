package vn.ce.sale;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import vn.ce.sale.util.ShareMemManager;

public class MyApp extends Application {

	public static Context instance;

	public static Context getInstance() {
		if (instance == null)
			instance = (Context) new MyApp();
		return instance;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		ShareMemManager.getInstance().setCurrentContext(getApplicationContext());

	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();

	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

}
