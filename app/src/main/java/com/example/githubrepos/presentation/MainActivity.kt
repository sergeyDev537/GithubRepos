package com.example.githubrepos.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.githubrepos.R
import com.example.githubrepos.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    private val navController: NavController by lazy {
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.navView.setupWithNavController(navController)
    }
}