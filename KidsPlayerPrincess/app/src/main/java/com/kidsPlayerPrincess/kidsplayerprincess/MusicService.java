package com.kidsPlayerPrincess.kidsplayerprincess;

import static com.kidsPlayerPrincess.kidsplayerprincess.SingerDetailsActivity.ON_COMPLETION_SINGER2;
import static com.kidsPlayerPrincess.kidsplayerprincess.AlbumDetailsActivity.ON_COMPLETION_ALBUM2;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {

    IBinder mBinder = new MyBinder();
    MediaPlayer mediaPlayer;
    ArrayList<MusicFiles> musicFilesService = new ArrayList<>();
    public Uri uri;
    int position = -1;
    ActionPlaying actionPlaying;
    MediaSessionCompat mediaSessionCompat;
    public static final String MUSIC_LAST_PLAYED = "LAST_PLAYED";
    public static final String MUSIC_FILE = "STORED_MUSIC";
    public static final String ARTIST_NAME = "ARTIST_NAME";
    public static final String SONG_NAME = "SONG_NAME";




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }

    public class MyBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int myPosition = intent.getIntExtra("servicePosition", -1);
        String actionName = intent.getStringExtra("ActionName");//from NotificationReceiver
        if(myPosition != -1){
            playMedia(myPosition);
        }
        if(actionName != null){
            switch (actionName) {
                case "playPause":
                    playBtnClicked();
                    break;
                case "next":
                    nextBtnClicked();
                    break;
                case "previous":
                    prevBtnClicked();

                    break;
            }
        }
        return START_STICKY;
    }

    private void playMedia(int startPosition) {
        musicFilesService = PlayerActivity.listSongs;
        position = startPosition;
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            if(musicFilesService != null){
                createMediaPlayer(position);
                mediaPlayer.start();
            }
        }else{
            createMediaPlayer(position);
            mediaPlayer.start();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaSessionCompat = new MediaSessionCompat(getBaseContext(), "Kid's Music Player");
    }

    void start() {
        mediaPlayer.start();
    }

    boolean isPlaying() {
        if(mediaPlayer.isPlaying()) {
            return true;
        }
        return false;
    }

    void stop() {
        mediaPlayer.stop();
    }

    void release() {
        mediaPlayer.release();
    }

    int getDuration() {
        return mediaPlayer.getDuration();
    }

    void seekTo(int position) {
        mediaPlayer.seekTo(position);
    }

    void pause() {
        mediaPlayer.pause();
    }

    int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    void createMediaPlayer(int positionInner) {
        position = positionInner;
        uri = Uri.parse(musicFilesService.get(position).getPath());
        SharedPreferences.Editor editor =
                getSharedPreferences(MUSIC_LAST_PLAYED, MODE_PRIVATE).edit();
        editor.putString(MUSIC_FILE, uri.toString());
        editor.putString(ARTIST_NAME, musicFilesService.get(position).getArtist());
        editor.putString(SONG_NAME, musicFilesService.get(position).getTitle());
        editor.apply();
        mediaPlayer = MediaPlayer.create(getBaseContext(), uri);

    }

    void setCallBack(ActionPlaying actionPlaying) {
        this.actionPlaying = actionPlaying;
    }

    void onCompleted() {
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if(actionPlaying != null){
            actionPlaying.next_btnClicked();
        }
        if(mediaPlayer != null){
            createMediaPlayer(position);
            start();
            onCompleted();
            MainActivity.IF_CREATE = false;
            ON_COMPLETION_SINGER2 = true;
            MainActivity.ON_COMPLETION = true;
            ON_COMPLETION_ALBUM2 = true;
            SharedPreferences.Editor editor =
                    getSharedPreferences("data", MODE_PRIVATE).edit();
            //editor.putString(MUSIC_FILE, uri.toString());
            editor.putString("nameArtist", musicFilesService.get(position).getArtist());
            editor.putString("nameSong", musicFilesService.get(position).getTitle());
            editor.putString("ifCreate", "false");
            editor.apply();

            SharedPreferences.Editor editorAlbum =
                    getSharedPreferences("dataAlbum", MODE_PRIVATE).edit();
            //editor.putString(MUSIC_FILE, uri.toString());
            editorAlbum.putString("nameArtistAlbum", musicFilesService.get(position).getArtist());
            editorAlbum.putString("nameSongAlbum", musicFilesService.get(position).getTitle());
            editorAlbum.putString("ifCreate", "false");
            editorAlbum.apply();


            SharedPreferences.Editor editorSinger =
                    getSharedPreferences("dataSinger", MODE_PRIVATE).edit();
            //editor.putString(MUSIC_FILE, uri.toString());
            editorSinger.putString("nameArtistSinger", musicFilesService.get(position).getArtist());
            editorSinger.putString("nameSongSinger", musicFilesService.get(position).getTitle());
            editorSinger.putString("ifCreate", "false");
            editorSinger.apply();

        }
    }

    public void showNotification(int playPauseBtn) {
        Intent intent = new Intent(this, PlayerActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, intent, 0);

        Intent prevIntent = new Intent(this, NotificationReceiver.class)
                .setAction(ApplicationClass.ACTION_PREVIOUS);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent prevPending = PendingIntent.getBroadcast(this,
                0, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent pauseIntent = new Intent(this, NotificationReceiver.class)
                .setAction(ApplicationClass.ACTION_PLAY);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pausePending = PendingIntent.getBroadcast(this,
                0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent nextIntent = new Intent(this, NotificationReceiver.class)
                .setAction(ApplicationClass.ACTION_NEST);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent nextPending = PendingIntent.getBroadcast(this,
                0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        byte[] picture = null;
        picture = getAlbumArt(musicFilesService.get(position).getPath());
        Bitmap thumb = null;
        if (picture != null) {
            thumb = BitmapFactory.decodeByteArray(picture, 0, picture.length);
        } else {
            thumb = BitmapFactory.decodeResource(getResources(), R.drawable.princess_transparent2);
        }

        Notification notification = new NotificationCompat.Builder(this, ApplicationClass.CHANNEL_ID_2)
                .setSmallIcon(playPauseBtn)
                .setLargeIcon(thumb)
                .setContentTitle(musicFilesService.get(position).getTitle())
                .setContentText(musicFilesService.get(position).getArtist())
                .addAction(R.drawable.skip_previous, "Previous", prevPending)
                .addAction(playPauseBtn, "Pause", pausePending)
                .addAction(R.drawable.skip_next, "Next", nextPending)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOnlyAlertOnce(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(5, notification);

    }

    private byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
    void nextBtnClicked(){
        if(actionPlaying != null){
            actionPlaying.next_btnClicked();
        }
    }
    void prevBtnClicked(){
        if(actionPlaying != null){
            actionPlaying.prev_btnClicked();
        }
    }
    void playBtnClicked(){
        if(actionPlaying != null){
            actionPlaying.play_pauseClicked();
        }
    }
}

