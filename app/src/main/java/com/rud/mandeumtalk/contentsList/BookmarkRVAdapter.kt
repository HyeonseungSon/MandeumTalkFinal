package com.rud.mandeumtalk.contentsList

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rud.mandeumtalk.R

class BookmarkRVAdapter (val context : Context, val items : ArrayList<ContentModel>, val keyList : ArrayList<String>, val bookmarkIdList : MutableList<String>)
    : RecyclerView.Adapter <BookmarkRVAdapter.ViewHolder> () {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkRVAdapter.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.bookmark_rv_item, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: BookmarkRVAdapter.ViewHolder, position: Int) {

        holder.bindItems(items[position], keyList[position])
    }

    override fun getItemCount(): Int {

        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: ContentModel, key: String) {

            itemView.setOnClickListener {

                val intent = Intent(context, ContentsShowActivity::class.java)
                intent.putExtra("url", item.webUrl)
                intent.putExtra("contentTitle", item.title)
//                val bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slide_down_enter, R.anim.slide_down_exit).toBundle()
                itemView.context.startActivity(intent)

            }

            val contentTitle = itemView.findViewById<TextView>(R.id.textArea)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)
//            val bookmarkArea = itemView.findViewById<ImageView>(R.id.bookmarkArea)

            contentTitle.text = item.title
            Glide.with(context).load(item.imageUrl).into(imageViewArea)
        }
    }

    fun refreshFragment (fragment : Fragment, fragmentManager: FragmentManager) {

        var fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.detach(fragment).attach(fragment).commit()
    }
}