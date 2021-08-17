package com.rud.mandeumtalk.contentsList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.rud.mandeumtalk.R

class ContentsShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_show)

        findViewById<WebView>(R.id.webView).loadUrl(intent.getStringExtra("url").toString())
    }
}