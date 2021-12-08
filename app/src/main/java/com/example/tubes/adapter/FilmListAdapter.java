package com.example.tubes.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tubes.model.Film;
import com.example.tubes.R;
import com.example.tubes.view.addedit.AddEditFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.ViewHolder> {

    private Context context;
    private List<Film> filmList;

    public FilmListAdapter(Context context) {
        this.context = context;
        filmList = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setFilmList(List<Film> filmList) {
        this.filmList = filmList;
        notifyDataSetChanged();
    }

    public List<Film> getFilmList() {
        return filmList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(filmList.get(position));
    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle, tvStatus, tvEpisode;
        protected ImageView ivFilm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvStatus = itemView.findViewById(R.id.tv_card_item_status);
            this.tvTitle = itemView.findViewById(R.id.tv_card_item_title);
            this.ivFilm = itemView.findViewById(R.id.iv_card_item);
            this.tvEpisode = itemView.findViewById(R.id.tv_card_item_episode);
        }

        public void bind(Film film) {
            tvTitle.setText(film.title);
            tvStatus.setText(film.status);
            String episode = String.format("Episode: %s", film.episode);
            ivFilm.setImageURI(Uri.parse(film.imageUri));
            if (!film.type.equals("Movie")) {
                tvEpisode.setText(episode);
                tvEpisode.setVisibility(View.VISIBLE);
            } else {
                tvEpisode.setVisibility(View.GONE);
            }

//            if (film.imageUri == null) {
//                ivFilm.setImageResource(R.drawable.poster_example_1);
//            } else {
//                ivFilm.setImageURI(Uri.parse(film.imageUri));
//            }

            itemView.setOnClickListener(v -> {
                AddEditFragment addEditFragment = new AddEditFragment();
                Bundle itemId = new Bundle();
                itemId.putLong("FILM_ID", film.id);
                addEditFragment.setArguments(itemId);

                FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, addEditFragment)
                        .addToBackStack(null)
                        .commit();
            });
        }
    }
}
