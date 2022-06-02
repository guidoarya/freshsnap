package com.daniel.android_freshsnap.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daniel.android_freshsnap.api.response.DetailResponse
import com.daniel.android_freshsnap.databinding.RecipeBinding

class ListRecipeAdapter(private val listRecipe: ArrayList<DetailResponse.ReferenceItem>)
    : RecyclerView.Adapter<ListRecipeAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: RecipeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = RecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val result = listRecipe[position]
        val modify = result.image
        holder.binding.recipeNameTv.text = result.referenceName
        Glide.with(holder.itemView)
            .load("http://172.168.1.103:5000/$modify")
            .into(holder.binding.recipeImage)
    }

    override fun getItemCount(): Int = listRecipe.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDetail(data: List<DetailResponse.ReferenceItem>){
        listRecipe.clear()
        listRecipe.addAll(data)
        notifyDataSetChanged()
    }
}