package com.kidsPlayerPrincess.kidsplayerprincess.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kidsPlayerPrincess.kidsplayerprincess.AlbumDetailsActivity;
import com.kidsPlayerPrincess.kidsplayerprincess.MusicFiles;
import com.kidsPlayerPrincess.kidsplayerprincess.R;


import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyHolder> {
    private Context mContext;
    private ArrayList<MusicFiles> albumFiles;
    private int[] imagesAlbum = new int[]{
            R.drawable.princess,
            R.drawable.princess2,
            R.drawable.princess3,
            R.drawable.princess4,
            R.drawable.princess,
            R.drawable.princess2,
            R.drawable.princess3,
            R.drawable.princess4,
            R.drawable.princess,
            R.drawable.princess2,
            R.drawable.princess3,
            R.drawable.princess4,
            R.drawable.princess,
            R.drawable.princess2,
            R.drawable.princess3,
            R.drawable.princess4,
            R.drawable.princess,
            R.drawable.princess2,
            R.drawable.princess3,
            R.drawable.princess4,
            R.drawable.princess,
            R.drawable.princess2,
            R.drawable.princess3,
            R.drawable.princess4
    };

    public AlbumAdapter(Context mContext, ArrayList<MusicFiles> albumFiles) {
        this.mContext = mContext;
        this.albumFiles = albumFiles;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.album_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.album_name2.setText(albumFiles.get(position).getAlbum());
        byte[] image = getAlbumArt(albumFiles.get(position).getPath());
        if(image != null){
            Glide.with(mContext).asBitmap()
                    .load(image)
                    .into(holder.album_image);
        }else{

            if(albumFiles.size() < imagesAlbum.length) {
                Glide.with(mContext)
                        .load(imagesAlbum[position]).into(holder.album_image);
            }else{
                if((albumFiles.size() - imagesAlbum.length) % 2 ==0){
                    Glide.with(mContext)
                            .load(imagesAlbum[0]).into(holder.album_image);
                }
                else{
                    Glide.with(mContext)
                            .load(imagesAlbum[1]).into(holder.album_image);
                }
            }
            Glide.with(mContext)
                    .load(imagesAlbum[position])
                    .into(holder.album_image);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AlbumDetailsActivity.class);
                intent.putExtra("albumName", albumFiles.get(position).getAlbum());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumFiles.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView album_image;
        TextView album_name2;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            album_image = itemView.findViewById(R.id.album_image);
            album_name2 = itemView.findViewById(R.id.album_name2);
        }
    }

    private byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}

