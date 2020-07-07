package com.abrarlohia.dressmaterialcatalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.abrarlohia.dressmaterialcatalog.Adapters.ShowCatalogDetailsAdapter;
import com.abrarlohia.dressmaterialcatalog.Models.CatalogDetails;
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

public class ShowCatalogDetails extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference collection = firestore.collection("CatalogDetails");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_catalog_details);

        recyclerView = findViewById(R.id.display_catalog);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowCatalogDetails.this));
        fetchCatalog();
    }

    private void fetchCatalog() {

        final Query query = collection.orderBy("costing");
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.getResult().size() == 0)
                            FancyToast.makeText(ShowCatalogDetails.this, "No catalog found!", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        FirestoreRecyclerOptions<CatalogDetails> options = new FirestoreRecyclerOptions.Builder<CatalogDetails>()
                                .setQuery(query, CatalogDetails.class)
                                .build();
                        ShowCatalogDetailsAdapter catalogDetailsAdapter = new ShowCatalogDetailsAdapter(options);
                        recyclerView.setAdapter(catalogDetailsAdapter);
                        catalogDetailsAdapter.startListening();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FancyToast.makeText(ShowCatalogDetails.this,
                                e.getMessage(), FancyToast.LENGTH_SHORT,
                                FancyToast.WARNING, false).show();
                    }
                });

    }

}
