package com.rud.mandeumtalk.contentsList.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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
import com.rud.mandeumtalk.databinding.FragmentEducationBinding

class EducationFragment : Fragment() {
//
//    private lateinit var auth : FirebaseAuth
//    private lateinit var binding : FragmentEducationBinding
//    lateinit var myRef : DatabaseReference
//    val bookmarkIdList = mutableListOf<String>()
//    lateinit var rvAdapter : ContentsRVAdapter
//    val database = Firebase.database
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    private fun getBookmarkData() {
//        val postListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                bookmarkIdList.clear()
//
//                for (dataModel in dataSnapshot.children) {
//
//                    bookmarkIdList.add(dataModel.key.toString())
//                }
//                println("EducationFragment.kt.bookmarkIdList:$bookmarkIdList")
//                rvAdapter.notifyDataSetChanged()
//            }
//            override fun onCancelled(databaseError: DatabaseError) {
//
//            }
//        }
//        val bookmarkRef = database.getReference("Bookmark_List")
//        auth = FirebaseAuth.getInstance()
//        val uid = auth.currentUser?.uid.toString()
//        bookmarkRef.child(uid).addValueEventListener(postListener)
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_education, container, false)
//
//        val items = ArrayList<ContentModel>()
//        val itemKeyList = ArrayList<String>()
//
//        rvAdapter = ContentsRVAdapter(requireContext(), items, itemKeyList, bookmarkIdList)
//
//        myRef = database.getReference("Education")
//
//        val postListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                for (dataModel in snapshot.children) {
//                    itemKeyList.add(dataModel.key.toString())
//                    val item = dataModel.getValue(ContentModel::class.java)
//                    items.add(item!!)
//                }
//                println("EducationFragment.kt.items:$items")
//                rvAdapter.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        }
//        myRef.addValueEventListener(postListener)
//
//        val rv : RecyclerView = binding.rv
//
//        rv.adapter = rvAdapter
//
//        rv.layoutManager = GridLayoutManager(activity, 3)
//
//        rvAdapter.itemClick = object : ContentsRVAdapter.ItemClick {
//            override fun onClick(view: View, position: Int) {
//                val intent : Intent = Intent(context, ContentsShowActivity::class.java)
//                intent.putExtra("url", items[position].webUrl)
//                startActivity(intent)
//            }
//        }
//        getBookmarkData()
//
//        return inflater.inflate(R.layout.fragment_education, container, false)
//    }
//
//    companion object {
//        fun newInstance() : EducationFragment {
//            return EducationFragment()
//        }
//    }
}