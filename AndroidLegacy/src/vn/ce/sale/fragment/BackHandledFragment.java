package vn.ce.sale.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import vn.ce.sale.ui.ICallBackFragmentToActivity;
import vn.ce.sale.ui.ZopostFragment;



public abstract class BackHandledFragment extends ZopostFragment{

	protected ICallBackFragmentToActivity mBackHandledInterface;

	public abstract boolean onBackPressed();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!(getActivity() instanceof ICallBackFragmentToActivity)){
			throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
		}else{
			this.mBackHandledInterface = (ICallBackFragmentToActivity)getActivity();
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		mBackHandledInterface.setSelectedFragment(this);
	}
	
}
