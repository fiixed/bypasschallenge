package com.fiixed.bypasschallenge;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by abell on 1/16/14.
 */
public class CartListFragment extends ListFragment {

    private static final String TAG = "CartListFragment";

    private ArrayList<BurgersDogs> mBurgersDogs = new ArrayList<BurgersDogs>();
    private CartAdapter cartAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.listfragment_title);

        //adding dummy data to ArrayList
        mBurgersDogs.add(new BurgersDogs("Cheeseburger", 0, 2.45));
        mBurgersDogs.add(new BurgersDogs("Hot dog", 1, 1.45));
        mBurgersDogs.add(new BurgersDogs("Baconburger", 2, 3.50));


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Setup ListView

        cartAdapter = new CartAdapter(getActivity().getApplicationContext(), R.layout.list_item_burgerdog, mBurgersDogs);


        setListAdapter(cartAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}
