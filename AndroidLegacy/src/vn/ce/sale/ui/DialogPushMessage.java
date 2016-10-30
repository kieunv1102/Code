package vn.ce.sale.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import vn.ce.sale.R;

public class DialogPushMessage extends Activity {
	EditText txtChatContent;
	String toUsername;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_push_message);

		// init information to show
		String toUsername = null;
		String toUsername1 = null;
		if (getIntent().getExtras() != null) {
			toUsername = getIntent().getExtras().getString("title");
			toUsername1 = getIntent().getExtras().getString("message");
		}

		TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
		txtTitle.setText(toUsername);

		TextView txt = (TextView) findViewById(R.id.txtContent);
		txt.setText(toUsername1);

	}
}
