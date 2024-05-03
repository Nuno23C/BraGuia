package com.example.braguia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.braguia.R;
import com.example.braguia.databinding.ActivityMainBinding;
import com.example.braguia.model.Objects.User;
import com.example.braguia.viewModel.UserViewModel;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                navController.navigate(R.id.HomeFragment);
            } else if (id == R.id.favourite) {
                navController.navigate(R.id.FavouritesFragment);
            } else if (id == R.id.profile) {
                navController.navigate(R.id.ProfileFragment);
            } else if (id == R.id.contacts) {
                navController.navigate(R.id.ContactFragment);
            }
            return true;
        });
    }

    public void showBottomNavigationView() {
        binding.bottomNavigationView.setVisibility(View.VISIBLE);
    }

    public void hideBottomNavigationView() {
        binding.bottomNavigationView.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}