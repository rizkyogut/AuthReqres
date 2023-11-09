package com.rizkym.authreqres

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.rizkym.authreqres.ui.auth.LoginActivity
import com.rizkym.authreqres.remote.AuthRepository
import com.rizkym.authreqres.ui.main.MainActivity
import com.rizkym.authreqres.ui.main.MainViewModel
import com.rizkym.authreqres.utils.UserPreferences
import com.rizkym.authreqres.utils.ViewModelFactory

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_key")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        setupViewModel()

        Handler(Looper.getMainLooper()).postDelayed({

            mainViewModel.getUserKey().observe(this) {
                if (it.isNullOrEmpty()) {
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
            finish()
        }, SPLASH_DELAY)
    }

    private fun setupViewModel() {
        val pref = UserPreferences.getInstance(dataStore)
        val repository = AuthRepository(pref)
        mainViewModel = ViewModelProvider(this, ViewModelFactory(repository))[MainViewModel::class.java]
    }

    companion object {
        const val SPLASH_DELAY = 2000L
    }
}