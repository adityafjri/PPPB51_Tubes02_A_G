package com.example.tubes.view.addedit;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tubes.database.AppDatabase;
import com.example.tubes.model.Film;

public class AddEditViewModel extends ViewModel {

    private MutableLiveData<Film> detailFilm;

    public AddEditViewModel() {
        detailFilm = new MutableLiveData<>();
    }

    public void addFilm(Context context, Film film){
        new InsertAsync(context).execute(film);
    }

    public void getFilmById(Context context, long instanceId) {
        new GetByIdAsync(context).execute(instanceId);
    }

    public MutableLiveData<Film> getDetailFilm() {
        return detailFilm;
    }

    public void updateFilm(Context context, Film film) {
        new UpdateAsync(context).execute(film);
    }

    private class InsertAsync extends AsyncTask<Film, Void, Void>{
        private Context context;

        public InsertAsync(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Film... films) {
            AppDatabase.getInstance(context).filmDAO().insert(films[0]);
            return null;
        }
    }

    private class GetByIdAsync extends AsyncTask<Long, Void, Film>{
        private Context context;

        public GetByIdAsync(Context context) {
            this.context = context;
        }

        @Override
        protected Film doInBackground(Long... longs) {
            return AppDatabase.getInstance(context).filmDAO().getFilm(longs[0]);
        }

        @Override
        protected void onPostExecute(Film film) {
            super.onPostExecute(film);
            detailFilm.setValue(film);
        }
    }

    private class UpdateAsync extends AsyncTask<Film, Void, Void>{
        Context context;

        public UpdateAsync(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Film... films) {
            AppDatabase.getInstance(context).filmDAO().update(films[0]);
            return null;
        }
    }
}
