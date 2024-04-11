package com.example.braguia.ui;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.braguia.R;


public class MapsActivity extends FragmentActivity {

    FrameLayout map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_maps);

        map = findViewById(R.id.map);
    }

}