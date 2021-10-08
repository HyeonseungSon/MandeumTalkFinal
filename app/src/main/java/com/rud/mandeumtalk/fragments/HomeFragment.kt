package com.rud.mandeumtalk.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rud.mandeumtalk.R
import com.rud.mandeumtalk.contentsList.ContentModel
import com.rud.mandeumtalk.contentsList.ContentsRVAdapter
import com.rud.mandeumtalk.contentsList.ContentsShowActivity
import com.rud.mandeumtalk.databinding.FragmentBoardBinding
import com.rud.mandeumtalk.databinding.FragmentHomeBinding


class HomeFragment : Fragment(){

	private lateinit var auth : FirebaseAuth
	private lateinit var binding : FragmentHomeBinding
	lateinit var myRef : DatabaseReference
	val bookmarkIdList = mutableListOf<String>()
	lateinit var rvAdapter : ContentsRVAdapter
	val database = Firebase.database

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {

		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

		binding.educationIcon.setOnClickListener{

			binding.contentsLayout.isVisible = false
			binding.contentFragmentWebView.isVisible = false
			binding.contentsListRV.isVisible = true
			getContentsList("Education")
		}
		binding.educationText.setOnClickListener{

			binding.contentsLayout.isVisible = false
			binding.contentFragmentWebView.isVisible = false
			binding.contentsListRV.isVisible = true
			getContentsList("Education")
		}

		binding.cookingIcon.setOnClickListener{

			binding.contentsLayout.isVisible = false
			binding.contentFragmentWebView.isVisible = false
			binding.contentsListRV.isVisible = true
			getContentsList("Cooking")
		}
		binding.cookingText.setOnClickListener{

			binding.contentsLayout.isVisible = false
			binding.contentFragmentWebView.isVisible = false
			binding.contentsListRV.isVisible = true
			getContentsList("Cooking")
		}

		binding.forestIcon.setOnClickListener {
			setWebView("https://namu.wiki/w/%EC%88%B2")
		}
		binding.forestText.setOnClickListener {
			setWebView("https://namu.wiki/w/%EC%88%B2")
		}

		binding.campingIcon.setOnClickListener {
			setWebView("https://namu.wiki/w/%EC%BA%A0%ED%95%91")
		}
		binding.campingText.setOnClickListener {
			setWebView("https://namu.wiki/w/%EC%BA%A0%ED%95%91")
		}

		binding.caravanIcon.setOnClickListener {
			setWebView("https://namu.wiki/w/%EC%BA%A0%ED%95%91%EC%B9%B4")
		}
		binding.caravanText.setOnClickListener {
			setWebView("https://namu.wiki/w/%EC%BA%A0%ED%95%91%EC%B9%B4")
		}


		binding.portfolioIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_guideFragment_to_portfolioFragment)
		}
		binding.portfolioText.setOnClickListener {
			it.findNavController().navigate(R.id.action_guideFragment_to_portfolioFragment)
		}

		binding.boardIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_guideFragment_to_homeFragment)
		}
		binding.boardText.setOnClickListener {
			it.findNavController().navigate(R.id.action_guideFragment_to_homeFragment)
		}

		binding.contactUsIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_guideFragment_to_boardFragment)
		}
		binding.contactUsText.setOnClickListener {
			it.findNavController().navigate(R.id.action_guideFragment_to_boardFragment)
		}

		binding.accountIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_guideFragment_to_accountFragment)
		}
		binding.accountText.setOnClickListener {
			it.findNavController().navigate(R.id.action_guideFragment_to_accountFragment)
		}

		return binding.root
	}

	private fun getBookmarkData() {
		val postListener = object : ValueEventListener {
			override fun onDataChange(dataSnapshot: DataSnapshot) {

				bookmarkIdList.clear()

				for (dataModel in dataSnapshot.children) {

					bookmarkIdList.add(dataModel.key.toString())
				}
				println("EducationFragment.kt.bookmarkIdList:$bookmarkIdList")
				rvAdapter.notifyDataSetChanged()
			}
			override fun onCancelled(databaseError: DatabaseError) {

			}
		}
		val bookmarkRef = database.getReference("Bookmark_List")
		auth = FirebaseAuth.getInstance()
		val uid = auth.currentUser?.uid.toString()
		bookmarkRef.child(uid).addValueEventListener(postListener)
	}

	private fun getContentsList (category : String) {
		val items = ArrayList<ContentModel>()
		val itemKeyList = ArrayList<String>()

		rvAdapter = ContentsRVAdapter(requireContext(), items, itemKeyList, bookmarkIdList)

		myRef = database.getReference(category)

		val postListener = object : ValueEventListener {
			override fun onDataChange(snapshot: DataSnapshot) {

				for (dataModel in snapshot.children) {
					itemKeyList.add(dataModel.key.toString())
					val item = dataModel.getValue(ContentModel::class.java)
					items.add(item!!)
				}
				rvAdapter.notifyDataSetChanged()
			}

			override fun onCancelled(error: DatabaseError) {

			}
		}
		myRef.addValueEventListener(postListener)

		val rv : RecyclerView = binding.contentsListRV

		rv.adapter = rvAdapter

		rv.layoutManager = GridLayoutManager(activity, 3)

		rvAdapter.itemClick = object : ContentsRVAdapter.ItemClick {
			override fun onClick(view: View, position: Int) {
				val intent : Intent = Intent(context, ContentsShowActivity::class.java)
				intent.putExtra("url", items[position].webUrl)
				startActivity(intent)
			}
		}
		getBookmarkData()
	}

	private fun setWebView (url : String) {

		binding.contentsLayout.isVisible = false
		binding.contentsListRV.isVisible = false
		binding.contentFragmentWebView.isVisible = true
		binding.contentFragmentWebView.loadUrl(url)
		binding.contentFragmentWebView.setWebViewClient(WebViewClient())

	}

}