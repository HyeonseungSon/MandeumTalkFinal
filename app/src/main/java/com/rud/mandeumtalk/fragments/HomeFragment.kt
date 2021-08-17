package com.rud.mandeumtalk.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rud.mandeumtalk.R
import com.rud.mandeumtalk.board.BoardAdapter
import com.rud.mandeumtalk.board.BoardModel
import com.rud.mandeumtalk.board.BoardWriteActivity
import com.rud.mandeumtalk.board.OnBoardItemClickListener
import com.rud.mandeumtalk.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

	lateinit var  myRef : DatabaseReference

	private lateinit var binding : FragmentHomeBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

		val items = ArrayList<BoardModel>()
		val adapter = BoardAdapter(items)

		val database = Firebase.database

		myRef = database.getReference("Board")

		val postListener = object : ValueEventListener {
			override fun onDataChange(dataSnapshot: DataSnapshot) {

				for (dataModel in dataSnapshot.children) {
					val item = dataModel.getValue(BoardModel::class.java)
					adapter.items.add(item!!)
				}
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
				val contents = view?.findViewById<TextView>(R.id.input2)?.text

				Toast.makeText(activity, "Item Click $position\nTitle : $title\nContents : $contents\nWriter : $", Toast.LENGTH_SHORT).show()

			}
		}
		/////////// End of Recycler View Area ///////////

		// Board Write Button
		binding.boardWriteButton.setOnClickListener {
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

		binding.boardIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_homeFragment_self)
		}
		binding.boardText.setOnClickListener {
			it.findNavController().navigate(R.id.action_homeFragment_self)
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