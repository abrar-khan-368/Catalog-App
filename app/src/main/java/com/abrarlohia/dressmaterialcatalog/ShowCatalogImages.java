package com.abrarlohia.dressmaterialcatalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.abrarlohia.dressmaterialcatalog.Adapters.DownloadImageAdapter;
import com.abrarlohia.dressmaterialcatalog.Adapters.ImageDisplayAdapter;
import com.abrarlohia.dressmaterialcatalog.Adapters.ImageToShowAdapter;
import com.abrarlohia.dressmaterialcatalog.Adapters.ShowCatalogDetailsAdapter;
import com.abrarlohia.dressmaterialcatalog.Models.CatalogDetails;
import com.abrarlohia.dressmaterialcatalog.Models.DownloadUrl;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowCatalogImages extends AppCompatActivity {

    private String catalogName = null;
    private List<DownloadUrl> urlList = new ArrayList<>();
    private ImageToShowAdapter imageToShowAdapter;
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_catalog_images);

        if (getIntent().getStringExtra("catalog-name") == null)
            Log.d("ShowCatalogImagesERROR", "Catalog-name is empty");
        else
            catalogName = getIntent().getStringExtra("catalog-name");

        CollectionReference reference = firestore.collection("CatalogDetails");
        final Query query = reference.whereEqualTo("catalogName", catalogName);

        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        urlList = (ArrayList<DownloadUrl>)task.getResult().getDocuments().get(0).get("imageLink");
                    }
                });
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        Toast.makeText(ShowCatalogImages.this, queryDocumentSnapshots.getDocuments().size() + "", Toast.LENGTH_SHORT).show();
//                        urlList = (ArrayList<DownloadUrl>) queryDocumentSnapshots.getDocuments().get(0)
//                                .get("imageLink");
//
//
//                    }
//                });

        recyclerView = findViewById(R.id.image_recycler);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowCatalogImages.this));


        imageToShowAdapter = new ImageToShowAdapter(urlList);
        //imageToShowAdapter.setUrls(urlList);
        recyclerView.setAdapter(imageToShowAdapter);
        for (int i = 0; i < urlList.size(); i++)
            imageToShowAdapter.notifyDataSetChanged();


    }

}
