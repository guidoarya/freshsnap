package com.daniel.android_freshsnap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniel.android_freshsnap.data.Fruits
import com.daniel.android_freshsnap.databinding.FruitLayoutBinding
import java.util.ArrayList

class ListFruitAdapter(private val listFruit: ArrayList<Fruits>) : RecyclerView.Adapter<ListFruitAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: FruitLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = FruitLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name_fruit, photo_fruit) = listFruit[position]
        holder.binding.fruitImage.setImageResource(photo_fruit)
        holder.binding.fruitNameTv.text = name_fruit
    }

    override fun getItemCount(): Int = listFruit.size
}