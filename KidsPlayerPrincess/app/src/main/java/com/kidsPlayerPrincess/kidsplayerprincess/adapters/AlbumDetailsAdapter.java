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
import com.kidsPlayerPrincess.kidsplayerprincess.MusicFiles;
import com.kidsPlayerPrincess.kidsplayerprincess.PlayerActivity;
import com.kidsPlayerPrincess.kidsplayerprincess.R;

import java.util.ArrayList;

public class AlbumDetailsAdapter extends RecyclerView.Adapter<AlbumDetailsAdapter.MyHolderAlbumDetails> {
    private Context mContext;
    public static ArrayList<MusicFiles> albumFiles;

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

    public AlbumDetailsAdapter(Context mContext, ArrayList<MusicFiles> albumFiles) {
        this.mContext = mContext;
        this.albumFiles = albumFiles;
    }

    @NonNull
    @Override
    public MyHolderAlbumDetails onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.music_item, parent, false);
        return new MyHolderAlbumDetails(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolderAlbumDetails holder, @SuppressLint("RecyclerView") int position) {
        holder.music_file_name.setText(albumFiles.get(position).getTitle());
        byte[] image = getAlbumArt(albumFiles.get(position).getPath());
        if(image != null){
            Glide.with(mContext).asBitmap()
                    .load(image)
                    .into(holder.music_img);
        }else{

            Glide.with(mContext)
                    .load(imagesAlbum[position])
                    .into(holder.music_img);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra("sender", "albumDetails");
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return albumFiles.size();
    }

    public class MyHolderAlbumDetails extends RecyclerView.ViewHolder{
        ImageView music_img;
        TextView music_file_name;

        public MyHolderAlbumDetails(@NonNull View itemView) {
            super(itemView);
            music_img = itemView.findViewById(R.id.music_img);
            music_file_name = itemView.findViewById(R.id.music_file_name);
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
