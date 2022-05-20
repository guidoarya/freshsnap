package com.daniel.android_freshsnap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniel.android_freshsnap.data.Vegetables
import com.daniel.android_freshsnap.databinding.VegetableLayoutBinding
import java.util.ArrayList

class ListVegetableAdapter (private val listVegetable: ArrayList<Vegetables>) : RecyclerView.Adapter<ListVegetableAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: VegetableLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = VegetableLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name_vegetable, photo_vegetable) = listVegetable[position]
        holder.binding.vegetablesImage.setImageResource(photo_vegetable)
        holder.binding.vegetablesNameTv.text = name_vegetable
    }

    override fun getItemCount(): Int = listVegetable.size
}