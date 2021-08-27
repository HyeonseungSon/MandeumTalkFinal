package com.rud.mandeumtalk.board

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rud.mandeumtalk.R

class HateListRVAdapter (val context: Context, val items : ArrayList<BoardModel>, val keyList : ArrayList<String>, val hateListIdList : MutableList<String>)
    : RecyclerView.Adapter <HateListRVAdapter.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HateListRVAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.hate_board_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItems(items[position], keyList[position])
    }

    override fun getItemCount(): Int {

        return items.size
    }

    inner class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems (item : BoardModel, key : String) {

            itemView.setOnClickListener {

                val intent = Intent(context, BoardViewActivity::class.java)
                intent.putExtra("Board Title", item.title)
                intent.putExtra("Board Content", item.contents)
                intent.putExtra("Board writer", item.writer)
                intent.putExtra("Board dateTime", item.dateTime)
                intent.putExtra("Board Writer Uid", item.writerUid)
                intent.putExtra("Board Key", item.key)

                itemView.context.startActivity(intent)
            }
            val boardTitle = itemView.findViewById<TextView>(R.id.input1)
            val writer = itemView.findViewById<TextView>(R.id.input3)

            boardTitle.text = item.title
            writer.text = item.writer
        }
    }
}