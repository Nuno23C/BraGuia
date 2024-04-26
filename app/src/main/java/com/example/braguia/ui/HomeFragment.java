package com.example.braguia.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.braguia.R;
import com.example.braguia.model.Trail;
import com.example.braguia.model.ApiService;
import com.example.braguia.repository.RetrofitClient;
import com.example.braguia.viewModel.TrailsViewModel;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements TrailsRecyclerViewAdapter.TrailClickListener {

    private TrailsViewModel trailsViewModel;

    private TrailsRecyclerViewAdapter adapter;

//    public HomeFragment() {
//    }

//    public static TrailListFragment newInstance(int itemCount) {
//        TrailListFragment fragment = new TrailListFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_ITEM_COUNT, itemCount);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        trailsViewModel = new ViewModelProvider(this).get(TrailsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.trail_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TrailsRecyclerViewAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        trailsViewModel = new ViewModelProvider(this).get(TrailsViewModel.class);
        trailsViewModel.getAllTrails().observe(getViewLifecycleOwner(), trails -> {
//            loadRecyclerView(view, trails);
            System.out.println("HomeFragment: " + trails);
            adapter.setTrails(trails);
        });

        return view;
    }

//    private void loadRecyclerView(View view, List<Trail> trails){
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            if (mItemCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mItemCount));
//            }
//            recyclerView.setAdapter(new TrailsRecyclerViewAdapter(trails));
//        }
//    }

    @Override
    public void onItemClick(int position) {
        Trail selected = adapter.getTrail(position);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("selectedTrail", (Serializable) selectedTrail);
//        Navigation.findNavController(getView()).navigate(R.id.action_trailListFragment_to_trailDetailsFragment, bundle);
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
