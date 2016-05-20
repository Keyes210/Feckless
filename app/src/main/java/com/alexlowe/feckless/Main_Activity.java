package com.alexlowe.feckless;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Main_Activity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private final FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FecklessPreferences fecklessPreferences = new FecklessPreferences(this);
        fecklessPreferences.retrieveDays();

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

        switch(menuItem.getItemId()) {
            case R.id.stopwatch:
                fragment = new StopwatchFragment();
                break;
            case R.id.mon:
                fragment =  new PagerFragment();
                args = 0;
                break;
            case R.id.tue:
                fragment =  new PagerFragment();
                args = 1;
                break;
            case R.id.wed:
                fragment =  new PagerFragment();
                args = 2;
                break;
            case R.id.thu:
                fragment =  new PagerFragment();
                args = 3;
                break;
            case R.id.fri:
                fragment =  new PagerFragment();
                args = 4;
                break;
            case R.id.sat:
                fragment =  new PagerFragment();
                args = 5;
                break;
            case R.id.sun:
                fragment =  new PagerFragment();
                args = 6;
                break;
            case R.id.total:
                fragment =  new TotalFragment();
                break;
            default:
                fragment =  new StopwatchFragment();
        }

        drawerFragmentTransaction(fragment, args, menuItem);

    }

    public void drawerFragmentTransaction(Fragment fragment, int args, MenuItem menuItem){
        Bundle bundle = new Bundle();
        bundle.putInt("tab", args);
        fragment.setArguments(bundle);

        fm.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
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
        if (id == R.id.action_timer) {
            fm.beginTransaction().replace(R.id.fragmentContainer, new StopwatchFragment()).commit();
            setTitle("Stopwatch");
        }else if(id == R.id.action_total) {
            fm.beginTransaction().replace(R.id.fragmentContainer, new TotalFragment()).commit();
            setTitle("Total");
        }else if(id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }


}
