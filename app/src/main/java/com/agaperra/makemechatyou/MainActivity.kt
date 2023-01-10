package com.agaperra.makemechatyou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.agaperra.makemechatyou.databinding.ActivityMainBinding

/**
 * Main activity
 *
 * @constructor Create empty Main activity with view binding
 */

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
}