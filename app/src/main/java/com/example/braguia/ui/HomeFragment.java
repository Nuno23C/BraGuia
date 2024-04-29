package com.example.braguia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.braguia.R;
import com.example.braguia.model.Objects.Trail;
import com.example.braguia.repository.TrailRepository;
import com.example.braguia.viewModel.TrailsViewModel;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements TrailsRecyclerViewAdapter.TrailClickListener {

    private TrailsViewModel trailsViewModel;

    private TrailsRecyclerViewAdapter adapter;


//    public HomeFragment() {
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        trailsViewModel = new ViewModelProvider(this).get(TrailsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.trail_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        trailsViewModel = new ViewModelProvider(this).get(TrailsViewModel.class);
        trailsViewModel.getAllTrails().observe(getViewLifecycleOwner(), trails -> {
            adapter = new TrailsRecyclerViewAdapter(trails, new TrailsRecyclerViewAdapter.TrailClickListener() {
                @Override
                public void onItemClick(int position) {
                    Trail selectedTrail = trails.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectedTrail", selectedTrail);

                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    TrailFragment fragment = new TrailFragment();
                    fragment.setArguments(bundle);
                    transaction.replace(R.id.frame_layout, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
            recyclerView.setAdapter(adapter);
        });

        return view;
    }


    @Override
    public void onItemClick(int position) {

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
