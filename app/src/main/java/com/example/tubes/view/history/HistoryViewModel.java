package com.example.tubes.view.history;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tubes.database.AppDatabase;
import com.example.tubes.model.Film;

import java.util.ArrayList;
import java.util.List;

public class HistoryViewModel extends ViewModel {

    MutableLiveData<List<Film>> filmList;

    public HistoryViewModel() {
        filmList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Film>> getFilmList() {
        return filmList;
    }

    public void refreshHistory(Context context){
        new GetHistoryAsync(context).execute();
    }

    private class GetHistoryAsync extends AsyncTask<Void, Void, List<Film>> {
        private Context context;

        public GetHistoryAsync(Context context) {
            this.context = context;
        }

        @Override
        protected List<Film> doInBackground(Void... voids) {
            List<Film> films = AppDatabase.getInstance(context).filmDAO().getFilmList();
            List<Film> historyFilm = new ArrayList<>();

            for (Film film : films){
                if (film.status.equals("Sedang ditonton")){
                    historyFilm.add(film);
                }
            }
            return historyFilm;
        }

        @Override
        protected void onPostExecute(List<Film> films) {
            super.onPostExecute(films);
            filmList.setValue(films);
        }
    }
}
