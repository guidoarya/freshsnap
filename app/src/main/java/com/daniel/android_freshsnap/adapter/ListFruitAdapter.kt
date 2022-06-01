package com.daniel.android_freshsnap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daniel.android_freshsnap.api.response.FruitsItem
import com.daniel.android_freshsnap.api.response.HomeResponse
import com.daniel.android_freshsnap.databinding.FruitLayoutBinding
import java.util.ArrayList

class ListFruitAdapter(private val listFruit: ArrayList<FruitsItem>) : RecyclerView.Adapter<ListFruitAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    class ListViewHolder(var binding: FruitLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = FruitLayoutBinding.inflate(
            LayoutInflater.from(parent.context)
            , parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val result = listFruit[position]
        val modifyUrl = result.image
        holder.binding.fruitNameTv.text = result.name
        Glide.with(holder.itemView)
            .load("http://192.168.0.22:5000/$modifyUrl")
            .into(holder.binding.fruitImage)
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listFruit[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: FruitsItem)
    }

    override fun getItemCount(): Int = listFruit.size

    fun setFruitData(data: List<FruitsItem>){
        listFruit.clear()
        listFruit.addAll(data)
        notifyDataSetChanged()
    }
}