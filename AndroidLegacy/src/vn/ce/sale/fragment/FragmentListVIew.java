package vn.ce.sale.fragment;

import java.sql.DataTruncation;
import java.util.Random;

import vn.ce.sale.DetailActivity;
import vn.ce.sale.adapter.CustomGrid;
import vn.ce.sale.adapter.CustomList;
import vn.ce.sale.data.HttpNetWorkManager;
import vn.ce.sale.data.HttpNetwork;
import vn.ce.sale.data.ICallBack;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.R;
import vn.ce.sale.R.id;
import vn.ce.sale.R.layout;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentListVIew extends Fragment {

	public static final String ARG_OBJECT = "object";
	ListView listview;
	View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		try {
			new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try

					{
						FragmentListVIew.this.getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								showLoading();
							}
						});
						// TODO Auto-generated method stub
						vn.ce.sale.data.DataManager.factoryData().fetDataRaw("catalog", null, new ICallBack() {
							@Override
							public void postExecuteData(int status, final String result) {
								Log.v("lamlt", result);
								// TODO Auto-generated method stub
								FragmentListVIew.this.getActivity().runOnUiThread(new Runnable() {
									@Override
									public void run() {
										// TODO Auto-generated method stub

										dismissLoading();
										fillDataSource(result);
									}
								});
							}
						});

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		rootView = inflater.inflate(R.layout.fragment_listview, container, false);
		// Bundle args = getArguments();
		// ((TextView) rootView.findViewById(android.R.id.text1)).setText(
		// Integer.toString(args.getInt(ARG_OBJECT)));
		progress = (ProgressBar) rootView.findViewById(R.id.form_pb);
		return rootView;
	}

	private void fillDataSource(String result) {
		CustomList adapter = new CustomList(getActivity(), TransformDataManager.getListJsonByXPath(result, "movies"));
		listview = (ListView) rootView.findViewById(R.id.grid);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getActivity(), "You Clicked at " + position, Toast.LENGTH_SHORT).show();
				startActivity(new Intent(getActivity(), DetailActivity.class));

			}
		});
		listview.setVisibility(View.VISIBLE);
	}

	ProgressBar progress;

	private void showLoading() {
		progress.setVisibility(View.VISIBLE);
	}

	private void dismissLoading() {
		if (progress != null)
			progress.setVisibility(View.GONE);
	}
}