package com.capstone.aquacare.ui.identification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.aquacare.data.IdentificationData
import com.capstone.aquacare.databinding.CardIdentificationHistoryBinding

class IdentificationAdapter (private val list: List<IdentificationData>) : RecyclerView.Adapter<IdentificationAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: IdentificationData)
    }

    class ViewHolder(var binding: CardIdentificationHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardIdentificationHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentIdentification = list[position]
        holder.binding.tvStatusAquascape.text = currentIdentification.result
        holder.binding.tvDate.text = currentIdentification.date

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(list[holder.adapterPosition])
        }
    }
}