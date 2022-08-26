package com.thaonx.mockt3h.view.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.thaonx.mockt3h.R
import com.thaonx.mockt3h.databinding.ActivityLoginBinding
import com.thaonx.mockt3h.utils.Constants

class LoginActivity : AppCompatActivity() {

    companion object {
        const val SHARED_PREF = "SHARED_PREF"

        const val KEY_CHECKBOX = "CHECK_BOX"
        const val KEY_USERNAME = "USERNAME"

    }

    private lateinit var binding: ActivityLoginBinding

    private lateinit var sharedPreferences: SharedPreferences
    private var isRemembered = false

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean(KEY_CHECKBOX, false)

        if (isRemembered) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener {

            val username = binding.edtName.text.toString()
            val password = binding.edtPassword.text.toString()
            val checked = binding.checkbox.isChecked

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, Constants.NOTIFY_LOGIN, Toast.LENGTH_SHORT).show()
            } else {
                val editor = sharedPreferences.edit()
                editor.putString(KEY_USERNAME, username)
                editor.putBoolean(KEY_CHECKBOX, checked)

                editor.apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
                finish()
            }
        }
    }
    override fun onStart() {
        super.onStart()
        //hide status bar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

}