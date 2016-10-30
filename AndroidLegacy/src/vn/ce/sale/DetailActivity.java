package vn.ce.sale;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import vn.ce.sale.adapter.CustomGridDummy;
import vn.ce.sale.R;

public class DetailActivity extends FragmentActivity {
	CustomGridDummy adapter;
	FrameLayout frameContainer;
	String[] web = { "aaaa", "aaaa", "aaaa", "aaaa", "aaaa", "aaaa", "aaaa", "aaaa", "aaaa" };
	int[] Imageid = { R.drawable.d_video, R.drawable.d_video, R.drawable.d_video, R.drawable.d_video,
			R.drawable.d_video, R.drawable.d_video, R.drawable.d_video, R.drawable.d_video, R.drawable.d_video };
	GridView grvDetail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		frameContainer = (FrameLayout) findViewById(R.id.frame_container);
		grvDetail = (GridView) findViewById(R.id.grv_detail);
		adapter = new CustomGridDummy(getApplicationContext(), web, Imageid);
		grvDetail.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		grvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				/*
				 * FragmentManager manager = getSupportFragmentManager();
				 * FragmentTransaction transaction = manager.beginTransaction();
				 * FragmentDetail frg = new FragmentDetail();
				 * transaction.replace(R.id.frame_container, frg);
				 * transaction.commit();
				 */
			}
		});
	}

}
