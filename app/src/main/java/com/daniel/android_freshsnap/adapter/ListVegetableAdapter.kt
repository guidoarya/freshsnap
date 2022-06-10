package com.daniel.android_freshsnap.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daniel.android_freshsnap.api.response.HomeResponse
import com.daniel.android_freshsnap.databinding.VegetableLayoutBinding
import java.util.ArrayList

class ListVegetableAdapter (private val listVegetable: ArrayList<HomeResponse.VegetablesItem>) : RecyclerView.Adapter<ListVegetableAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: VegetableLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = VegetableLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val result = listVegetable[position]
        holder.binding.vegetablesNameTv.text = result.name
        Glide.with(holder.itemView)
            .load(result.image)
            .into(holder.binding.vegetablesImage)
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listVegetable[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: HomeResponse.VegetablesItem)
    }

    override fun getItemCount(): Int = listVegetable.size

    @SuppressLint("NotifyDataSetChanged")
    fun setVegetableData(data: List<HomeResponse.VegetablesItem>){
        listVegetable.clear()
        listVegetable.addAll(data)
        notifyDataSetChanged()
    }
}