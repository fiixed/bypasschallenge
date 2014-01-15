package com.fiixed.bypasschallenge;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by abell on 1/16/14.
 */
public class BypassChallengeListFragment extends ListFragment {

    private static final String TAG = "BypassChallengeListFragment";

    private ArrayList<BurgersDogs> mBurgersDogs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.listfragment_title);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}
