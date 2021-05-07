package com.debanshu777.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.debanshu777.newsapp.R
import com.debanshu777.newsapp.models.Article
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

    private var onItemClickListener: ((Option) -> Unit)? = null

    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        val currentItem=list[position]
        holder.textView.text=currentItem.title
        holder.itemView.apply {
            Glide.with(this).load(currentItem.url)
                .centerInside()
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.placeholder_image_logo).diskCacheStrategy(
                            DiskCacheStrategy.ALL)
                ).into(imageView)
            setOnClickListener {
                onItemClickListener?.let { it(currentItem) }
            }
        }
    }

    override fun getItemCount() = list.size

    fun setOnItemClickListener(listener: (Option) -> Unit) {
        onItemClickListener = listener
    }
}