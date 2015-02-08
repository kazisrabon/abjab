package com.example.bs_36.cwc1;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.bs_36.cwc1.util.CatagoryArrayAdapter;


public class ListSubCatagoryActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String[] subCatagory=intent.getStringArrayExtra("com.example.bs_36.cwc1.values");
        setListAdapter(new CatagoryArrayAdapter(this,subCatagory));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String selectedValue = (String) getListAdapter().getItem(position);
        Intent intent=new Intent(this,AdvertisementActivity.class);
        startActivity(intent);
        ListSubCatagoryActivity.this.finish();
    }
}
