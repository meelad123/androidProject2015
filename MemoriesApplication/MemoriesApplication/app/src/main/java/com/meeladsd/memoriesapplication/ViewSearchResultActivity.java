package com.meeladsd.memoriesapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

public class ViewSearchResultActivity extends ActionBarActivity {
    private SearchView _sr;
    private ListView _lstResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_result);
        _lstResult = (ListView) findViewById(R.id.search_result_list);
        _sr = (SearchView)findViewById(R.id.search_memories);

        _sr.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                new ViewSearchResult(query, _lstResult, ViewSearchResultActivity.this, getApplicationContext()).execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
