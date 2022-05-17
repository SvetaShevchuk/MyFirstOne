package com.kidsPlayerPrincess.kidsplayerprincess.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kidsPlayerPrincess.kidsplayerprincess.MainActivity;
import com.kidsPlayerPrincess.kidsplayerprincess.R;
import com.kidsPlayerPrincess.kidsplayerprincess.adapters.AlbumAdapter;



public class AlbumFragment extends Fragment {
    RecyclerView recyclerView;
    public static AlbumAdapter albumAdapter;

    public AlbumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_album);
        recyclerView.setHasFixedSize(true);
        if (!(MainActivity.albums.size() < 1)) {
            albumAdapter = new AlbumAdapter(getContext(), MainActivity.albums);
            //getItemTouchHelper().attachToRecyclerView(recyclerView);
            recyclerView.setAdapter(albumAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        return view;
    }
}