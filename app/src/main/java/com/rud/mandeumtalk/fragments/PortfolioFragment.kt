package com.rud.mandeumtalk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rud.mandeumtalk.R
import com.rud.mandeumtalk.databinding.FragmentPortfolioBinding
import com.rud.mandeumtalk.portfolioList.PortfolioModel
import com.rud.mandeumtalk.portfolioList.PortfolioRVAdapter
import kotlinx.android.synthetic.main.fragment_portfolio.*



class PortfolioFragment : Fragment() {

	private lateinit var binding : FragmentPortfolioBinding

	lateinit var  rvAdapter: PortfolioRVAdapter
	val items = ArrayList<PortfolioModel>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)


	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {

		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_portfolio, container, false)

		rvAdapter = PortfolioRVAdapter(requireContext(), items)

		val rv : RecyclerView = binding.rv
		rv.adapter = rvAdapter

		rv.layoutManager = GridLayoutManager(requireContext(), 2)

		val items = ArrayList<PortfolioModel>()
		rvAdapter.items.add(PortfolioModel("CJ대한통운", "http://cjcourier.com/img/home/section2_slider03_660x425.png", "http://cjcourier.com/"))
		rvAdapter.items.add(PortfolioModel("모두의낚시", "data:image/jpeg;base64,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", "https://monak.kr/"))
		rvAdapter.items.add(PortfolioModel("세진 기술산업", "data:image/png;base64,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", "http://www.sejintec.co.kr/"))
		rvAdapter.items.add(PortfolioModel("셀프인하우스", "https://셀프인하우스.com/img/main/banner04_img.png", "http://셀프인하우스.com/"))
		rvAdapter.items.add(PortfolioModel("현우산업", "data:image/png;base64,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", "http://www.hyunwoopcb.com/"))
		rvAdapter.items.add(PortfolioModel("오럭스", "data:image/jpeg;base64,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", "http://www.oluxe.kr/"))



		binding.homeIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_portfolioFragment_to_guideFragment)
		}
		binding.homeText.setOnClickListener {
			it.findNavController().navigate(R.id.action_portfolioFragment_to_guideFragment)
		}

		binding.portfolioIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_portfolioFragment_self)
		}
		binding.portfolioText.setOnClickListener {
			it.findNavController().navigate(R.id.action_portfolioFragment_self)
		}

		binding.boardIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_portfolioFragment_to_homeFragment)
		}
		binding.boardText.setOnClickListener {
			it.findNavController().navigate(R.id.action_portfolioFragment_to_homeFragment)
		}

		binding.contactUsIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_portfolioFragment_to_boardFragment)
		}
		binding.contactUsText.setOnClickListener {
			it.findNavController().navigate(R.id.action_portfolioFragment_to_boardFragment)
		}

		binding.accountIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_portfolioFragment_to_accountFragment)
		}
		binding.accountText.setOnClickListener {
			it.findNavController().navigate(R.id.action_portfolioFragment_to_accountFragment)
		}
		return binding.root
	}
}