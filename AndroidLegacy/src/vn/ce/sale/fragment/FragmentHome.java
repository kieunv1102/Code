package vn.ce.sale.fragment;

import vn.ce.sale.adapter.CustomGrid;
import vn.ce.sale.data.HttpNetWorkManager;
import vn.ce.sale.data.HttpNetwork;
import vn.ce.sale.data.ICallBack;
import vn.ce.sale.R;
import vn.ce.sale.R.drawable;
import vn.ce.sale.R.id;
import vn.ce.sale.R.layout;
import android.app.ProgressDialog;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentHome extends Fragment // implements IdtUI
{

	public static final String ARG_OBJECT = "object";
	ListView grid;
	String[] web = { "Google", "Github", "Instagram", "Facebook", "Flickr", "Pinterest", "Quora", "Twitter", "Vimeo",
			"WordPress", "Youtube", "Stumbleupon", "SoundCloud", "Reddit", "Blogger"

	};
	int[] imageId = { R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher

	};
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
						FragmentHome.this.getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								showLoading();
							}
						});
						// TODO Auto-generated method stub
						vn.ce.sale.data.DataManager.factoryData().fetDataRaw("catalog", null, new ICallBack() {
							@Override
							public void postExecuteData(int status, String result) {
								Log.v("lamlt", result);
								// TODO Auto-generated method stub
								FragmentHome.this.getActivity().runOnUiThread(new Runnable() {
									@Override
									public void run() {
										// TODO Auto-generated method stub

										dismissLoading();
										fillDataSource();
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

		rootView = inflater.inflate(R.layout.fragment_home, container, false);
		// Bundle args = getArguments();
		// ((TextView) rootView.findViewById(android.R.id.text1)).setText(
		// Integer.toString(args.getInt(ARG_OBJECT)));
		progress = (ProgressBar) rootView.findViewById(R.id.form_pb);
		return rootView;
	}

	private void fillDataSource() {
		CustomGrid adapter = new CustomGrid(getActivity(), null);
		grid = (ListView) rootView.findViewById(R.id.grid);
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getActivity(), "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();

			}
		});
		grid.setVisibility(View.VISIBLE);
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