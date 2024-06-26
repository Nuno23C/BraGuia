package com.example.braguia.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.braguia.R;
import com.example.braguia.model.Objects.Contact;
import com.example.braguia.viewModel.AppViewModel;

public class ContactActivity extends AppCompatActivity {

    private AppViewModel appViewModel;

    private ContactRecyclerViewAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.contact_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        appViewModel.getApp().observe(this, app -> {
            adapter = new ContactRecyclerViewAdapter(app.getContacts(), position -> {
                Contact selectedContact = app.getContacts().get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedContact", selectedContact);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                ContactInfoFragment fragment = new ContactInfoFragment();
                fragment.setArguments(bundle);
                transaction.replace(android.R.id.content, fragment); // Replace with activity's root container ID
                transaction.addToBackStack(null);
                transaction.commit();
            });
            recyclerView.setAdapter(adapter);
        });

        ImageButton go_back = findViewById(R.id.go_back_contacts);
        go_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
