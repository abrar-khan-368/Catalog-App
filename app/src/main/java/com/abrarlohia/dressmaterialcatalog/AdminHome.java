package com.abrarlohia.dressmaterialcatalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.abrarlohia.dressmaterialcatalog.Models.CatalogDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shashank.sony.fancytoastlib.FancyToast;

public class AdminHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Toolbar toolbar = findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_admin_home);

        NavigationView navigationView = findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle =
                new ActionBarDrawerToggle(AdminHome.this, drawerLayout, toolbar,
                        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_add_item:
                startActivity(new Intent(AdminHome.this, AddCatalog.class));
                break;
            case R.id.nav_show_item:
                startActivity(new Intent(AdminHome.this, ShowCatalogDetails.class));
                break;
            case R.id.nav_add_category:
                break;
            case R.id.nav_show_category:
                startActivity(new Intent(AdminHome.this, ShowCategories.class));
                break;
            case R.id.nav_logout:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
