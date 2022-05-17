package com.kidsPlayerPrincess.kidsplayerprincess.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kidsPlayerPrincess.kidsplayerprincess.MainActivity;
import com.kidsPlayerPrincess.kidsplayerprincess.R;
import com.kidsPlayerPrincess.kidsplayerprincess.adapters.MusicAdapter;

public class SongsFragment extends Fragment {
    RecyclerView recyclerView;
    public static MusicAdapter musicAdapter;

    public SongsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_song);
        recyclerView.setHasFixedSize(true);
        if(!(MainActivity.musicFiles.size() < 1)) {
            musicAdapter = new MusicAdapter(getContext(), MainActivity.musicFiles);
            //getItemTouchHelper().attachToRecyclerView(recyclerView);
            recyclerView.setAdapter(musicAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,
                    false));
        }
        return view;
    }
}