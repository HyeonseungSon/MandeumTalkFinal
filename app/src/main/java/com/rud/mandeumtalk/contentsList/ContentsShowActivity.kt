package com.rud.mandeumtalk.contentsList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import com.rud.mandeumtalk.R

class ContentsShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_show)

        val contentTitle : String = intent.getStringExtra("contentTitle").toString()
        findViewById<TextView>(R.id.categoryTitle).setText(contentTitle)

        val contentWebView : WebView = findViewById<WebView>(R.id.webView)
        contentWebView.loadUrl(intent.getStringExtra("url").toString())
        contentWebView.setWebViewClient(WebViewClient())

        findViewById<ImageView>(R.id.backIcon).setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        finish()
    }
}