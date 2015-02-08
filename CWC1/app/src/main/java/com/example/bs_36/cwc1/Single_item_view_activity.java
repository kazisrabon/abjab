package com.example.bs_36.cwc1;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.bs_36.cwc1.app.AppController;

import java.util.ArrayList;
import java.util.List;


public class Single_item_view_activity extends ActionBarActivity implements View.OnClickListener {

    Button bCall, bSMS;
    TextView tvProductName,tvPrice;
    CheckBox check;
    DataHandler dataHandler;
    String product_name,image_url,price,location,contactAddress,mobile_number;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_view_activity);

        Intent intent=getIntent();
        product_name=intent.getStringExtra("productName");
        image_url=intent.getStringExtra("image");
        price=intent.getStringExtra("price");
        location=intent.getStringExtra("location");
        contactAddress=intent.getStringExtra("contactAddress");
        mobile_number=intent.getStringExtra("mobile");

        check = (CheckBox)findViewById(R.id.bFavorite);
        check.setOnClickListener(this);
        bCall = (Button) findViewById(R.id.bCall);
        bCall.setOnClickListener(this);
        bSMS = (Button) findViewById(R.id.bSMS);
        bSMS.setOnClickListener(this);

        dataHandler = new DataHandler(getBaseContext());

        tvProductName= (TextView) findViewById(R.id.productName);
        tvPrice=(TextView)findViewById(R.id.productPrice);

        tvProductName.setText(product_name);
        tvPrice.setText(price);

        Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        gallery.setAdapter(new ImageAdapter(this));
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        final NetworkImageView thumbNail = (NetworkImageView) findViewById(R.id.image1);
        thumbNail.setImageUrl(getImgUrl().get(0), imageLoader);
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getBaseContext(), "pic" + (position + 1) + " selected",
                        Toast.LENGTH_SHORT).show();
                // display the images selected
                thumbNail.setImageUrl(getImgUrl().get(position), imageLoader);
//
//                    ImageView imageView = (ImageView) findViewById(R.id.image1);
//                    imageView.setImageResource(imageIDs[position]);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_item_view_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.bCall){
            Intent callIntent=new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:01919471272"));
            startActivity(callIntent);
        }

        if(v.getId()==R.id.bSMS){
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setData(Uri.parse("smsto:"));
            smsIntent.setType("vnd.android-dir/mms-sms");

            smsIntent.putExtra("address"  , new String ("01919471272"));
            smsIntent.putExtra("sms_body"  , "Hi, I would love to buy your product. Tell me ,when I can get it ???");
            startActivity(smsIntent);

        }
        if(v.getId() == R.id.bFavorite){
            CheckBox checkBox = (CheckBox)v;
            Log.d("ssssssssss", "before if........................");
            if(checkBox.isChecked()){
                dataHandler.open();
                Log.d("Databaase", "opened ok.....................................");
                dataHandler.insertData(product_name, image_url, price, location, contactAddress, mobile_number);
                Log.d("Databaase", "insert ok.....................................");
                dataHandler.close();
            }
        }
    }

    private List<String> getImgUrl(){
        List<String>list=new ArrayList<String>();
        list.add("http://api.androidhive.info/json/movies/1.jpg");
        list.add("http://api.androidhive.info/json/movies/2.jpg");
        list.add("http://api.androidhive.info/json/movies/3.jpg");
        return list;
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private int itemBackground;

        public ImageAdapter(Context c) {
            context = c;
            // sets a grey background; wraps around the images
            TypedArray a = obtainStyledAttributes(R.styleable.MyGallery);
            itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
            a.recycle();
        }

        // returns the number of images
        public int getCount() {
            return getImgUrl().size();
        }

        // returns the ID of an item
        public Object getItem(int position) {
            return position;
        }

        // returns the ID of an item
        public long getItemId(int position) {
            return position;
        }

        // returns an ImageView view
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            NetworkImageView thumbNail = new NetworkImageView(context);
            //         (NetworkImageView) findViewById(R.id.image1);
            thumbNail.setDefaultImageResId(R.drawable.pic1);
            thumbNail.setErrorImageResId(R.drawable.ic_launcher);
            thumbNail.setAdjustViewBounds(true);
            thumbNail.setLayoutParams(new Gallery.LayoutParams(100, 100));
            thumbNail.setImageUrl(getImgUrl().get(position), imageLoader);
            thumbNail.setBackgroundResource(itemBackground);
//            ImageView imageView = new ImageView(context);
//            imageView.setImageResource(imageIDs[position]);
//            imageView.setLayoutParams(new Gallery.LayoutParams(100, 100));
//            imageView.setBackgroundResource(itemBackground);
            return thumbNail;
        }
    }
}
