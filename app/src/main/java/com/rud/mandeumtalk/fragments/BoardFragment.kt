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
import com.rud.mandeumtalk.R
import com.rud.mandeumtalk.board.BoardAdapter
import com.rud.mandeumtalk.board.BoardModel
import com.rud.mandeumtalk.board.BoardWriteActivity
import com.rud.mandeumtalk.board.OnBoardItemClickListener
import com.rud.mandeumtalk.contentsList.ContentsListActivity
import com.rud.mandeumtalk.databinding.FragmentBoardBinding


class BoardFragment : Fragment() {

	private lateinit var binding : FragmentBoardBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {

		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board, container, false)


		binding.educationIcon.setOnClickListener {
			val intent = Intent(context, ContentsListActivity::class.java)
			intent.putExtra("category", "Education")
			startActivity(intent)
		}
		binding.educationText.setOnClickListener {
			val intent = Intent(context, ContentsListActivity::class.java)
			intent.putExtra("category", "Education")
			startActivity(intent)
		}

		binding.cookingIcon.setOnClickListener {
			val intent = Intent(context, ContentsListActivity::class.java)
			intent.putExtra("category", "Cooking")
			startActivity(intent)
		}
		binding.cookingText.setOnClickListener {
			val intent = Intent(context, ContentsListActivity::class.java)
			intent.putExtra("category", "Cooking")
			startActivity(intent)
		}

		binding.forestIcon.setOnClickListener {
			Toast.makeText(context, "Forest Tap is not Ready", Toast.LENGTH_SHORT).show()
		}
		binding.forestText.setOnClickListener {
			Toast.makeText(context, "Forest Tap is not Ready", Toast.LENGTH_SHORT).show()
		}
		binding.campingIcon.setOnClickListener {
			Toast.makeText(context, "Camping Tap is not Ready", Toast.LENGTH_SHORT).show()
		}
		binding.campingText.setOnClickListener {
			Toast.makeText(context, "Camping Tap is not Ready", Toast.LENGTH_SHORT).show()
		}
		binding.caravanIcon.setOnClickListener {
			Toast.makeText(context, "Caravan Tap is not Ready", Toast.LENGTH_SHORT).show()
		}
		binding.caravanText.setOnClickListener {
			Toast.makeText(context, "Caravan Tap is not Ready", Toast.LENGTH_SHORT).show()
		}


		binding.homeIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_self)
		}
		binding.homeText.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_self)
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
}