package com.abrarlohia.dressmaterialcatalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.abrarlohia.dressmaterialcatalog.Models.Category;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shashank.sony.fancytoastlib.FancyToast;

public class AddCategories extends AppCompatActivity {

    private EditText categoryNameField;
    private Button addCategoryItem;

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categories);

        categoryNameField = findViewById(R.id.category_name);
        addCategoryItem = findViewById(R.id.add_category_button);

        addCategoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(categoryNameField.getText().toString().isEmpty()) {
                    FancyToast.makeText(AddCategories.this, "Fill out category name field", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                    categoryNameField.setError("Field Required");
                } else {
                    if(!categoryNameField.getText().toString().matches("^[a-zA-Z]*$")) {
                        categoryNameField.setError("Only Alphabet is allowed");
                    } else {
                        firestore.collection("Categories")
                                .document()
                                .set(new Category(categoryNameField.getText().toString()))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        FancyToast.makeText(AddCategories.this, "Item added successfully", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        FancyToast.makeText(AddCategories.this, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                    }
                                });
                    }
                }
            }
        });

    }
}
