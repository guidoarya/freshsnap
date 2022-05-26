package com.daniel.android_freshsnap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniel.android_freshsnap.data.Recipe
import com.daniel.android_freshsnap.databinding.RecipeBinding

class ListRecipeAdapter(private val listRecipe: ArrayList<Recipe>) : RecyclerView.Adapter<ListRecipeAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: RecipeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = RecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name_recipe, photo_recipe) = listRecipe[position]
        holder.binding.recipeImage.setImageResource(photo_recipe)
        holder.binding.recipeNameTv.text = name_recipe
    }

    override fun getItemCount(): Int = listRecipe.size
}