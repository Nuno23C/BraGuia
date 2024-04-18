package com.example.braguia.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TrailDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Trail> trails);

    @Query("SELECT DISTINCT * FROM trail")
    LiveData<List<Trail>> getAllTrails();

    @Query("DELETE FROM trail")
    void deleteAll();
}
