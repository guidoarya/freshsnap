package com.daniel.android_freshsnap.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniel.android_freshsnap.api.ApiConfig
import com.daniel.android_freshsnap.api.response.ListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountViewModel: ViewModel() {
    private val _reviewItem = MutableLiveData<List<ListResponse.ListResponseItem>>()
    val listReview: LiveData<List<ListResponse.ListResponseItem>> = _reviewItem
    private val _isLoading = MutableLiveData<Boolean>()


    fun getListReviewItem(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getReview("Bearer $token")
        client.enqueue(object : Callback<List<ListResponse.ListResponseItem>> {
            override fun onResponse(
                call: Call<List<ListResponse.ListResponseItem>>,
                response: Response<List<ListResponse.ListResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d(TAG, response.body().toString())
                        _reviewItem.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<List<ListResponse.ListResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e("FETCHINGSTORIES", t.message.toString())
            }

        })
    }

    companion object{
        private const val TAG = "FragmentAccount"

    }
}