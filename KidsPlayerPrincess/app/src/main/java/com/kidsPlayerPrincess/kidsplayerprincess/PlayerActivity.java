package com.kidsPlayerPrincess.kidsplayerprincess;


import static com.kidsPlayerPrincess.kidsplayerprincess.adapters.AlbumDetailsAdapter.albumFiles;
import static com.kidsPlayerPrincess.kidsplayerprincess.adapters.MusicAdapter.mFiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kidsPlayerPrincess.kidsplayerprincess.adapters.SingerDetailsAdapter;

import java.util.ArrayList;
import java.util.Random;

public class PlayerActivity extends AppCompatActivity implements
        ActionPlaying, ServiceConnection {
    SeekBar seekbar;
    TextView now_playing, song_name, song_artist, duration_played, duration_total;
    ImageView back_btn, menu_btn, cover_art, imageViewGradient, shuffle_btn, prev_btn,
            next_btn, repeat_btn;
    FloatingActionButton play_pause;
    int position = -1;
    public static ArrayList<MusicFiles> listSongs = new ArrayList<>();
    public static Uri uri;
    //static MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    private Thread playThread, prevThread, nextThread;
    MusicService musicService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);


        initViews();
        getIntentMethod();
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (musicService != null && b) {
                    musicService.seekTo(i * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (musicService != null) {
                    int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                    seekbar.setProgress(mCurrentPosition);
                    duration_played.setText(formattedTime(mCurrentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });
        shuffle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.shuffleButton) {
                    MainActivity.shuffleButton = false;
                    shuffle_btn.setImageResource(R.drawable.shuffle);
                } else {
                    MainActivity.shuffleButton = true;
                    shuffle_btn.setImageResource(R.drawable.shuffle_blue);
                }
            }
        });
        repeat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.repeatBoolean) {
                    MainActivity.repeatBoolean = false;
                    repeat_btn.setImageResource(R.drawable.repeat);
                } else {
                    MainActivity.repeatBoolean = true;
                    repeat_btn.setImageResource(R.drawable.epeat_blue);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, this, BIND_AUTO_CREATE);
        playThreadBtn();
        nextThreadBtn();
        prevThreadBtn();

        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);

    }

    private void getIntentMethod() {
        position = getIntent().getIntExtra("position", -1);
        String sender = getIntent().getStringExtra("sender");//from AlbumDetailsActivity
        String senderSinger= getIntent().getStringExtra("senderSinger");//from SingerDetailsAdapter
        if(sender!= null){
            if(sender.equals("albumDetails")){
                listSongs = albumFiles;
            }else{
                listSongs = MainActivity.musicFiles;
            }
            if(listSongs != null) {
                play_pause.setImageResource(R.drawable.pause);
                uri = Uri.parse(listSongs.get(position).getPath());
            }
        }else if(senderSinger != null){
            if(senderSinger.equals("singerDetails")){
                listSongs = SingerDetailsAdapter.singerFiles;
            }else{
                listSongs = MainActivity.musicFiles;
            }
            if(listSongs != null) {
                play_pause.setImageResource(R.drawable.pause);
                uri = Uri.parse(listSongs.get(position).getPath());
            }
        }else{
            listSongs = mFiles;
            if(listSongs != null) {
                play_pause.setImageResource(R.drawable.pause);
                uri = Uri.parse(listSongs.get(position).getPath());
            }
        }


//        if(sender != null && sender.equals("albumDetails")){
//            listSongs = albumFiles;
//        }else {
//            listSongs = musicFiles;
//        }
//
//        if(senderSinger != null && senderSinger.equals("singerDetails")){
//            listSongs = singerFiles;
//        }else {
//            listSongs = musicFiles;
//        }
//
//        if(listSongs != null){
//            play_pause.setImageResource(R.drawable.pause);
//            uri = Uri.parse(listSongs.get(position).getPath());
//        }
//        if(musicService != null){
//            musicService.stop();
//            musicService.release();
////            musicService.createMediaPlayer(position);
////            musicService.start();
//        }else{
//            musicService.createMediaPlayer(position);
//            musicService.start();
//        }
        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra("servicePosition", position);
        startService(intent);

    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service) {
        MusicService.MyBinder myBinder = (MusicService.MyBinder) service;
        musicService = myBinder.getService();
        musicService.setCallBack(this);
        if (musicService != null) {
            seekbar.setMax(musicService.getDuration() / 1000);
        } else {
            musicService.createMediaPlayer(position);
            musicService.start();
            seekbar.setMax(musicService.getDuration() / 1000);
        }
        metaData(uri);
        song_name.setText(listSongs.get(position).getTitle());
        song_artist.setText(listSongs.get(position).getArtist());
        musicService.onCompleted();
        musicService.showNotification(R.drawable.pause);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        musicService = null;
    }

    public void play_pauseClicked() {
        if(musicService.isPlaying()){
            play_pause.setImageResource(R.drawable.play);
            musicService.showNotification(R.drawable.play);
            musicService.pause();
            if (musicService != null) {
                seekbar.setMax(musicService.getDuration() / 1000);
            } else {
                musicService.createMediaPlayer(position);
                musicService.start();
                seekbar.setMax(musicService.getDuration() / 1000);
            }
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (musicService != null) {
                        int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                        seekbar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }else{
            play_pause.setImageResource(R.drawable.pause);
            musicService.showNotification(R.drawable.pause);
            musicService.start();
            if (musicService != null) {
                seekbar.setMax(musicService.getDuration() / 1000);
            } else {
                musicService.createMediaPlayer(position);
                musicService.start();
                seekbar.setMax(musicService.getDuration() / 1000);
            }
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (musicService != null) {
                        int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                        seekbar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }
    }

    public void next_btnClicked() {
        if(musicService.isPlaying()){
            musicService.stop();
            musicService.release();
            if (MainActivity.shuffleButton && !MainActivity.repeatBoolean) {
                position = randomNumber(listSongs.size() - 1);
            } else if (!MainActivity.shuffleButton && !MainActivity.repeatBoolean) {
                position = ((position + 1) % listSongs.size());
            }
            uri = Uri.parse(listSongs.get(position).getPath());
            musicService.createMediaPlayer(position);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            song_artist.setText(listSongs.get(position).getArtist());
            if (musicService != null) {
                seekbar.setMax(musicService.getDuration() / 1000);
            } else {
                musicService.createMediaPlayer(position);
                musicService.start();
                seekbar.setMax(musicService.getDuration() / 1000);
            }
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (musicService != null) {
                        int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                        seekbar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            musicService.onCompleted();
            musicService.showNotification(R.drawable.pause);
            play_pause.setBackgroundResource(R.drawable.pause);
            musicService.start();
        }else{
            musicService.stop();
            musicService.release();
            if (MainActivity.shuffleButton && !MainActivity.repeatBoolean) {
                position = randomNumber(listSongs.size() - 1);
            } else if (!MainActivity.shuffleButton && !MainActivity.repeatBoolean) {
                position = ((position + 1) % listSongs.size());
            }
            uri = Uri.parse(listSongs.get(position).getPath());
            musicService.createMediaPlayer(position);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            song_artist.setText(listSongs.get(position).getArtist());
            if (musicService != null) {
                seekbar.setMax(musicService.getDuration() / 1000);
            } else {
                musicService.createMediaPlayer(position);
                musicService.start();
                seekbar.setMax(musicService.getDuration() / 1000);
            }
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (musicService != null) {
                        int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                        seekbar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            musicService.onCompleted();
            musicService.showNotification(R.drawable.play);
            play_pause.setBackgroundResource(R.drawable.play);
        }
    }

    public void prev_btnClicked() {
        if(musicService.isPlaying()){
            musicService.stop();
            musicService.release();
            if (MainActivity.shuffleButton && !MainActivity.repeatBoolean) {
                position = randomNumber(listSongs.size() - 1);
            } else if (!MainActivity.shuffleButton && !MainActivity.repeatBoolean) {
                position = ((position - 1) < 0 ? (listSongs.size() - 1) : (position - 1));
            }
            uri = Uri.parse(listSongs.get(position).getPath());
            musicService.createMediaPlayer(position);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            song_artist.setText(listSongs.get(position).getArtist());
            if (musicService != null) {
                seekbar.setMax(musicService.getDuration() / 1000);
            } else {
                musicService.createMediaPlayer(position);
                musicService.start();
                seekbar.setMax(musicService.getDuration() / 1000);
            }
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (musicService != null) {
                        int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                        seekbar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            musicService.onCompleted();
            musicService.showNotification(R.drawable.pause);
            play_pause.setBackgroundResource(R.drawable.pause);
            musicService.start();
        }else{
            musicService.stop();
            musicService.release();
            if (MainActivity.shuffleButton && !MainActivity.repeatBoolean) {
                position = randomNumber(listSongs.size() - 1);
            } else if (!MainActivity.shuffleButton && !MainActivity.repeatBoolean) {
                position = ((position - 1) < 0 ? (listSongs.size() - 1) : (position - 1));
            }
            uri = Uri.parse(listSongs.get(position).getPath());
            musicService.createMediaPlayer(position);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            song_artist.setText(listSongs.get(position).getArtist());
            if (musicService != null) {
                seekbar.setMax(musicService.getDuration() / 1000);
            } else {
                musicService.createMediaPlayer(position);
                musicService.start();
                seekbar.setMax(musicService.getDuration() / 1000);
            }
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (musicService != null) {
                        int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                        seekbar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            musicService.onCompleted();
            musicService.showNotification(R.drawable.play);
            play_pause.setBackgroundResource(R.drawable.play);
        }
    }

    private void initViews() {
        seekbar = findViewById(R.id.seek_bar);
        now_playing = findViewById(R.id.now_playing);
        song_name = findViewById(R.id.song_name);
        song_artist = findViewById(R.id.song_artist);
        duration_played = findViewById(R.id.duration_played);
        duration_total = findViewById(R.id.duration_total);
        back_btn = findViewById(R.id.back_btn);
        menu_btn = findViewById(R.id.menu_btn);
        cover_art = findViewById(R.id.cover_art);
        imageViewGradient = findViewById(R.id.imageViewGradient);
        shuffle_btn = findViewById(R.id.shuffle_btn);
        prev_btn = findViewById(R.id.prev_btn);
        next_btn = findViewById(R.id.next_btn);
        repeat_btn = findViewById(R.id.repeat_btn);
        play_pause = findViewById(R.id.play_pause);
    }
    private String formattedTime(int mCurrentPosition) {
        String totalOut = "";
        String totalNew = "";
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        totalOut = minutes + ":" + seconds;
        totalNew = minutes + ":" + "0" + seconds;
        if (seconds.length() == 1) {
            return totalNew;
        } else {
            return totalOut;
        }
    }
    private void metaData(Uri uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int durationTotal = Integer.parseInt(listSongs.get(position).getDuration()) / 1000;
        duration_total.setText(formattedTime(durationTotal));
        byte[] art = retriever.getEmbeddedPicture();
//        if (art != null) {
//            Glide.with(this)
//                    .asBitmap()
//                    .load(art)
//                    .into(cover_art);
//        } else {
//            Glide.with(this)
//                    .asBitmap()
//                    .load(R.drawable.princess_transparent2)
//                    .into(cover_art);
//        }
    }
    private void prevThreadBtn() {
        prevThread = new Thread() {
            @Override
            public void run() {
                super.run();
                prev_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prev_btnClicked();
                    }
                });
            }
        };
        prevThread.start();
    }
    private void nextThreadBtn() {
        nextThread = new Thread() {
            @Override
            public void run() {
                super.run();
                next_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        next_btnClicked();
                    }
                });
            }
        };
        nextThread.start();
    }
    private void playThreadBtn() {
        playThread = new Thread() {
            @Override
            public void run() {
                super.run();
                play_pause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        play_pauseClicked();
                    }
                });
            }
        };
        playThread.start();
    }
    private int randomNumber(int i) {
        Random random = new Random();
        return random.nextInt(i + 1);
    }

    private byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }

}