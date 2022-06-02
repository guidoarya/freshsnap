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
import com.daniel.android_freshsnap.R
import com.daniel.android_freshsnap.adapter.ListFruitAdapter
import com.daniel.android_freshsnap.adapter.ListVegetableAdapter
import com.daniel.android_freshsnap.data.Fruits
import com.daniel.android_freshsnap.databinding.FragmentFirstBinding
import com.daniel.android_freshsnap.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentFirstBinding

    private lateinit var rvListFruits: RecyclerView
    private lateinit var listFruitAdapter: ListFruitAdapter

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var rvListVegetables: RecyclerView
    private lateinit var listVegetableAdapter: ListVegetableAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        rvListFruits = binding.fruitsRecyclerview
        rvListVegetables = binding.vegetablesRecyclerview

        rvListFruits.setHasFixedSize(true)
        rvListVegetables.setHasFixedSize(true)

        showRecyclerListFruit()
        showRecyclerListVegetables()

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        homeViewModel.findData()

        homeViewModel.listFruits.observe(viewLifecycleOwner) {
            if (it != null) {
                listFruitAdapter.setFruitData(it)
                showLoading(false)
            }
        }

        homeViewModel.listVegetables.observe(viewLifecycleOwner) {
            if (it != null) {
                listVegetableAdapter.setVegetableData(it)
                showLoading(false)
            }
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

    }


    private fun showRecyclerListFruit() {
        listFruitAdapter = ListFruitAdapter(arrayListOf())
        rvListFruits = binding.fruitsRecyclerview
        rvListFruits.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = listFruitAdapter
        }

        /*listFruitAdapter.setOnItemClickCallback(object : ListFruitAdapter.OnItemClickCallback {
            override fun onItemClicked(fruits: Fruits) {
                showSelectedUser(fruits)
            }
        })*/
    }

    private fun showSelectedUser(fruits: Fruits){
        val mDetailFragment = DetailFragment()

        val mBundle = Bundle()
        mBundle.putParcelable(DetailFragment.EXTRA_USER, fruits)

        mDetailFragment.arguments = mBundle

        val mFragmentManager = parentFragmentManager
        mFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, mDetailFragment, DetailFragment::class.java.simpleName)
            addToBackStack(null)
            commit()
        }
    }

    /*private val listVegetables: ArrayList<Vegetables>
        @SuppressLint("Recycle")
        get() {
            val dataNameVegetable = resources.getStringArray(R.array.data_vegetables_name)
            val dataPhotoVegetable = resources.obtainTypedArray(R.array.data_photo_vegetables)
            val listVegetable = ArrayList<Vegetables>()
            for (i in dataNameVegetable.indices) {
                val vegetable = Vegetables(dataNameVegetable[i], dataPhotoVegetable.getResourceId(i, -1))
                listVegetable.add(vegetable)
            }
            return listVegetable
        }*/

    private fun showRecyclerListVegetables() {
        listVegetableAdapter = ListVegetableAdapter(arrayListOf())
        rvListVegetables = binding.vegetablesRecyclerview
        rvListVegetables.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = listVegetableAdapter
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