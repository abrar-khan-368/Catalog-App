package com.abrarlohia.dressmaterialcatalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.abrarlohia.dressmaterialcatalog.Models.CatalogDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shashank.sony.fancytoastlib.FancyToast;

public class AdminHome extends AppCompatActivity {

    private EditText catalog;
    private EditText costing;

    private Button addButton, tempButton;

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        catalog = findViewById(R.id.catalog_name);
        costing = findViewById(R.id.costing);

        addButton = findViewById(R.id.insert_data);
        tempButton = findViewById(R.id.button2);

        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, ShowCatalogDetails.class));
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!catalog.getText().toString().isEmpty()) {
                    if(!costing.getText().toString().isEmpty()) {
                        int cost = Integer.parseInt(costing.getText().toString());

                        if(cost > 0) {
                            InsertDataCall();
                        } else {
                            FancyToast.makeText(AdminHome.this, "Price is not valid", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        }

                    } else {
                        FancyToast.makeText(AdminHome.this, "Fill out costing of catalog", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                } else {
                    FancyToast.makeText(AdminHome.this, "Fill out catalog name", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }

            }
        });

    }

    private void InsertDataCall()
    {
        firestore.collection("CatalogDetails")
                .document()
                .set(new CatalogDetails(catalog.getText().toString(),
                        costing.getText().toString()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        FancyToast.makeText(AdminHome.this, "Catalog Saved Successfully", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FancyToast.makeText(AdminHome.this, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                });
    }

}
