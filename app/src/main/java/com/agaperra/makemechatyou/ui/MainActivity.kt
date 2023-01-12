package com.agaperra.makemechatyou.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.agaperra.makemechatyou.R
import com.agaperra.makemechatyou.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity
 *
 * @constructor Create empty Main activity with view binding
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /**
     * Binding
     *
     * lateinit container for view binding
     */
    private lateinit var binding: ActivityMainBinding


    /**
     * On create
     *
     * @param savedInstanceState saved instance state parameter
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun onBackPressed() {
        val id = findNavController(R.id.navHostFragment).currentDestination?.label
        if (id?.contains("ChatFragment") == true) {
            super.onBackPressed()
        } else if (id?.contains("ChannelFragment") == true) {
            val name = supportFragmentManager.getBackStackEntryAt(0).name
            supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }
}