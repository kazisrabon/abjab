package com.example.bs_36.cwc1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.bs_36.cwc1.app.AppController;
import com.example.bs_36.cwc1.util.LocationTracking;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class FlatsForRentActivity extends ActionBarActivity {
    private ProgressDialog pDialog;
    private List<FlatsForRent> myFlatRent = new ArrayList<FlatsForRent>();
    String url = "http://www.mocky.io/v2/54cb9dd396d6b2150f431f70"; //http://www.mocky.io/v2/54ca3487fb2205170c9eff13
    InputStream is = null;
    String json = "";
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    TextView tvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flats_for_rent);
        tvLocation = (TextView)findViewById(R.id.location);
        StrictMode.enableDefaults();

        // Showing progress dialog before making http request
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        popupalteView();

        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
    }

    private class ParseAsyncTask extends AsyncTask<String, Void, Boolean> {


        @Override
        protected Boolean doInBackground(String... arg0) {

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget = new HttpGet(url);
                HttpResponse response;
                response = httpclient.execute(httpget);

                HttpEntity entity = response.getEntity();
                is = entity.getContent();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"), 8);

                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                json = sb.toString();

                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonArray jArray = parser.parse(json).getAsJsonArray();

                for (JsonElement obj : jArray) {
                    FlatsForRent parse = gson.fromJson(obj, FlatsForRent.class);
                    Log.e("parse", jArray.toString());
                    myFlatRent.add(parse);
                }
                hidePDialog();

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            populateListView();
            clickCallBack();
        }

    }

    private void popupalteView() {

        new ParseAsyncTask().execute();

    }
    private void populateListView() {
        ArrayAdapter<FlatsForRent> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listViewProducts);
        list.setAdapter(adapter);

    }

    private void clickCallBack() {
        ListView list = (ListView) findViewById(R.id.listViewProducts);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

                FlatsForRent clickedBranch = myFlatRent.get(position);
                sendNotification(clickedBranch.getFlatsforrent_name());
                String message = "You clicked " + position + " which is "
                        + clickedBranch.getFlatsforrent_name();

                Intent intent=new Intent(FlatsForRentActivity.this, Single_item_view_activity.class);
                intent.putExtra("productName",clickedBranch.getFlatsforrent_name());
                intent.putExtra("image",clickedBranch.getImgUrl());
                intent.putExtra("price",clickedBranch.getPrice());
                intent.putExtra("location",clickedBranch.getLocation());
                intent.putExtra("contactAddress",clickedBranch.getContact_address());
                intent.putExtra("mobile",clickedBranch.getMobile());
                startActivity(intent);

//                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG)
//                        .show();
            }
        });
    }
    private class MyListAdapter extends ArrayAdapter<FlatsForRent> {

        public MyListAdapter() {
            super(FlatsForRentActivity.this, R.layout.item_view, myFlatRent);
            // TODO Auto-generated constructor stub
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            itemView = getLayoutInflater().inflate(R.layout.item_view,
                    parent, false);

            if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();

            FlatsForRent flatRent1 = myFlatRent.get(position);

//            ImageView imageView = (ImageView) itemView
//                    .findViewById(R.id.imageView1);
//            imageView.;
            NetworkImageView thumbNail = (NetworkImageView) itemView.findViewById(R.id.imageView1);
            thumbNail.setImageUrl(flatRent1.getImgUrl(), imageLoader);

            tvLocation.setText((CharSequence) flatRent1.getLocation());

            TextView textView1 = (TextView) itemView
                    .findViewById(R.id.textView1);
            textView1.setText("Flats Name: " + flatRent1.getFlatsforrent_name());

            TextView textView2 = (TextView) itemView
                    .findViewById(R.id.textView2);
            textView2.setText("Price: " + flatRent1.getPrice());

            TextView textView3 = (TextView) itemView
                    .findViewById(R.id.textView3);
            textView3.setText("Location: "+flatRent1.getLocation());

            TextView textView4 = (TextView) itemView
                    .findViewById(R.id.textView4);
            textView4.setText("Contract Address: " + flatRent1.getContact_address());

            TextView textView5 = (TextView) itemView
                    .findViewById(R.id.textView5);
            textView5.setText("Description: " + flatRent1.getDescription());

            TextView textView6 = (TextView) itemView
                    .findViewById(R.id.textView6);
            textView6.setText("Phone: "+flatRent1.getPhone());

            TextView textView7 = (TextView) itemView
                    .findViewById(R.id.textView7);
            textView7.setText("Mobile: " + flatRent1.getMobile());

            TextView textView8 = (TextView) itemView
                    .findViewById(R.id.textView8);
            textView8.setText("Email: " + flatRent1.getEmail());

            return itemView;
        }
    }
    private void sendNotification(String branch_name) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setAutoCancel(true);
        builder.setContentTitle("Flats");
        builder.setContentText(branch_name);
        builder.setSmallIcon(R.drawable.ic_launcher);

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(8, notification);
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
//        restoreActionBar();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.search:
                Intent searchIntent = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(searchIntent);
                break;

            case R.id.help:
                Intent carIntent = new Intent(getApplicationContext(), CarActivity.class);
                startActivity(carIntent);
                break;

            case R.id.call:
                Toast.makeText(getBaseContext(), "Call", Toast.LENGTH_SHORT).show();
                break;

            case R.id.share:
                Intent shareIntent = new Intent(getApplicationContext(), ShareMenu.class);
                startActivity(shareIntent);
                break;

            case R.id.map:
                Toast.makeText(getBaseContext(), "Map", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sell:

                Intent resolveAdvertisement=new Intent(getApplicationContext(),LocationTracking.class);
                startActivity(resolveAdvertisement);
                break;
            case R.id.logout:
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(login);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
