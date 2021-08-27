package com.rud.mandeumtalk.contentsList

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rud.mandeumtalk.R

class ContentsRVAdapter (val context : Context,
                         val items : ArrayList<ContentModel>,
                         val keyList : ArrayList<String>,
                         val bookmarkIdList : MutableList<String>) : RecyclerView.Adapter <ContentsRVAdapter.ViewHolder> () {

    private lateinit var auth : FirebaseAuth

    interface ItemClick {
        fun onClick (view : View, position : Int)
    }
    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentsRVAdapter.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.content_rv_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ContentsRVAdapter.ViewHolder, position: Int) {

        if (itemClick != null) {
            holder.itemView.setOnClickListener { v ->
                itemClick?.onClick(v, position)
            }
        }
        holder.bindItems(items[position], keyList[position])
    }

    override fun getItemCount(): Int {

        return items.size
    }

    inner class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems (item : ContentModel, key : String) {

            itemView.setOnClickListener {

                val intent = Intent (context, ContentsShowActivity::class.java)

                intent.putExtra("url", item.webUrl)
                intent.putExtra("contentTitle", item.title)

                itemView.context.startActivity(intent)
            }

            val contentTitle = itemView.findViewById<TextView>(R.id.textArea)
            contentTitle.text = item.title

            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)
            Glide.with(context).load(item.imageUrl).into(imageViewArea)

            val bookmarkArea = itemView.findViewById<ImageView>(R.id.bookmarkArea)

            if (bookmarkIdList.contains(key)) {
                bookmarkArea.setImageResource(R.drawable.bookmarkred)
            } else {
                bookmarkArea.setImageResource(R.drawable.bookmark)
            }

            bookmarkArea.setOnClickListener {

                if (bookmarkIdList.contains(key)) {

                    val database = Firebase.database
                    val bookmarkREf = database.getReference("Bookmark_List")
                    auth = FirebaseAuth.getInstance()
                    val uid = auth.currentUser?.uid.toString()

                    Toast.makeText(context, "${item.title}\n북마크가 취소되었습니다.", Toast.LENGTH_LONG).show()

                    bookmarkREf.child(uid).child(key).removeValue()

                } else {

                    val database = Firebase.database
                    val bookmarkREf = database.getReference("Bookmark_List")
                    auth = FirebaseAuth.getInstance()
                    val uid = auth.currentUser?.uid.toString()

                    Toast.makeText(context, "${item.title}\n북마크가 설정되었습니다.", Toast.LENGTH_LONG).show()

                    bookmarkREf.child(uid).child(key).setValue(BookmarkModel(true))
                }
            }
        }
    }
}







