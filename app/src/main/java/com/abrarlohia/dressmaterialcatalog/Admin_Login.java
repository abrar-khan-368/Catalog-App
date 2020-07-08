package com.abrarlohia.dressmaterialcatalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Admin_Login extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;

    private EditText email, password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        login = findViewById(R.id.btn_login);
        email = findViewById(R.id.edtText_mail);
        password = findViewById(R.id.edtText_password);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null) {
                    FancyToast.makeText(Admin_Login.this, "Logging in...", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                    startActivity(new Intent(Admin_Login.this, AdminHome.class));
                    finish();
                }

            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty())
                    FancyToast.makeText(Admin_Login.this, "Fill out email address and password!", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                else {
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),
                            password.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    FancyToast.makeText(Admin_Login.this, "Successfully signed in!", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                    try {
                                        Thread.sleep(50);
                                        startActivity(new Intent(Admin_Login.this, AdminHome.class));
                                        finish();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    FancyToast.makeText(Admin_Login.this, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                }
                            });
                }


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}