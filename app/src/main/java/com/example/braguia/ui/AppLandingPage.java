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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AppLandingPage extends AppCompatActivity {

    private TextView app_desc;
    private TextView app_land;
    private Button loginLanding;

    private AppViewModel appViewModel;
    private UserViewModel userViewModel;

    private Date currentDate;

    private Date formatted_expire;

    private String expireDate;



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

        });

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        loginLanding.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                expireDate = userViewModel.getCookieExpire();


                if (!expireDate.isEmpty()) {

                    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                    currentDate = new Date();

                    //expireDate = "Sun, 28 Apr 2024 00:17:00 GMT";

                    try {
                        formatted_expire = sdf.parse(expireDate);

                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }


                    if (formatted_expire.before(currentDate)) { // currentDate

                        startActivity(new Intent(AppLandingPage.this, LoginActivity.class));
                    } else {

                        startActivity(new Intent(AppLandingPage.this, MainActivity.class));
                    }
                }
                else{

                    startActivity(new Intent(AppLandingPage.this, LoginActivity.class));
                }


            }
        });
    }
}
