package com.fiixed.bypasschallenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by abell on 11/21/13.
 */
public abstract class SingleFragmentActivity extends FragmentActivity {
    protected abstract Fragment createFragment();  //makes the code reusable - I can extend any fragment activity without having to write out a lot of code from this



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer); //the CartListFragment may already be in the FragmentManagers list if this activity is being recreated after
        //being destroyed on rotation or to reclaim memory

        if(fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()  //returns an instance of FragmentTransaction
                    .add(R.id.fragmentContainer, fragment)  //two parameters - the container view id and the fragment gathered from the createFragment method
                    .commit();
        }
    }
}

//The container view ID serves two purposes: It tells the Fragment Manager where in the activities view the fragments view should appear, and it is used as a unique identifier for a fragment in the FragmentManagers list