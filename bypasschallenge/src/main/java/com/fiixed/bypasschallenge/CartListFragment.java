package com.fiixed.bypasschallenge;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by abell on 1/16/14.
 */
public class CartListFragment extends ListFragment implements CartAdapter.UpdatePriceListener {

    private static final String TAG = "CartListFragment";
    protected ArrayList<BurgersDogs> myData = new ArrayList<BurgersDogs>();
    protected Object mActionMode;
    public int selectedItem = -1;
    private TextView costTextView;
    CartAdapter cartAdapter;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.listfragment_title);
        setHasOptionsMenu(true);  //allows a fragment to populate the options menu
        new FetchItemsTask().execute();  //starts the AsyncTask and runs doInBackground()
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //get the burger from the adapter
        BurgersDogs burger = ((CartAdapter)getListAdapter()).getItem(position);

        //Start DetailActivity
        Intent i = new Intent(getActivity(), DetailActivity.class);
        i.putExtra(DetailFragment.EXTRA_BURGERSDOGS_ID, burger.getId());
        i.putExtra(DetailFragment.EXTRA_BURGERSDOGS_TITLE, burger.getTitle());
        i.putExtra(DetailFragment.EXTRA_BURGERSDOGS_QUANTITY, burger.getPrice());
        i.putExtra(DetailFragment.EXTRA_BURGERSDOGS_PRICE, burger.getPrice());
        startActivity(i);
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_all_items, container, false);

        //Setup ListView
        cartAdapter = new CartAdapter(getActivity().getApplicationContext(), R.layout.list_item_burgerdog, myData, this);
        setListAdapter(cartAdapter);

        costTextView = (TextView) v.findViewById(R.id.cost_textView);


        ListView listView = (ListView) v.findViewById(android.R.id.list);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            registerForContextMenu(listView);
        } else {
            //Implements contextual action mode
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    if (mActionMode != null) {
                        return false;
                    }
                    selectedItem = position;
                    // start the CAB using the ActionMode.Callback defined above
                    mActionMode = getActivity()
                            .startActionMode(new ActionMode.Callback() {
                                @Override
                                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                                    MenuInflater inflater1 = mode.getMenuInflater();
                                    inflater1.inflate(R.menu.cart_list_item_context, menu);
                                    return true;
                                }

                                @Override
                                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                                    return false;
                                }

                                @Override
                                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                                    cartAdapter = (CartAdapter) getListAdapter();
                                    BurgersDogs burger = cartAdapter.getItem(selectedItem);
                                    switch (item.getItemId()) {
                                        case R.id.menu_item_delete_quantity:
                                            burger.removeQuantity();
                                            updatePrice(myData);
                                            cartAdapter.notifyDataSetChanged();
                                            // the Action was executed, close the CAB
                                            mode.finish();

                                            return true;
                                        default:
                                            return false;
                                    }
                                }

                                @Override
                                public void onDestroyActionMode(ActionMode mode) {
                                    mActionMode = null;
                                    selectedItem = -1;
                                }
                            });
                    view.setSelected(true);
                    return true;
                }
            });
        }


        return v;
    }

    /*
    updates the total price whenever called
     */

    public double updatePrice(ArrayList<BurgersDogs> item) {
        double total = 0.0;

        // Loop through each object in the array to find its quantity and price, then add these to total
        for (BurgersDogs b : item) {
            total = total + b.getPrice() * b.getQuantity();
        }
        costTextView.setText("$" + String.valueOf(total));

        return total;
    }


    /**
     * Creates contextual menu
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.cart_list_item_context, menu);
    }

    /**
     * For pre-Honeycomb devices
     */

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        CartAdapter adapter = (CartAdapter) getListAdapter();
        BurgersDogs burger = adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_delete_quantity:
                burger.removeQuantity();
                updatePrice(myData);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }


    /**
     * creates background thread
     */
    private class FetchItemsTask extends AsyncTask<Void, Void, ArrayList<BurgersDogs>> {
        @Override
        protected ArrayList<BurgersDogs> doInBackground(Void... params) {
            return new JSONFetcher().fetchItems();

        }

        @Override
        protected void onPostExecute(ArrayList<BurgersDogs> items) {  //run in the main UI thread, not the background
            myData = items;
            cartAdapter.updateData(myData);


        }
    }
}
