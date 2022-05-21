package com.daniel.android_freshsnap.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.daniel.android_freshsnap.MainActivity
import com.daniel.android_freshsnap.R
import com.daniel.android_freshsnap.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.btnLogin.setOnClickListener(this)
        binding.textRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.textRegister -> {
                val intent = Intent(this,RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

}