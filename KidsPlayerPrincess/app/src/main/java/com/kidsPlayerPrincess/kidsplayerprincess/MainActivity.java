package com.kidsPlayerPrincess.kidsplayerprincess;

import static com.kidsPlayerPrincess.kidsplayerprincess.fragments.SongsFragment.musicAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.kidsPlayerPrincess.kidsplayerprincess.adapters.MusicAdapter;
import com.kidsPlayerPrincess.kidsplayerprincess.fragments.AlbumFragment;
import com.kidsPlayerPrincess.kidsplayerprincess.fragments.SingersFragment;
import com.kidsPlayerPrincess.kidsplayerprincess.fragments.SongsFragment;
//import com.kidsplayer3.myapplication.fragments.AlbumFragment;
//import com.kidsplayer3.myapplication.fragments.SingersFragment;
//import com.kidsplayer3.myapplication.fragments.SongsFragment;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ServiceConnection,
        SearchView.OnQueryTextListener {
    public static   boolean SHOW_MINI_PLAYER = false;
    ImageView next_btn, album_art, prev_btn;
    TextView artist_name, song_name1;
    FloatingActionButton play_pause_btn;
    RelativeLayout relativeLayout;
    public static final int REQUEST_CODE = 1;
    public static ArrayList<MusicFiles> musicFiles;
    static Boolean shuffleButton = false, repeatBoolean = false;
    public static ArrayList<MusicFiles> albums = new ArrayList<>();
    public static ArrayList<MusicFiles> singers = new ArrayList<>();
    public static final String MUSIC_LAST_PLAYED = "LAST_PLAYED";
    public static final String MUSIC_FILE = "STORED_MUSIC";
    public static final String ARTIST_NAME = "ARTIST_NAME";
    public static final String SONG_NAME = "SONG_NAME";
    MusicService musicService;
    public static boolean ON_COMPLETION;
    Handler handler = new Handler();
    public static boolean IF_CREATE = false;


    private AdView adView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
          adView = findViewById(R.id.adView2);
          AdRequest adRequest = new AdRequest.Builder().build();
          adView.loadAd(adRequest);

        permission();
        initViews();

        SharedPreferences preferences2 = getSharedPreferences("data", Context.MODE_PRIVATE);
        String ifCreate = preferences2.getString("ifCreate", null);
        if(ifCreate != null){
            if(ifCreate.equals("false")){
                IF_CREATE = false;
            }
        }else{
            IF_CREATE = true;
        }
        IF_CREATE = true;
        relativeLayout.setVisibility(View.INVISIBLE);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicService != null) {
                    musicService.nextBtnClicked();
                    SharedPreferences.Editor editor =
                            getSharedPreferences(MUSIC_LAST_PLAYED, MODE_PRIVATE).edit();
                    editor.putString(MUSIC_FILE, musicService.musicFilesService.
                            get(musicService.position).getPath());
                    editor.putString(ARTIST_NAME, musicService.musicFilesService.
                            get(musicService.position).getArtist());
                    editor.putString(SONG_NAME, musicService.musicFilesService.
                            get(musicService.position).getTitle());
                    editor.apply();
                    SharedPreferences preferences =
                            getSharedPreferences(MUSIC_LAST_PLAYED, MODE_PRIVATE);
                    String path = preferences.getString(MUSIC_FILE, null);
                    String artistName = preferences.getString(ARTIST_NAME, null);
                    String songName = preferences.getString(SONG_NAME, null);
                    if(path != null){
                        SHOW_MINI_PLAYER = true;
                        byte[] art = getAlbumArt(path);
                        if(art != null){
                            Glide.with(getApplicationContext()).load(art).into(album_art);
                            song_name1.setText(songName);
                            artist_name.setText(artistName);
                        }else{
                            Glide.with(getApplicationContext()).load(R.drawable.princess_transparent2).into(album_art);
                            song_name1.setText(songName);
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
                            getSharedPreferences(MUSIC_LAST_PLAYED, MODE_PRIVATE).edit();
                    editor.putString(MUSIC_FILE, musicService.musicFilesService.
                            get(musicService.position).getPath());
                    editor.putString(ARTIST_NAME, musicService.musicFilesService.
                            get(musicService.position).getArtist());
                    editor.putString(SONG_NAME, musicService.musicFilesService.
                            get(musicService.position).getTitle());
                    editor.apply();
                    SharedPreferences preferences =
                            getSharedPreferences(MUSIC_LAST_PLAYED, MODE_PRIVATE);
                    String path = preferences.getString(MUSIC_FILE, null);
                    String artistName = preferences.getString(ARTIST_NAME, null);
                    String songName = preferences.getString(SONG_NAME, null);
                    if(path != null){
                        SHOW_MINI_PLAYER = true;
                        byte[] art = getAlbumArt(path);
                        if(art != null){
                            Glide.with(getApplicationContext()).load(art).into(album_art);
                            song_name1.setText(songName);
                            artist_name.setText(artistName);
                        }else{
                            Glide.with(getApplicationContext()).load(R.drawable.princess_transparent2).into(album_art);
                            song_name1.setText(songName);
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
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (ON_COMPLETION) {
//                    String name2 = getIntent().getStringExtra("nameAgain");
//                    String artist2 = getIntent().getStringExtra("artistAgain");
                    SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                    String artist2 = preferences.getString("nameArtist", null);
                    String name2 = preferences.getString("nameSong", null);
                    if (name2 != null) {
                        song_name1.setText(name2);
                        artist_name.setText(artist2);
                    }
                    name2 = null;
                    artist2 = null;

                }
                ON_COMPLETION = false;

                handler.postDelayed(this, 1000);

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(IF_CREATE){
            relativeLayout.setVisibility(View.INVISIBLE);
        }
        else{
            relativeLayout.setVisibility(View.VISIBLE);
        }
        IF_CREATE = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, this, BIND_AUTO_CREATE);
        SharedPreferences preferences = getSharedPreferences(MUSIC_LAST_PLAYED, MODE_PRIVATE);
        String path = preferences.getString(MUSIC_FILE, null);
        String songName = preferences.getString(SONG_NAME, null);
        String artistName = preferences.getString(ARTIST_NAME, null);
        if(path != null){
            SHOW_MINI_PLAYER = true;
            byte[] art = getAlbumArt(path);
            if(art != null){
                Glide.with(this).load(art).into(album_art);
                song_name1.setText(songName);
                artist_name.setText(artistName);
            }else{
                Glide.with(this).load(R.drawable.princess_transparent2).into(album_art);
                song_name1.setText(songName);
                artist_name.setText(artistName);
            }
        }else{
            SHOW_MINI_PLAYER = false;
        }
    }

    private void permission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        } else {
            musicFiles = getAllAudio(this);
            initViewPager();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            musicFiles = getAllAudio(this);
            initViewPager();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
    }


    public ArrayList<MusicFiles> getAllAudio(Context context) {
        ArrayList<String> duplicates = new ArrayList<>();
        ArrayList<MusicFiles> tempAudioList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media._ID
        };
        Cursor cursor = context.getContentResolver().query(uri, projection, null,
                null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String album = cursor.getString(0);
                String title = cursor.getString(1);
                String duration = cursor.getString(2);
                String path = cursor.getString(3);
                String artist = cursor.getString(4);
                String id = cursor.getString(5);
                MusicFiles musicFiles = new MusicFiles(path, title, artist, album, duration, id);
                tempAudioList.add(musicFiles);
                if(!duplicates.contains(album)){
                    albums.add(musicFiles);
                    duplicates.add(album);
                }
                if(!duplicates.contains(artist)){
                    singers.add(musicFiles);
                    duplicates.add(artist);
                }
            }
            cursor.close();
        }
        return tempAudioList;
    }

    void initViews() {
        artist_name = findViewById(R.id.song_artist_mini_player);
        song_name1 = findViewById(R.id.song_name_mini_player);
        album_art = findViewById(R.id.bottom_album_art);
        next_btn = findViewById(R.id.skip_next_bottom);
        prev_btn = findViewById(R.id.skip_prev_bottom);
        play_pause_btn = findViewById(R.id.play_pause_mini_player);
        relativeLayout = findViewById(R.id.card_bottom_player);
    }

    private void initViewPager() {
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new SongsFragment(), "songs");
        viewPagerAdapter.addFragments(new AlbumFragment(), "albums");
        viewPagerAdapter.addFragments(new SingersFragment(), "singers");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service) {
        MusicService.MyBinder myBinder = (MusicService.MyBinder) service;
        musicService = myBinder.getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        musicService=null;
    }


    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        public void addFragments(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
    private byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.search_option);
        SearchView searchView =(SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        ArrayList<MusicFiles> myFiles = new ArrayList<>();
        for(MusicFiles song : musicFiles){
            if(song.getTitle().toLowerCase().contains(userInput)){
                myFiles.add(song);
            }
        }
        musicAdapter.updateList(myFiles);
        return true;
    }
}