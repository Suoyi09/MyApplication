package com.example.lhz.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.*;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class Activity2048 extends AppCompatActivity{
    private WebView webView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048);
        webView = (WebView)findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/2048/2048.html");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls((true));
    }
}
