package com.meeladsd.memoriesapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.meeladsd.memoriesapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class extraAdobpter extends BaseAdapter {

    private Context _con;
    private ArrayList<String> _memTitles;
    private ArrayList<String> _VacIds;
    public extraAdobpter(Context c, ArrayList<String>titles, ArrayList<String> ids) {
        _VacIds = ids;
        _memTitles = titles;

        _con = c;
    }

    @Override
    public int getCount() {
        return _VacIds.size();
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
        View rowView = LayoutInflater.from(_con).inflate(R.layout.vacationsrowlayout, parent, false);

        TextView text1 = (TextView) rowView.findViewById(R.id.Myvac_item);



        text1.setText(_memTitles.get(position));


        rowView.setTag(_VacIds.get(position));


        return rowView;
    }
}
