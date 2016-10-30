package vn.ce.sale.fragment.bee;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.Home;
import vn.ce.sale.LoginActivity;
import vn.ce.sale.adapter.CustomGrid;
import vn.ce.sale.adapter.CustomGridAndFilter;
import vn.ce.sale.adapter.bee.AdapterCategoriesProductGrid;
import vn.ce.sale.adapter.bee.BeeAutoAdapterSearchProduct;
import vn.ce.sale.adapter.bee.DisplayProductGrid;
import vn.ce.sale.adapter.bee.PagerAdapterCustomerViewBanner;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.fragment.FragmentBarCode;
import vn.ce.sale.fragment.Fragment_Customer_Edit;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.TypeUI;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.ui.ZopostValue;
import vn.ce.sale.util.CircleImageView;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.TimeUtil;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.R.integer;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.MenuInflater;

public class Bee_Fragment_Category_Product_List extends ZopostFragment
		implements ICallBackUI, OnClickListener, ICallBackActivityToFragment {

	private ProgressDialog pd;
	ListView lvAllProduct;
	AdapterCategoriesProductGrid adapter;
	PagerAdapterCustomerViewBanner adapterPager;
	ViewPager viewPagerBanner;
	List<JSONObject> lJsonObjectsBanner = new ArrayList<JSONObject>();
	ImageView viewDialogMenu;
	Dialog dialog;
	RelativeLayout layoutPromotionMenu, layoutNewMenu, layoutOrdestMenu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		((Home) getActivity()).getSupportActionBar().show();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.bee_layout_customer_grid_all_product, container, false);
		lvAllProduct = (ListView) rootView.findViewById(R.id.lvAllProduct);
		viewDialogMenu = (ImageView) rootView.findViewById(R.id.viewDialogMenu);
		viewPagerBanner = (ViewPager) rootView.findViewById(R.id.viewpagerBanner);
		layoutPromotionMenu = (RelativeLayout) rootView.findViewById(R.id.layoutPromotionMenu);
		layoutNewMenu = (RelativeLayout) rootView.findViewById(R.id.layoutNewMenu);
		layoutOrdestMenu = (RelativeLayout) rootView.findViewById(R.id.layoutOrdestMenu);
		layoutPromotionMenu.setOnClickListener(this);
		layoutNewMenu.setOnClickListener(this);
		layoutOrdestMenu.setOnClickListener(this);
		createDialog();

		viewDialogMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.show();
				viewDialogMenu.setBackgroundResource(R.drawable.floatbuttonmenuclose);
			}
		});

		dialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub
				viewDialogMenu.setBackgroundResource(R.drawable.floatbuttonmenuopen);
			}
		});

		pd = new ProgressDialog(getActivity());

		// ServiceManager.factoryData()
		// .getDataRaw("http://192.168.10.202:7963/bee/process/?data={\"ActionType\":\"PRODUCT\""
		// + ",\"UserName\":\""
		// + ShareMemManager.getInstance().readFromDB(getContext(), "username")
		// + "\",\"Password\":\""
		// + ShareMemManager.getInstance().readFromDB(getContext(), "password")
		// + "\",\"FromDate\":\""
		// + "20150115000000" + "\",\"ToDate\":\"" + "20151216235959" + "\"}",
		// "",
		// DataOrder.NETWORK_THEN_CACHE, this);

		setHasOptionsMenu(true);
		return rootView;
	}

	@SuppressWarnings("static-access")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.layoutPromotionMenu:
			FragmentTransaction fmdetail1 = ((FragmentActivity) getActivity()).getSupportFragmentManager()
					.beginTransaction();
			fmdetail1.replace(R.id.container, new Bee_Fragment_Product_List().newInstance("a", "b"));
			fmdetail1.addToBackStack("tag");
			fmdetail1.commit();
			break;
		case R.id.layoutNewMenu:
			FragmentTransaction fmdetail2 = ((FragmentActivity) getActivity()).getSupportFragmentManager()
					.beginTransaction();
			fmdetail2.replace(R.id.container, new Bee_Fragment_Product_List().newInstance("a", "b"));
			fmdetail2.addToBackStack("tag");
			fmdetail2.commit();
			break;
		case R.id.layoutOrdestMenu:
			FragmentTransaction fmdetail3 = ((FragmentActivity) getActivity()).getSupportFragmentManager()
					.beginTransaction();
			fmdetail3.replace(R.id.container, new Bee_Fragment_Product_List().newInstance("a", "b"));
			fmdetail3.addToBackStack("tag");
			fmdetail3.commit();
			break;

		default:
			break;
		}
	}

	private void createDialog() {
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.bee_custom_dialog_category);
		WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
		wmlp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
		wmlp.x = 10;
		wmlp.y = 180;
		dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
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
	protected void startLoadData() {
		final ProgressDialog pd = new ProgressDialog(getContext());
		pd.setMessage("Đang gửi dữ liệu xác nhận!");
		pd.show();
		ServiceManager.factoryData().getDataRaw(
				"http://192.168.10.214:9981/mobile/GetAllProduct?categoryid=0&username="
						+ ShareMemManager.getInstance().readFromDB(getActivity(), "username") + "&password="
						+ ShareMemManager.getInstance().readFromDB(getActivity(), "password"),
				"", DataOrder.NETWORK_THEN_CACHE, new ICallBackUI() {

					@Override
					public void process(String key, int status, List<JSONObject> lst) {
						// TODO Auto-generated method stub

					}

					@Override
					public void processRaw(String key, final int status, final String json) {
						// TODO Auto-generated method stub
						runOnUiThreadX(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (status == -1001) {
									pd.dismiss();
								}

								if (status == 200) {
									pd.dismiss();
									JSONObject oJson;
									List<JSONObject> lJson = new ArrayList<JSONObject>();
									try {
										oJson = new JSONObject(json);

										String arrw = oJson.getString("ResponseMessage");
										lJson = TransformDataManager.convertArrayToListJSON(new JSONArray(arrw));
										for (JSONObject obj : lJson) {
											if (obj.getString("PriceInfo").equals("[]")) {

											} else {
												lJsonObjectsBanner.add(obj);
											}
										}
										adapterPager = new PagerAdapterCustomerViewBanner(getActivity(),
												lJsonObjectsBanner);
										viewPagerBanner.setAdapter(adapterPager);
										final Handler handler = new Handler();
										Runnable runnable = new Runnable() {
											@Override
											public void run() {
												while (!Thread.interrupted()) {
													for (int i = 0; i < adapterPager.getCount(); i++) {
														final int value = i;
														try {
															Thread.sleep(5000);
														} catch (InterruptedException e) {
															e.printStackTrace();
														}
														handler.post(new Runnable() {
															@Override
															public void run() {
																viewPagerBanner.setCurrentItem(value, true);
															}
														});
													}
												}
											}
										};
										new Thread(runnable).start();
										adapterPager.notifyDataSetChanged();
										refreshData();
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

								}
							}
						});
					}

				});

		// try {
		// List<JSONObject> lJson = new ArrayList<JSONObject>();
		// String arr = ShareMemManager.getInstance().readFromDB(getActivity(),
		// "ProductBee");
		// lJson = TransformDataManager.convertArrayToListJSON(new
		// JSONArray(arr));
		// for (JSONObject obj : lJson) {
		// if (obj.getString("PriceInfo").equals("[]")) {
		//
		// } else {
		// lJsonObjectsBanner.add(obj);
		// }
		// }
		// adapterPager = new PagerAdapterCustomerViewBanner(getActivity(),
		// lJsonObjectsBanner);
		// viewPagerBanner.setAdapter(adapterPager);
		// final Handler handler = new Handler();
		// Runnable runnable = new Runnable() {
		// @Override
		// public void run() {
		// while (!Thread.interrupted()) {
		// for (int i = 0; i < adapterPager.getCount(); i++) {
		// final int value = i;
		// try {
		// Thread.sleep(5000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// handler.post(new Runnable() {
		// @Override
		// public void run() {
		// viewPagerBanner.setCurrentItem(value, true);
		// }
		// });
		// }
		// }
		// }
		// };
		// new Thread(runnable).start();
		// adapterPager.notifyDataSetChanged();
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	protected void refreshData() {
		try {
			List<JSONObject> lstJsonObjects = new ArrayList<JSONObject>();
			String arrw = ShareMemManager.getInstance().readFromDB(getActivity(), "CategoryName");
			lstJsonObjects = TransformDataManager.convertArrayToListJSON(new JSONArray(arrw));
			adapter = new AdapterCategoriesProductGrid(getActivity(), lstJsonObjects, lJsonObjectsBanner);
			lvAllProduct.setAdapter(adapter);
			adapter.notifyDataSetChanged();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void process(String key, int status, List<JSONObject> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processRaw(String key, final int status, final String json) {
		// TODO Auto-generated method stub
		runOnUiThreadX(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (status == -1001) {
					pd.dismiss();
					return;
				}
				// displayloading with json is percentage of
				// loading data
				if (status == 200) {
					pd.dismiss();
					JSONObject oJson;
					List<JSONObject> lJson = new ArrayList<JSONObject>();

					try {
						oJson = new JSONObject(json);
						// lJsonObjectsBanner.add(new
						// JSONObject("{\"UnitName\":\"Thùng\",\"Price\":9000}"));
						// lJsonObjectsBanner.add(new
						// JSONObject("{\"UnitName\":\"Gói\",\"Price\":9000}"));
						// lJsonObjectsBanner.add(new
						// JSONObject("{\"UnitName\":\"Thỏi\",\"Price\":9000}"));
						// String res = "[{\"Name\":\"Xả
						// vải\",\"PriceInfo\":\"[{\"UnitName\":\"Thùng\",\"Price\":9000}]\"}]";
						String arr = oJson.getString("ResponseMessage");
						lJson = TransformDataManager.convertArrayToListJSON(new JSONArray(arr));
						// lJson.add(new
						// JSONObject("{\"Name\":\"Xả
						// vải\",\"PriceInfo\":\"[{\"UnitName\":\"Thùng\",\"Price\":9000}]\"}"));
						for (JSONObject obj : lJson) {
							if (obj.getString("PriceInfo").equals("[]")) {

							} else {
								lJsonObjectsBanner.add(obj);
							}
						}
						ShareMemManager.getInstance().saveToDB(getContext(), "allProduct",
								lJsonObjectsBanner.toString());
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					adapterPager = new PagerAdapterCustomerViewBanner(getActivity(), lJsonObjectsBanner);
					viewPagerBanner.setAdapter(adapterPager);
					final Handler handler = new Handler();
					Runnable runnable = new Runnable() {
						@Override
						public void run() {
							while (!Thread.interrupted()) {
								for (int i = 0; i < adapterPager.getCount(); i++) {
									final int value = i;
									try {
										Thread.sleep(5000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									handler.post(new Runnable() {
										@Override
										public void run() {
											viewPagerBanner.setCurrentItem(value, true);
										}
									});
								}
							}
						}
					};
					new Thread(runnable).start();
					adapterPager.notifyDataSetChanged();

				}
			}
		});

	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onParamsFromActivity(Bundle data) {
		// TODO Auto-generated method stub

	}

}