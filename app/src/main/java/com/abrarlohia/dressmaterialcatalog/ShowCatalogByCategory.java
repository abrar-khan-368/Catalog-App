package com.abrarlohia.dressmaterialcatalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.abrarlohia.dressmaterialcatalog.Adapters.ShowCatalogByCategoryAdapter;
import com.abrarlohia.dressmaterialcatalog.Adapters.ShowCatalogDetailsAdapter;
import com.abrarlohia.dressmaterialcatalog.Models.CatalogDetails;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.shashank.sony.fancytoastlib.FancyToast;

public class ShowCatalogByCategory extends AppCompatActivity {

    private String categoryName;
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private ImageButton btn_whatsapp_catalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_catalog_by_category);

        categoryName = getIntent().getStringExtra("category");
        recyclerView = findViewById(R.id.catalog_by_category);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchDataByCategory();
        btn_whatsapp_catalog = findViewById(R.id.btn_whatsapp_catalog);
        btn_whatsapp_catalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSupportChat();
            }
        });

    }

    private void fetchDataByCategory() {

        final Query query = firestore.collection("CatalogDetails").whereEqualTo("catalogCategory", categoryName);
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.getResult().size() == 0)
                            FancyToast.makeText(ShowCatalogByCategory.this, "No Records found", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        FirestoreRecyclerOptions<CatalogDetails> options = new FirestoreRecyclerOptions.Builder<CatalogDetails>()
                                .setQuery(query, CatalogDetails.class)
                                .build();

                        ShowCatalogByCategoryAdapter adapter = new ShowCatalogByCategoryAdapter(options);
                        adapter.setContext(ShowCatalogByCategory.this);
                        recyclerView.setAdapter(adapter);
                        adapter.startListening();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FancyToast.makeText(ShowCatalogByCategory.this, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                });

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
