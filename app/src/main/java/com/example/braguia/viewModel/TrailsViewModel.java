package com.example.braguia.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.braguia.model.Objects.Trail;
import com.example.braguia.model.Objects.Pin;
import com.example.braguia.repository.TrailRepository;

import java.util.List;

public class TrailsViewModel extends AndroidViewModel {

    private TrailRepository trailRepository;

    public LiveData<List<Trail>> trails;

    public TrailsViewModel(@NonNull Application application) {
        super(application);
        trailRepository = new TrailRepository(application);
        trails = trailRepository.getAllTrails();
    }

    public LiveData<List<Trail>> getAllTrails() {
        return trails;
    }

    public LiveData<Trail> getTrailById(int id) {
        return trailRepository.getTrailById(id);
    }

    public LiveData<List<Pin>> getPinsOfTrail(int id) {
        return trailRepository.getPinsOfTrail(id);
    }
}
