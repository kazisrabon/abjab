package com.example.bs_36.cwc1;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.bs_36.cwc1.util.CatagoryArrayAdapter;


public class ListCatagoryActivity extends ListActivity {
    private final String[] CATAGORIES = new String[]{"Vehicles",
            "Gadgets", "Real Estate", "Accessories"};
    private final String[] VEHICLES_SUB={"Car","Motorcycle"};
    private final String[] GADGETS={"PC","Phone"};
    private final String[] REAL_ESTATE={"Flats For Rent","Flats For Sale"};
    private final String[] ACCESSORIES={"Shoe","Shade","Bag"};
    private final String VALUES="com.example.bs_36.cwc1.values";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new CatagoryArrayAdapter(this, CATAGORIES));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String selectedValue = (String) getListAdapter().getItem(position);
        Intent intent=new Intent(this,ListSubCatagoryActivity.class);
        switch(selectedValue){
            case "Vehicles":
                intent.putExtra(VALUES,VEHICLES_SUB);
                break;
            case "Gadgets":
                intent.putExtra(VALUES,GADGETS);
                break;
            case "Real Estate":
                intent.putExtra(VALUES,REAL_ESTATE);
                break;
            case "Accessories":
                intent.putExtra(VALUES,ACCESSORIES);
                break;
        }
        startActivity(intent);
        ListCatagoryActivity.this.finish();;
    }
}
