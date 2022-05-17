package com.kidsPlayerPrincess.kidsplayerprincess.adapters;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.kidsPlayerPrincess.kidsplayerprincess.MusicFiles;
import com.kidsPlayerPrincess.kidsplayerprincess.PlayerActivity;
import com.kidsPlayerPrincess.kidsplayerprincess.R;

import java.io.File;
import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {
    private Context mContext;
    public static ArrayList<MusicFiles> mFiles;
    private int[] images = new int[]{
            R.drawable.princess,
            R.drawable.princess2,
            R.drawable.princess3,
            R.drawable.princess4,
            R.drawable.princess5,
            R.drawable.princess,
            R.drawable.princess2,
            R.drawable.princess3,
            R.drawable.princess4,
            R.drawable.princess5,
            R.drawable.princess6,
            R.drawable.princess,
            R.drawable.princess2,
            R.drawable.princess3,
            R.drawable.princess4,
            R.drawable.princess6,
            R.drawable.princess,
            R.drawable.princess2,
            R.drawable.princess3,
            R.drawable.princess5,
            R.drawable.princess6,
            R.drawable.princess,
            R.drawable.princess3,
            R.drawable.princess4,
            R.drawable.princess5,
            R.drawable.princess,
            R.drawable.princess2,
            R.drawable.princess3,
            R.drawable.princess4,
            R.drawable.princess,
            R.drawable.princess2,
            R.drawable.princess3,
            R.drawable.princess4,
            R.drawable.princess
    };

    public MusicAdapter(Context mContext, ArrayList<MusicFiles> mFiles) {
        this.mContext = mContext;
        this.mFiles = mFiles;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.music_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.music_file_name.setText(mFiles.get(position).getTitle());
        byte[] image = getAlbumArt(mFiles.get(position).getPath());
        if(image != null){
            Glide.with(mContext).asBitmap()
                    .load(image).into(holder.music_img);
        }else{
            if(mFiles.size() < images.length) {
                Glide.with(mContext)
                        .load(images[position]).into(holder.music_img);
            }else{
                if((mFiles.size() - images.length) % 2 ==0){
                    Glide.with(mContext)
                            .load(images[0]).into(holder.music_img);
                }
                else{
                    Glide.with(mContext)
                            .load(images[1]).into(holder.music_img);
                }
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });
        holder.menu_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popupMenu = new PopupMenu(mContext, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener((item) -> {
                    switch (item.getItemId()){
                        case R.id.id_delete:
                            deleteFile(position, view);
                            break;
                    }
                    return  true;
                });
            }
        });

    }

    private void deleteFile(int position, View view) {
        Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                Long.parseLong(mFiles.get(position).getId()));
        File file = new File(mFiles.get(position).getPath());
        boolean deleted = file.delete();
        if(deleted) {
            mContext.getContentResolver().delete(contentUri, null, null);
            mFiles.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mFiles.size());
            Snackbar.make(view, "File deleted: ", Snackbar.LENGTH_LONG).show();
        }else{
            Snackbar.make(view, "File can not be deleted: ", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView music_img, menu_more;
        TextView music_file_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            music_img = itemView.findViewById(R.id.music_img);
            music_file_name = itemView.findViewById(R.id.music_file_name);
            menu_more = itemView.findViewById(R.id.music_img_more);
        }
    }
    private byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }

    public void deleteFile(int position){
        Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                Long.parseLong(mFiles.get(position).getId()));
        File file = new File(mFiles.get(position).getPath());
        Boolean deleted = file.delete();
        if(deleted) {
            mContext.getContentResolver().delete(contentUri, null, null);
            mFiles.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mFiles.size());

        }else{
            Toast.makeText(mContext, "File can not be deleted", Toast.LENGTH_SHORT).show();
        }

    }

    public void updateList(ArrayList<MusicFiles> musicFilesArrayList){
        mFiles = new ArrayList<>();
        mFiles.addAll(musicFilesArrayList);
        notifyDataSetChanged();
    }
}

