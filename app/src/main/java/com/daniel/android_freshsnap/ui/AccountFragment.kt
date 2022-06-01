package com.daniel.android_freshsnap.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniel.android_freshsnap.R
import com.daniel.android_freshsnap.adapter.ListFruitAdapter
import com.daniel.android_freshsnap.data.Fruits
import com.daniel.android_freshsnap.databinding.FragmentThirdBinding

class AccountFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentThirdBinding
    //
    private val list_fruit = ArrayList<Fruits>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        list_fruit.addAll(listFruits)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.show()

        binding.btnExit.setOnClickListener(this)
        //
        binding.rvHistory.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        showRecyclerListFruit()
    }

    //
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
    //
    private fun showRecyclerListFruit() {
        val listAdapter = ListFruitAdapter(arrayListOf())
        binding.rvHistory.adapter = listAdapter
    }

    override fun onClick(v: View) {
        activity?.let {
            val intent = Intent(it, LoginActivity::class.java)
            it.startActivity(intent)
            it.finish()
        }
    }
}