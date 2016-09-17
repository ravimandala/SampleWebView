package com.example.ravimandala.samplewebview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Ravi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        WebView browser = (WebView) findViewById(R.id.webview);
        browser.getSettings().setJavaScriptEnabled(true);
        WebView.setWebContentsDebuggingEnabled(true);
        browser.loadUrl("javascript:(function(window) {'use strict';var vungle = window.vungle = window.vungle || {}; vungle.myFunction = function() {document.getElementById(\"demo\").innerHTML = \"Hello World\"; return true};})(window);");
        browser.loadUrl("file:/data/user/0/com.example.ravimandala.samplewebview/cache/vungle/dummy_file.html");
   }
}
