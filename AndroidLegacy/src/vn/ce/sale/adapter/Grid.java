package vn.ce.sale.adapter;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vn.ce.sale.ui.BindDataUI;
import vn.ce.sale.ui.DownloadImageTask;
import vn.ce.sale.ui.ImageLoadingHolder;
import vn.ce.sale.R;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Grid extends BaseAdapter {
	private Context mContext;
	private List<JSONObject> dataSource;

	public List<JSONObject> getDataSource() {
		return dataSource;
	}

	public void setDataSource(List<JSONObject> dt) {
		dataSource = dt;
	}

	private HashMap<Integer, BindDataUI> bindRule;
	private int layoutRow = -1;

	public Grid(Context c, List<JSONObject> d, int rowLineLayput) {
		mContext = c;
		dataSource = d;
		layoutRow = rowLineLayput;
	}

	public Grid(Context c, List<JSONObject> d) {
		mContext = c;
		dataSource = d;
		layoutRow = R.layout.fragment_home_single;
	}

	public void bindFields(BindDataUI[] _arrBindDataUIs) {
		// arrBindDataUIs=_arrBindDataUIs;
		bindRule = new HashMap<Integer, BindDataUI>();
		for (BindDataUI o : _arrBindDataUIs) {
			bindRule.put(o.getIdUI(), o);
		}
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
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		View grid = null;
		try {
			JSONObject lineSource = dataSource.get(position);
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// mapping datasourcename and id of view
			if (convertView == null) {

				grid = new View(mContext);
				// grid = inflater.inflate(R.layout.fragment_home_single, null);
				grid = inflater.inflate(layoutRow, null);

				// setup for holder
				final ViewHolder1 viewHolder = new ViewHolder1();
				for (Integer i : bindRule.keySet()) {
					BindDataUI o = bindRule.get(i);
					if (o.isText()) {
						TextView textView = (TextView) grid.findViewById(i.intValue());
						viewHolder.addTextView(o.getIdUI(), textView);// .text=textView;
						continue;
					}
					if (o.isEditText()) {
						EditText textView = (EditText) grid.findViewById(i.intValue());
						viewHolder.addEditText(o.getIdUI(), textView);// .text=textView;
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
						viewHolder.addImageView(o.getIdUI(), imageView, pb);
						continue;
					}
					if (o.isImageStatic()) {
						ImageView imageView = (ImageView) grid.findViewById(i.intValue());
						viewHolder.addImageViewStatic(o.getIdUI(), imageView);
						continue;
					}
				}
				grid.setTag(viewHolder);

			} else {
				grid = (View) convertView;
			}

			// bind data
			ViewHolder1 holder = (ViewHolder1) grid.getTag();
			for (Integer i : bindRule.keySet()) {
				BindDataUI o = bindRule.get(i);
				if (o.getHandleClick() != null) {
					if (o.isText() || o.isComplex()) {
						holder.text.get(o.getIdUI()).setOnClickListener(o.getHandleClick());
					}
					if (o.isImage()) {

						holder.icon.get(o.getIdUI()).setOnClickListener(o.getHandleClick());
					}
					if (o.isImageStatic()) {

						holder.icon.get(o.getIdUI()).setOnClickListener(o.getHandleClick());
					}
					if (o.isCheckBox()) {

						holder.checkbox.get(o.getIdUI()).setOnClickListener(o.getHandleClick());
					}
					if (o.isButon()) {

						holder.button.get(o.getIdUI()).setOnClickListener(o.getHandleClick());
					}
				}
				if (o.watcher != null) {
					if (o.isEditText()) {
						holder.editText.get(o.getIdUI()).addTextChangedListener(o.watcher);
					}
				}
				if (o.isText()) {
					holder.text.get(o.getIdUI()).setText(o.parseValueFromSource(lineSource));
					holder.text.get(o.getIdUI()).setTag(position);

					continue;
				}
				if (o.isEditText()) {
					holder.editText.get(o.getIdUI()).setText(o.parseValueFromSource(lineSource));
					holder.editText.get(o.getIdUI()).setTag(position);
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
				if (o.isImageStatic()) {
					holder.icon.get(o.getIdUI()).setTag(position);
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

	static class ViewHolder1 {
		SparseArray<CheckBox> checkbox = new SparseArray<CheckBox>();
		SparseArray<TextView> text = new SparseArray<TextView>();
		SparseArray<EditText> editText = new SparseArray<EditText>();
		SparseArray<ImageView> icon = new SparseArray<ImageView>();
		SparseArray<ProgressBar> pb = new SparseArray<ProgressBar>();
		SparseArray<Button> button = new SparseArray<Button>();

		public void addEditText(int idui, EditText view) {
			editText.append(idui, view);
		}

		public void addTextView(int idui, TextView view) {
			text.append(idui, view);
		}

		public void addButton(int idui, Button view) {
			button.append(idui, view);
		}

		public void addCheckBox(int idui, CheckBox view) {
			checkbox.append(idui, view);
		}

		public void addImageView(int idUI, ImageView imageView, ProgressBar pb2) {
			// TODO Auto-generated method stub
			icon.append(idUI, imageView);
			pb.append(idUI, pb2);
		}

		public void addImageViewStatic(int idUI, ImageView imageView) {
			// TODO Auto-generated method stub
			icon.append(idUI, imageView);
		}
	}
}