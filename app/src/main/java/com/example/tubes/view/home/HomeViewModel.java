package com.example.tubes.view.home;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tubes.database.AppDatabase;
import com.example.tubes.model.Film;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Film>> filmList;

    public HomeViewModel() {
        this.filmList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Film>> getFilmList() {
        return filmList;
    }

    public void refreshFilm(Context context, String type){
        new GetFilmList(context).execute(type);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortAscending(List<Film> films) {
        List<Film> temp = new ArrayList<>(films);
        Collections.sort(temp, Comparator.comparing(Film::getTitle).reversed());

        filmList.setValue(temp);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortDescending(List<Film> films) {
        List<Film> temp = new ArrayList<>(films);
        Collections.sort(temp, Comparator.comparing(Film::getTitle));
        filmList.setValue(temp);
    }

    private class GetFilmList extends AsyncTask<String, Void, List<Film>> {
        private Context context;

        public GetFilmList(Context context) {
            this.context = context;
        }

        @Override
        protected List<Film> doInBackground(String... strings) {
            List<Film> films = AppDatabase.getInstance(context).filmDAO().getFilmList();
            List<Film> filteredFilm = new ArrayList<>();

            if (strings[0].equals("All")){
                filteredFilm.addAll(films);
            } else {
                for (Film film : films) {
                    if (strings[0].equals(film.type))
                        filteredFilm.add(film);
                }

            }
            return filteredFilm;
        }

        @Override
        protected void onPostExecute(List<Film> films) {
            super.onPostExecute(films);
            filmList.setValue(films);
        }
    }
}
