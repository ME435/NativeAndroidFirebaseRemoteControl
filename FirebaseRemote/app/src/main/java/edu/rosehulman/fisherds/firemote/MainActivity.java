package edu.rosehulman.fisherds.firemote;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import edu.rosehulman.fisherds.firemote.fragments.CompetitionFragment;
import edu.rosehulman.fisherds.firemote.fragments.FirebasePathFragment;
import edu.rosehulman.fisherds.firemote.fragments.ManualDriveFragment;
import edu.rosehulman.fisherds.firemote.fragments.ObserveOnlyFragment;
import edu.rosehulman.fisherds.firemote.fragments.ParamsFragment;
import edu.rosehulman.fisherds.firemote.fragments.RobotTestingFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FirebasePathFragment.OnFirebasePathSetListener {

    public static final String TAG = "FirebaseRobotRemote";

    private FirebaseState mFirebaseState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirebaseState = new FirebaseState(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container, FirebasePathFragment.newInstance());
            ft.commit();
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment switchTo = null;
        switch (item.getItemId()) {
            case R.id.nav_set_url:
                switchTo = FirebasePathFragment.newInstance();
                break;
            case R.id.nav_observer:
                switchTo = ObserveOnlyFragment.newInstance();
                break;
            case R.id.nav_manual_drive:
                switchTo = ManualDriveFragment.newInstance();
                break;
            case R.id.nav_testing:
                switchTo = RobotTestingFragment.newInstance();
                break;
            case R.id.nav_competition:
                switchTo = CompetitionFragment.newInstance();
                break;
            case R.id.nav_params:
                switchTo = ParamsFragment.newInstance();
                break;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, switchTo);
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public FirebaseState getFirebaseState() {
        return mFirebaseState;
    }

    @Override
    public void onFirebasePathSet() {
        // Automatically go somewhere new. :)
        Fragment switchTo = ParamsFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, switchTo);
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
    }
}
