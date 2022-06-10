package com.daniel.android_freshsnap.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.daniel.android_freshsnap.R
import com.daniel.android_freshsnap.api.ApiConfig
import com.daniel.android_freshsnap.api.response.RegisterResponse
import com.daniel.android_freshsnap.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setLogo(R.drawable.ic_back)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.title = "Register Account"

        binding.btnRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register -> {
                if (validateCreateAccount()) {
                    requestCreateAccount()
                }
            }
        }
    }

    private fun requestCreateAccount() {
        val name = binding.edtUser.text.toString().trim()
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        showLoading(true)
        val client= ApiConfig.getApiService().getRegist(name, email, password)
        client.enqueue(object: Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if(response.isSuccessful) {
                    showLoading(false)
                    AlertDialog.Builder(this@RegisterActivity).apply {
                        setTitle("Yeah!")
                        setMessage("Your account was successfully created")
                        setPositiveButton("Continue") { _, _ ->
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }
                        create()
                        show()
                    }
                }else{
                    showLoading(false)
                    Toast.makeText(this@RegisterActivity, "This email address is already being used", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@RegisterActivity, "Error your account cannot be created at this time", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun validateCreateAccount(): Boolean {

        return if(binding.edtEmail.text!!.isNotEmpty()
            && binding.edtPassword.text!!.isNotEmpty()
            && binding.edtUser.text!!.isNotEmpty()
            && android.util.Patterns.EMAIL_ADDRESS.matcher(binding.edtEmail.text.toString()).matches()
            && binding.edtPassword.text.toString().length > 5) {
            true
        } else {
            Toast.makeText(this, "Please input your data", Toast.LENGTH_SHORT).show()
            false
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