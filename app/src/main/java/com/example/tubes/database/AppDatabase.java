package com.example.tubes.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tubes.model.Film;

@Database(entities = Film.class, version = 7)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FilmDao filmDAO();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context){
        if (INSTANCE == null) {
            synchronized (AppDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "db").build();
                }
            }
        }
        return INSTANCE;
    }
}
