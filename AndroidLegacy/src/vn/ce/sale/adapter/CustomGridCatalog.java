package vn.ce.sale.adapter;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.adapter.CustomGrid.ViewHolder1;
import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.DownloadImageTask;
import vn.ce.sale.ui.ImageLoadingHolder;
import vn.ce.sale.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CustomGridCatalog extends BaseAdapter {

	private static final int ITEM_VIEW_TYPE_SEPARATOR = 0;// header
	private static final int ITEM_VIEW_TYPE_REGULAR = 1;// header
	// -- Separators and Regular rows --: number of different other
	private static final int ITEM_VIEW_TYPE_COUNT = 2;

	private Context mContext;
	List<JSONObject> dataSource;

	public List<JSONObject> getDataSource() {
		return dataSource;
	}

	public void setDataSource(List<JSONObject> dataSource) {
		this.dataSource = dataSource;
	}

	private int layoutRowCatalog;
	private int layoutRowItem;// R.layout.fragment_listview_section_header;R.layout.fragment_listview_section_item
	private String filerKeyCatalog;
	private HashMap<Integer, BindDataUI> bindRule;
	private HashMap<Integer, BindDataUI> bindRuleCatalog;

	public void bindFields(BindDataUI[] _arrBindDataUIs) {
		// arrBindDataUIs=_arrBindDataUIs;
		bindRule = new HashMap<Integer, BindDataUI>();
		for (BindDataUI o : _arrBindDataUIs) {
			bindRule.put(o.getIdUI(), o);
		}
	}

	public void bindFieldCatalog(BindDataUI[] _arrBindDataUIs) {
		// arrBindDataUIs=_arrBindDataUIs;
		bindRuleCatalog = new HashMap<Integer, BindDataUI>();
		for (BindDataUI o : _arrBindDataUIs) {
			bindRuleCatalog.put(o.getIdUI(), o);
		}
	}

	public CustomGridCatalog(Context c, List<JSONObject> _dataSource, int _layoutRowCatalog, int _layoutRowItem,
			String _filerKeyCatalog) {
		mContext = c;
		dataSource = _dataSource;
		layoutRowCatalog = _layoutRowCatalog;
		layoutRowItem = _layoutRowItem;
		filerKeyCatalog = _filerKeyCatalog;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataSource.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getItemViewType(int position) {
		boolean isSection = dataSource.get(position).has(filerKeyCatalog);// it
																			// is
																			// very
																			// important
																			// for
																			// developers
																			// to
																			// use
																			// this
																			// framework
		if (isSection) {
			return ITEM_VIEW_TYPE_SEPARATOR;
		} else {
			return ITEM_VIEW_TYPE_REGULAR;
		}
	}

	@Override
	public int getViewTypeCount() {
		return ITEM_VIEW_TYPE_COUNT;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		JSONObject lineSource = dataSource.get(position);
		// TODO Auto-generated method stub
		View grid = null;
		HashMap<Integer, BindDataUI> bindRulexxxHashMap;
		int itemViewType = getItemViewType(position);
		if (itemViewType == ITEM_VIEW_TYPE_REGULAR)
			bindRulexxxHashMap = bindRule;
		else
			bindRulexxxHashMap = bindRuleCatalog;

		try {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {
				final ViewHolder1 viewHolder = new ViewHolder1();

				// view = new View(mContext);
				if (itemViewType == ITEM_VIEW_TYPE_SEPARATOR) {
					grid = inflater.inflate(layoutRowCatalog, null);

				} else {
					grid = inflater.inflate(layoutRowItem, null);

				}

				// setup for holder
				// final ViewHolder1 viewHolder= new ViewHolder1();
				for (Integer i : bindRulexxxHashMap.keySet()) {
					BindDataUI o = bindRulexxxHashMap.get(i);
					if (o.isText()) {
						TextView textView = (TextView) grid.findViewById(i.intValue());
						viewHolder.addTextView(o.getIdUI(), textView);// .text=textView;
						continue;
					}
					if (o.isComplex()) {
						TextView textView = (TextView) grid.findViewById(i.intValue());
						viewHolder.addTextView(o.getIdUI(), textView);// .text=textView;
						continue;
					}
					if (o.isButon()) {
						Button button = (Button) grid.findViewById(i.intValue());
						viewHolder.addButton(o.getIdUI(), button);// .text=textView;
						continue;
					}
					if (o.isCheckBox()) {
						CheckBox chk = (CheckBox) grid.findViewById(i.intValue());
						viewHolder.addCheckBox(o.getIdUI(), chk);// .text=textView;
						continue;
					}
					if (o.isImage()) {
						ImageView imageView = (ImageView) grid.findViewById(i.intValue());
						ProgressBar pb = (ProgressBar) grid.findViewById(R.id.grid_pb);
						// viewHolder.icon=imageView;
						// viewHolder.pb=pb;
						viewHolder.addImageView(o.getIdUI(), imageView, pb);
						continue;
					}
				}
				grid.setTag(viewHolder);

			} else {
				grid = (View) convertView;
			}

			// bind data
			ViewHolder1 holder = (ViewHolder1) grid.getTag();
			for (Integer i : bindRulexxxHashMap.keySet()) {
				BindDataUI o = bindRulexxxHashMap.get(i);
				if (o.getHandleClick() != null) {
					if (o.isText() || o.isComplex()) {
						holder.text.get(o.getIdUI()).setOnClickListener(o.getHandleClick());
					}
					if (o.isImage()) {

						holder.icon.get(o.getIdUI()).setOnClickListener(o.getHandleClick());
					}
					if (o.isCheckBox()) {

						holder.checkbox.get(o.getIdUI()).setOnClickListener(o.getHandleClick());
					}
					if (o.isButon()) {

						holder.button.get(o.getIdUI()).setOnClickListener(o.getHandleClick());
					}
				}
				if (o.isText()) {
					holder.text.get(o.getIdUI()).setText(o.parseValueFromSource(lineSource));
					holder.text.get(o.getIdUI()).setTag(position);

					continue;
				}
				if (o.isComplex()) {
					holder.text.get(o.getIdUI()).setText(o.parseValueFromSource(lineSource));
					holder.text.get(o.getIdUI()).setTag(position);
					continue;
				}
				if (o.isButon()) {
					holder.button.get(o.getIdUI()).setText(o.parseValueFromSource(lineSource));
					holder.button.get(o.getIdUI()).setTag(position);
					continue;
				}
				if (o.isCheckBox()) {
					holder.checkbox.get(o.getIdUI())
							.setChecked(o.parseValueFromSource(lineSource).equalsIgnoreCase("1"));
					holder.checkbox.get(o.getIdUI()).setTag(position);
					continue;
				}
				if (o.isImage()) {
					holder.icon.get(o.getIdUI()).setTag(o.parseValueFromSource(lineSource));
					holder.icon.get(o.getIdUI()).setId(position);

					ImageLoadingHolder pb_and_image = new ImageLoadingHolder();
					pb_and_image.setImg(holder.icon.get(o.getIdUI()));
					pb_and_image.setPb(holder.pb.get(o.getIdUI()));
					new DownloadImageTask().execute(pb_and_image);
					continue;
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return grid;
	}

}