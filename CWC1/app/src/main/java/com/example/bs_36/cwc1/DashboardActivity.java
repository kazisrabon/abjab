package com.example.bs_36.cwc1;

//import android.app.ActionBar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.bs_36.cwc1.library.UserFunctions;
import com.example.bs_36.cwc1.util.LocationTracking;

//import android.support.v7.app.ActionBar;

public class DashboardActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, ActionBar.OnNavigationListener {
    UserFunctions userFunctions;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    int mPosition = -1;
    String[] mPage;
    String[] mycategory = new String[]{
            "Code Warrior",
            "Vehicles", "Books & CD", "Gadgets", "Real Estate", "Accessories"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));


        /**
         * Dashboard Screen for the application
         * */
        // Check login status in database
        userFunctions = new UserFunctions();
        if (userFunctions.isUserLoggedIn(getApplicationContext())) {
            setContentView(R.layout.dashboard);


            mPage = getResources().getStringArray(R.array.pages);
            mNavigationDrawerFragment = (NavigationDrawerFragment)
                    getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
            mTitle = getTitle();

            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));

        } else {
            // user is not logged in show login screen
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
            // Closing dashboard screen
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
//            return true;
//        }
        return super.onCreateOptionsMenu(menu);
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
                userFunctions.logoutUser(getApplicationContext());
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(login);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment objFragment = null;
        switch (position) {
            case 0:
                objFragment = new profile_Fragment();
                break;
            case 1:
                objFragment = new chngpass_Fragment();
                break;
            case 2:
                objFragment = new products_Fragment();
                break;
            case 3:
                objFragment = new Favorite_Fragment();
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, objFragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {

//        Toast.makeText(getBaseContext(), "You select: " + itemPosition, Toast.LENGTH_LONG).show();
        switch (itemPosition){
            case 1:
                startActivity(new Intent(this, CarActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, BookActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, PhoneActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, FlatForSaleActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, ShoeActivity.class));
                break;
        }

        return false;
    }


    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.dashboard, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((DashboardActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = mPage[0];

                break;
            case 2:
                mTitle = mPage[1];
                break;
            case 3:
                mTitle = mPage[2];
                break;

            case 4:
                mTitle = mPage[3];
        }
    }

    public void restoreActionBar() {

        ActionBar actionBar = getSupportActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, mycategory);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(myAdapter, this);

        actionBar.setTitle(mTitle);
    }
}
