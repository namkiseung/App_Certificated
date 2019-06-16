package com.example.protected_root;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewActivity extends AppCompatActivity {
    public WebView mWebView;
    private String myUrl = "file:///android_asset"; // 접속 URL (내장HTML의 경우 왼쪽과 같이 쓰고 아니면 걍 URL)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        // 웹뷰 셋팅팅
        mWebView = (WebView) findViewById(R.id.testWebview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        //mWebView.loadUrl("http://www.pois.co.kr/mobile/login.do");

        mWebView.loadUrl("https://lukasstefanko.com/2019/02/android-clipper-found-on-google-play.html"); // 접속 URL
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClientClass());
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("check URL",url);
            view.loadUrl(url);
            return true;
        }
    }
}
