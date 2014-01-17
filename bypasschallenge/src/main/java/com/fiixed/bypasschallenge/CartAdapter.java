package com.fiixed.bypasschallenge;

import android.app.ListFragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by abell on 1/16/14.
 */
public class CartAdapter extends ArrayAdapter<BurgersDogs>  {

    Context mContext;
    int mLayoutResourceId;
    ArrayList<BurgersDogs> mData;
    UpdatePriceListener updatePriceListener;



    public CartAdapter(Context context, int layoutResourceId, ArrayList<BurgersDogs> data) {
        super(context, layoutResourceId, data);
        this.mContext = context;
        this.mLayoutResourceId = layoutResourceId;
        this.mData = data;


    }
    public interface UpdatePriceListener {
        public double updatePrice(ArrayList<BurgersDogs> mData);
    }

    @Override
    public BurgersDogs getItem(int position) {
        return super.getItem(position);
    }

    public int getCount() {
        return mData.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BurgersDogsHolder holder;

        View row = convertView;
        //inflate the layout for a single row
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        if (row == null) {//if the row has not been created before
            row = layoutInflater.inflate(mLayoutResourceId, parent, false);


            holder = new BurgersDogsHolder();

            //get a reference to all the different view elements we wish to update
            holder.title = (TextView) row.findViewById(R.id.title_textView);
            holder.quantity = (TextView) row.findViewById(R.id.quantity_textView);
            holder.add = (ImageButton) row.findViewById(R.id.add_imageButton);
            row.setTag(holder);

        } else {
            holder = (BurgersDogsHolder) row.getTag();
        }

        //get the data from the data array
        BurgersDogs burgerDog = mData.get(position);

        holder.add.setOnClickListener(AddListener);

        Integer myPosition = position;
        holder.add.setTag(myPosition);

        //setting the view to the data we need to display

        holder.add.setFocusableInTouchMode(false);
        holder.add.setFocusable(false);


        holder.title.setText(burgerDog.getTitle());
        if (burgerDog.getQuantity() < 1) {
            holder.quantity.setText("");
        } else {
            holder.quantity.setText(String.valueOf(burgerDog.getQuantity()));
        }


        return row;
    }

    View.OnClickListener AddListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Integer viewPosition = (Integer) v.getTag();
            BurgersDogs b = mData.get(viewPosition);
            b.addQuantity();
            updatePriceListener.updatePrice(mData);
            notifyDataSetChanged();
        }
    };

    private static class BurgersDogsHolder {
        public TextView title;
        public TextView quantity;
        public ImageButton add;

    }
}
