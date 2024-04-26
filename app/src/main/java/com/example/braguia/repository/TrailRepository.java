package com.example.braguia.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

// import com.example.braguia.BuildConfig;
import com.example.braguia.model.API_service;
import com.example.braguia.model.BraguiaDatabase;
import com.example.braguia.model.Objects.Trail;
import com.example.braguia.model.DAO.TrailDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrailRepository {

    private static final String BASE_URL = "https://579f8a2e08b3ae26545741d09e8f230a.serveo.net/";
    private static final String LAST_UPDATE = "last_update";
    private ApiService api;
    SharedPreferences sharedPreferences;

    private TrailDAO trailDAO;

    private MediatorLiveData<List<Trail>> trails;


    public TrailRepository(Application application) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ApiService.class);
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
                        System.out.println("if case 1");
                        trails.setValue(localTrails);
                    } else {
                        System.out.println("if case 2");
                        getTrails();
                    }
                }
        );
    }

    public LiveData<List<Trail>> getAllTrails() {
        return trails;
    }

    public void insertTrails(List<Trail> trails) {
        BraguiaDatabase.databaseWriteExecutor.execute(() -> {
            trailDAO.insert(trails);
        });
    }

    private void getTrails() {
        Call<List<Trail>> call = api.getTrails();
        System.out.println("entrou aqui");
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

    private long getLastUpdateTime() {
        return sharedPreferences.getLong(LAST_UPDATE, 0);
    }

    private void setLastUpdateTime(long timestamp) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(LAST_UPDATE, timestamp);
        editor.apply();
    }
}
