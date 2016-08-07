package com.tv.gamelauncher.ui;

import android.support.v17.leanback.app.SearchFragment;
import android.support.v17.leanback.app.SearchFragment.SearchResultProvider;
import android.support.v17.leanback.widget.ObjectAdapter;

/**
 * Created by Francis on 2016/8/5.
 */

public class MySearchFragment extends SearchFragment implements SearchResultProvider {
    @Override
    public ObjectAdapter getResultsAdapter() {
        return null;
    }

    @Override
    public boolean onQueryTextChange(String newQuery) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
