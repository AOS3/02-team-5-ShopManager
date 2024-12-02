package com.lion.five.shopmanager

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.lion.five.shopmanager.databinding.ActivityMainBinding
import com.lion.five.shopmanager.fragment.HomeFragment
import com.lion.five.shopmanager.fragment.SalesFragment
import com.lion.five.shopmanager.fragment.SignInFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.commit {
                        replace(R.id.container_main, HomeFragment())
                        setReorderingAllowed(true)
                    }
                    true
                }
                R.id.navigation_sales -> {
                    supportFragmentManager.commit {
                        replace(R.id.container_main, SalesFragment())
                        setReorderingAllowed(true)
                    }
                    true
                }
                R.id.navigation_login -> {
                    supportFragmentManager.commit {
                        replace(R.id.container_main, SignInFragment())
                        setReorderingAllowed(true)
                    }
                    true
                }
                else -> false
            }
        }
    }

    fun setBottomNavigationVisibility(isVisible: Boolean) {
        binding.bottomNavigationMain.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}