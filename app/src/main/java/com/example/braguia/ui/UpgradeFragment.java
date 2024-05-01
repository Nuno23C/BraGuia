package com.example.braguia.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.braguia.R;


public class UpgradeFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upgrade, container, false);

//        ImageButton backButton = view.findViewById(R.id.go_back_button);
//
//        backButton.setOnClickListener(v -> {
//            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//            if (fragmentManager.getBackStackEntryCount() > 0) {
//                fragmentManager.popBackStack();
//            }
//        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).hideBottomNavigationView();
    }
}
