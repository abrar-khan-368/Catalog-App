package com.abrarlohia.dressmaterialcatalog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.shashank.sony.fancytoastlib.FancyToast;

public class catalog_video extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    public static final String API_KEY = "AIzaSyBsAcYNn7Y_qS_dXMQh-YV_u2gxpzTm1DA";
    public static final String PlayList_ID = "PLNnN3fkarNvkhOmSsPTEnmH5kj6CWxkmC";

    private YouTubePlayerView youtube_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_video);
        youtube_player = findViewById(R.id.youtube_player);
        youtube_player.initialize(API_KEY,this);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        FancyToast.makeText(this,"Fail to Initialize ",FancyToast.LENGTH_LONG,FancyToast.ERROR,true);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        player.setPlayerStateChangeListener(playerStateListener);
        player.setPlaybackEventListener(playbackEventListener);
        /*start buffering*/
        if (!wasRestored){
            player.cuePlaylist(PlayList_ID);
        }
    }
   private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
       @Override
       public void onPlaying() {

       }

       @Override
       public void onPaused() {

       }

       @Override
       public void onStopped() {

       }

       @Override
       public void onBuffering(boolean b) {

       }

       @Override
       public void onSeekTo(int i) {

       }
   };

    private YouTubePlayer.PlayerStateChangeListener playerStateListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };
}