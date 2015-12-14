package com.meeladsd.memoriesapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hamster on 14/12/2015.
 */
public class ListAdapter extends ArrayAdapter<VacationItem> {

    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapter(Context context, int resource, List<VacationItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.itemlistrow, null);
        }

        VacationItem p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.ListTitle);
            TextView tt2 = (TextView) v.findViewById(R.id.ListPlace);
            TextView tt3 = (TextView) v.findViewById(R.id.ListUser);

            if (tt1 != null) {
                tt1.setText(p.Title);
            }

            if (tt2 != null) {
                tt2.setText(p.Place);
            }

            if (tt3 != null) {
                tt3.setText(p.UserName);
            }
        }

        return v;
    }

}