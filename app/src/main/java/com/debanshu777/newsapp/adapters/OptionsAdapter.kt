package com.debanshu777.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.debanshu777.newsapp.R
import com.debanshu777.newsapp.models.Option
import kotlinx.android.synthetic.main.item_article_preview.view.*
import kotlinx.android.synthetic.main.top_option_items.view.*

class OptionsAdapter(private val list: ArrayList<Option>) : RecyclerView.Adapter<OptionsAdapter.OptionsViewHolder>() {
     class OptionsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textView : TextView= itemView.tvOption
        val imageView :ImageView=itemView.imageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.top_option_items,parent,false)
        return OptionsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        val currentItem=list[position]
        holder.textView.text=currentItem.title
    }

    override fun getItemCount() = list.size
}