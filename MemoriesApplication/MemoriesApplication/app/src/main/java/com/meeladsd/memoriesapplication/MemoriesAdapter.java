package com.meeladsd.memoriesapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alem1324 on 12/1/2015.
 */
public class MemoriesAdapter extends BaseAdapter {

    private Context _con;
    private ArrayList<String> _memTitles, _memDesc, _memIds;

    public MemoriesAdapter(Context c, ArrayList<String> titles, ArrayList<String> descs, ArrayList<String> ids) {
        _memDesc = descs;
        _memIds = ids;
        _memTitles = titles;
        _con = c;
    }

    @Override
    public int getCount() {
        return _memIds.size();
    }

    @Override
    public Object getItem(int position) {
        return _memTitles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = LayoutInflater.from(_con).
                inflate(R.layout.two_line_icon, parent, false);

        TextView text1 = (TextView) rowView.findViewById(R.id.text1);
        TextView text2 = (TextView) rowView.findViewById(R.id.text2);
        ImageView icon = (ImageView) rowView.findViewById(R.id.icon);

        text1.setText(_memTitles.get(position));
        text2.setText(_memDesc.get(position));
        icon.setImageResource(R.drawable.ic_flight_takeoff_black_36dp);

        rowView.setTag(_memIds.get(position));


        return rowView;
    }
}
