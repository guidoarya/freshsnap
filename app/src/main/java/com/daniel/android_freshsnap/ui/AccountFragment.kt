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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daniel.android_freshsnap.adapter.ListHistoryAdapter
import com.daniel.android_freshsnap.api.response.ListResponse
import com.daniel.android_freshsnap.databinding.FragmentThirdBinding
import com.daniel.android_freshsnap.viewmodel.AccountViewModel

class AccountFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentThirdBinding
    private lateinit var accountViewModel: AccountViewModel
    private lateinit var rvReview: RecyclerView

    private lateinit var preferences: SharedPreferences
    private lateinit var name: String

    private lateinit var reviewAdapter: ListHistoryAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        val linearlayoutManager   = LinearLayoutManager(context)
        rvReview = binding.rvHistory
        binding.rvHistory.setHasFixedSize(true)

        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        accountViewModel.listReview.observe(viewLifecycleOwner){
            if (it !=null){
                setupRecycleView(it)
                //reviewAdapter.setReviewData(it)
                showLoading(false)
            }
        }

        reviewAdapter = ListHistoryAdapter(arrayListOf())
        binding.rvHistory.apply {
            layoutManager = linearlayoutManager
            adapter = reviewAdapter
        }


        binding.btnExit.setOnClickListener(this)
        //setupRecycleView()

        preferences = this.requireActivity().getSharedPreferences(LoginActivity.PREFS_USER, Context.MODE_PRIVATE)
        name = preferences.getString(LoginActivity.NAME, "").toString()
        val token = preferences.getString(LoginActivity.TOKEN, "").toString()
        binding.user.text = name

        searchListItem(token)
    }

    private fun setupRecycleView(ListReview: List<ListResponse.ListResponseItem>) {
        reviewAdapter = ListHistoryAdapter(ListReview)
        binding.rvHistory.adapter = reviewAdapter
    }

    private fun searchListItem(token: String){
        showLoading(true)
        accountViewModel.getListReviewItem(token)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

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