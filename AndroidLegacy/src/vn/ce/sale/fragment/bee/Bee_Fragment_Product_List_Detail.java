package vn.ce.sale.fragment.bee;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import vn.ce.sale.adapter.bee.BeeAdapterHorizontalListView;
import vn.ce.sale.adapter.bee.PagerAdapterCustomer;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.CircleImageView;
import vn.ce.sale.util.FormatUtil;
import vn.ce.sale.util.HorizontalListView;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.view.MenuInflater;

public class Bee_Fragment_Product_List_Detail extends ZopostFragment
		implements ICallBackActivityToFragment, ICallBackUI, OnClickListener {
	CircleImageView imvSlider1, imvSlider2, imvSlider3, imvSlider4, imvSlider5;
	EditText edtQuantityOrder;
	Spinner spnUnit;
	List<String> arrUnit;
	List<String> arrUnitID;
	public static final String ARG_OBJECT = "object";
	View rootView;
	View footerGrid;
	ViewPager pager;
	PagerAdapterCustomer adapter;
	RadioGroup radioGroup;
	private List<Fragment> listData;
	int page;
	TextView txtNameProductDetail, txtPriceProductDetail, txtPriceSaleDetail, txtDesProductDetail, txtDesContinue;
	Button btnOrderProduct;
	List<JSONObject> lstJsonObjects = new ArrayList<JSONObject>();
	List<JSONObject> lstJsonPriceInfo = new ArrayList<JSONObject>();
	HorizontalListView hlvProduct;
	BeeAdapterHorizontalListView mAdapterHorizontalListView;
	double t;
	String dv;
	int uId;
	int pos;
	int pUnit;
	String arr;

	String u1 = "http://thigia.com/Images/finrline-thai-lan-3000ml.jpg";
	String u2 = "http://rongbay2.vcmedia.vn/thumb_max/up_new/2011/06/08/0/201106145746_nuocgiat.700x0.jpg";
	String u3 = "https://www.cp.com.vn/VN/images/temp/Product/product_cp.jpg";
	String u4 = "http://www.vpbank.com.vn/sites/default/files/750x300_tien_gui.jpg";
	String u5 = "http://k14.vcmedia.vn/k:thumb_w/600/pgHuXrcq18KdYtKp3bAtptdIKIxsLl/Image/2013/04/1-03387/10-san-pham-co-thiet-ke-dep-nhat-moi-thoi-dai.jpg";

	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	private String posBanner;
	private String posCategory,lstPriceInfo;
	JSONObject obj;
	public static Bee_Fragment_Product_List_Detail newInstance(String posBanner, String category) {
		Bee_Fragment_Product_List_Detail fragment = new Bee_Fragment_Product_List_Detail();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, posBanner);
		args.putString(ARG_PARAM2, category);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		getActivity().setTitle("Chi Tiết Sản Phẩm");
		if (getArguments() != null) {
			posBanner = getArguments().getString(ARG_PARAM1);
			posCategory = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.bee_layout_customer_list_detail, container, false);
		imvSlider1 = (CircleImageView) rootView.findViewById(R.id.imvSlider1);
		imvSlider2 = (CircleImageView) rootView.findViewById(R.id.imvSlider2);
		imvSlider3 = (CircleImageView) rootView.findViewById(R.id.imvSlider3);
		imvSlider4 = (CircleImageView) rootView.findViewById(R.id.imvSlider4);
		imvSlider5 = (CircleImageView) rootView.findViewById(R.id.imvSlider5);
		imvSlider1.setOnClickListener(this);
		imvSlider2.setOnClickListener(this);
		imvSlider3.setOnClickListener(this);
		imvSlider4.setOnClickListener(this);
		imvSlider5.setOnClickListener(this);
		Picasso.with(getActivity()).load(u1).placeholder(R.drawable.mobile).into(imvSlider1);
		Picasso.with(getActivity()).load(u2).placeholder(R.drawable.mobile).into(imvSlider2);
		Picasso.with(getActivity()).load(u3).placeholder(R.drawable.mobile).into(imvSlider3);
		Picasso.with(getActivity()).load(u4).placeholder(R.drawable.mobile).into(imvSlider4);
		Picasso.with(getActivity()).load(u5).placeholder(R.drawable.mobile).into(imvSlider5);

		spnUnit = (Spinner) rootView.findViewById(R.id.spnUnit);
		edtQuantityOrder = (EditText) rootView.findViewById(R.id.edtQuantityOrder);
		txtNameProductDetail = (TextView) rootView.findViewById(R.id.txtNameProductDetail);
		txtPriceProductDetail = (TextView) rootView.findViewById(R.id.txtPriceProductDetail);
		txtPriceProductDetail.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		txtPriceSaleDetail = (TextView) rootView.findViewById(R.id.txtPriceSaleDetail);
		txtDesProductDetail = (TextView) rootView.findViewById(R.id.txtDesProductDetailaaa);
		txtDesContinue = (TextView) rootView.findViewById(R.id.txtDesContinueaaa);
		pager = (ViewPager) rootView.findViewById(R.id.viewpager);
		radioGroup = (RadioGroup) rootView.findViewById(R.id.radiogroup);
		detailItemGrid();
		pagerSlider();
		
		hlvProduct = (HorizontalListView) rootView.findViewById(R.id.hlv_product);
		mAdapterHorizontalListView = new BeeAdapterHorizontalListView(getActivity(), lstJsonObjects);
		hlvProduct.setAdapter(mAdapterHorizontalListView);
		hlvProduct.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
		mAdapterHorizontalListView.notifyDataSetChanged();
		btnOrderProduct = (Button) rootView.findViewById(R.id.btnOrderProduct);
		btnOrderProduct.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				orderProduct();
			}

		});
		setHasOptionsMenu(true);
		return rootView;
	}

	@SuppressWarnings({ "static-access", "deprecation" })
	private void pagerSlider() {
		ArrayAdapter<String> adapterNhaMang = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, arrUnit);
		adapterNhaMang.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		spnUnit.setAdapter(adapterNhaMang);
		spnUnit.setOnItemSelectedListener(new UnitProcessEvent());
		listData = new ArrayList<Fragment>();
		listData.add(new ItemFragment().newInstance(u1));
		listData.add(new ItemFragment().newInstance(u2));
		listData.add(new ItemFragment().newInstance(u3));
		listData.add(new ItemFragment().newInstance(u4));
		listData.add(new ItemFragment().newInstance(u5));
		String a[] = { u1, u2, u3, u4, u5 };
		adapter = new PagerAdapterCustomer(getActivity(), a);
		final Handler handler = new Handler();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while (!Thread.interrupted()) {
					for (int i = 0; i < adapter.getCount(); i++) {
						final int value = i;
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						handler.post(new Runnable() {
							@Override
							public void run() {
								pager.setCurrentItem(value, true);
							}
						});
					}
				}
			}
		};
		new Thread(runnable).start();

		pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {

				switch (position) {
				case 0:
					radioGroup.check(R.id.radioButton);
					break;
				case 1:
					radioGroup.check(R.id.radioButton2);
					break;
				case 2:
					radioGroup.check(R.id.radioButton3);
					break;
				case 3:
					radioGroup.check(R.id.radioButton4);
					break;
				case 4:
					radioGroup.check(R.id.radioButton5);
					break;
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		pager.setAdapter(adapter);
		
	}

	private void orderProduct() {

		String s = ShareMemManager.getInstance().readFromDB(getActivity(), "productOrder");

		int x = Integer.parseInt(edtQuantityOrder.getText().toString());
		if (s.equals("")||s.equals("[]")) {
			try {
				if (x > 9999) {
					obj.put("SL", 9999);
					int tt = (int) (9999 * t);
					obj.put("TT", tt);
					obj.put("UNAME", dv);
					obj.put("UID", uId);
					obj.put("PRICE", t);
				} else {
					obj.put("SL", x);
					int tt = (int) (x * t);
					obj.put("TT", tt);
					obj.put("UNAME", dv);
					obj.put("UID", uId);
					obj.put("PRICE", t);
				}
				Util.saveOrderProduct(getActivity(), obj);
				nextToFragmentAndKeepState(new BeeFragmentOrderProduct(), null, true);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			List<JSONObject> lst = new ArrayList<JSONObject>();
			try {
				lst = TransformDataManager.convertArrayToListJSON(new JSONArray(s));
				for (int i = 0; i < lst.size(); i++) {
					if (lst.get(i).getInt("Id") == obj.getInt("Id")) {
						try {
							if (lst.get(i).getInt("UID")==uId) {
								if (x > 9999) {
									obj.put("SL", 9999);
									int tt = (int) (9999 * t);
									obj.put("TT", tt);
									obj.put("UNAME", dv);
									obj.put("UID", uId);
									obj.put("PRICE", t);
								} else {
									int sl = lst.get(i).getInt("SL") + x;
									obj.put("SL", sl);
									int tt = (int) (sl * t);
									obj.put("TT", tt);
									obj.put("UNAME", dv);
									obj.put("UID", uId);
									obj.put("PRICE", t);

								}
							}
							else {
								if (x > 9999) {
									obj.put("SL", 9999);
									int tt = (int) (9999 * t);
									obj.put("TT", tt);
									obj.put("UNAME", dv);
									obj.put("UID", uId);
									obj.put("PRICE", t);
								} else {
									obj.put("SL", x);
									int tt = (int) (x * t);
									obj.put("TT", tt);
									obj.put("UNAME", dv);
									obj.put("UID", uId);
									obj.put("PRICE", t);
								}
							}
							
							
							Util.saveOrderProduct(getActivity(), obj);
							nextToFragmentAndKeepState(new BeeFragmentOrderProduct(), null, true);
						} catch (JSONException e) {
							// TODO Auto-generated catch
							// block
							e.printStackTrace();
						}
						break;
					} else {
						if (x > 9999) {
							obj.put("SL", 9999);
							int tt = (int) (9999 * t);
							obj.put("TT", tt);
							obj.put("UNAME", dv);
							obj.put("UID", uId);
							obj.put("PRICE", t);
						} else {
							obj.put("SL", x);
							int tt = (int) (x * t);
							obj.put("TT", tt);
							obj.put("UNAME", dv);
							obj.put("UID", uId);
							obj.put("PRICE", t);
						}
						Util.saveOrderProduct(getActivity(), obj);
						nextToFragmentAndKeepState(new BeeFragmentOrderProduct(), null, true);
					}

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private class UnitProcessEvent implements android.widget.AdapterView.OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			pUnit = position;
			try {
				t = lstJsonPriceInfo.get(position).getDouble("Price");
				dv = arrUnit.get(pUnit);
				uId = Integer.parseInt(arrUnitID.get(pUnit));
				txtPriceProductDetail.setText(FormatUtil.formatCurrency(t) + " VND");
				txtPriceSaleDetail.setText(FormatUtil.formatCurrency(t) + " VND");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			// txtDisplayNhaMang.setText(arrNhaMang[0]);
		}

	}

	private void detailItemGrid() {
		// TODO Auto-generated method stub
//		if (Util.checkBanner == true) {
//			pos = Integer.parseInt(posBanner);
//			arr = ShareMemManager.getInstance().readFromDB(getContext(), "ProductBee");
//		}else if(Util.checkItemCate ==true){
//			pos = Integer.parseInt(posBanner);
//			arr = ShareMemManager.getInstance().readFromDB(getContext(), "ProductBee");
//		}
//		else {
//			pos = Integer.parseInt(posBanner);
//			arr = ShareMemManager.getInstance().readFromDB(getContext(), "productDetail");
//		}

		try {
			arr = ShareMemManager.getInstance().readFromDB(getContext(), "ProductBee");
			lstJsonObjects = TransformDataManager.convertArrayToListJSON(new JSONArray(arr));
			for (int i = 0; i < lstJsonObjects.size(); i++) {
				if (lstJsonObjects.get(i).getString("Id").equalsIgnoreCase(posBanner)) {
					obj = new JSONObject();
					obj = lstJsonObjects.get(i);
					lstPriceInfo = lstJsonObjects.get(i).getString("PriceInfo");
					txtNameProductDetail.setText(lstJsonObjects.get(i).getString("Name"));
				}
			}
			
			lstJsonPriceInfo = TransformDataManager.convertArrayToListJSON(new JSONArray(lstPriceInfo));
			arrUnit = new ArrayList<String>();
			arrUnitID = new ArrayList<String>();
			for (int i = 0; i < lstJsonPriceInfo.size(); i++) {
				arrUnit.add(lstJsonPriceInfo.get(i).getString("UnitName"));
				arrUnitID.add(String.valueOf(lstJsonPriceInfo.get(i).getInt("UnitID")));
			}
			
			t = lstJsonPriceInfo.get(pUnit).getDouble("Price");
			dv = arrUnit.get(pUnit);
			uId = Integer.parseInt(arrUnitID.get(pUnit));
			txtPriceProductDetail.setText(FormatUtil.formatCurrency(t) + " VND");
			txtPriceSaleDetail.setText(FormatUtil.formatCurrency(t) + " VND");
			// txtDesProductDetail.setText("");

			txtDesContinue.setText(
					"The position parameter indicates where a given page is located relative to the center of the screen. It is a dynamic property that changes as the user scrolls through the pages. When a page fills the screen, its position value is 0. When a page is drawn just off the right side of the screen, its position value is 1. ");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Util.checkBanner = false;
		Util.checkItemCate =false;
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
	public void onParamsFromActivity(Bundle args) {
		if (args == null)
			return;
		// TODO Auto-generated method stub
		// ((TextView)
		// rootView.findViewById(android.R.id.text1)).setText(args.getString(ARG_OBJECT));
	}

	@Override
	public void process(String key, int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void startLoadData() {

		ServiceManager.factoryData()
				.getDataRaw(Util.SERVER_URL + "system=bee&data={\"ActionType\":\"STORE\"" + ",\"UserName\":\""
						+ ShareMemManager.getInstance().readFromDB(getContext(), "username") + "\",\"Password\":\""
						+ ShareMemManager.getInstance().readFromDB(getContext(), "password") + "\"}", "",
				DataOrder.NETWORK_THEN_CACHE, this);

	}

	protected void refreshData() {

		page = 0;
		ServiceManager.factoryData()
				.getDataRaw(Util.SERVER_URL + "system=bee&data={\"ActionType\":\"STORE\"" + ",\"UserName\":\""
						+ ShareMemManager.getInstance().readFromDB(getContext(), "username") + "\",\"Password\":\""
						+ ShareMemManager.getInstance().readFromDB(getContext(), "password") + "\"}", "",
				DataOrder.NETWORK_THEN_CACHE, this);
	}

	@Override
	public void processRaw(String key, final int status, final String json) {

		runOnUiThreadX(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (status == 1001) {
					return;
				}
				// displayloading with json is percentage of loading data
				if (status == 200) {

				}
			}
		});

	}

	// @Override
	// public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	// menu.clear();
	// // ((IdtActivity)
	// //
	// getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
	// inflater.inflate(R.menu.bee_menu_product, menu);
	// SearchManager searchManager = (SearchManager)
	// (getActivity().getSystemService(Context.SEARCH_SERVICE));
	// SearchView searchView = (SearchView)
	// menu.findItem(R.id.actionSearchProductBee).getActionView();
	// searchView.setSearchableInfo(searchManager.getSearchableInfo((getActivity().getComponentName())));
	//
	// String barCode = searchView.getQuery().toString();
	// // auto search on client
	// searchView.setOnQueryTextListener((SearchView.OnQueryTextListener)
	// getActivity());
	// super.onCreateOptionsMenu(menu, inflater);
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case R.id.actionRefreshBee:
	// // save it here
	// page = 0;
	// startLoadData();
	// return true;
	// case R.id.actionSearchProductBee:
	//
	// return true;
	// default:
	// return super.onOptionsItemSelected(item);
	// }
	// }

	public void setupMenuItem(Menu menu) {
		menu.findItem(R.id.action_add).setVisible(true);
		menu.findItem(R.id.action_search).setVisible(true);
		menu.findItem(R.id.action_save).setVisible(false);
		menu.findItem(R.id.action_edit).setVisible(false);
		menu.findItem(R.id.action_refresh).setVisible(true);
		menu.findItem(R.id.action_list).setVisible(false);
		menu.findItem(R.id.action_upload).setVisible(false);
		menu.findItem(R.id.action_exit).setVisible(false);
		menu.findItem(R.id.action_saveoffline).setVisible(false);
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imvSlider1:
			for (int i = 0; i < adapter.getCount(); i++) {
				pager.setCurrentItem(0, true);
			}
			break;
		case R.id.imvSlider2:
			for (int i = 0; i < adapter.getCount(); i++) {
				pager.setCurrentItem(1, true);
			}
			break;
		case R.id.imvSlider3:
			for (int i = 0; i < adapter.getCount(); i++) {
				pager.setCurrentItem(2, true);
			}
			break;
		case R.id.imvSlider4:
			for (int i = 0; i < adapter.getCount(); i++) {
				pager.setCurrentItem(3, true);
			}
			break;
		case R.id.imvSlider5:
			for (int i = 0; i < adapter.getCount(); i++) {
				pager.setCurrentItem(4, true);
			}
			break;

		default:
			break;
		}
	}

}