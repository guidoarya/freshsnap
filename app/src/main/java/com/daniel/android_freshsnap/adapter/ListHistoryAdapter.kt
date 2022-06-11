package com.daniel.android_freshsnap.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daniel.android_freshsnap.databinding.HistoryRowBinding
import com.daniel.android_freshsnap.api.response.ListResponse


class ListHistoryAdapter(private val listReview: List<ListResponse.ListResponseItem>) : RecyclerView.Adapter<ListHistoryAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: HistoryRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = HistoryRowBinding.inflate(
            LayoutInflater.from(parent.context)
            , parent, false)
        return ListViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val result = listReview[position]
        holder.binding.username.text = result.userName
        Glide.with(holder.itemView)
            .load(result.image)
            .into(holder.binding.imgPhoto)
        holder.binding.date.text = result.createdAt
        holder.binding.location.text = result.location
        holder.binding.name.text = result.itemName
    }

    override fun getItemCount(): Int = listReview.size

}