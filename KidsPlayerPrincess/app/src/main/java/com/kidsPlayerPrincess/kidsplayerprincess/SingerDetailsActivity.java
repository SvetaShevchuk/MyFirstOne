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
import com.kidsPlayerPrincess.kidsplayerprincess.adapters.SingerDetailsAdapter;
//import com.kidsplayer3.myapplication.adapters.SingerDetailsAdapter;

import java.util.ArrayList;

public class SingerDetailsActivity extends AppCompatActivity implements ServiceConnection {
    RecyclerView recyclerView;
    ImageView singerPhoto;
    String albumName, albumName2;
    ImageView next_btn, singer_art, prev_btn;
    TextView artist_name3, song_name3;
    FloatingActionButton play_pause_btn;
    RelativeLayout relativeLayoutSinger;
    ArrayList<MusicFiles> singerSongs = new ArrayList<>();
    SingerDetailsAdapter singerDetailsAdapter;
    MusicService musicService;
    public static boolean SHOW_MINI_PLAYER = false;
    Handler handler3 = new Handler();
    public static boolean ON_COMPLETION_SINGER2;
    public static boolean IF_CREATE;
    String singersName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer_details);
        initViews();
        SharedPreferences preferences4 = getSharedPreferences("dataSinger", Context.MODE_PRIVATE);
        String ifCreate = preferences4.getString("ifCreate", null);
        if(ifCreate != null){
            if(ifCreate.equals("false")){
                IF_CREATE = false;
            }
        }else{
            IF_CREATE = true;
        }

        SingerDetailsActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (ON_COMPLETION_SINGER2) {
                    SharedPreferences pref = getSharedPreferences("dataSinger", Context.MODE_PRIVATE);
                    String artist3 = pref.getString("nameArtistSinger", null);
                    String name3 = pref.getString("nameSongSinger", null);
                    if (name3 != null) {
                        song_name3.setText(name3);
                        artist_name3.setText(artist3);
                    }
                    name3 = null;
                    artist3 = null;

                }
                ON_COMPLETION_SINGER2 = false;

                handler3.postDelayed(this, 1000);

            }
        });
        singersName = getIntent().getStringExtra("singerName");
        int j = 0;
        for (int i = 0; i < MainActivity.musicFiles.size(); i++) {
            if (singersName.equals(MainActivity.musicFiles.get(i).getArtist())) {
                singerSongs.add(j, MainActivity.musicFiles.get(i));
                j++;
            }
        }
        byte[] image = getAlbumArt(singerSongs.get(0).getPath());
        if (image != null) {
            Glide.with(this).load(image).into(singerPhoto);
        } else {
            Glide.with(this).load(R.drawable.princess_transparent2).into(singerPhoto);
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
                    if (path != null) {
                        SHOW_MINI_PLAYER = true;
                        byte[] art = getAlbumArt(path);
                        if (art != null) {
                            Glide.with(getApplicationContext()).load(art).into(singer_art);
                            song_name3.setText(songName);
                            artist_name3.setText(artistName);
                        } else {
                            Glide.with(getApplicationContext()).load(R.drawable.princess_transparent2).
                                    into(singer_art);
                            song_name3.setText(songName);
                            artist_name3.setText(artistName);
                        }
                    } else {
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
                    if (path != null) {
                        SHOW_MINI_PLAYER = true;
                        byte[] art = getAlbumArt(path);
                        if (art != null) {
                            Glide.with(getApplicationContext()).load(art).into(singer_art);
                            song_name3.setText(songName);
                            artist_name3.setText(artistName);
                        } else {
                            Glide.with(getApplicationContext())
                                    .load(R.drawable.princess_transparent2).into(singer_art);
                            song_name3.setText(songName);
                            artist_name3.setText(artistName);
                        }
                    } else {
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
        if (!(singerSongs.size() < 1)) {
            singerDetailsAdapter = new SingerDetailsAdapter(this, singerSongs);
            recyclerView.setAdapter(singerDetailsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,
                    false));
        }
        SharedPreferences preferences = getSharedPreferences(MusicService.MUSIC_LAST_PLAYED, MODE_PRIVATE);
        String path = preferences.getString(MusicService.MUSIC_FILE, null);
        String songName = preferences.getString(MusicService.SONG_NAME, null);
        String artistName = preferences.getString(MusicService.ARTIST_NAME, null);
        if (path != null) {
            SHOW_MINI_PLAYER = true;
            byte[] art = getAlbumArt(path);
            if (art != null) {
                Glide.with(this).load(art).into(singer_art);
                song_name3.setText(songName);
                artist_name3.setText(artistName);
            } else {
                Glide.with(this).load(R.drawable.princess_transparent2).into(singer_art);
                song_name3.setText(songName);
                artist_name3.setText(artistName);
            }
        } else {
            SHOW_MINI_PLAYER = false;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    void initViews() {
        recyclerView = findViewById(R.id.recyclerView_singer_details);
        singerPhoto = findViewById(R.id.singerPhoto);
        artist_name3 = findViewById(R.id.song_artist_mini_player_singer);
        song_name3 = findViewById(R.id.song_name_mini_player_singer);
        singer_art = findViewById(R.id.bottom_singer_art);
        next_btn = findViewById(R.id.skip_next_bottom_singer);
        prev_btn = findViewById(R.id.skip_prev_bottom_singer);
        play_pause_btn = findViewById(R.id.play_pause_mini_player_singer);
        relativeLayoutSinger = findViewById(R.id.card_bottom_player_singer);
    }

    private byte[] getAlbumArt(String uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
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