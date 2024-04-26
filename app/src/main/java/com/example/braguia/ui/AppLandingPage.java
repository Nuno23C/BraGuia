package com.example.braguia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.braguia.R;
import com.example.braguia.viewModel.AppViewModel;
import com.example.braguia.viewModel.UserViewModel;

public class AppLandingPage extends AppCompatActivity {

    private TextView app_desc;
    private TextView app_land;
    private Button loginLanding;

    private AppViewModel appViewModel;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_landing_page);



        app_desc = findViewById(R.id.app_desc);
        app_land = findViewById(R.id.app_landing);
        loginLanding = findViewById(R.id.loginLanding);
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        appViewModel.getApp().observe(AppLandingPage.this,app -> {

            if (app != null ) {

                app_desc.setText(app.getApp_desc());
                app_land.setText(app.getApp_landing_page_text());
            }
            else{
                System.out.println("TÁ VAZIO ISTO");
            }
        });
        loginLanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppLandingPage.this,LoginActivity.class));
            }
        });
    }
}
