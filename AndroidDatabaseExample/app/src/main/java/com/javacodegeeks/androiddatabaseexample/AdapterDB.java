package com.javacodegeeks.androiddatabaseexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kieunv on 5/31/2016.
 */
public class AdapterDB extends BaseAdapter {
    List<String> lsdata;
    LayoutInflater layoutInflater;

    public List<String> getLsdata() {
        return lsdata;
    }

    public void setLsdata(List<String> lsdata) {
        this.lsdata = lsdata;
    }

    public AdapterDB(List<String> lsdata, Context context) {

        this.lsdata = lsdata;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lsdata.size();
    }

    @Override
    public Object getItem(int position) {
        return lsdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    TextView txtId, txtTitle;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_rcv_sql, parent,false);
        txtId= (TextView) convertView.findViewById(R.id.txtId);
        txtId.setText(lsdata.get(position));
        txtTitle= (TextView) convertView.findViewById(R.id.txtName);
        txtTitle.setText(lsdata.get(position));
        return view;
    }
}
