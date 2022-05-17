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
import com.kidsPlayerPrincess.kidsplayerprincess.adapters.SingerAdapter;


public class SingersFragment extends Fragment {
    RecyclerView recyclerView;
    public static SingerAdapter singerAdapter;

    public SingersFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_singers, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_singer);
        recyclerView.setHasFixedSize(true);
        if(!(MainActivity.singers.size() < 1)) {
            singerAdapter = new SingerAdapter(getContext(), MainActivity.singers);
            //getItemTouchHelper().attachToRecyclerView(recyclerView);
            recyclerView.setAdapter(singerAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }

        return view;
    }
}