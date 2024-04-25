package com.example.braguia.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

// import com.example.braguia.BuildConfig;
import com.example.braguia.model.API_service;
import com.example.braguia.model.BraguiaDatabase;
import com.example.braguia.model.Trail;
import com.example.braguia.model.API_service;
import com.example.braguia.model.TrailDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrailRepository {

    public TrailDAO trailDAO;
    public MediatorLiveData<List<Trail>> trails;

    public TrailRepository(Application application) {
        BraguiaDatabase db = BraguiaDatabase.getInstance(application);
        trailDAO = db.trailDAO();
        trails = new MediatorLiveData<>();
        trails.addSource(
                trailDAO.getAllTrails(), localTrails -> {
                    // TODO: ADD cache validation logic
                    if (localTrails != null && !localTrails.isEmpty()) {
                        trails.setValue(localTrails);
                    } else {
                        makeRequest();
                    }
                }
        );
    }

    private void makeRequest() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://c14d-193-137-92-5.ngrok-free.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API_service api = retrofit.create(API_service.class);
        Call<List<Trail>> call = api.getTrails();
        call.enqueue(new retrofit2.Callback<List<Trail>>() {
            @Override
            public void onResponse(Call<List<Trail>> call, Response<List<Trail>> response) {
                if(response.isSuccessful()) {
                    insertTrails(response.body());
                }
                else{
                    Log.e("main", "onFailure: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Trail>> call, Throwable t) {
                Log.e("main", "onFailure: " + t.getMessage());
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

//    public static final ExecutorService executorService = Executors.newFixedThreadPool(4);
//
//    public static void insertAsync(TrailDAO trailDAO, List<Trail> trails){
//        executorService.execute(() -> {
//            trailDAO.insert(trails);
//        });
//    }
}
