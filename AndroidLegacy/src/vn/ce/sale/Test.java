package vn.ce.sale;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Test extends Activity {
	TextView test;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		test = (TextView) findViewById(R.id.test);
		new JSONAsyncTask().execute("http://callapi.chimen.xyz/API/GetBalance?username=conong");
		
	}

	class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Boolean doInBackground(String... urls) {
			try {

				// ------------------>>
				HttpPost httppost = new HttpPost(urls[0]);
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = httpclient.execute(httppost);

				// StatusLine stat = response.getStatusLine();
				int status = response.getStatusLine().getStatusCode();

				if (status == 200) {
					HttpEntity entity = response.getEntity();
					String data = EntityUtils.toString(entity);

					JSONObject jsono = new JSONObject(data);
					String code = jsono.getString("code");
					String mesage = jsono.getString("message");
					//String dt = jsono.getString("data");
					//JSONObject objDT = new JSONObject(dt);
					System.out.print(code);
					System.out.print(mesage);
					//System.out.print(dt);
					test.setText(mesage);
					return true;
				}

				// ------------------>>

			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return false;
		}

		protected void onPostExecute(Boolean result) {

		}
	}
}
