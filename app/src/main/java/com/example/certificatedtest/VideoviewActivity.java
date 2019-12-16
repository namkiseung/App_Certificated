package com.example.certificatedtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoviewActivity extends AppCompatActivity{
    /*
    youtube API : https://developers.google.com/youtube/android/player/downloads?hl=ko
    ListView : https://debuglog.tistory.com/57?category=721102 || https://www.youtube.com/watch?v=lrm70H2OKIc
    Usage Youtube api : https://gamjatwigim.tistory.com/39* || https://debuglog.tistory.com/59
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview);
    }
}
