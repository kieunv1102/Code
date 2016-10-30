package vn.ce.sale.adapter.bee;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class BeeAdapterReportOrder extends BaseAdapter {
	private Context mContext;
	private List<JSONObject> dataSource;
	private int status;

	public List<JSONObject> getDataSource() {
		return dataSource;
	}

	public void setDataSource(List<JSONObject> dt) {
		dataSource = dt;
	}

	private HashMap<Integer, BindDataUI> bindRule;
	private int layoutRow = -1;
	private View footer;

	public BeeAdapterReportOrder(Context c, List<JSONObject> d, int stt) {
		mContext = c;
		dataSource = d;
		status = stt;
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
				grid = inflater.inflate(R.layout.bee_item_report_order, null);
			else
				grid = inflater.inflate(layoutRow, null);

			// setup for holder
			final ViewHolder1 viewHolder = new ViewHolder1();
			viewHolder.txtSTT = (TextView) grid.findViewById(R.id.txtSTT);
			viewHolder.txtOrderCode = (TextView) grid.findViewById(R.id.txtOrderCode);
			viewHolder.txtStatus = (TextView) grid.findViewById(R.id.txtStatus);
			viewHolder.txtFullName = (TextView) grid.findViewById(R.id.txtFullName);
			viewHolder.txtPhone = (TextView) grid.findViewById(R.id.txtPhone);
			viewHolder.txtDiscount = (TextView) grid.findViewById(R.id.txtDiscount);
			viewHolder.lnlManipulation = (LinearLayout) grid.findViewById(R.id.lnlManipulation);
			grid.setTag(viewHolder);

		} else {
			grid = (View) convertView;
		}

		// bind data
		final ViewHolder1 holder = (ViewHolder1) grid.getTag();

		try {

			holder.txtSTT.setTag(position);
			holder.txtSTT.setText(String.valueOf(position + 1));
			holder.txtOrderCode.setTag(position);
			holder.txtOrderCode.setText(lineSource.getString("MaDH"));
			if (status==0) {
				holder.txtStatus.setTag(position);
				holder.txtStatus.setText(lineSource.getString("TrangThai"));
				holder.txtFullName.setTag(position);
				holder.txtFullName.setText(lineSource.getString("HoTen"));
				holder.txtPhone.setTag(position);
				holder.txtPhone.setText(lineSource.getString("Phone"));
				holder.txtDiscount.setTag(position);
				holder.txtDiscount.setVisibility(View.GONE);
				holder.lnlManipulation.setTag(position);
				holder.lnlManipulation.setVisibility(View.GONE);
			}
			if (status==1) {
				holder.txtStatus.setTag(position);
				holder.txtStatus.setVisibility(View.GONE);
				holder.txtFullName.setTag(position);
				holder.txtFullName.setText("Date now");
				holder.txtPhone.setTag(position);
				holder.txtPhone.setVisibility(View.GONE);
				holder.txtDiscount.setTag(position);
				holder.txtDiscount.setVisibility(View.GONE);
				holder.lnlManipulation.setTag(position);
				holder.lnlManipulation.setVisibility(View.VISIBLE);
			}
			if (status==2) {
				holder.txtStatus.setTag(position);
				holder.txtStatus.setVisibility(View.GONE);
				holder.txtFullName.setTag(position);
				holder.txtFullName.setText("Date now");
				holder.txtPhone.setTag(position);
				holder.txtPhone.setVisibility(View.GONE);
				holder.txtDiscount.setTag(position);
				holder.txtDiscount.setVisibility(View.GONE);
				holder.lnlManipulation.setTag(position);
				holder.lnlManipulation.setVisibility(View.VISIBLE);
			}
			

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
		public TextView txtSTT, txtOrderCode;
		public TextView txtStatus, txtFullName;
		public TextView txtPhone, txtDiscount, txtEditOrder,txtCancelOrder;
		public LinearLayout lnlManipulation;

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