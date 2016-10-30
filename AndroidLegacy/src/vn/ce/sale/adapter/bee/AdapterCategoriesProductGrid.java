package vn.ce.sale.adapter.bee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.fragment.bee.Bee_Fragment_Product_List;
import vn.ce.sale.fragment.bee.Bee_Fragment_Product_List_Detail;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.util.CircleImageView;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;

public class AdapterCategoriesProductGrid extends BaseAdapter implements OnClickListener {
	private Context mContext;
	private List<JSONObject> dataSource;
	private List<JSONObject> dataSourceProduct;
	List<JSONObject> priceInfo1;
	List<JSONObject> priceInfo2;
	List<JSONObject> priceInfo3;
	int posCate;

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

	public AdapterCategoriesProductGrid(Context c, List<JSONObject> d, List<JSONObject> dp) {
		mContext = c;
		dataSource = d;
		dataSourceProduct = dp;

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
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		View grid = null;
		posCate = position;
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
		try {
			final JSONObject lineSource = dataSource.get(position);
			final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final JSONObject s1 = dataSourceProduct.get(0);
			final JSONObject s2 = dataSourceProduct.get(1);
			final JSONObject s3 = dataSourceProduct.get(2);
			String p1 = s1.getString("PriceInfo");
			String p2 = s2.getString("PriceInfo");
			String p3 = s3.getString("PriceInfo");
			priceInfo1 = TransformDataManager.convertArrayToListJSON(new JSONArray(p1));
			priceInfo2 = TransformDataManager.convertArrayToListJSON(new JSONArray(p2));
			priceInfo3 = TransformDataManager.convertArrayToListJSON(new JSONArray(p3));
			// mapping datasourcename and id of view
			if (convertView == null) {

				grid = new View(mContext);

				//
				if (layoutRow == -1)
					grid = inflater.inflate(R.layout.bee_item_grv_all_product, null);
				else
					grid = inflater.inflate(layoutRow, null);

				// setup for holder
				final ViewHolder1 viewHolder = new ViewHolder1();
				viewHolder.txtCategoriProduct = (TextView) grid.findViewById(R.id.txtCategoriProduct);

				viewHolder.txtNameProductEcom1 = (TextView) grid.findViewById(R.id.txtNameProductEcom1);
				viewHolder.txtNameProductEcom2 = (TextView) grid.findViewById(R.id.txtNameProductEcom2);
				viewHolder.txtNameProductEcom3 = (TextView) grid.findViewById(R.id.txtNameProductEcom3);

				viewHolder.txtPriceSaleEcom1 = (TextView) grid.findViewById(R.id.txtPriceSaleEcom1);
				viewHolder.txtPriceSaleEcom2 = (TextView) grid.findViewById(R.id.txtPriceSaleEcom2);
				viewHolder.txtPriceSaleEcom3 = (TextView) grid.findViewById(R.id.txtPriceSaleEcom3);

				viewHolder.txtSaleEcom1 = (TextView) grid.findViewById(R.id.txtSaleEcom1);
				viewHolder.txtSaleEcom2 = (TextView) grid.findViewById(R.id.txtSaleEcom2);
				viewHolder.txtSaleEcom3 = (TextView) grid.findViewById(R.id.txtSaleEcom3);

				viewHolder.txtPriceEcom1 = (TextView) grid.findViewById(R.id.txtPriceEcom1);
				viewHolder.txtPriceEcom2 = (TextView) grid.findViewById(R.id.txtPriceEcom2);
				viewHolder.txtPriceEcom3 = (TextView) grid.findViewById(R.id.txtPriceEcom3);

				viewHolder.imvProduct1 = (ImageView) grid.findViewById(R.id.imv_grv_product1);
				viewHolder.imvProduct2 = (ImageView) grid.findViewById(R.id.imv_grv_product2);
				viewHolder.imvProduct3 = (ImageView) grid.findViewById(R.id.imv_grv_product3);

				viewHolder.lnlItem1 = (FrameLayout) grid.findViewById(R.id.lnlItem1);
				viewHolder.lnlItem2 = (FrameLayout) grid.findViewById(R.id.lnlItem2);
				viewHolder.lnlItem3 = (FrameLayout) grid.findViewById(R.id.lnlItem3);
				grid.setTag(viewHolder);

			} else {
				grid = (View) convertView;
			}

			// bind data
			final ViewHolder1 holder = (ViewHolder1) grid.getTag();
			holder.txtCategoriProduct.setText(lineSource.getString("Name"));
			holder.txtCategoriProduct.setTag(position);
			holder.txtCategoriProduct.setOnClickListener(this);
			holder.txtNameProductEcom1.setTag(position);
			holder.txtNameProductEcom1.setText(dataSourceProduct.get(0).getString("Name"));
			holder.txtNameProductEcom2.setTag(position);
			holder.txtNameProductEcom2.setText(dataSourceProduct.get(1).getString("Name"));
			holder.txtNameProductEcom3.setTag(position);
			holder.txtNameProductEcom3.setText(dataSourceProduct.get(2).getString("Name"));
			holder.txtPriceEcom1.setTag(position);
			holder.txtPriceEcom1
					.setText(String.valueOf(FormatUtil.formatCurrency(priceInfo1.get(0).getDouble("Price"))) + "đ");
			holder.txtPriceEcom1.setPaintFlags(holder.txtPriceEcom1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			holder.txtPriceEcom2.setTag(position);
			holder.txtPriceEcom2
					.setText(String.valueOf(FormatUtil.formatCurrency(priceInfo2.get(0).getDouble("Price"))) + "đ");
			holder.txtPriceEcom2.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			holder.txtPriceEcom3.setTag(position);
			holder.txtPriceEcom3
					.setText(String.valueOf(FormatUtil.formatCurrency(priceInfo3.get(0).getDouble("Price"))) + "đ");
			holder.txtPriceEcom3.setPaintFlags(holder.txtPriceEcom3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			holder.txtPriceSaleEcom1.setTag(position);
			holder.txtPriceSaleEcom1.setText(String.valueOf(FormatUtil.formatCurrency((double) 550000)) + "đ");
			holder.txtPriceSaleEcom2.setTag(position);
			holder.txtPriceSaleEcom2.setText(String.valueOf(FormatUtil.formatCurrency((double) 550000)) + "đ");
			holder.txtPriceSaleEcom3.setTag(position);
			holder.txtPriceSaleEcom3.setText(String.valueOf(FormatUtil.formatCurrency((double) 550000)) + "đ");
			holder.txtSaleEcom1.setTag(position);
			holder.txtSaleEcom1.setText("-40%");
			holder.txtSaleEcom2.setTag(position);
			holder.txtSaleEcom2.setText("-40%");
			holder.txtSaleEcom3.setTag(position);
			holder.txtSaleEcom3.setText("-40%");
			holder.lnlItem1.setTag(position);
			holder.lnlItem1.setOnClickListener(this);
			holder.lnlItem2.setTag(position);
			holder.lnlItem2.setOnClickListener(this);
			holder.lnlItem3.setTag(position);
			holder.lnlItem3.setOnClickListener(this);

			holder.imvProduct1.setTag(position);
			holder.imvProduct2.setTag(position);
			holder.imvProduct3.setTag(position);
			Picasso.with(mContext).load(Util.IMAGE_URL).placeholder(R.drawable.backimvproduct).into(holder.imvProduct1);
			Picasso.with(mContext).load(Util.IMAGE_URL).placeholder(R.drawable.backimvproduct).into(holder.imvProduct2);
			Picasso.with(mContext).load(Util.IMAGE_URL).placeholder(R.drawable.backimvproduct).into(holder.imvProduct3);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return grid;
	}

	@SuppressWarnings("static-access")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txtCategoriProduct:

			try {
				String a = dataSource.get(posCate).getString("ID");
				FragmentTransaction fm = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
				fm.replace(R.id.container, new Bee_Fragment_Product_List().newInstance(a, "b"));
				fm.addToBackStack("tag");
				fm.commit();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.lnlItem1:
			try {
				Util.checkItemCate = true;
				FragmentTransaction fmdetail1 = ((FragmentActivity) mContext).getSupportFragmentManager()
						.beginTransaction();
				fmdetail1.replace(R.id.container, new Bee_Fragment_Product_List_Detail()
						.newInstance(dataSourceProduct.get(0).getString("Id"), String.valueOf(posCate)));
				fmdetail1.addToBackStack("tag");
				fmdetail1.commit();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.lnlItem2:
			try {
				Util.checkItemCate = true;
				FragmentTransaction fmdetail2 = ((FragmentActivity) mContext).getSupportFragmentManager()
						.beginTransaction();
				fmdetail2.replace(R.id.container, new Bee_Fragment_Product_List_Detail()
						.newInstance(dataSourceProduct.get(1).getString("Id"), String.valueOf(posCate)));
				fmdetail2.addToBackStack("tag");
				fmdetail2.commit();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.lnlItem3:
			try {
				Util.checkItemCate = true;
				FragmentTransaction fmdetail3 = ((FragmentActivity) mContext).getSupportFragmentManager()
						.beginTransaction();
				fmdetail3.replace(R.id.container, new Bee_Fragment_Product_List_Detail()
						.newInstance(dataSourceProduct.get(2).getString("Id"), String.valueOf(posCate)));
				fmdetail3.addToBackStack("tag");
				fmdetail3.commit();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}

	}

	public void setLayoutRow(int layoutRow) {
		this.layoutRow = layoutRow;
	}

	static class ViewHolder2 {

	}

	static class ViewHolder1 {
		public TextView txtCategoriProduct;
		public ImageView imvProduct1, imvProduct2, imvProduct3;
		public TextView txtNameProductEcom1, txtNameProductEcom2, txtNameProductEcom3;
		public TextView txtPriceSaleEcom1, txtPriceSaleEcom2, txtPriceSaleEcom3;
		public TextView txtSaleEcom1, txtSaleEcom2, txtSaleEcom3;
		public TextView txtPriceEcom1, txtPriceEcom2, txtPriceEcom3;
		public TextView btnOrderNow1, btnOrderNow2, btnOrderNow3;
		public FrameLayout lnlItem1, lnlItem2, lnlItem3;

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