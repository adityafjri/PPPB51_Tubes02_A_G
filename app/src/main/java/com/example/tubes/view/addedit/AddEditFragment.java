package com.example.tubes.view.addedit;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.tubes.R;
import com.example.tubes.model.Film;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class AddEditFragment extends Fragment {

    private AddEditViewModel addEditViewModel;
    private EditText etTitle, etReview, etEpisode;
    private AutoCompleteTextView dropdownStatus, dropdownType;
    private RatingBar rbRating;
    private Button btnSave;
    private ImageView imgUpload;
    private ArrayAdapter<CharSequence> statusListAdapter, typeListAdapter;
    private long instanceId;

    private String imageUri;
    private Button btnChooseImg;

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Here, no request code
                    Uri selectedImageUri = result.getData().getData();
                    imageUri = selectedImageUri.toString();
                    imgUpload.setImageURI(selectedImageUri);
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null)
            instanceId = getArguments().getLong("FILM_ID");
        return inflater.inflate(R.layout.fragment_add_edit, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        statusListAdapter = new ArrayAdapter(getActivity(), R.layout.dropdown_item_list, getResources().getStringArray(R.array.status));
        typeListAdapter = new ArrayAdapter(getActivity(), R.layout.dropdown_item_list, getResources().getStringArray(R.array.type));
        dropdownStatus.setAdapter(statusListAdapter);
        dropdownType.setAdapter(typeListAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.addEditViewModel = new ViewModelProvider(this).get(AddEditViewModel.class);

        this.dropdownStatus = view.findViewById(R.id.dropdown_status);
        this.dropdownType = view.findViewById(R.id.dropdown_type);
        this.etTitle = view.findViewById(R.id.et_title);
        this.etReview = view.findViewById(R.id.et_review);
        this.etEpisode = view.findViewById(R.id.et_episode);
        this.rbRating = view.findViewById(R.id.rb_rating);
        this.btnSave = view.findViewById(R.id.btn_save);
        this.btnChooseImg = view.findViewById(R.id.btn_choose_image);
        this.imgUpload = view.findViewById(R.id.img_upload_main);

        btnChooseImg.setOnClickListener(v -> {
            chooseImageFromDevice();
        });

        dropdownType.setOnItemClickListener((parent, view1, position, id) -> etEpisode.setEnabled(position != 0));

        btnSave.setOnClickListener(v -> {
            if (etTitle.getText().toString().trim().isEmpty()
                    || etReview.getText().toString().trim().isEmpty()
                    || dropdownStatus.getText().toString().trim().equals("Status")
                    || rbRating.getRating() == 0
                    || dropdownType.getText().toString().trim().equals("Jenis")
                    || (dropdownType.getText().toString().trim().equals("Series") && etEpisode.getText().toString().trim().isEmpty())
                    || imgUpload.getDrawable() == null
            ) Toast.makeText(getActivity(), "Mohon Lengkapi Form", Toast.LENGTH_SHORT).show();
            else if (instanceId == 0) {
                Film newFilm = new Film();
                newFilm.title = etTitle.getText().toString().trim();
                newFilm.status = dropdownStatus.getText().toString().trim();
                newFilm.rating = rbRating.getRating();
                newFilm.review = etReview.getText().toString().trim();
                newFilm.type = dropdownType.getText().toString().trim();
                newFilm.episode = etEpisode.getText().toString().trim();
                newFilm.imageUri = imageUri;

                addEditViewModel.addFilm(getActivity(), newFilm);

                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                Film updateFilm = new Film();
                updateFilm.id = instanceId;
                updateFilm.title = etTitle.getText().toString().trim();
                updateFilm.status = dropdownStatus.getText().toString().trim();
                updateFilm.rating = rbRating.getRating();
                updateFilm.review = etReview.getText().toString().trim();
                updateFilm.type = dropdownType.getText().toString().trim();
                updateFilm.episode = etEpisode.getText().toString().trim();
                updateFilm.imageUri = imageUri;

                addEditViewModel.updateFilm(getActivity(), updateFilm);

                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        if (instanceId != 0) {
            addEditViewModel.getFilmById(getActivity(), instanceId);
        }

        addEditViewModel.getDetailFilm().observe(requireActivity(), film -> {
            etTitle.setText(film.title);
            dropdownStatus.setText(film.status, false);
            rbRating.setRating(film.rating);
            etReview.setText(film.review);
            imageUri = film.imageUri;
            imgUpload.setImageURI(Uri.parse(film.imageUri));
            dropdownType.setText(film.type, false);
            if (film.type.equals("Movie")) {
                etEpisode.setEnabled(false);
                etEpisode.setText("");
            } else {
                etEpisode.setEnabled(true);
                etEpisode.setText(film.episode);
            }
        });

        dropdownType.setOnItemClickListener((parent, view1, position, id) -> {
            if (position == 0) {
                etEpisode.setText("");
                etEpisode.setEnabled(false);
            } else {
                etEpisode.setEnabled(true);
            }

        });
    }

    private void chooseImageFromDevice() {
        Intent intent;
        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        someActivityResultLauncher.launch(intent);
    }

}