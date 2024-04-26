package com.example.braguia.ui;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.braguia.R;
import com.example.braguia.viewModel.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;


public class LoginActivity extends AppCompatActivity {

    EditText inputUser;
    EditText inputPass;
    Button login;
    UserViewModel userViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView button = findViewById(R.id.newAccount);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Register.class));
            }
        });

        inputUser = findViewById(R.id.inputUsername);
        inputPass = findViewById(R.id.inputPass1);
        login = findViewById(R.id.buttonLogin);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = inputUser.getText().toString();
                String password = inputPass.getText().toString();
                System.out.println(username);

                userViewModel.login(username,password);

                userViewModel.getLoginStatus().observe(LoginActivity.this, success -> {
                    if (success){
                        Toast.makeText(LoginActivity.this, "LOGIN EFETUADO COM SUCESSO!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "DADOS INCORRETOS", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });
    }

    interface LoginCallback {
        void onSuccess(boolean success);

    }
}
