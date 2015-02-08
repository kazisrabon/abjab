package com.example.bs_36.cwc1;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bs_36.cwc1.util.LocationTracking;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class AdvertisementActivity extends ActionBarActivity {
    private Bitmap imageBitmap;
    private int RESULT_IMAGE = 1;
    private ImageView view;
    private int TAKE_PICTURE=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        EditText text=(EditText)findViewById(R.id.location);
        text.setText( LocationTracking.locationEdit.getText());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    public void upload() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, RESULT_IMAGE);
        } else {
            Toast.makeText(getApplicationContext(), "Unable to open image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_IMAGE) {
            if (resultCode == RESULT_OK) {
                //ImageView imageView=(ImageView)findViewById(R.id.productImage);
                try {
                    InputStream stream = getContentResolver().openInputStream(data.getData());
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 120, true);
                    stream.close();
                    //imageView.setImageBitmap(resized);
                    imageBitmap = resized;
                    if (view != null) {
                        view.setImageBitmap(imageBitmap);
                    }
                } catch (FileNotFoundException e) {
                    Log.d("ERROR", "File Not Found");
                } catch (IOException e) {
                    Log.d("ERROR", "ERROR In File Closing");
                }

            }
        }
        else if(requestCode==TAKE_PICTURE){
            if(resultCode==RESULT_OK){
                Bundle extras=data.getExtras();
                Bitmap bitmap=(Bitmap)extras.get("data");
                Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 120, true);
                imageBitmap = resized;
                if (view != null) {
                    view.setImageBitmap(imageBitmap);
                }
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    public void cameraCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // start camera activity
        startActivityForResult(intent, TAKE_PICTURE);
    }
    public void uploadImage(View view) {
        this.view = (ImageView) view;
        final ListView listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, new String[]{"Browse Image", "Capture Image"}));
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(listView);
        dialog.show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = (String) listView.getAdapter().getItem(position);
                if (selectedValue.equals("Browse Image")) {
                    upload();
                    dialog.cancel();
                } else {
                    cameraCapture();
                    dialog.cancel();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void post(View view){
        EditText editText = (EditText) findViewById(R.id.type);
        String type = editText.getText().toString();
        editText = (EditText) findViewById(R.id.desc);
        String description = editText.getText().toString();
        editText=(EditText)findViewById(R.id.location);
        String location=editText.getText().toString();
        editText=(EditText)findViewById(R.id.address);
        String address=editText.getText().toString();
        editText=(EditText)findViewById(R.id.mobile);
        String mobile=editText.getText().toString();
        editText = (EditText) findViewById(R.id.phone);
        String phone_no = editText.getText().toString();
        editText = (EditText) findViewById(R.id.price);
        String price = editText.getText().toString();
        editText = (EditText) findViewById(R.id.email);
        String email = editText.getText().toString();
        if(type.equals("")||location.equals("")||description.equals("")||address.equals("")||mobile.equals("")||phone_no.equals("")||price.equals("")||email.equals("")){
            Toast.makeText(getApplicationContext(),"One or more field empty",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"Posted Successfully",Toast.LENGTH_SHORT).show();
        }
    }
}
