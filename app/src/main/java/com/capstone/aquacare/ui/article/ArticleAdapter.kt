package com.capstone.aquacare.ui.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.aquacare.data.ArticleData
import com.capstone.aquacare.databinding.CardArticleBinding

class ArticleAdapter (private val list: List<ArticleData>) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ArticleData)
    }

    class ViewHolder(var binding: CardArticleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentAquascapeInfo = list[position]
        holder.binding.tvTitle.text = currentAquascapeInfo.title
        if (!currentAquascapeInfo.image.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(currentAquascapeInfo.image)
                .into(holder.binding.ivImage)
        }
        holder.binding.tvType.text = currentAquascapeInfo.type

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(list[holder.adapterPosition])
        }
    }
}