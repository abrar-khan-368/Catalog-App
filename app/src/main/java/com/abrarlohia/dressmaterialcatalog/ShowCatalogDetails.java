package com.abrarlohia.dressmaterialcatalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.abrarlohia.dressmaterialcatalog.Adapters.ShowCatalogDetailsAdapter;
import com.abrarlohia.dressmaterialcatalog.Models.CatalogDetails;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

public class ShowCatalogDetails extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference collection = firestore.collection("CatalogDetails");

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_catalog_details);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        recyclerView = findViewById(R.id.display_catalog);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowCatalogDetails.this));



        fetchCatalog();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

    private void fetchCatalog() {

        final Query query = collection.orderBy("costing");
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressDialog.hide();
                        if(task.getResult().size() == 0)
                            FancyToast.makeText(ShowCatalogDetails.this, "No catalog found!", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        progressDialog.dismiss();
                        FirestoreRecyclerOptions<CatalogDetails> options = new FirestoreRecyclerOptions.Builder<CatalogDetails>()
                                .setQuery(query, CatalogDetails.class)
                                .build();
                        final ShowCatalogDetailsAdapter catalogDetailsAdapter = new ShowCatalogDetailsAdapter(options);
                        catalogDetailsAdapter.setContext(ShowCatalogDetails.this);
                        recyclerView.setAdapter(catalogDetailsAdapter);

                        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                            @Override
                            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                                return false;
                            }

                            @Override
                            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                                catalogDetailsAdapter.deleteDocument(viewHolder.getAdapterPosition());
                            }
                        }).attachToRecyclerView(recyclerView);

                        catalogDetailsAdapter.startListening();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.hide();
                        FancyToast.makeText(ShowCatalogDetails.this,
                                e.getMessage(), FancyToast.LENGTH_SHORT,
                                FancyToast.WARNING, false).show();
                    }
                });

    }


}
