package com.daniel.android_freshsnap.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniel.android_freshsnap.R
import com.daniel.android_freshsnap.adapter.ListFruitAdapter
import com.daniel.android_freshsnap.adapter.ListVegetableAdapter
import com.daniel.android_freshsnap.data.Fruits
import com.daniel.android_freshsnap.data.Vegetables
import com.daniel.android_freshsnap.databinding.FragmentFirstBinding


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentFirstBinding

    private val list_fruit = ArrayList<Fruits>()
    private val list_vegetable = ArrayList<Vegetables>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        list_fruit.addAll(listFruits)

        list_vegetable.addAll(listVegetables)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        binding.fruitsRecyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.vegetablesRecyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        binding.fruitsRecyclerview.setHasFixedSize(true)
        binding.vegetablesRecyclerview.setHasFixedSize(true)

        showRecyclerListFruit()
        showRecyclerListVegetables()

    }

    private val listFruits: ArrayList<Fruits>
        @SuppressLint("Recycle")
        get() {
            val dataNameFruit = resources.getStringArray(R.array.data_fruits_name)
            val dataPhotoFruit = resources.obtainTypedArray(R.array.data_photo_fruits)
            val listFruit = ArrayList<Fruits>()
            for (i in dataNameFruit.indices) {
                val fruit = Fruits(dataNameFruit[i], dataPhotoFruit.getResourceId(i, -1))
                listFruit.add(fruit)
            }
            return listFruit
        }

    private fun showRecyclerListFruit() {
        val listFruitAdapter = ListFruitAdapter(list_fruit)
        binding.fruitsRecyclerview.adapter = listFruitAdapter

        listFruitAdapter.setOnItemClickCallback(object : ListFruitAdapter.OnItemClickCallback {
            override fun onItemClicked(fruits: Fruits) {
                showSelectedUser(fruits)
            }
        })
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

    private val listVegetables: ArrayList<Vegetables>
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
        }

    private fun showRecyclerListVegetables() {
        val listVegetableAdapter = ListVegetableAdapter(list_vegetable)
        binding.vegetablesRecyclerview.adapter = listVegetableAdapter
    }
}