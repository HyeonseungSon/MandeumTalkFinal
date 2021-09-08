package com.rud.mandeumtalk.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient
import com.rud.mandeumtalk.R
import com.rud.mandeumtalk.auth.IntroActivity
import com.rud.mandeumtalk.board.BoardModel
import com.rud.mandeumtalk.board.HateListRVAdapter
import com.rud.mandeumtalk.contentsList.BookmarkRVAdapter
import com.rud.mandeumtalk.databinding.FragmentAccountBinding
import com.rud.mandeumtalk.contentsList.ContentModel

class AccountFragment : Fragment() {

	private lateinit var auth : FirebaseAuth
	private lateinit var binding : FragmentAccountBinding
	lateinit var alertDialog : AlertDialog
	lateinit var builder : AlertDialog.Builder

	// Bookmark Recyclerview
	val bookmarkIdList = mutableListOf<String>()
	val items = ArrayList<ContentModel>()
	val itemKeyList = ArrayList<String>()
	lateinit var rvAdapter: BookmarkRVAdapter

	// Hate List
	val hateList : MutableList<String> = mutableListOf<String>()
	val hateItems = ArrayList<BoardModel> ()
	val hateItemKeyList = ArrayList<String> ()
	lateinit var hateRvAdapter : HateListRVAdapter

	private lateinit var callback: OnBackPressedCallback

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

