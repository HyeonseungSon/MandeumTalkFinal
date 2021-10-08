package com.rud.mandeumtalk.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rud.mandeumtalk.R
import com.rud.mandeumtalk.board.*
import com.rud.mandeumtalk.databinding.FragmentBoardBinding
import com.rud.mandeumtalk.databinding.FragmentHomeBinding


class BoardFragment : Fragment() {

	private lateinit var auth: FirebaseAuth
	lateinit var  myRef : DatabaseReference

	private lateinit var binding : FragmentBoardBinding

	private val boardKeyList = mutableListOf<String>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board, container, false)

		val items = ArrayList<BoardModel>()

		val adapter = BoardAdapter(items)

		val database = Firebase.database

		myRef = database.getReference("Board")

		val postListener = object : ValueEventListener {

			override fun onDataChange(dataSnapshot: DataSnapshot) {

				items.clear()
				for (dataModel in dataSnapshot.children) {

					val item = dataModel.getValue(BoardModel::class.java)
					adapter.items.add(item!!)
					boardKeyList.add(dataModel.key.toString())
				}

				Log.e("boardKeyList2", boardKeyList.toString())
				boardKeyList.reverse()
				items.reverse()
				adapter.notifyDataSetChanged()
			}
			override fun onCancelled(databaseError: DatabaseError) {

			}
		}
		myRef.addValueEventListener(postListener)

		binding.boardRecyclerView.adapter = adapter

		val layoutManager = LinearLayoutManager (activity, LinearLayoutManager.VERTICAL, false)

		binding.boardRecyclerView.layoutManager = layoutManager

		adapter.listener = object : OnBoardItemClickListener {

			override fun onItemClick(holder: BoardAdapter.ViewHolder?, view: View?, position: Int) {

				val title = view?.findViewById<TextView>(R.id.input1)?.text
				val content = view?.findViewById<TextView>(R.id.input2)?.text
				val writer = view?.findViewById<TextView>(R.id.input3)?.text
				val dateTime = view?.findViewById<TextView>(R.id.input4)?.text
				val writerUid : String = view?.findViewById<TextView>(R.id.input5)?.text.toString()

				var intent = Intent(activity, BoardViewActivity::class.java)

				intent.putExtra("Board Title", title)
				intent.putExtra("Board Content", content)
				intent.putExtra("Board writer", writer)
				intent.putExtra("Board dateTime", dateTime)
				intent.putExtra("Board Writer Uid", writerUid)
				intent.putExtra("Board Key", boardKeyList[position])

				startActivity(intent)
			}
		}

		// Start of FAB Icon Animation
		var fabIconCheck : Boolean = false
		val boardWriteFAB = binding.boardWriteButton
		val boardWriteTest = binding.boardWriteText

		val onFABIconAnim : Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_icon_scale_anim_on)
		val onFABTextAnim : Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_text_alpha_anim_on)
		val offFABIconAnim : Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_icon_scale_anim_off)
		val offFABTextAnim : Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_text_alpha_anim_off)

		val rcView = binding.boardRecyclerView
		rcView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
			if (scrollY > oldScrollY+3 && fabIconCheck == false) { // 내려간다 , 체크가 false 이면
				boardWriteFAB.startAnimation(onFABIconAnim)
				boardWriteTest.startAnimation(onFABTextAnim)
				fabIconCheck = true
			}
			if (scrollY+3 < oldScrollY && fabIconCheck == true) { // 올라간다, 체크가 true 이면
				boardWriteFAB.startAnimation(offFABIconAnim)
				boardWriteTest.startAnimation(offFABTextAnim)
				fabIconCheck = false
			}
		}
//            if (oldScrollY >= 1000  && fabIconCheck == false) { // 스크롤 y 값이 500을 이상이면, 체크가 false 이면
//                mainFabIcon.startAnimation(onFABIconAnim)
//                mainFabText.startAnimation(onFABTextAnim)
//                fabIconCheck = true
//            }
//            if (oldScrollY < 1000 && fabIconCheck == true) { // 스크롤 y 값이 500미만이고 체크가 true 이면
//                mainFabIcon.startAnimation(offFABIconAnim)
//                mainFabText.startAnimation(offFABTextAnim)
//                fabIconCheck = false
//            }

		boardWriteFAB.setOnClickListener {
			val intent = Intent (activity, BoardWriteActivity::class.java)
			startActivity(intent)
		}

		binding.homeIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_homeFragment_to_guideFragment)
		}
		binding.homeText.setOnClickListener {
			it.findNavController().navigate(R.id.action_homeFragment_to_guideFragment)
		}

		binding.portfolioIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_homeFragment_to_portfolioFragment)
		}
		binding.portfolioText.setOnClickListener {
			it.findNavController().navigate(R.id.action_homeFragment_to_portfolioFragment)
		}

		binding.contactUsIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_homeFragment_to_boardFragment)
		}
		binding.contactUsText.setOnClickListener {
			it.findNavController().navigate(R.id.action_homeFragment_to_boardFragment)
		}

		binding.accountIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_homeFragment_to_accountFragment)
		}
		binding.accountText.setOnClickListener {
			it.findNavController().navigate(R.id.action_homeFragment_to_accountFragment)
		}

		return binding.root
	}
}