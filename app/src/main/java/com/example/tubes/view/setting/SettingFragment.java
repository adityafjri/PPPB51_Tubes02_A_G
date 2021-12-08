package com.example.tubes.view.setting;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tubes.R;
import com.example.tubes.database.AppDatabase;

public class SettingFragment extends Fragment implements View.OnClickListener {

    private Button btnErase, btnReach;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnErase = view.findViewById(R.id.btn_erase);
        btnReach = view.findViewById(R.id.btn_reach);

        btnReach.setOnClickListener(this);
        btnErase.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnErase){
            showDialog();
        } else if (v == btnReach) {
            Uri uri = Uri.parse("http://instagram.com/_u/filipussetio");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            intent.setPackage("com.instagram.android");

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/filipussetio")));
            }
        }
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle("Hapus semua data?");
        alertDialogBuilder
                .setIcon(R.drawable.ic_baseline_warning_24)
                .setCancelable(true)
                .setPositiveButton("Ya", (dialog, id) -> new DeleteAsync(getActivity()).execute())
                .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private class DeleteAsync extends AsyncTask<Void, Void, Void> {
        private Context context;

        public DeleteAsync(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase.getInstance(context).filmDAO().deleteAll();
            return null;
        }
    }
}