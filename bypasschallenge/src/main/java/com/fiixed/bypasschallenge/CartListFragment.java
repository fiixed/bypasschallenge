package com.fiixed.bypasschallenge;

import android.annotation.TargetApi;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by abell on 1/16/14.
 */
public class CartListFragment extends ListFragment {

    private static final String TAG = "CartListFragment";
    protected ArrayList<BurgersDogs> item2;
    protected Object mActionMode;
    public int selectedItem = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.listfragment_title);
        setHasOptionsMenu(true);
        new FetchItemsTask().execute();  //starts the AsyncTask and runs doInBackground()
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_all_items, container, false);

        ListView listView = (ListView) v.findViewById(android.R.id.list);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            registerForContextMenu(listView);
        } else {
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    if(mActionMode != null) {
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
                                    CartAdapter adapter = (CartAdapter) getListAdapter();
                                    BurgersDogs burger = adapter.getItem(selectedItem);
                                    switch (item.getItemId()) {
                                        case R.id.menu_item_delete_quantity:
                                            burger.removeQuantity();
                                            adapter.notifyDataSetChanged();
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

        TextView costTextView = (TextView) v.findViewById(R.id.cost_textView);
        costTextView.setText("test");


        return v;
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.cart_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        CartAdapter adapter = (CartAdapter) getListAdapter();
        BurgersDogs burger = adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_delete_quantity:
                burger.removeQuantity();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }


    /*
        creates background thread
         */
    private class FetchItemsTask extends AsyncTask<Void, Void, ArrayList<BurgersDogs>> {
        @Override
        protected ArrayList<BurgersDogs> doInBackground(Void... params) {
            return new JSONFetcher().fetchItems();

        }

        @Override
        protected void onPostExecute(ArrayList<BurgersDogs> items) {  //run in the main UI thread, not the background
            //Setup ListView
            CartAdapter cartAdapter = new CartAdapter(getActivity().getApplicationContext(), R.layout.list_item_burgerdog, items);
            setListAdapter(cartAdapter);
            item2 = items;

        }
    }
}
