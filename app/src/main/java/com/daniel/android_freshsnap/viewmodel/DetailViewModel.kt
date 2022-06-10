package com.daniel.android_freshsnap.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniel.android_freshsnap.api.ApiConfig
import com.daniel.android_freshsnap.api.response.DetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel(){
    private val _detail = MutableLiveData<List<DetailResponse.DetailItem>>()
    val listDetail: LiveData<List<DetailResponse.DetailItem>> = _detail

    private val _recipe = MutableLiveData<List<DetailResponse.ReferenceItem>>()
    val listRecipe: LiveData<List<DetailResponse.ReferenceItem>> = _recipe

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun findDetail(id: Int?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetail(id)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d(TAG, response.body().toString())
                        _detail.value = response.body()!!.detail
                        _recipe.value = response.body()?.reference
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object{
        private const val TAG = "FragmentDetail"

    }

}