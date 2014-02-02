package com.fiixed.bypasschallenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by abell on 27/01/2014.
 */
public class DetailFragment extends Fragment {

    String burgerTitle;
    int burgerQuantity;
    double burgerPrice;

    public static final String EXTRA_BURGERSDOGS_ID = "com.fiixed.bypasschallenge.burgersdogs_id";
    public static final String EXTRA_BURGERSDOGS_TITLE = "com.fiixed.bypasschallenge.burgersdogs_title";
    public static final String EXTRA_BURGERSDOGS_QUANTITY = "com.fiixed.bypasschallenge.burgersdogs_quantity";
    public static final String EXTRA_BURGERSDOGS_PRICE = "com.fiixed.bypasschallenge.burgersdogs_price";

    public static DetailFragment newInstance(UUID burgerID, String burgerTitle, int burgerQuantity, double burgerPrice) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_BURGERSDOGS_ID, burgerID);
        args.putString(EXTRA_BURGERSDOGS_TITLE, burgerTitle);
        args.putInt(EXTRA_BURGERSDOGS_QUANTITY, burgerQuantity);
        args.putDouble(EXTRA_BURGERSDOGS_PRICE, burgerPrice);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        burgerTitle = getArguments().getString(EXTRA_BURGERSDOGS_TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detail_fragment, container, false);

        TextView titleTextView = (TextView)v.findViewById(R.id.title_textView);
        titleTextView.setText(burgerTitle);


        return v;
    }
}
