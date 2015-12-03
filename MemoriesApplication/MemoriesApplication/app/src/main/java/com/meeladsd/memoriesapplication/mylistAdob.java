package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by Sevag on 2015-11-26.
 */
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class mylistAdob extends BaseAdapter implements Filterable {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;
    private Activity activity;
    List<String> arrayListNames;


    public mylistAdob(ArrayList<String> list, Context context, Activity _ac) {
        this.list = list;
        activity = _ac;
        arrayListNames = list;
        this.context = context;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.rowlayout, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView) view.findViewById(R.id.label);
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button) view.findViewById(R.id.followersFriends);


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder adb = new AlertDialog.Builder(context);
                adb.setTitle("Alert")
                        .setMessage("are you sure you want to delete this user")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String friendsName = list.get(position);

                                new DeleteFriend(activity, friendsName).execute();
                                list.remove(position); //or some other task
                                notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        }).show();


            }
        });

        return view;
    }

    @Override
    public Filter getFilter() {


        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                    list = (ArrayList<String>) results.values;
                    notifyDataSetChanged();

            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                Filter.FilterResults results = new FilterResults();
                ArrayList<String> FilteredArrayNames = new ArrayList<String>();

                // perform your search here using the searchConstraint String.

                constraint = constraint.toString().toLowerCase();
                for (int i = 0; i < arrayListNames.size(); i++) {
                    String dataNames = arrayListNames.get(i);
                    if (dataNames.toLowerCase().startsWith(constraint.toString()))  {
                        FilteredArrayNames.add(dataNames);
                    }
                }

                results.count = FilteredArrayNames.size();
                results.values = FilteredArrayNames;


                return results;
            }
        };

        return filter;
    }
    }

