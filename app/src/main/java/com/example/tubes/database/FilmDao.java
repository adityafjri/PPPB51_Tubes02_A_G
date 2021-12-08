package com.example.tubes.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tubes.model.Film;

import java.util.List;

@Dao
public interface FilmDao {

    @Query("SELECT * FROM film")
    List<Film> getFilmList();

    @Query("SELECT * FROM film WHERE id LIKE :filmId")
    Film getFilm(long filmId);

    @Insert
    void insert(Film film);

    @Update
    void update(Film film);

    @Query("DELETE FROM film")
    void deleteAll();
}
