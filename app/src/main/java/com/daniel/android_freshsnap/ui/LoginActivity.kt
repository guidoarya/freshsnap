package com.daniel.android_freshsnap.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.auth0.android.jwt.JWT
import com.daniel.android_freshsnap.R
import com.daniel.android_freshsnap.api.ApiConfig
import com.daniel.android_freshsnap.api.response.LoginResponse
import com.daniel.android_freshsnap.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var isChecked = false

    companion object {
        const val PREFS_USER = "data_user"
        const val NAME = "name"
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val TOKEN = "token"
        const val USER_ID = "id_user"
        const val CHECK_BOX = "isChecked"
        const val TAG = "Login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        sharedPreferences = getSharedPreferences(PREFS_USER, Context.MODE_PRIVATE)
        isChecked = sharedPreferences.getBoolean(CHECK_BOX, false)
        checkLogin(isChecked)

        binding.btnLogin.setOnClickListener(this)
        binding.textRegister.setOnClickListener(this)
    }

    private fun checkLogin(login: Boolean){
        if (login){
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                loginAccount()
            }
            R.id.textRegister -> {
                val intent = Intent(this,RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private var tokenUser :String? = null

    private fun loginAccount() {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        showLoading(true)
        val client= ApiConfig.getApiService().getLogin(email, password)
        client.enqueue(object: Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if(response.isSuccessful) {
                    showLoading(false)
                    Log.d(TAG, response.body().toString())
                    tokenUser = response.body()?.token
                    tokenUser?.let{ setUser(it)}
                    val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                }else{
                    showLoading(false)
                    Toast.makeText(this@LoginActivity, "You have entered an invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                //showLoading(false)
                Toast.makeText(this@LoginActivity, "Error your account cannot login at this time", Toast.LENGTH_SHORT).show()
            }

        })
    }

    //parsing token using JWT
    fun setUser(token : String) {
        val parsedJWT = JWT((token))
        val subscriptionMetaDataName = parsedJWT.getClaim("name")
        val parsedValueName = subscriptionMetaDataName.asString()
        val subscriptionMetaDataId = parsedJWT.getClaim("id")
        val parsedValueId = subscriptionMetaDataId.asString()
        val subscriptionMetaDataEmail = parsedJWT.getClaim("email")
        val parsedValueEmail = subscriptionMetaDataEmail.asString()
        val subscriptionMetaDataPassword = parsedJWT.getClaim("password")
        val parsedValuePassword = subscriptionMetaDataPassword.asString()

        val nameLogin = parsedValueName.toString()
        val idLogin = parsedValueId.toString()
        val emailLogin = parsedValueEmail.toString()
        val passwordLogin = parsedValuePassword.toString()


        saveUser(idLogin,nameLogin, token, emailLogin, passwordLogin)

    }

    //shared pref
    fun saveUser(userId: String, name: String, token: String, email: String, password: String) {
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(NAME, name)
        editor.putString(USER_ID, userId)
        editor.putString(TOKEN, token)
        editor.putString(EMAIL, email)
        editor.putString(PASSWORD, password)
        editor.putBoolean(CHECK_BOX, binding.check.isChecked)
        editor.apply()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}