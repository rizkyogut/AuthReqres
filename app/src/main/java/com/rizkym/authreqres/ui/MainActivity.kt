package com.rizkym.authreqres.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkym.authreqres.R
import com.rizkym.authreqres.adapter.LoadingStateAdapter
import com.rizkym.authreqres.adapter.UserListAdapter
import com.rizkym.authreqres.databinding.ActivityMainBinding
import com.rizkym.authreqres.ui.auth.LoginActivity
import com.rizkym.authreqres.utils.UserPreferences

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_key")
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory(this, UserPreferences.getInstance(dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        setSupportActionBar(binding.toolbar)

        binding.rvUser.layoutManager = LinearLayoutManager(this)

        getData()
    }

    private fun setupViewModel() {
//        val preferences = UserPreferences.getInstance(dataStore)
//        val viewModelFactory = ViewModelFactory(preferences)
//        viewModelFactory.setApplication(application)
//        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_logout -> {
                mainViewModel.logout()
                startActivity(Intent(this, LoginActivity::class.java))
                Toast.makeText(this@MainActivity, getString(R.string.logout_alert),
                    Toast.LENGTH_SHORT).show()
                true
            }
            else -> true
        }
    }


    private fun getData() {
        val adapter = UserListAdapter()
        binding.rvUser.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        mainViewModel.quota.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }
}