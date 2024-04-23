package com.capstone.aquacare.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.aquacare.data.AquascapeData
import com.capstone.aquacare.databinding.CardAquascapeBinding

class AquascapeAdapter (private val list: List<AquascapeData>) : RecyclerView.Adapter<AquascapeAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: AquascapeData)
    }

    class ViewHolder(var binding: CardAquascapeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardAquascapeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentAquascape = list[position]

        holder.binding.tvAquascapeName.text = currentAquascape.name
        holder.binding.tvStatusAquascape.text = currentAquascape.status
        holder.binding.tvDate.text = currentAquascape.lastCheckDate

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(list[holder.adapterPosition])
        }
    }
}