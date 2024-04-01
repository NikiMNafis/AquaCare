package com.capstone.aquacare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.capstone.aquacare.databinding.ActivityMainBinding
import com.capstone.aquacare.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val fragmentManager = supportFragmentManager
        val homeFragment = HomeFragment()
        val fragment = fragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)

        if (fragment !is HomeFragment) {
            Log.d(TAG, "Fragment Name :" + HomeFragment::class.java.simpleName)
            fragmentManager
                .beginTransaction()
                .add(R.id.main_frame_container, homeFragment, HomeFragment::class.java.simpleName)
                .commit()
        }

    }

    companion object {
        private const val TAG = "MainActivity"

    }

}