package vn.ce.sale.fragment;

import java.util.List;

import org.json.JSONObject;

import vn.ce.sale.DetailActivity;
import vn.ce.sale.adapter.CustomCatalogList;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class FragmentListViewCatalogIdt extends ZopostFragment {
	private ListView listview;

	// private View rootView ;
	// start
	@Override
	protected void initParamsForFragment() {
		Bundle bund = new Bundle();
		bund.putInt("number", 0);
		bund.putString("title", "ListView Catalog");
		paramsToActivity = bund;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// setup ui
		rootView = inflater.inflate(R.layout.fragment_listview_catalog, container, false);

		setupUI();

		// start load data in other thread

		return rootView;// return super.onCreateView(inflater, container,
						// savedInstanceState);
	}

	protected void fillDataSource(int status, final List<JSONObject> lst) {
		dismissLoading();

		CustomCatalogList adapter = new CustomCatalogList(getActivity(), lst);
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

	@Override
	protected void startLoadData() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}
}