package com.abrarlohia.dressmaterialcatalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.InternetConnection;
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
        //TODO: Check Internet
        if (InternetConnection.checkConnection(getApplicationContext())) {

        }
        else {
            FancyToast.makeText(this,"Internet not available",FancyToast.LENGTH_LONG,FancyToast.ERROR,true);
        }

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Drawer
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //ActionBar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


//       if(savedInstanceState==null){
//           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                   new SearchFragment()).commit();
//       }

        if(isNetworkAvailable()) {
            //nothing
        } else {
            FancyToast.makeText(MainActivity.this, "No Internet available", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(this, MainActivity.class));
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
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new RateFragment()).commit();
                showRateDialog(this);
                break;
            case R.id.nav_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchFragment()).commit();
                break;
            case R.id.nav_share:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new ShareFragment()).commit();
                ApplicationShareFragment();
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

    //TODO:Rate on playstore activity
    public static void showRateDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Rate application")
                .setMessage("Would you like to rate the app at PlayStore")
                .setPositiveButton("RATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (context != null) {
                            String link = "https://play.google.com/store/apps/details?id=com.whatsapp";
                            //String link = "market://details?id=";
                            try {
                                // play market available
                                context.getPackageManager()
                                        .getPackageInfo("com.android.vending", 0);
                                // not available
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                                // should use browser
                                link = "https://play.google.com/store/apps/details?id=";
                            }
                            // starts external action
                            context.startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(link + context.getPackageName())));
                        }
                    }
                })
                .setNegativeButton("CANCEL", null);
        builder.show();
    }

    //TODO: Application share
    private void ApplicationShareFragment() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Dress Catalog");
            String shareMessage = "\nCheck this awesome application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));

        } catch (Exception e) {
            //e.toString();
        }
    }

    //TODO:Drawer
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
