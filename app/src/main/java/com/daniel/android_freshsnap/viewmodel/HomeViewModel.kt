package com.daniel.android_freshsnap.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniel.android_freshsnap.api.ApiConfig
import com.daniel.android_freshsnap.api.response.HomeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    private val _fruits = MutableLiveData<List<HomeResponse.FruitsItem>>()
    val listFruits: LiveData<List<HomeResponse.FruitsItem>> = _fruits

    private val _vegetables = MutableLiveData<List<HomeResponse.VegetablesItem>>()
    val listVegetables: LiveData<List<HomeResponse.VegetablesItem>> = _vegetables

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun findData() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFruit()
        client.enqueue(object : Callback<HomeResponse> {
            override fun onResponse(
                call: Call<HomeResponse>,
                response: Response<HomeResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d(TAG, response.body().toString())
                        _fruits.value = response.body()?.fruits
                        _vegetables.value = response.body()?.vegetables
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object{
        private const val TAG = "MainActivity"

    }

}