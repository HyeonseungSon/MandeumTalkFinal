package com.rud.mandeumtalk.board

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.rud.mandeumtalk.R

class BoardAdapter (val items : ArrayList<BoardModel>) : RecyclerView.Adapter<BoardAdapter.ViewHolder>(){

    lateinit var listener : OnBoardItemClickListener

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.board_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BoardAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    inner class ViewHolder (itemView : View) : RecyclerView.ViewHolder (itemView) {

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(this, itemView, adapterPosition)
                Log.e("listenerlistener", listener.toString())

            }
        }
        fun setItem (item : BoardModel) {
            val itemLinearLayoutView = itemView.findViewById<CardView>(R.id.itemCard)

            itemView.findViewById<TextView>(R.id.input1).text = item.title
            itemView.findViewById<TextView>(R.id.input2).text = item.contents
            itemView.findViewById<TextView>(R.id.input3).text = item.writer
            itemView.findViewById<TextView>(R.id.input4).text = item.dateTime
            itemView.findViewById<TextView>(R.id.input5).text = item.writerUid
            val key = item.key
            Log.e("keykey", key)

            val storageReference = Firebase.storage.reference.child(key + ".png")
            val imageViewFromFB = itemView.findViewById<ImageView>(R.id.imageView3)

            storageReference.downloadUrl.addOnCompleteListener({ task ->
                if (task.isSuccessful) {
                    Glide.with(itemView).load(task.result).into(imageViewFromFB)
                } else {
                    Log.e("taskfail", key)
                }
            })

            if (item.writerUid == FirebaseAuth.getInstance().currentUser?.uid.toString()) {
                itemLinearLayoutView?.setBackgroundColor(Color.parseColor("#FFF4EB"))
            }
        }
    }
}