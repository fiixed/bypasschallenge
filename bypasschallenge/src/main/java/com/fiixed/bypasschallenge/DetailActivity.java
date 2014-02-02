package com.fiixed.bypasschallenge;

import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by abell on 27/01/2014.
 */
public class DetailActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {

        //gets the extra from the intent
        UUID burgerId = (UUID)getIntent().getSerializableExtra(DetailFragment.EXTRA_BURGERSDOGS_ID);
        String burgerTitle = getIntent().getStringExtra(DetailFragment.EXTRA_BURGERSDOGS_TITLE);
        int burgerQuantity = getIntent().getIntExtra(DetailFragment.EXTRA_BURGERSDOGS_QUANTITY, 0);
        double burgerPrice = getIntent().getDoubleExtra(DetailFragment.EXTRA_BURGERSDOGS_PRICE, 0);

        return DetailFragment.newInstance(burgerId, burgerTitle, burgerQuantity, burgerPrice);
    }
}
