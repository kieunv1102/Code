package vn.ce.sale;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import vn.ce.sale.adapter.CustomGridDummy;
import vn.ce.sale.R;

public class FragmentDetail extends Fragment {
	CustomGridDummy adapter;
	String[] web = { "aaaa", "aaaa", "aaaa", "aaaa", "aaaa", "aaaa", "aaaa", "aaaa", "aaaa" };
	int[] Imageid = { R.drawable.d_video, R.drawable.d_video, R.drawable.d_video, R.drawable.d_video,
			R.drawable.d_video, R.drawable.d_video, R.drawable.d_video, R.drawable.d_video, R.drawable.d_video };
	GridView grvDetail;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.detail_product, container, false);
		// grvDetail = (GridView) rootView.findViewById(R.id.grv_detail);
		// adapter = new CustomGridDummy(getActivity(), web, Imageid);
		// grvDetail.setAdapter(adapter);
		// adapter.notifyDataSetChanged();

		return rootView;
	}
}
