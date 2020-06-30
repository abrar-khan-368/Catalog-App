package com.abrarlohia.dressmaterialcatalog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FancyToast.makeText(this, "Welcome",
                FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
    }
}