		auth = Firebase.auth
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)

		// Logout Button
		binding.accountBtnLogout.setOnClickListener {
			logoutDialog()
		}

		// Bookmark
		getBookmarkData()
		rvAdapter = BookmarkRVAdapter(requireContext(), items, itemKeyList, bookmarkIdList)
		val rv : RecyclerView = binding.bookmarkRV
		rv.adapter = rvAdapter
		rv.layoutManager = GridLayoutManager(requireContext(), 3)

		// 나의 계정 화면에 내가 신고한 게시물을 출력합니다.
		getHateListData()
		hateRvAdapter = HateListRVAdapter(requireContext(), hateItems, hateItemKeyList, hateList)
		val hateRv : RecyclerView = binding.hateListRV
		hateRv.adapter = hateRvAdapter
		hateRv.layoutManager = GridLayoutManager(requireContext(), 3)

		// Home Icon
		binding.homeIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_accountFragment_to_guideFragment)
		}
		binding.homeText.setOnClickListener {
			it.findNavController().navigate(R.id.action_accountFragment_to_guideFragment)
		}

		// Portfolio Icon
		binding.portfolioIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_accountFragment_to_portfolioFragment)
		}
		binding.portfolioText.setOnClickListener {
			it.findNavController().navigate(R.id.action_accountFragment_to_portfolioFragment)
		}

		// Board Icon
		binding.boardIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_accountFragment_to_homeFragment)
		}
		binding.boardText.setOnClickListener {
			it.findNavController().navigate(R.id.action_accountFragment_to_homeFragment)
		}

		// Contact Us Icon
		binding.contactUsIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_accountFragment_to_boardFragment)
		}
		binding.contactUsText.setOnClickListener {
			it.findNavController().navigate(R.id.action_accountFragment_to_boardFragment)
		}

		// 유저의 이메일 정보를 나의 계정 화면 상단에 출력합니다.
		// 회원가입 유저라면 이메일 정보를, 비회원 로그인 유저라면 "비회원 사용자" 라는 문구를 출력합니다.
		val currentUserEmail : String = auth.currentUser?.email.toString()
		if (currentUserEmail == "null" || currentUserEmail == "") {
			binding.userEmail.text = "비회원 사용자"
		} else {
			binding.userEmail.text = currentUserEmail
		}
		return binding.root
	}

	// Logout Dialog
	private fun logoutDialog() {
		try{
			builder = AlertDialog.Builder(getContext())
			builder.setTitle("로그아웃")
			builder.setIcon(R.drawable.logout_icon)
			builder.setMessage("정말 로그아웃 하시겠습니까?")
			builder.setCancelable(true)
			builder.setNegativeButton("로그아웃", DialogInterface.OnClickListener { dialog, which ->
				auth.signOut()
				UserApiClient.instance.logout { error ->
					if (error != null) {
						Toast.makeText(context, "로그아웃 실패\n관리자 문의", Toast.LENGTH_SHORT).show()
					} else {
						Toast.makeText(context, "로그아웃에 성공하였습니다.\n로그인 화면으로 돌아갑니다.", Toast.LENGTH_SHORT).show()
					}
				}
				val intent = Intent(activity, IntroActivity::class.java)
				intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
				startActivity(intent)
			})
			builder.setPositiveButton("취소", DialogInterface.OnClickListener { dialog, which ->
				Toast.makeText(context, "로그아웃을 취소했습니다.", Toast.LENGTH_SHORT).show()
			})
			alertDialog = builder.create()
			try {
				alertDialog.show()
			} catch (e : Exception){
				Log.e("AccountFragment.alertDialog.show().Exception : ", e.toString())
				e.printStackTrace()
			}
		} catch(e : Exception){
			Log.e("AccountFragment.AlertDialog.Builder().Exception : ", e.toString())
			e.printStackTrace()
		}
	}

	private fun getBookmarkData() {
		val postListener = object : ValueEventListener {
			override fun onDataChange(dataSnapshot: DataSnapshot) {

				bookmarkIdList.clear()

				for (dataModel in dataSnapshot.children) {
					bookmarkIdList.add(dataModel.key.toString())
					Log.d("dataModel.key", dataModel.key.toString())
				}
				getEducationData()
				getCookingData()

			}
			override fun onCancelled(databaseError: DatabaseError) {
				Log.e("AccountFragment.getBookmarkData().databaseError : ",
					databaseError.toString(),
					databaseError.toException())
			}
		}
		Firebase.database.getReference("Bookmark_List").child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addValueEventListener(postListener)
	}

	private fun getEducationData () {
		val postListener = object : ValueEventListener {
			override fun onDataChange(dataSnapshot: DataSnapshot) {

				items.clear()
				for (dataModel in dataSnapshot.children) {

					val item = dataModel.getValue(ContentModel::class.java)

					if(bookmarkIdList.contains(dataModel.key.toString())) {

						items.add(item!!)
						itemKeyList.add(dataModel.key.toString())
					}
				}
				rvAdapter.notifyDataSetChanged()
			}
			override fun onCancelled(databaseError: DatabaseError) {
				Log.e("AccountFragment.getEducationData().databaseError : ",
					databaseError.toString(),
					databaseError.toException())
			}
		}
		Firebase.database.getReference("Education").addValueEventListener(postListener)
	}

	private fun getCookingData () {
		val postListener = object : ValueEventListener {
			override fun onDataChange(dataSnapshot: DataSnapshot) {

//				items.clear()
				for (dataModel in dataSnapshot.children) {

					val item = dataModel.getValue(ContentModel::class.java)

					if(bookmarkIdList.contains(dataModel.key.toString())) {

						items.add(item!!)
						itemKeyList.add(dataModel.key.toString())
					}
				}
				rvAdapter.notifyDataSetChanged()
			}
			override fun onCancelled(databaseError: DatabaseError) {
				Log.e("AccountFragment.getCookingData().databaseError : ",
					databaseError.toString(),
					databaseError.toException())
			}
		}
		Firebase.database.getReference("Cooking").addValueEventListener(postListener)
	}

	private fun getHateListData () {
		val postListener = object : ValueEventListener {
			override fun onDataChange(dataSnapshot: DataSnapshot) {

				hateList.clear()

				for (dataModel in dataSnapshot.children) {
					hateList.add(dataModel.key.toString())
				}
				getBoardData()
			}
			override fun onCancelled(databaseError: DatabaseError) {
				Log.e("AccountFragment.getHateListData().databaseError : ",
						databaseError.toString(),
						databaseError.toException())
			}
		}
		Firebase.database.getReference("Hate_List").child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addValueEventListener(postListener)
	}

	private fun getBoardData () {

		val postListener = object : ValueEventListener {
			override fun onDataChange(dataSnapshot: DataSnapshot) {

				for (dataModel in dataSnapshot.children) {

					val item = dataModel.getValue(BoardModel::class.java)

					if (hateList.contains(dataModel.key.toString())) {

						hateItems.add(item!!)
						hateItemKeyList.add(dataModel.key.toString())
					}
				}
				hateRvAdapter.notifyDataSetChanged()
			}
			override fun onCancelled(databaseError: DatabaseError) {

			}
		}
		Firebase.database.getReference("Board").addValueEventListener(postListener)
	}
}