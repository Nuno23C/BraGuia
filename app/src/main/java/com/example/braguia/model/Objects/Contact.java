package com.example.braguia.model.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact",
        foreignKeys = @ForeignKey(entity = App.class,parentColumns = "app_name",childColumns = "contact_app",onDelete = ForeignKey.CASCADE))

public class Contact {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "contact_name")
    private String contact_name;

    @ColumnInfo(name = "contact_phone")
    private String contact_phone;

    @ColumnInfo(name = "contact_url")
    private String contact_url;

    @ColumnInfo(name = "contact_mail")
    private String contact_mail;

    @ColumnInfo(name = "contact_desc")
    private String contact_desc;

    @ColumnInfo(name = "contact_app")
    private String contact_app;

    @NonNull
    public String getContact_name() {
        return contact_name;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public String getContact_url() {
        return contact_url;
    }

    public String getContact_mail() {
        return contact_mail;
    }

    public String getContact_desc() {
        return contact_desc;
    }

    public String getContact_app() {
        return contact_app;
    }
}
