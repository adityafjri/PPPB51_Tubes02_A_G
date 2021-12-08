package com.example.tubes.model;


import android.graphics.Bitmap;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "film")
public class Film implements Comparable<Film> {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    public String title;
    public String status;
    public float rating;
    public String review;
    public String type;
    public String episode;
    public String imageUri;

    @Override
    public int compareTo(Film other) {
        return title.compareTo(other.title);
    }

    public String getTitle() {
        return title;
    }
}
