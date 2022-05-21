package com.daniel.android_freshsnap.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.daniel.android_freshsnap.R
import com.daniel.android_freshsnap.databinding.ActivityRegisterBinding

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
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
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
}