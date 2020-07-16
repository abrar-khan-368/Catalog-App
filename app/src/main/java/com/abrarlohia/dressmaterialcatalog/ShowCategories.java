package com.abrarlohia.dressmaterialcatalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.abrarlohia.dressmaterialcatalog.Adapters.CategoryAdapter;
import com.abrarlohia.dressmaterialcatalog.Models.Category;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.shashank.sony.fancytoastlib.FancyToast;

public class ShowCategories extends AppCompatActivity {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference collection = firestore.collection("Categories");
    private RecyclerView showCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_categories);

        showCategories = findViewById(R.id.show_category_adapter);
        showCategories.setHasFixedSize(true);
        showCategories.setLayoutManager(new LinearLayoutManager(ShowCategories.this));
        //showCategories.setLayoutManager(new GridLayoutManager(ShowCategories.this, 2)); // Use this for grid layout view.
        fetchCategoryInfo();
    }

    private void fetchCategoryInfo() {

        final Query query = collection.orderBy("name");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.getResult().size() == 0)
                    FancyToast.makeText(ShowCategories.this, "No records found!", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
        })
        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                FirestoreRecyclerOptions<Category> options = new FirestoreRecyclerOptions.Builder<Category>()
                        .setQuery(query, Category.class)
                        .build();

                CategoryAdapter adapter = new CategoryAdapter(options);
                adapter.setContext(ShowCategories.this);
                showCategories.setAdapter(adapter);
                adapter.startListening();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                FancyToast.makeText(ShowCategories.this, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
        });
    }

}
