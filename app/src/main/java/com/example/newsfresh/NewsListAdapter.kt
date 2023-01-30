package com.example.newsfresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(private val listener:NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {
    private val items:ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {//it is called when viewholder is created. it is called the no. of times the all views present are created.

        // LayoutInflator is used to convert the xml file to views:-
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder= NewsViewHolder(view)
        view.setOnClickListener {

            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        // this function returns the viewholder.
//        return NewsViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {// this  function binds the data with the holder.

        val currentItem=items[position]
        holder.titleView.text=currentItem.title
        holder.author.text=currentItem.description
//        holder.image.text=currentItem.title
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {

        return items.size
    }

    fun updateNews(updatedItems:ArrayList<News>){
        items.clear()
        items.addAll(updatedItems)
        notifyDataSetChanged()//to call all the above 3 functions again to perform all operations.
    }

}

//private fun View.setOnClickListener(onItemClicked: Unit) {
//
//}

class NewsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
val titleView: TextView=itemView.findViewById(R.id.title)
val author: TextView=itemView.findViewById(R.id.author)
    val image: ImageView = itemView.findViewById(R.id.image)
}

interface NewsItemClicked{
    fun onItemClicked(item:News)
}