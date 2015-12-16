package com.meeladsd.memoriesapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by alem1324 on 12/16/2015.
 */
public class ViewMediaAdapter extends BaseAdapter {

    private Context _con;
    private ArrayList<String> _medAll, _medList, _medListVid, _medListAud;

    public ViewMediaAdapter(Context c, ArrayList<String> pics, ArrayList<String> vid, ArrayList<String> aud) {
        _medList = pics;
        _medListVid = vid;
        _medListAud = aud;
        _medAll = new ArrayList<String>();

        _medAll.addAll(_medList);
        _medAll.addAll(_medListVid);
        _medAll.addAll(_medListAud);
        _con = c;
    }
    @Override
    public int getCount() {
        return _medAll.size();
    }

    @Override
    public Object getItem(int position) {
        return _medAll.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = LayoutInflater.from(_con).
                inflate(R.layout.med_with_icon, parent, false);

        TextView text1 = (TextView) rowView.findViewById(R.id.mem_url_list);
        ImageView icon = (ImageView) rowView.findViewById(R.id.mem_icon_list);


        icon.setImageResource(R.drawable.ic_flight_takeoff_black_36dp);
        text1.setText(_medAll.get(position));

        rowView.setTag(_medAll.get(position));

        return rowView;
    }
}
