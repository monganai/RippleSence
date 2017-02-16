package com.example.brian.progproj;

import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import menu.BlankFragment;
import menu.DetailFragment;
import menu.SettingsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    BlankFragment fragment;
    DetailFragment detailFragment;
    SettingsFragment settingsFragment;
    // Insert the fragment by replacing any existing fragment
    FragmentManager fragmentManager = getFragmentManager();
    int currentFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragment = new BlankFragment();
        detailFragment = new DetailFragment();
        settingsFragment = new SettingsFragment();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        fragment.setRetainInstance(true);
        detailFragment.setRetainInstance(true);
        settingsFragment.setRetainInstance(true);
        currentFrag = 0;
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, fragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current", currentFrag);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null) {
            currentFrag = savedInstanceState.getInt("current");
        }
        if (currentFrag == 0) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();
        } else if (currentFrag == 1) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, detailFragment)
                    .commit();
        } else if (currentFrag == 2) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, settingsFragment)
                    .commit();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_home) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();
                currentFrag = 0;
        } else if (id == R.id.nav_add) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, detailFragment)
                    .commit();
                currentFrag = 1;
        } else if (id == R.id.nav_settings) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, settingsFragment)
                    .commit();
                currentFrag = 2;
        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
