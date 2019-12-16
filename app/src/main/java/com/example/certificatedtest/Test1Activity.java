package com.example.certificatedtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

public class Test1Activity extends YouTubeBaseActivity implements View.OnClickListener {
    /*
       youtube API : https://developers.google.com/youtube/android/player/downloads?hl=ko
       ListView : https://debuglog.tistory.com/57?category=721102 || https://www.youtube.com/watch?v=lrm70H2OKIc
       Usage Youtube api : https://gamjatwigim.tistory.com/39* || https://debuglog.tistory.com/59
       */
    Button button1, button2, button3, button4;
    YouTubePlayerView youtubeView;
    YouTubePlayer.OnInitializedListener listener1, listener2, listener3, listener4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        button1 = (Button) findViewById(R.id.youtubeBtn1);
        button2 = (Button) findViewById(R.id.youtubeBtn2);
        button3 = (Button) findViewById(R.id.youtubeBtn3);
        button4 = (Button) findViewById(R.id.youtubeBtn4);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        youtubeView = (YouTubePlayerView) findViewById(R.id.youtubeView1);
        listener1 = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo("aC6SmnJovn8", 1);
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        listener2 = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo("Xa3wzrqZz5g");
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        listener3 = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                ArrayList<String> list = new ArrayList<String>();
                list.add("Lx22h3YXV5E");list.add("g0O6pVkCteY");list.add("m8k-mMIJt3M");list.add("TrWPz7m8KXY");list.add("wqeYfO088g8");list.add("LiYBUhi-TSc");list.add("e1H-mlRXVYU");list.add("X8q6qPdFUBM");list.add("6xGMlr51tQA");list.add("ICgSA0KSUeU");list.add("M2K4Bp0cIck");list.add("l4UQXG8VInU");
                youTubePlayer.loadVideos(list);
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        listener4 = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.release();
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.youtubeBtn1:
                youtubeView.initialize("AIzaSyC-c0vdEroTiKiiVl13EcksnkWEaBp8j6s", listener1);
                break;
            case R.id.youtubeBtn2:
                youtubeView.initialize("AIzaSyC-c0vdEroTiKiiVl13EcksnkWEaBp8j6s", listener2);
                break;
            case R.id.youtubeBtn3:
                youtubeView.initialize("AIzaSyC-c0vdEroTiKiiVl13EcksnkWEaBp8j6s", listener3);
                break;
            case R.id.youtubeBtn4:
                youtubeView.initialize("AIzaSyC-c0vdEroTiKiiVl13EcksnkWEaBp8j6s", listener4);
                break;
        }
    }
}
