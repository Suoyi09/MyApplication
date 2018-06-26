package com.example.lhz.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.*;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class EnglishActivity extends AppCompatActivity{
    private WebView webView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);
        webView = (WebView)findViewById(R.id.webview);
        webView.loadUrl("https://translate.google.com");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls((true));
    }
}

