package com.rud.mandeumtalk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.rud.mandeumtalk.R
import com.rud.mandeumtalk.databinding.FragmentContactusBinding
import kotlinx.android.synthetic.main.activity_contents_show.*
import kotlinx.android.synthetic.main.fragment_contactus.*

class ContactUsFragment : Fragment() {

//	private lateinit var binding : FragmentContactusBinding

	override fun onCreate(savedInstanceState: Bundle?) {

		super.onCreate(savedInstanceState)

	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {

//		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contactus, container, false)

		val view : View = inflater.inflate(R.layout.fragment_contactus, container, false)

		val webView : WebView = view.findViewById<WebView>(R.id.webView)

		webView.apply {
			webViewClient = WebViewClient()
			settings.javaScriptEnabled = true
		}
		webView.loadUrl("http://xn--ry1bx6g.kr/contact.php")

		// Home Icon & Home Text
		view.findViewById<ImageView>(R.id.homeIcon).setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_guideFragment)
		}
		view.findViewById<TextView>(R.id.homeText).setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_guideFragment)
		}
		// Portfolio Icon & Portfolio Text
		view.findViewById<ImageView>(R.id.portfolioIcon).setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_portfolioFragment)
		}
		view.findViewById<TextView>(R.id.portfolioText).setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_portfolioFragment)
		}
		// Board Icon & Board Text
		view.findViewById<ImageView>(R.id.boardIcon).setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_homeFragment)
		}
		view.findViewById<TextView>(R.id.boardText).setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_homeFragment)
		}
//		view.findViewById<ImageView>(R.id.contactUsIcon).setOnClickListener {
//			it.findNavController().navigate(R.id.action_boardFragment_self)
//		}
//		view.findViewById<TextView>(R.id.contactUsText).setOnClickListener {
//			it.findNavController().navigate(R.id.action_boardFragment_self)
//		}
		view.findViewById<ImageView>(R.id.accountIcon).setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_accountFragment)
		}
		view.findViewById<TextView>(R.id.accountText).setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_accountFragment)
		}

		return view
	}
}