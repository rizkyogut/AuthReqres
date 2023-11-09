package com.rizkym.authreqres.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.rizkym.authreqres.ui.main.MainActivity
import com.rizkym.authreqres.remote.Result
import com.rizkym.authreqres.utils.UserPreferences
import com.rizkym.authreqres.utils.ViewModelFactory
import com.rizkym.authreqres.databinding.ActivityLoginBinding
import com.rizkym.authreqres.remote.AuthRepository

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_key")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupView()
    }

    private fun setupViewModel() {
        val pref = UserPreferences.getInstance(dataStore)
        val repository = AuthRepository(pref)
        loginViewModel = ViewModelProvider(this, ViewModelFactory(repository))[LoginViewModel::class.java]
    }

    private fun setupView() {
        with(binding) {
            btnLogin.setOnClickListener(this@LoginActivity)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnLogin -> {

                val email = binding.edEmail.text.toString()
                val password = binding.edPassword.text.toString()

                loginViewModel.loginPost(email, password).observe(this) {
                    when (it) {
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> {
                            showLoading(false)
                            saveUserData(it)
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                        is Result.Error -> {
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                            showLoading(false)
                        }
                        else -> throw AssertionError()
                    }
                }
            }
        }
    }

    private fun saveUserData(it: Result<String>) {
        loginViewModel.saveUser(it.data.toString())
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}