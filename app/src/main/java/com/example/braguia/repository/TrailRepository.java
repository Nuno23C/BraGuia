package com.example.braguia.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.braguia.BuildConfig;
import com.example.braguia.model.API_service;
import com.example.braguia.model.BraguiaDatabase;
import com.example.braguia.model.Objects.Edge;
import com.example.braguia.model.Objects.Trail;
import com.example.braguia.model.Objects.Pin;
import com.example.braguia.model.DAO.TrailDAO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrailRepository {

    private static final String BACKEND_URL = BuildConfig.BRAGUIA_API_URL;
    private static final String LAST_UPDATE = "last_update";
    private API_service api;
    SharedPreferences sharedPreferences;

    private TrailDAO trailDAO;

    private MediatorLiveData<List<Trail>> trails;



    public TrailRepository(Application application) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API_service.class);
        sharedPreferences = application.getApplicationContext().getSharedPreferences("BraguiaData", Context.MODE_PRIVATE);

        System.out.println("TrailRepository: starting");

        BraguiaDatabase db = BraguiaDatabase.getInstance(application);
        trailDAO = db.trailDAO();
        trails = new MediatorLiveData<>();
        trails.addSource(
                trailDAO.getAllTrails(), localTrails -> {
                    long currentTime = System.currentTimeMillis();
                    long lastUpdateTime = getLastUpdateTime();
                    long diff = currentTime - lastUpdateTime;
                    long threshold = 24 * 60 * 60 * 1000; // 24 hours
                    if (localTrails != null && !localTrails.isEmpty() && diff < threshold) {
                        trails.setValue(localTrails);
                    } else {
                        getTrails();
                    }
                }
        );
    }

    private void getTrails() {
        Call<List<Trail>> call = api.getTrails();
        call.enqueue(new retrofit2.Callback<List<Trail>>() {
            @Override
            public void onResponse(Call<List<Trail>> call, Response<List<Trail>> response) {
                if (response.isSuccessful()) {
                    System.out.println("RESPONSE.BODY: " + response.body());
                    insertTrails(response.body());

                    long currentTime = System.currentTimeMillis();
                    setLastUpdateTime(currentTime);
                } else {
//                    Log.e("main", "onFailure: " + response.errorBody());
                    System.out.println("error: " + response);

                }
            }

            @Override
            public void onFailure(Call<List<Trail>> call, Throwable t) {
//                Log.e("main", "onFailure: " + t.getMessage());
                System.out.println("onFailure: " + t);
            }
        });
    }

    public LiveData<List<Trail>> getAllTrails() {
        return trails;
    }

    public void insertTrails(List<Trail> trails) {
        BraguiaDatabase.databaseWriteExecutor.execute(() -> {
            trailDAO.insert(trails);
        });
    }

    public LiveData<Trail> getTrailById(int id) {
        return trailDAO.getTrailById(id);
    }

    public LiveData<List<Pin>> getPinsOfTrail(int id) {
        MutableLiveData<List<Pin>> pins = new MutableLiveData<>();
        LiveData<Trail> trailLiveData = getTrailById(id);

        trailLiveData.observeForever(trail -> {
            if (trail != null) {
                List<Edge> edges = trail.getEdges();
                Set<Pin> pinSet = new HashSet<>();

                edges.forEach(edge -> {
                    pinSet.add(edge.getEdge_start());
                    pinSet.add(edge.getEdge_end());
                });

                pins.setValue(new ArrayList<>(pinSet));

            }
        });

        return pins;
    }


    private long getLastUpdateTime() {
        return sharedPreferences.getLong(LAST_UPDATE, 0);
    }

    private void setLastUpdateTime(long timestamp) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(LAST_UPDATE, timestamp);
        editor.apply();
    }
}
