package com.example.moviecataloguefordicoding;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecataloguefordicoding.adapter.MovieAdapter;
import com.example.moviecataloguefordicoding.adapter.TVAdapter;
import com.example.moviecataloguefordicoding.model.ModelFilm;
import com.example.moviecataloguefordicoding.model.ModelTV;

import java.util.ArrayList;


public class MovieFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String STATE_ITEMS = "items";
    private ArrayList<ModelFilm> list;
    private ArrayList<ModelTV> listTV;
    private MovieAdapter movieAdapter;
    private TVAdapter tVAdapter;
    private ProgressBar progressBar;
    private final Observer<ArrayList<ModelFilm>> getShow = new Observer<ArrayList<ModelFilm>>() {
        @Override
        public void onChanged(ArrayList<ModelFilm> showItem) {
            if (showItem != null) {
                movieAdapter.setData(showItem);
            }
            showLoading(false);
        }
    };
    private final Observer<ArrayList<ModelTV>> getTVShow = new Observer<ArrayList<ModelTV>>() {
        @Override
        public void onChanged(ArrayList<ModelTV> showItem) {
            if (showItem != null) {
                tVAdapter.setData(showItem);
            }
            showLoading(false);
        }
    };
    private RecyclerView rvMovie;

    public MovieFragment() {
    }

    public static MovieFragment newInstance(int index) {
        MovieFragment fragment = new MovieFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        MovieViewModel viewModel = new MovieViewModel();
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        assert getArguments() != null;
        if (getArguments().getInt(ARG_SECTION_NUMBER, 0) == 0) {
            viewModel.setMovie("movie");
            viewModel.getShow().observe(this, getShow);
            rvMovie.setAdapter(movieAdapter);
        } else if (getArguments().getInt(ARG_SECTION_NUMBER, 0) == 1) {
            viewModel.setMovie("tv");
            viewModel.getTVShow().observe(this, getTVShow);
            rvMovie.setAdapter(tVAdapter);
        } else if (getArguments().getInt(ARG_SECTION_NUMBER, 0) == 2) {
            viewModel.setFavorite(getContext());
            viewModel.getShow().observe(this, getShow);
            rvMovie.setAdapter(movieAdapter);
        } else if (getArguments().getInt(ARG_SECTION_NUMBER, 0) == 3) {
            viewModel.setFavoriteTV(getContext());
            viewModel.getTVShow().observe(this, getTVShow);
            rvMovie.setAdapter(tVAdapter);
        } else {
            Log.d("Page", "error index ");
        }


    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMovie = view.findViewById(R.id.rv_movie_list);
        progressBar = view.findViewById(R.id.progressBar);
        showLoading(true);
        rvMovie.setHasFixedSize(true);

        assert getArguments() != null;
        if (savedInstanceState != null) {
            list = (ArrayList<ModelFilm>) savedInstanceState.getSerializable(STATE_ITEMS);
            listTV = (ArrayList<ModelTV>) savedInstanceState.getSerializable(STATE_ITEMS);
        } else {
            list = new ArrayList<>();
            listTV = new ArrayList<>();
        }
        movieAdapter = new MovieAdapter(list);
        tVAdapter = new TVAdapter(listTV);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STATE_ITEMS, list);
        outState.putSerializable(STATE_ITEMS, listTV);

    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


}
