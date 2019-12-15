package com.example.certificatedtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoviewActivity extends YouTubeBaseActivity {
    /*
    youtube API : https://developers.google.com/youtube/android/player/downloads?hl=ko
    ListView : https://debuglog.tistory.com/57?category=721102 || https://www.youtube.com/watch?v=lrm70H2OKIc
    Usage Youtube api : https://gamjatwigim.tistory.com/39* || https://debuglog.tistory.com/59
    */
    YouTubePlayerView youtubeView;
    Button button;
    YouTubePlayer.OnInitializedListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview);
        button = (Button) findViewById(R.id.youtubeButton);
        youtubeView = (YouTubePlayerView)findViewById(R.id.youtubeView);
        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer,
                                                boolean b) {
                youTubePlayer.loadVideo("유튜브 동영상 id");
            }
​
            @Override
            public void onInitializationFailure(
                    YouTubePlayer.Provider provider,
                    YouTubeInitializationResult youTubeInitializationResult) {
            }
        };
​
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                youtubeView.initialize("구글 API키", listener);
            }
        });
    }
}
