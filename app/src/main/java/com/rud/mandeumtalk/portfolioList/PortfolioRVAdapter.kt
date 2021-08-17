package com.rud.mandeumtalk.portfolioList

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
import com.rud.mandeumtalk.R
import kotlinx.android.synthetic.main.portfolio_rv_item.view.*

class PortfolioRVAdapter(val context : Context, val items: ArrayList<PortfolioModel>) :
	RecyclerView.Adapter<PortfolioRVAdapter.ViewHolder>() {



	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): PortfolioRVAdapter.ViewHolder {
			val v = LayoutInflater.from(parent.context).inflate(R.layout.portfolio_rv_item, parent, false)
		return ViewHolder(v)
	}

	override fun onBindViewHolder(holder: PortfolioRVAdapter.ViewHolder, position: Int) {



		holder.bindItem(items[position])
	}

	override fun getItemCount(): Int {
		return items.size
	}

	inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

		fun bindItem(item: PortfolioModel) {

			itemView.setOnClickListener {
				Toast.makeText(context, item.title, Toast.LENGTH_LONG).show()
				val intent = Intent(context, PortfolioShowActivity::class.java)
				intent.putExtra("url", item.webUrl)
				itemView.context.startActivity(intent)
			}

			val Title1 = itemView.findViewById<TextView>(R.id.textArea)
			val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)

			Title1.text = item.title

		Glide.with(context)
			.load(item.imageUrl)
			.into(imageViewArea)

		}
	}
}