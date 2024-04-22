package com.example.braguia.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.braguia.R;
import com.example.braguia.model.Trail;
import com.example.braguia.model.ApiService;
import com.example.braguia.repository.RetrofitClient;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private ApiService apiService;

    private TextView trailsTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        trailsTextView = rootView.findViewById(R.id.trailsTextView);

        Call<List<Trail>> call = apiService.getTrails();
        call.enqueue(new Callback<List<Trail>>() {
            @Override
            public void onResponse(Call<List<Trail>> call, Response<List<Trail>> response) {
                if (response.isSuccessful()) {
                    List<Trail> trails = response.body();
                    if (trails != null && !trails.isEmpty()) {
                        StringBuilder trailsText = new StringBuilder();
                        for (Trail trail : trails) {
                            trailsText.append(trail.getTrail_name()).append("\n");
                        }
                        trailsTextView.setText(trailsText.toString());
                    }
                } else {
                    trailsTextView.setText("Erro ao carregar os roteiros");
                }
            }
            @Override
            public void onFailure(Call<List<Trail>> call, Throwable t) {
                trailsTextView.setText("Erro de conexão");
            }
        });
        return rootView;
    }
}
