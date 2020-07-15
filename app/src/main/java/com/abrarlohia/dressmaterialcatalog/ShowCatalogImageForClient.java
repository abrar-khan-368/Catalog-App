package com.abrarlohia.dressmaterialcatalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.abrarlohia.dressmaterialcatalog.Adapters.DownloadImageAdapter;
import com.abrarlohia.dressmaterialcatalog.Adapters.ImageToShowAdapter;
import com.abrarlohia.dressmaterialcatalog.Models.CatalogDetails;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowCatalogImageForClient extends AppCompatActivity {

    private String catalogName;
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private ArrayList<String> urls = new ArrayList<>();
    private ArrayList<String> downloadUrls = new ArrayList<>();

    private DownloadImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_catalog_image_for_client);

        catalogName = getIntent().getStringExtra("catalog-name");

        recyclerView = findViewById(R.id.image_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        imageAdapter = new DownloadImageAdapter(downloadUrls, this);
        recyclerView.setAdapter(imageAdapter);

        fetchImages();

    }

    private void fetchImages() {
        final Query query = firestore.collection("CatalogDetails").whereEqualTo("catalogName", catalogName);
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        urls = (ArrayList<String>) task.getResult().getDocuments().get(0).get("imageLink");
                        Log.d("SIZE", urls.size() + "");
                        for (int i = 0; i < urls.size(); i++) {
                            downloadUrls.add(urls.get(i));
                            imageAdapter.notifyDataSetChanged();

                        }
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        FirestoreRecyclerOptions<CatalogDetails> options = new FirestoreRecyclerOptions.Builder<CatalogDetails>()
                                .setQuery(query, CatalogDetails.class)
                                .build();

                        final ImageToShowAdapter adapter = new ImageToShowAdapter(options);
                        adapter.setContext(ShowCatalogImageForClient.this);
                        //recyclerView.setAdapter(adapter);
                        adapter.startListening();

                    }
                });
    }
}
