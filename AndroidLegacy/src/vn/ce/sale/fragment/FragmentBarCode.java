package vn.ce.sale.fragment;

import java.net.URI;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.adapter.CustomGrid;
import vn.ce.sale.data.ICallBack;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.fragment.vi21.FragmentOrderProduct;
import vn.ce.sale.service.ServiceManager;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.DownloadImageTask;
import vn.ce.sale.ui.ICallBackUI;
import vn.ce.sale.ui.ImageLoadingHolder;
import vn.ce.sale.ui.TypeUI;
import vn.ce.sale.ui.ZopostActivity;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.ui.ZopostValue;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.R;
import vn.ce.sale.R.layout;
import android.R.id;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentBarCode extends ZopostFragment {
	private ListView grid;
	private EditText edtBarcode;
	private Button btnOk;
	// private View rootView ;
	// start

	protected void initCreatedView() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// setup ui
		rootView = inflater.inflate(R.layout.fragment_barcode, container, false);

		setupUI();
		edtBarcode = (EditText) rootView.findViewById(R.id.edtBarcode);
		edtBarcode.setText("1512020000168");
		btnOk = (Button) rootView.findViewById(R.id.btnok);
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String allProduct = ShareMemManager.getInstance().readFromDB(getContext(), "product");
				List<JSONObject> lstAllProduct = TransformDataManager.convertStringToListJSON(allProduct);

				for (JSONObject o : lstAllProduct) {
					try {
						if (o.getString("BarCode").equalsIgnoreCase(edtBarcode.getText().toString())) {
							// TODO Auto-generated method stub
							nextToFragment(new FragmentOrderProduct(),
									BundleData.createNew().putString("item", o.toString()).data());
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// nextToFragment(new FragmentOrderProduct(),
				// BundleData.createNew().putString("barcode",
				// edtBarcode.getText().toString()).data());
			}
		});

		// start load data in other thread
		return rootView;// return super.onCreateView(inflater, container,
						// savedInstanceState);
	}

	private TextView txtDetail;
	private ImageView imgView;
	private TextView txtContent;

	@Override
	protected void setupUI() {
		super.setupUI();
		setHasOptionsMenu(true);
		// txtDetail=((TextView)rootView.findViewById(R.id.detail_title));
		// imgView=((ImageView)rootView.findViewById(R.id.detail_image));
		// txtContent=((TextView)rootView.findViewById(R.id.detail_content));
	}

	protected void fillDataSource(int status, final List<JSONObject> lst) {
		dismissLoading();
		try {
			JSONObject oDetail = lst.get(0);
			((TextView) rootView.findViewById(R.id.txtDetail1))
					.setText(oDetail.getString("ticketnumber") + "-" + oDetail.getString("tradeType") + "-"
							+ oDetail.getString("symbol") + "-" + oDetail.getString("timeframe"));
			((TextView) rootView.findViewById(R.id.txtDetail2)).setText("At:" + oDetail.getString("price") + "|SL:"
					+ (oDetail.getString("stoploss")) + "|TP:" + oDetail.getString("takeprofit"));
			((TextView) rootView.findViewById(R.id.txtDetail3))
					.setText("RR:" + oDetail.getString("rr") + "|" + oDetail.getString("risk"));
			((TextView) rootView.findViewById(R.id.txtDetail4)).setText(oDetail.getString("category"));

			final CustomGrid adapter = new CustomGrid(getActivity(),
					TransformDataManager.getListJsonByXPath(lst.get(0), "detail"),
					R.layout.fragment_grid_detail_single);
			// TicketNumber_TradeType_Currency_TimeFrame
			adapter.bindFields(new BindDataUI[] { BindDataUI.createNew(R.id.grid_image, "imgpath3", TypeUI.IMAGE) });
			grid = (ListView) rootView.findViewById(R.id.grid);
			grid.setAdapter(adapter);

			grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Toast.makeText(getActivity(), "You Clicked at " + adapter.getDataSource().get(position),
							Toast.LENGTH_SHORT).show();

					/*
					 * View viewImage =
					 * ((Activity)getContext()).getLayoutInflater().inflate(R.
					 * layout.full_image, null); ImageLoadingHolder pb_and_image
					 * = new ImageLoadingHolder(); ImageView
					 * img=(ImageView)viewImage.findViewById(R.id.grid_image);
					 * ProgressBar
					 * pb=(ProgressBar)viewImage.findViewById(R.id.grid_pb);
					 * 
					 * try {
					 * img.setTag(adapter.getDataSource().get(position).get(
					 * "imgpath")); } catch (JSONException e) { // TODO
					 * Auto-generated catch block e.printStackTrace(); }
					 * 
					 * pb_and_image.setImg(img); pb_and_image.setPb(pb);
					 * 
					 * new DownloadImageTask().execute(pb_and_image);
					 * 
					 * showDialogGridIdt("Title", viewImage, new
					 * DialogInterface.OnClickListener() {
					 * 
					 * @Override public void onClick(DialogInterface arg0, int
					 * arg1) { // TODO Auto-generated method stub
					 * 
					 * } }, new DialogInterface.OnClickListener() {
					 * 
					 * @Override public void onClick(DialogInterface arg0, int
					 * arg1) { // TODO Auto-generated method stub
					 * 
					 * } });
					 */
				}
			});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// txtDetail.setText(e.toString());
		}
	}

	@Override
	protected void initParamsForFragment() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void startLoadData() {
		// getParams();
		// TODO Auto-generated method stub
		// loadDataSource("detail",params);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		((ZopostActivity) getActivity()).getSupportActionBar()
				.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		inflater.inflate(R.menu.main_demo, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_example: {
			Toast.makeText(getActivity(), "This is message from menu of activity to fragment", Toast.LENGTH_SHORT)
					.show();
			return true;
		}
		default: {
			return super.onOptionsItemSelected(item);
		}
		}

	}
	/**/

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

}