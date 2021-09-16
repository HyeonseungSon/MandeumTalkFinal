package com.rud.mandeumtalk.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class FragmentAdapter(manager: FragmentManager): FragmentPagerAdapter(manager) {

	private val fragmentList = ArrayList<Fragment>()
	private val titleList = ArrayList<String>()

	override fun getItem(position: Int): Fragment {
		return fragmentList[position]
	}
	override fun getCount(): Int {
		return titleList.size
	}
	override fun getPageTitle(position: Int): CharSequence {
		return titleList[position]
	}
	fun addFragment(fragment: Fragment, title: String) {
		fragmentList.add(fragment)
		titleList.add(title)
	}
}