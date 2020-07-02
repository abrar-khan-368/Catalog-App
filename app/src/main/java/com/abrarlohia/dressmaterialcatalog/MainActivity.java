package com.abrarlohia.dressmaterialcatalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.abrarlohia.fragmets.AboutUsFragment;
import com.abrarlohia.fragmets.ContactUsFragment;
import com.abrarlohia.fragmets.NotificationFragment;
import com.abrarlohia.fragmets.RateFragment;
import com.abrarlohia.fragmets.SearchFragment;
import com.abrarlohia.fragmets.ShareFragment;
import com.abrarlohia.fragmets.VideosFragment;
import com.google.android.material.navigation.NavigationView;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FancyToast.makeText(this, "Welcome",
                FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//       if(savedInstanceState==null){
//           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                   new SearchFragment()).commit();
//       }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(this,MainActivity.class));
                finish();
                break;
            case R.id.nav_about_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AboutUsFragment()).commit();
                break;
            case R.id.nav_contact:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ContactUsFragment()).commit();
                break;
            case R.id.nav_rate:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RateFragment()).commit();
                break;
            case R.id.nav_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchFragment()).commit();
                break;
            case R.id.nav_share:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ShareFragment()).commit();
                break;
            case R.id.nav_videos:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new VideosFragment()).commit();
                break;
            case R.id.nav_notification:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NotificationFragment()).commit();
                break;
            case R.id.instagram:
                FancyToast.makeText(this, "Sorry no instagram account right now ",
                        FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
                break;
            case R.id.facebook:
                FancyToast.makeText(this, "Very soon on facebook ",
                        FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
                break;
            case R.id.youtube:
                FancyToast.makeText(this, "Youtube channel on the way ",
                        FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
