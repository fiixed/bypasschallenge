package com.fiixed.bypasschallenge;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by abell on 1/16/14.
 */
public class CartListFragment extends ListFragment {

    private static final String TAG = "CartListFragment";

    private ArrayList<BurgersDogs> mBurgersDogs = new ArrayList<BurgersDogs>();
    private TextView mTitleTextView;
    private TextView mQuantityTextView;
    private ImageButton mAddImageButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.listfragment_title);

        //adding dummy data to ArrayList
        mBurgersDogs.add(new BurgersDogs("Cheeseburger", 0, 2.45));
        mBurgersDogs.add(new BurgersDogs("Hot dog", 0, 1.45));
        mBurgersDogs.add(new BurgersDogs("Baconburger", 3, 3.50));

        //Setup ListView
        CartAdapter cartAdapter = new CartAdapter(getActivity().getApplicationContext(), R.layout.list_item_burgerdog, mBurgersDogs);
        setListAdapter(cartAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_all_items, container, false);



        TextView costTextView = (TextView)v.findViewById(R.id.cost_textView);
        costTextView.setText("test");



        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}
