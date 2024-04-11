package com.example.braguia.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "user_type")
    private String user_type;

    public void setId(@NonNull int id) {this.id = id;}

    public void setUsername(@NonNull String username){
        this.username = username;
    }

    public void setPassword(@NonNull String password){
        this.password = password;
    }

    public void setUser_type(@NonNull String user_type){
        this.user_type = user_type;
    }

    public int getId(){
        return this.id;
    }
    @NonNull
    public String getUsername(){
        return this.username;
    }

    @NonNull
    public String getPassword(){
        return this.password;
    }

    @NonNull
    public String getUser_type(){
        return this.user_type;
    }

}
