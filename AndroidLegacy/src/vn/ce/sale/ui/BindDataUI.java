package vn.ce.sale.ui;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.text.TextWatcher;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.TypeUI;
import vn.ce.sale.ui.ZopostValue;

public class BindDataUI {
	public static BindDataUI createNew(int idUI, String datasourceId, TypeUI typeUI) {
		return new BindDataUI(idUI, datasourceId, typeUI);
	}

	public static BindDataUI createNew(int idUI, String datasourceId, TypeUI typeUI, OnClickListener handleClick) {
		return new BindDataUI(idUI, datasourceId, typeUI, handleClick);
	}

	public static BindDataUI createNew(int idUI, String datasourceId, TypeUI typeUI,
			OnFocusChangeListener handleClick) {
		return new BindDataUI(idUI, datasourceId, typeUI, handleClick);
	}

	public static BindDataUI createNew(int idUI, String datasourceId, TypeUI typeUI, OnClickListener handleClick,
			ZopostValue funIdtValue) {
		return new BindDataUI(idUI, datasourceId, typeUI, handleClick, funIdtValue);
	}

	public static BindDataUI createNew(int idUI, String datasourceId, TypeUI typeUI, OnClickListener handleClick,
			MyTextWatcher watch, ZopostValue funIdtValue) {
		return new BindDataUI(idUI, datasourceId, typeUI, handleClick, watch);
	}

	public static BindDataUI createNew(int idUI, String datasourceId, TypeUI typeUI, MyTextWatcher watch) {
		return new BindDataUI(idUI, datasourceId, typeUI, watch);
	}

	private int idUI;
	private int position;
	private OnFocusChangeListener onFocusChange;

	public BindDataUI(int idUI, String datasourceId, TypeUI typeUI) {
		super();
		this.idUI = idUI;
		this.datasourceId = datasourceId;
		this.typeUI = typeUI;
	}

	public BindDataUI(int idUI, String datasourceId, TypeUI typeUI, OnClickListener handleClick) {
		super();
		this.idUI = idUI;
		this.datasourceId = datasourceId;
		this.typeUI = typeUI;
		this.handleClick = handleClick;
	}

	public BindDataUI(int idUI, String datasourceId, TypeUI typeUI, OnFocusChangeListener handleClick) {
		super();
		this.idUI = idUI;
		this.datasourceId = datasourceId;
		this.typeUI = typeUI;
		this.setOnFocusChange(handleClick);
	}

	public BindDataUI(int idUI, String datasourceId, TypeUI typeUI, OnClickListener handleClick, MyTextWatcher watch) {
		super();
		this.idUI = idUI;
		this.datasourceId = datasourceId;
		this.typeUI = typeUI;
		this.handleClick = handleClick;
		this.watcher = watch;
	}

	public ZopostValue getFunctionValue() {
		return functionValue;
	}

	public void setFunctionValue(ZopostValue functionValue) {
		this.functionValue = functionValue;
	}

	public BindDataUI(int idUI, String datasourceId, TypeUI typeUI, OnClickListener handleClick,
			ZopostValue funIdtValue) {
		super();//
		this.idUI = idUI;
		this.datasourceId = datasourceId;
		this.typeUI = typeUI;
		this.handleClick = handleClick;
		this.functionValue = funIdtValue;
		this.handleClick = handleClick;
	}

	public BindDataUI(int idUI, String datasourceId, TypeUI typeUI, MyTextWatcher watch) {
		// TODO Auto-generated constructor stub
		super();
		this.idUI = idUI;
		this.datasourceId = datasourceId;
		this.typeUI = typeUI;
		this.watcher = watch;
	}

	private String datasourceId;
	private ZopostValue functionValue;
	public MyTextWatcher watcher;
	private TypeUI typeUI;

	public int getIdUI() {
		return idUI;
	}

	public void setIdUI(int idUI) {
		this.idUI = idUI;
	}

	public String getDatasourceId() {
		return datasourceId;
	}

	public void setDatasourceId(String datasourceId) {
		this.datasourceId = datasourceId;
	}

	public TypeUI getTypeUI() {
		return typeUI;
	}

	public void setTypeUI(TypeUI typeUI) {
		this.typeUI = typeUI;
	}

	public boolean isText() {
		return typeUI == typeUI.TEXT;
	}

	public boolean isEditText() {
		return typeUI == typeUI.EDIT_TEXT;
	}

	public boolean isImage() {
		return typeUI == typeUI.IMAGE;
	}

	public boolean isImageStatic() {
		return typeUI == typeUI.IMAGE_STATIC;
	}

	public boolean isCheckBox() {
		return typeUI == typeUI.CHECKBOX;
	}

	public boolean isButon() {
		return typeUI == typeUI.BUTTON;
	}

	public boolean isComplex() {
		return typeUI == typeUI.COMPLEX;
	}

	public String parseValueFromSource(JSONObject o) throws JSONException {
		if (functionValue == null)
			return o.getString(this.getDatasourceId());
		return this.functionValue.parseFromSource(o, this);
	}

	private OnClickListener handleClick;

	public OnClickListener getHandleClick() {
		return handleClick;
	}

	public void setHandleClick(OnClickListener handleClick) {
		this.handleClick = handleClick;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public OnFocusChangeListener getOnFocusChange() {
		return onFocusChange;
	}

	public void setOnFocusChange(OnFocusChangeListener onFocusChange) {
		this.onFocusChange = onFocusChange;
	}

}
