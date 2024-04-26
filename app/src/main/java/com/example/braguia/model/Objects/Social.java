package com.example.braguia.model.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "social",
         foreignKeys = @ForeignKey(entity = App.class,parentColumns = "app_name",childColumns = "social_app",onDelete = ForeignKey.CASCADE))

public class Social {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "social_name")
    private String social_name;

    @ColumnInfo(name = "social_url")
    private String social_url;

    @ColumnInfo(name = "social_share_link")
    private String social_share_link;

    @ColumnInfo(name = "social_app")
    private String social_app;

    @NonNull
    public String getSocial_name() {
        return social_name;
    }

    public String getSocial_url() {
        return social_url;
    }

    public String getSocial_share_link() {
        return social_share_link;
    }

    public String getSocial_app() {
        return social_app;
    }
}
