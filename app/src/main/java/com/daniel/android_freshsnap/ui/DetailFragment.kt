package com.daniel.android_freshsnap.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daniel.android_freshsnap.R
import com.daniel.android_freshsnap.adapter.ListRecipeAdapter
import com.daniel.android_freshsnap.databinding.FragmentDetailBinding
import com.daniel.android_freshsnap.viewmodel.DetailViewModel

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    private lateinit var rvRecipe: RecyclerView
    private lateinit var listRecipeAdapter: ListRecipeAdapter

    private lateinit var detailViewModel: DetailViewModel

    companion object {
        var EXTRA_NAME = "name"
        var EXTRA_PHOTO = "photo"
        var EXTRA_HOW = "howto"
        var EXTRA_ID = "id"
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


        val id = arguments?.getInt(EXTRA_ID)

        rvRecipe = binding.rvRecipe
        binding.rvRecipe.setHasFixedSize(true)

        showRecyclerListRecipe()
        setData()

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        setId(id)

        detailViewModel.listRecipe.observe(viewLifecycleOwner){
            if (it !=null){
                listRecipeAdapter.setDetail(it)
                showLoading(false)
            }
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setId(id: Int?){
        showLoading(true)
        detailViewModel.findDetail(id)
    }

    private fun setData() {
        if (arguments != null) {
            val name = arguments?.getString(EXTRA_NAME).toString()
            val image = arguments?.getString(EXTRA_PHOTO).toString()
            val howto = arguments?.getString(EXTRA_HOW).toString()
            with(binding) {
                Glide.with(binding.imageView)
                    .load("http://192.168.0.22:5000/$image")
                    .into(imageView)
                tvType.text = name
                tvHowto.text = howto
            }
        }
    }

    private fun showRecyclerListRecipe() {
        listRecipeAdapter = ListRecipeAdapter(arrayListOf())
        rvRecipe = binding.rvRecipe
        rvRecipe.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = listRecipeAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}