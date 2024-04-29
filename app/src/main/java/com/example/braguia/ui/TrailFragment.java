package com.example.braguia.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import com.example.braguia.R;
import com.example.braguia.model.Objects.Trail;
import com.squareup.picasso.Picasso;

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

        if (trail!=null){

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
            //String imageUrl = trail.getTrail_img();
            //Picasso.get().load(imageUrl).into(imageView);
        }

        ImageButton backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                }
            }
        });

        return view;
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
