package com.example.tubes.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tubes.model.Film;

import java.util.function.Predicate;

@RequiresApi(api = Build.VERSION_CODES.N)
public class FilmFilter implements Predicate<Film> {

    @Override
    public boolean test(Film film) {
        return false;
    }
}
