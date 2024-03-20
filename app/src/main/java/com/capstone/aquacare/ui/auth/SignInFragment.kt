package com.capstone.aquacare.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.capstone.aquacare.MainActivity
import com.capstone.aquacare.R
import com.capstone.aquacare.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        checkUserLoginStatus()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnLogin.setOnClickListener {
                if (checkForm()) {
                    submitForm()
                }
            }

            tvSignup.setOnClickListener{
                val signUpFragment = SignUpFragment()
                val fragmentManager = parentFragmentManager
                fragmentManager.beginTransaction().apply {
                    replace(
                        R.id.auth_frame_container,
                        signUpFragment,
                        SignUpFragment::class.java.simpleName
                    )
                    addToBackStack(null)
                    commit()
                }
            }

        }

    }

    private fun checkForm(): Boolean {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(activity, "Enter Email", Toast.LENGTH_SHORT).show()
            return false
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(activity, "Enter Password", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun submitForm() {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    Toast.makeText(activity, "Login Success", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    val id = user?.uid.toString()
                    val name = user?.displayName.toString()
                    val email = user?.email.toString()
                    val photo = user?.photoUrl.toString()

                    saveLoginSession(id, name, email, photo)

                    startActivity(Intent(activity, MainActivity::class.java))
                    requireActivity().finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        activity,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }

    }

    private fun saveLoginSession(userId: String, name: String, email: String, photo: String) {
        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("userId", userId)
        editor?.putString("name", name)
        editor?.putString("email", email)
        editor?.putString("photo", photo)
        editor?.apply()
    }

    private fun checkUserLoginStatus() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(activity, MainActivity::class.java))
        }
    }


    companion object {
        private const val TAG = "AuthActivity"

    }
}
