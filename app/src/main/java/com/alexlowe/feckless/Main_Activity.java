package com.alexlowe.feckless;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;

public class Main_Activity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = new StopwatchFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Create Navigation drawer and inflate layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        // Adding menu icon to Toolbar
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if(supportActionBar != null){
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set behavior of Navigation drawer
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        // This method will trigger on item Click of navigation menu
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            // Set item in checked state
                            menuItem.setChecked(true);
                            // TODO: handle navigation
                            selectDrawerItem(menuItem);
                            // Closing drawer on item click
                            drawerLayout.closeDrawers();
                            return true;
                        }
                    }
            );
        }


    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        int args = 0;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.stopwatch:
                fragmentClass = StopwatchFragment.class;
                break;
            case R.id.mon:
                fragmentClass = PagerFragment.class;
                args = 0;
                break;
            case R.id.tue:
                fragmentClass = PagerFragment.class;
                args = 1;
                break;
            case R.id.wed:
                fragmentClass = PagerFragment.class;
                args = 2;
                break;
            case R.id.thu:
                fragmentClass = PagerFragment.class;
                args = 3;
                break;
            case R.id.fri:
                fragmentClass = PagerFragment.class;
                args = 4;
                break;
            case R.id.sat:
                fragmentClass = PagerFragment.class;
                args = 5;
                break;
            case R.id.sun:
                fragmentClass = PagerFragment.class;
                args = 6;
                break;
            case R.id.total:
                fragmentClass = TotalFragment.class;
                break;
            default:
                fragmentClass = StopwatchFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putInt("tab", args);
        fragment.setArguments(bundle);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();

        setTitle(menuItem.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }else if(id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }
}
