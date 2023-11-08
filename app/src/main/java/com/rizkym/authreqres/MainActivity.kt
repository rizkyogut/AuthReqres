package com.rizkym.authreqres

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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkym.authreqres.auth.LoginActivity
import com.rizkym.authreqres.databinding.ActivityMainBinding
import com.rizkym.authreqres.paging.LoadingStateAdapter
import com.rizkym.authreqres.paging.PagingViewModel
import com.rizkym.authreqres.paging.UserListAdapter
import com.rizkym.authreqres.remote.Repository
import com.rizkym.authreqres.remote.RepositoryAuth
import com.rizkym.authreqres.utils.UserPreferences
import com.rizkym.authreqres.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_key")
    private val pagingViewModel: PagingViewModel by viewModels {
        PagingViewModel.ViewModelFactory()
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
        val preferences = UserPreferences.getInstance(dataStore)
        val repository = RepositoryAuth(preferences)
        val viewModelFactory = ViewModelFactory(repository)
        viewModelFactory.setApplication(application)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
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
        pagingViewModel.getPagingUsers().observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }
}