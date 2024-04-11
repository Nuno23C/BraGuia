package com.example.braguia.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.DeleteTable;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM user WHERE username LIKE :user_name LIMIT 1")
    User findByName(String user_name);

    @Query("SELECT * FROM user WHERE id LIKE :uid LIMIT 1")
    User findById(int uid);

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM user WHERE id IN (:usersID)")
    LiveData<List<User>> getAllById(int[] usersID);

}
