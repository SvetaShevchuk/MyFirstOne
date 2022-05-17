package com.kidsPlayerPrincess.kidsplayerprincess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kidsPlayerPrincess.kidsplayerprincess.adapters.AlbumDetailsAdapter;

import java.util.ArrayList;

public class AlbumDetailsActivity extends AppCompatActivity implements ServiceConnection {
    RecyclerView recyclerView;
    ImageView albumPhoto;
    String albumName, albumName2;
    RelativeLayout relativeLayoutAlbum;
    ImageView next_btn, album_art, prev_btn;
    TextView artist_name, song_name2;
    FloatingActionButton play_pause_btn;
    ArrayList<MusicFiles> albumSongs = new ArrayList<>();
    AlbumDetailsAdapter albumDetailsAdapter;
    MusicService musicService;
    public static   boolean SHOW_MINI_PLAYER = false;
    Handler handler2 = new Handler();
    public static boolean ON_COMPLETION_ALBUM2;
    public static boolean IF_CREATE;
    public static boolean IF_CREATE_PLAYER = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);
        initViews();
        SharedPreferences preferences3 = getSharedPreferences("dataAlbum", Context.MODE_PRIVATE);
        String ifCreate = preferences3.getString("ifCreate", null);
        if(ifCreate != null){
            if(ifCreate.equals("false")){
                IF_CREATE = false;
            }
        }else{
            IF_CREATE = true;
        }

        AlbumDetailsActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (ON_COMPLETION_ALBUM2) {
                    SharedPreferences p = getSharedPreferences("dataAlbum", Context.MODE_PRIVATE);
                    String artist2 = p.getString("nameArtistAlbum", null);
                    String name2 = p.getString("nameSongAlbum", null);
                    if (name2 != null) {
                        song_name2.setText(name2);
                        artist_name.setText(artist2);
                    }
                    name2 = null;
                    artist2 = null;

                }
                ON_COMPLETION_ALBUM2 = false;

                handler2.postDelayed(this, 1000);

            }
        });
        albumName = getIntent().getStringExtra("albumName");
        int j = 0;
        for (int i = 0; i < MainActivity.musicFiles.size(); i++) {
            if (albumName.equals(MainActivity.musicFiles.get(i).getAlbum())) {
                albumSongs.add(j, MainActivity.musicFiles.get(i));
                j++;
            }
        }
        byte[] image = getAlbumArt(albumSongs.get(0).getPath());
        if(image != null){
            Glide.with(this).load(image).into(albumPhoto);
        }else{
            Glide.with(this).load(R.drawable.princess_transparent2).into(albumPhoto);
        }
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicService != null) {
                    musicService.nextBtnClicked();
                    SharedPreferences.Editor editor =
                            getSharedPreferences(MusicService.MUSIC_LAST_PLAYED, MODE_PRIVATE).edit();
                    editor.putString(MusicService.MUSIC_FILE, musicService.musicFilesService.
                            get(musicService.position).getPath());
                    editor.putString(MusicService.ARTIST_NAME, musicService.musicFilesService.
                            get(musicService.position).getArtist());
                    editor.putString(MusicService.SONG_NAME, musicService.musicFilesService.
                            get(musicService.position).getTitle());
                    editor.apply();
                    SharedPreferences preferences =
                            getSharedPreferences(MusicService.MUSIC_LAST_PLAYED, MODE_PRIVATE);
                    String path = preferences.getString(MusicService.MUSIC_FILE, null);
                    String artistName = preferences.getString(MusicService.ARTIST_NAME, null);
                    String songName = preferences.getString(MusicService.SONG_NAME, null);
                    if(path != null){
                        SHOW_MINI_PLAYER = true;
                        byte[] art = getAlbumArt(path);
                        if(art != null){
                            Glide.with(getApplicationContext()).load(art).into(album_art);
                            song_name2.setText(songName);
                            artist_name.setText(artistName);
                        }else{
                            Glide.with(getApplicationContext()).load(R.drawable.princess_transparent2).into(album_art);
                            song_name2.setText(songName);
                            artist_name.setText(artistName);
                        }
                    }else{
                        SHOW_MINI_PLAYER = false;
                    }
                }
            }
        });
        prev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicService != null) {
                    musicService.prevBtnClicked();
                    SharedPreferences.Editor editor =
                            getSharedPreferences(MusicService.MUSIC_LAST_PLAYED, MODE_PRIVATE).edit();
                    editor.putString(MusicService.MUSIC_FILE, musicService.musicFilesService.
                            get(musicService.position).getPath());
                    editor.putString(MusicService.ARTIST_NAME, musicService.musicFilesService.
                            get(musicService.position).getArtist());
                    editor.putString(MusicService.SONG_NAME, musicService.musicFilesService.
                            get(musicService.position).getTitle());
                    editor.apply();
                    SharedPreferences preferences =
                            getSharedPreferences(MusicService.MUSIC_LAST_PLAYED, MODE_PRIVATE);
                    String path = preferences.getString(MusicService.MUSIC_FILE, null);
                    String artistName = preferences.getString(MusicService.ARTIST_NAME, null);
                    String songName = preferences.getString(MusicService.SONG_NAME, null);
                    if(path != null){
                        SHOW_MINI_PLAYER = true;
                        byte[] art = getAlbumArt(path);
                        if(art != null){
                            Glide.with(getApplicationContext()).load(art).into(album_art);
                            song_name2.setText(songName);
                            artist_name.setText(artistName);
                        }else{
                            Glide.with(getApplicationContext()).load(R.drawable.princess_transparent2).into(album_art);
                            song_name2.setText(songName);
                            artist_name.setText(artistName);
                        }
                    }else{
                        SHOW_MINI_PLAYER = false;
                    }
                }

            }
        });
        play_pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicService != null) {
                    musicService.playBtnClicked();
                    if (musicService.isPlaying()) {
                        play_pause_btn.setImageResource(R.drawable.pause);
                    } else {
                        play_pause_btn.setImageResource(R.drawable.play);
                    }
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, this, BIND_AUTO_CREATE);
        if (!(albumSongs.size() < 1)){
            albumDetailsAdapter = new AlbumDetailsAdapter(this, albumSongs);
            recyclerView.setAdapter(albumDetailsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,
                    false));
        }
        SharedPreferences preferences = getSharedPreferences(MusicService.MUSIC_LAST_PLAYED, MODE_PRIVATE);
        String path = preferences.getString(MusicService.MUSIC_FILE, null);
        String songName = preferences.getString(MusicService.SONG_NAME, null);
        String artistName = preferences.getString(MusicService.ARTIST_NAME, null);
        if(path != null){
            SHOW_MINI_PLAYER = true;
            byte[] art = getAlbumArt(path);
            if(art != null){
                Glide.with(this).load(art).into(album_art);
                song_name2.setText(songName);
                artist_name.setText(artistName);
            }else{
                Glide.with(this).load(R.drawable.princess_transparent2).into(album_art);
                song_name2.setText(songName);
                artist_name.setText(artistName);
            }
        }else{
            SHOW_MINI_PLAYER = false;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

//        if(IF_CREATE){
//            relativeLayoutAlbum.setVisibility(View.INVISIBLE);
//        }
//        else{
//            relativeLayoutAlbum.setVisibility(View.VISIBLE);
//        }
//        IF_CREATE = false;
    }

    private byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
    void initViews(){
        recyclerView = findViewById(R.id.recyclerView_album_details);
        albumPhoto = findViewById(R.id.albumPhoto);
        artist_name = findViewById(R.id.song_artist_mini_player);
        song_name2= findViewById(R.id.song_name_mini_player);
        album_art = findViewById(R.id.bottom_album_art);
        next_btn = findViewById(R.id.skip_next_bottom);
        prev_btn = findViewById(R.id.skip_prev_bottom);
        play_pause_btn = findViewById(R.id.play_pause_mini_player);
        relativeLayoutAlbum = findViewById(R.id.card_bottom_player_album);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service) {
        MusicService.MyBinder myBinder = (MusicService.MyBinder) service;
        musicService = myBinder.getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        musicService = null;
    }
}