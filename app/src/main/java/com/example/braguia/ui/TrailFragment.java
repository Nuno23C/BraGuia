package com.example.braguia.ui;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.braguia.R;
import com.example.braguia.model.Objects.Edge;
import com.example.braguia.model.Objects.Trail;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TrailFragment extends Fragment implements OnMapReadyCallback {

    private Trail trail;
    private ArrayList<LatLng> coordinates;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1001;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            trail = (Trail) getArguments().getSerializable("selectedTrail");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trail, container, false);

        coordinates = new ArrayList<>();

        TextView trail_name = view.findViewById(R.id.trail_trail_name);
        TextView trail_desc = view.findViewById(R.id.trail_trail_desc);
        TextView trail_duration = view.findViewById(R.id.trail_trail_duration);
        ImageView difficulty_image = view.findViewById(R.id.trail_trail_difficulty);
        ImageView imageView = view.findViewById(R.id.trail_trail_image);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

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

        String imageUrl = trail.getTrail_img();
        Picasso.get().load(imageUrl).into(imageView);

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

        Button nav = view.findViewById(R.id.navButton);
        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                } else {
                    setGoogleMap();
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


    public void setGoogleMap() {
        trail = (Trail) getArguments().getSerializable("selectedTrail");

        for (Edge edge : trail.getEdges()){
            LatLng trailLocation_start = new LatLng(edge.getEdge_start().getPin_lat(), edge.getEdge_start().getPin_lng());
            LatLng trailLocation_end = new LatLng(edge.getEdge_end().getPin_lat(), edge.getEdge_end().getPin_lng());
            coordinates.add(trailLocation_start);
            coordinates.add(trailLocation_end);
        }
        if (coordinates.size() < 2){
            System.out.println("Coordenadas insuficientes");
        } else{
            // Remover repetidos
            Set<LatLng> noReps = new HashSet<>(coordinates);

            LatLng startingPoint = coordinates.get(0);
            LatLng endingPoint = coordinates.get(coordinates.size() - 1);
            List<LatLng> waypoints = new ArrayList<>(noReps);

            waypoints.remove(startingPoint);
            waypoints.remove(endingPoint);

            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), location -> {
                    if (location != null) {
                        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        LatLng newStartingPoint = currentLocation;

                        StringBuilder uriBuilder = new StringBuilder("https://www.google.com/maps/dir/?api=1");

                        uriBuilder.append("&origin=").append(newStartingPoint.latitude).append(",").append(startingPoint.longitude);
                        uriBuilder.append("&destination=").append(endingPoint.latitude).append(",").append(endingPoint.longitude);

                        if (!waypoints.isEmpty()) {
                            uriBuilder.append("&waypoints=");
                            for (LatLng waypoint : waypoints) {
                                uriBuilder.append(waypoint.latitude).append(",").append(waypoint.longitude).append("|");
                            }
                            uriBuilder.deleteCharAt(uriBuilder.length() - 1); // Remover o último "|"
                        }
                        Uri gmmIntentUri = Uri.parse(uriBuilder.toString());

                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");

                        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                        } else {
                            startActivity(mapIntent);
                        }
                    } else {
                        Log.e("TrailFragment", "Falha ao obter a localização do dispositivo");
                    }
                });
            } else {
                Log.e("TrailFragment", "Permissão ACCESS_FINE_LOCATION não dada");
            }
        }
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        this.googleMap = googleMap;
    }

}


