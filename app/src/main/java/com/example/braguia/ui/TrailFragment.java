package com.example.braguia.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.braguia.R;
import com.example.braguia.model.Objects.Edge;
import com.example.braguia.model.Objects.Pin;
import com.example.braguia.model.Objects.Trail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TrailFragment extends Fragment {

    private Trail trail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            trail = (Trail) getArguments().getSerializable("selectedTrail");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trail, container, false);

        TextView trail_name = view.findViewById(R.id.trail_trail_name);
        TextView trail_desc = view.findViewById(R.id.trail_trail_desc);
        TextView trail_duration = view.findViewById(R.id.trail_trail_duration);
        ImageView difficulty_image = view.findViewById(R.id.trail_trail_difficulty);
        ImageView imageView = view.findViewById(R.id.trail_trail_image);
        ImageButton backButton = view.findViewById(R.id.backButton);
        RecyclerView recyclerView = view.findViewById(R.id.trail_pin_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        if (trail != null){

            trail_name.setText(trail.getTrail_name());
            trail_desc.setText(trail.getTrail_desc());
            trail_duration.setText(String.valueOf(trail.getTrail_duration()));

            switch (trail.getTrail_difficulty()) {
                case "E":
                    difficulty_image.setImageResource(R.drawable.easy);
                    break;

                case "M":
                    difficulty_image.setImageResource(R.drawable.medium);
                    break;

                case "H":
                    difficulty_image.setImageResource(R.drawable.hard);
                    break;

                default:
                    difficulty_image.setImageResource(R.drawable.easy);
                    break;
            }

            Picasso.get()
                    .load(trail.getTrail_img().replace("http:", "https:"))
                    .into(imageView);

            backButton.setOnClickListener(v -> {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                }
            });

//            getPinsOfTrail(trail).observe(getViewLifecycleOwner(), pins -> {
//                PinsRecyclerViewAdapter adapter = new PinsRecyclerViewAdapter(pins, new PinsRecyclerViewAdapter.PinClickListener() {
//
//                    @Override
//                    public void onItemClick(int position) {
//
//                    }
//                });
//                recyclerView.setAdapter(adapter);
//            });
        }

        return view;
    }

    public LiveData<List<Pin>> getPinsOfTrail(Trail trail) {
        MutableLiveData<List<Pin>> pins = new MutableLiveData<>();
        List<Edge> edges = trail.getEdges();

//        System.out.println("aaaa");
//
//        for (Edge edge: edges) {
//            System.out.println("edge_start: " + edge.getEdge_start());
//        }

        Set<Pin> pinSet = new HashSet<>();

        edges.forEach(edge -> {
            pinSet.add(edge.getEdge_start());
            pinSet.add(edge.getEdge_end());
        });

        pins.setValue(new ArrayList<>(pinSet));

        return pins;
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
