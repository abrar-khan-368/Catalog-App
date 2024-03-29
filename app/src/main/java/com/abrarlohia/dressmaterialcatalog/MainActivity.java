package com.abrarlohia.dressmaterialcatalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.InternetConnection;
import com.abrarlohia.dressmaterialcatalog.Adapters.HomeCategoryAdapter;
import com.abrarlohia.dressmaterialcatalog.Models.Category;
import com.abrarlohia.fragmets.AboutUsFragment;
import com.abrarlohia.fragmets.ContactUsFragment;
import com.abrarlohia.fragmets.NotificationFragment;
import com.abrarlohia.fragmets.SearchFragment;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private Button btn_admin;
    private RecyclerView recyclerView;
    private ImageButton btn_whatsapp;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference collection = firestore.collection("Categories");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_whatsapp = findViewById(R.id.btn_whatsapp);
        btn_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSupportChat();
            }
        });

        //TODO: Check Internet
        if (InternetConnection.checkConnection(getApplicationContext())) {
        } else {
            FancyToast.makeText(this, "Internet not available", FancyToast.LENGTH_LONG, FancyToast.ERROR, true);
        }
        //TODO: Admin Button
        btn_admin = findViewById(R.id.btn_admin);
        btn_admin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(MainActivity.this, Admin_Login.class);
                startActivity(intent);
                return false;
            }
        });


        //CategoryFetchCode
        recyclerView = findViewById(R.id.categoryShow);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        fetchRecords();
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

        if (drawer.isDrawerOpen(GravityCompat.START))
            recyclerView.setVisibility(View.INVISIBLE);
        else
            recyclerView.setVisibility(View.VISIBLE);

//       if(savedInstanceState==null){
//           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                   new SearchFragment()).commit();
//       }

        if (isNetworkAvailable()) {
            //nothing
        } else {
            FancyToast.makeText(MainActivity.this, "No Internet available", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
        }

    }

    private void fetchRecords() {
        final Query query = collection.orderBy("name");
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.getResult().size() == 0)
                            FancyToast.makeText(MainActivity.this, "No records found!", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        FirestoreRecyclerOptions<Category> options = new FirestoreRecyclerOptions.Builder<Category>()
                                .setQuery(query, Category.class)
                                .build();
                        HomeCategoryAdapter adapter = new HomeCategoryAdapter(options);
                        adapter.setContext(MainActivity.this);
                        recyclerView.setAdapter(adapter);
                        adapter.startListening();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
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
                recyclerView.setVisibility(View.INVISIBLE);
                break;
            case R.id.nav_contact:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ContactUsFragment()).commit();
                recyclerView.setVisibility(View.INVISIBLE);
                break;
            case R.id.nav_rate:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new RateFragment()).commit();
                showRateDialog(this);
                recyclerView.setVisibility(View.INVISIBLE);
                break;
            case R.id.nav_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchFragment()).commit();
                recyclerView.setVisibility(View.INVISIBLE);
                break;
            case R.id.nav_share:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new ShareFragment()).commit();
                ApplicationShareFragment();
                recyclerView.setVisibility(View.INVISIBLE);
                break;
            case R.id.nav_videos:

//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new VideosFragment()).commit();
                startActivity(new Intent(this, catalog_video.class));
                recyclerView.setVisibility(View.INVISIBLE);

                break;
            case R.id.nav_notification:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NotificationFragment()).commit();
                recyclerView.setVisibility(View.INVISIBLE);
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
            e.toString();
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

    private void startSupportChat() {
        try {
            String trimToNumner = "+919558021665"; //10 digit number
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://wa.me/" + trimToNumner + "/?text=" + "Hello WomanTrend"));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
