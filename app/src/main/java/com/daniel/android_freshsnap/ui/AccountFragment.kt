package com.daniel.android_freshsnap.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniel.android_freshsnap.databinding.FragmentThirdBinding

class AccountFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentThirdBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var name: String

    //private val list_fruit = ArrayList<Fruits>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //list_fruit.addAll(listFruits)

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

        binding.rvHistory.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        //showRecyclerListFruit()

        preferences = this.requireActivity().getSharedPreferences(LoginActivity.PREFS_USER, Context.MODE_PRIVATE)
        name = preferences.getString(LoginActivity.NAME, "").toString()
        binding.user.text = "$name"
    }

    //
    /*private val listFruits: ArrayList<Fruits>
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
    }*/

    override fun onClick(v: View) {
        preferences.edit().apply {
            clear()
            apply()
        }
        activity?.let {
            val intent = Intent(it, LoginActivity::class.java)
            it.startActivity(intent)
            it.finish()
        }
    }
}