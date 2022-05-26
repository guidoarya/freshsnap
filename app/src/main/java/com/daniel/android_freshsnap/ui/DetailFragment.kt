package com.daniel.android_freshsnap.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.daniel.android_freshsnap.R
import com.daniel.android_freshsnap.adapter.ListRecipeAdapter
import com.daniel.android_freshsnap.data.Fruits
import com.daniel.android_freshsnap.data.Recipe
import com.daniel.android_freshsnap.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    private val list_recipe = ArrayList<Recipe>()

    companion object {
        var EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        list_recipe.addAll(listRecipe)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setLogo(R.drawable.ic_back)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayUseLogoEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Fruit Detail"

        if (arguments != null) {
            val data = arguments?.getParcelable<Fruits>(EXTRA_USER) as Fruits
            with(binding) {
                Glide.with(binding.imageView)
                    .load(data.photo_fruit)
                    .into(imageView)
                tvType.text = data.name_fruit
            }
        }

        binding.rvRecipe.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        binding.rvRecipe.setHasFixedSize(true)

        showRecyclerListRecipe()
    }

    private val listRecipe: ArrayList<Recipe>
        @SuppressLint("Recycle")
        get() {
            val dataNameRecipe = resources.getStringArray(R.array.data_recipe)
            val dataPhotoRecipe = resources.obtainTypedArray(R.array.data_photo_recipe)
            val listRecipe = ArrayList<Recipe>()
            for (i in dataNameRecipe.indices) {
                val recipe = Recipe(dataNameRecipe[i], dataPhotoRecipe.getResourceId(i, -1))
                listRecipe.add(recipe)
            }
            return listRecipe
        }

    private fun showRecyclerListRecipe() {
        val listRecipeAdapter = ListRecipeAdapter(list_recipe)
        binding.rvRecipe.adapter = listRecipeAdapter
    }
}