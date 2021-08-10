package com.rud.mandeumtalk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.rud.mandeumtalk.R
import com.rud.mandeumtalk.databinding.FragmentContactusBinding


class ContactUsFragment : Fragment() {

	private lateinit var binding : FragmentContactusBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {

		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contactus, container, false)

		binding.homeIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_homeFragment)
		}
		binding.homeText.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_homeFragment)
		}

		binding.portfolioIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_portfolioFragment)
		}
		binding.portfolioText.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_portfolioFragment)
		}

		binding.boardIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_guideFragment)
		}
		binding.boardText.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_guideFragment)
		}

		binding.contactUsIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_self)
		}
		binding.contactUsText.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_self)
		}

		binding.accountIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_accountFragment)
		}
		binding.accountText.setOnClickListener {
			it.findNavController().navigate(R.id.action_boardFragment_to_accountFragment)
		}

		return binding.root
	}
}