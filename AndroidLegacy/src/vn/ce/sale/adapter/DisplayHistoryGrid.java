package vn.ce.sale.adapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.LoginActivity;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.util.CircleImageView;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.TimeUtil;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DisplayHistoryGrid extends BaseAdapter {
	private Context mContext;
	private List<JSONObject> dataSource;
	private List<JSONObject> lstJsonItemList;

	public List<JSONObject> getDataSource() {
		return dataSource;
	}

	public void setDataSource(List<JSONObject> dt) {
		dataSource = dt;
	}

	private HashMap<Integer, BindDataUI> bindRule;
	private int layoutRow = -1;
	private View footer;
	private View header;

	public View getHeader() {
		return footer;
	}

	public void setHeader(View header) {
		this.header = header;
	}

	public DisplayHistoryGrid(Context c, List<JSONObject> d) {
		mContext = c;
		dataSource = d;
	}

	public View getFooter() {
		return footer;
	}

	public void setFooter(View footer) {
		this.footer = footer;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataSource.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dataSource.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		View grid;

		new ICallBackUI() {

			@Override
			public void processRaw(String key, int status, String json) {
				// TODO Auto-generated method stub
			}

			@Override
			public void process(String key, int status, List<JSONObject> lst) {
				// TODO Auto-generated method stub

			}
		};

		final JSONObject lineSource = dataSource.get(position);
		final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {

			grid = new View(mContext);

			//
			if (layoutRow == -1)
				grid = inflater.inflate(R.layout.layout_customer_list_row_history, null);
			else
				grid = inflater.inflate(layoutRow, null);

			// setup for holder
			final ViewHolder1 viewHolder = new ViewHolder1();
			viewHolder.txtPhoneHistory = (TextView) grid.findViewById(R.id.txtPhoneHistory);
			viewHolder.txtMoneyHistory = (TextView) grid.findViewById(R.id.txtMoneyHistory);
			viewHolder.txtTimeHistory = (TextView) grid.findViewById(R.id.txtTimeHistory);
			viewHolder.imvHistory = (CircleImageView) grid.findViewById(R.id.imvHistory);

			grid.setTag(viewHolder);

		} else {
			grid = (View) convertView;
		}

		// bind data
		final ViewHolder1 holder = (ViewHolder1) grid.getTag();

		try {
			holder.txtPhoneHistory.setText("Số điện thoại nạp tiền: " + lineSource.getString("phone"));
			holder.txtPhoneHistory.setTag(position);
			holder.txtMoneyHistory.setText("Số tiền nạp: " + lineSource.getString("money"));
			holder.txtMoneyHistory.setTag(position);
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			holder.txtTimeHistory.setText("Thời gian nạp tiền:" + formatter.format(Long.parseLong(lineSource.getString("time"))));
			holder.txtTimeHistory.setTag(position);
			holder.imvHistory.setImageResource(R.drawable.mobile);
			holder.imvHistory.setTag(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return grid;
	}

	public void setLayoutRow(int layoutRow) {
		this.layoutRow = layoutRow;
	}

	static class ViewHolder2 {

	}

	static class ViewHolder1 {
		public CircleImageView imvHistory;
		public TextView txtPhoneHistory;
		public TextView txtMoneyHistory;
		public TextView txtTimeHistory;

		SparseArray<CheckBox> checkbox = new SparseArray<CheckBox>();
		SparseArray<TextView> text = new SparseArray<TextView>();
		SparseArray<EditText> editText = new SparseArray<EditText>();
		SparseArray<ImageView> icon = new SparseArray<ImageView>();
		SparseArray<ProgressBar> pb = new SparseArray<ProgressBar>();
		SparseArray<Button> button = new SparseArray<Button>();

		public void addEditText(int idui, EditText view) {
			editText.append(idui, view);
		}

		public void addTextView(int idui, TextView view) {
			text.append(idui, view);
		}

		public void addButton(int idui, Button view) {
			button.append(idui, view);
		}

		public void addCheckBox(int idui, CheckBox view) {
			checkbox.append(idui, view);
		}

		public void addImageView(int idUI, ImageView imageView, ProgressBar pb2) {
			// TODO Auto-generated method stub
			icon.append(idUI, imageView);
			pb.append(idUI, pb2);
		}

		public void addImageViewStatic(int idUI, ImageView imageView) {
			// TODO Auto-generated method stub
			icon.append(idUI, imageView);
		}
	}

}