package com.example.tubes.view.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tubes.R;
import com.example.tubes.adapter.HistoryListAdapter;
import com.example.tubes.view.home.HomeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HistoryFragment extends Fragment {

    private RecyclerView rvHistory;
    private HistoryViewModel historyViewModel;
    private HistoryListAdapter historyListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        historyListAdapter = new HistoryListAdapter(getActivity());
        rvHistory = view.findViewById(R.id.rv_history);

        rvHistory.setAdapter(historyListAdapter);
        rvHistory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rvHistory.setHasFixedSize(true);

        historyViewModel.getFilmList().observe(requireActivity(), films -> historyListAdapter.setFilmList(films));

    }

    @Override
    public void onResume() {
        super.onResume();
        historyViewModel.refreshHistory(getActivity());
    }
}