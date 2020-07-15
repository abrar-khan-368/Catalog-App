package com.abrarlohia.dressmaterialcatalog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.abrarlohia.dressmaterialcatalog.Adapters.ImageDisplayAdapter;
import com.abrarlohia.dressmaterialcatalog.Models.CatalogDetails;
import com.abrarlohia.dressmaterialcatalog.Models.DownloadUrl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddCatalog extends AppCompatActivity {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private ImageButton imageButton;
    private RecyclerView showUploadedImages;
    private Button insertCatalog;
    private EditText catalogName, catalogCosting;
    private Spinner catalogCategory;

    private List<String> fileNameList;
    private List<Uri> imageUriList;
    private ImageDisplayAdapter displayAdapter;

    private ProgressDialog progressDialog;

    private String item;

    static List<String> uploadedImageLink = new ArrayList<>();

    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_catalog);

        imageButton = findViewById(R.id.catalog_images);
        showUploadedImages = findViewById(R.id.image_display);
        insertCatalog = findViewById(R.id.add_catalog);
        catalogCosting = findViewById(R.id.catalog_costing);
        catalogName = findViewById(R.id.catalog_name);
        catalogCategory = findViewById(R.id.category_type);

        fileNameList = new ArrayList<>();
        imageUriList = new ArrayList<>();
        displayAdapter = new ImageDisplayAdapter(fileNameList);

        fetchCategory();

        catalogCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        progressDialog = new ProgressDialog(this);

        showUploadedImages.setHasFixedSize(true);
        showUploadedImages.setLayoutManager(new LinearLayoutManager(AddCatalog.this));
        showUploadedImages.setAdapter(displayAdapter);



        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Catalog Images"), 1);
            }
        });

        insertCatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!catalogName.getText().toString().isEmpty()) {
                    if(!catalogCosting.getText().toString().isEmpty()) {
                        final int cost = Integer.parseInt(catalogCosting.getText().toString());
                        if(cost > 0) {
                            if(fileNameList != null) {
                                firestore.collection("CatalogDetails")
                                        .document(catalogName.getText().toString())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.getResult().exists()) {
                                                    FancyToast.makeText(AddCatalog.this, "Catalog already exists! Try using different name.", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                                                    return;
                                                } else {
                                                    uploadCatalogInformation(cost);
                                                }
                                            }
                                        });
                            } else {
                                FancyToast.makeText(AddCatalog.this, "Please select atleast one product image!", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                            }
                        }
                    } else {
                        FancyToast.makeText(AddCatalog.this, "Enter catalog costing", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                        catalogCosting.setError("Field is required");
                    }
                } else {
                    FancyToast.makeText(AddCatalog.this, "Enter catalog name", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                    catalogName.setError("Field is required");
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                int totalSelectedItem = data.getClipData().getItemCount();
                if(totalSelectedItem > 5) {
                    FancyToast.makeText(AddCatalog.this, "You can select only 5 images", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    return;
                }
                for (int i = 0; i < totalSelectedItem; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imageUriList.add(imageUri);
                    String filename = getFileName(imageUri);

                    fileNameList.add(filename);
                    displayAdapter.notifyDataSetChanged();
                }

            } else if(data.getData() != null) {
                Toast.makeText(this, "Single image selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadCatalogInformation(final int cost) {
        final String[] link = new String[1];
        for (int i = 0; i < fileNameList.size(); i++) {
            StorageReference fileToUpload = storageReference.child("catalog-images")
                    .child(System.currentTimeMillis() + fileNameList.get(i));

            fileToUpload.putFile(imageUriList.get(i))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            final Uri downloadUri = urlTask.getResult();
                            link[0] = downloadUri.toString();
                            Log.d("DOWNLOAD", downloadUri.toString());
                            uploadedImageLink.add(downloadUri.toString());

                            firestore.collection("CatalogDetails")
                                    .document(catalogName.getText().toString())
                                    .set(new CatalogDetails(catalogName.getText().toString(),
                                            cost+"", uploadedImageLink, item))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                FancyToast.makeText(AddCatalog.this, "Completed Successfully", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                                uploadedImageLink.clear();
                                            }
                                        }
                                    })
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            FancyToast.makeText(AddCatalog.this, "Uploaded Successfully", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            FancyToast.makeText(AddCatalog.this, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                        }
                                    });

                            progressDialog.hide();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            FancyToast.makeText(AddCatalog.this, e.getMessage(),
                                    FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.setTitle("Status");
                            int progress = (int)((100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploading..."+progress);
                            progressDialog.show();
                        }
                    });

        }


        //Log.d("HASHMAPIMAGE", uploadedImageLink.get("imageLink"+0));



    }

    private void fetchCategory() {
        final ArrayList<String> categories = new ArrayList<String>();
        CollectionReference reference = firestore.collection("Categories");

        Query query = reference.orderBy("name");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                        categories.add(snapshot.get("name").toString());
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddCatalog.this,
                        R.layout.spinner_item, categories);
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                catalogCategory.setAdapter((adapter));

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FancyToast.makeText(AddCatalog.this, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                });

    }


    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


}
