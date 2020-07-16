package com.abrarlohia.fragmets;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abrarlohia.dressmaterialcatalog.Adapters.ShowCatalogByCategoryAdapter;
import com.abrarlohia.dressmaterialcatalog.Adapters.ShowCatalogDetailsAdapter;
import com.abrarlohia.dressmaterialcatalog.Models.CatalogDetails;
import com.abrarlohia.dressmaterialcatalog.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SearchFragment extends Fragment {

    private EditText searchBox;
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchBox = view.findViewById(R.id.search_text);
        recyclerView = view.findViewById(R.id.search_recycler);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == 4) {
                    if(!searchBox.getText().toString().isEmpty()) {
                        String searchParam = searchBox.getText().toString().trim();
                        searchParam = searchParam.toLowerCase();
                        fetchCatalogUsingSearch(searchParam);
                    } else {
                        FancyToast.makeText(getContext(), "Please enter catalog name!", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                    }
                }
                return false;
            }
        });
    }

    private void fetchCatalogUsingSearch(String searchParam) {

        final Query query = firestore.collection("CatalogDetails").whereEqualTo("catalogName", searchParam);
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.getResult().size() == 0)
                            FancyToast.makeText(getContext(), "No results found", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        FirestoreRecyclerOptions<CatalogDetails> options = new FirestoreRecyclerOptions.Builder<CatalogDetails>()
                                .setQuery(query, CatalogDetails.class)
                                .build();

                        ShowCatalogByCategoryAdapter adapter = new ShowCatalogByCategoryAdapter(options);
                        adapter.setContext(getContext());
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
}
