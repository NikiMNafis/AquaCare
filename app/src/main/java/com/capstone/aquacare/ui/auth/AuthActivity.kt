package com.capstone.aquacare.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.capstone.aquacare.R
import com.capstone.aquacare.databinding.ActivityAuthBinding
import com.capstone.aquacare.databinding.ActivityMainBinding

class AuthActivity : AppCompatActivity() {

    private var _binding: ActivityAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val fragmentManager = supportFragmentManager
        val signInFragment = SignInFragment()
        val fragment = fragmentManager.findFragmentByTag(SignInFragment::class.java.simpleName)

        if (fragment !is SignInFragment) {
            Log.d(TAG, "Fragment Name :" + SignInFragment::class.java.simpleName)
            fragmentManager
                .beginTransaction()
                .add(R.id.auth_frame_container, signInFragment, SignInFragment::class.java.simpleName)
                .commit()
        }
    }

    companion object {
        private const val TAG = "AuthActivity"
    }
}