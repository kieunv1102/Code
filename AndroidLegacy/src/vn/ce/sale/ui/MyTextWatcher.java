package vn.ce.sale.ui;

import android.text.TextWatcher;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;

public abstract class MyTextWatcher implements TextWatcher {

	private EditText editText;

	public MyTextWatcher() {

	}

	public EditText getEditTextView() {
		return editText;
	}

	public void setEditTextView(EditText _editText) {
		editText = _editText;
	}

	public ViewParent getParentView() {
		return editText.getParent();
	}

	public View getRootView() {
		return editText.getRootView();
	}
}