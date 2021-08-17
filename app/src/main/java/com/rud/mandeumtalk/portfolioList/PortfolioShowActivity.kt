package com.rud.mandeumtalk.portfolioList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import com.rud.mandeumtalk.R

class PortfolioShowActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {

		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_portfolio_show)

		val getUrl = intent.getStringExtra("url")
		Toast.makeText(this, getUrl, Toast.LENGTH_LONG).show()

		val webView : WebView = findViewById(R.id.webView)
		webView.loadUrl(getUrl.toString())

	}
}