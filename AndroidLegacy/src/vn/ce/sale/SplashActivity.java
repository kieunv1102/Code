package vn.ce.sale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import vn.ce.sale.R;

public class SplashActivity extends Activity {
	private long startTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash);
		startTime = System.currentTimeMillis();
		onSucessLoading();
	}

	private void onSucessLoading() {
		final long time = (System.currentTimeMillis() >= startTime + 1000) ? 0
				: (1000 + startTime - System.currentTimeMillis());
		new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					try {
						Intent it = new Intent(SplashActivity.this, LoginActivity.class);
						startActivity(it);

						SplashActivity.this.finish();
					} catch (Exception e) {
					}
				}
			}
		}.start();
	}
}
