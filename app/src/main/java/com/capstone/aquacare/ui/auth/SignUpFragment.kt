package com.capstone.aquacare.ui.auth

import android.content.ClipData
import android.content.ClipData.Item
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.capstone.aquacare.R
import com.capstone.aquacare.data.UserData
import com.capstone.aquacare.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignup.setOnClickListener {
            if (checkForm()) {
//                submitForm()

                val name = binding.edtName.text.toString()
                val email = binding.edtEmail.text.toString()
                val password = binding.edtPassword.text.toString()
                val userType = "user"

                signUpUser(name, email, password, userType)
            }
        }
    }

    private fun checkForm(): Boolean {
        val name = binding.edtName.text.toString()
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        val confirmPassword = binding.edtConfirmPassword.text.toString()

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(activity, "Enter Name", Toast.LENGTH_SHORT).show()
            return false
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(activity, "Enter Email", Toast.LENGTH_SHORT).show()
            return false
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(activity, "Enter Password", Toast.LENGTH_SHORT).show()
            return false
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(activity, "Enter Confirm Password", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

//    private fun signUpAuth() {
//
//        val name = binding.edtName.text.toString()
//        val email = binding.edtEmail.text.toString()
//        val password = binding.edtPassword.text.toString()
//        val confirmPassword = binding.edtConfirmPassword.text.toString()
//
//        binding.emailContainer.helperText = validEmail()
////        binding.passwordContainer.helperText = validPassword()
////        binding.passwordConfirmContainer.helperText = validPasswordConfirm()
//
//        auth.createUserWithEmailAndPassword(email, confirmPassword)
//            .addOnCompleteListener(requireActivity()) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "createUserWithEmail:success")
//                    Toast.makeText(activity, "Account Created", Toast.LENGTH_SHORT).show()
//                    val user = auth.currentUser
//
//                    val fragmentManager = parentFragmentManager
//                    fragmentManager.popBackStack()
//
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(
//                        activity,
//                        "Authentication failed.",
//                        Toast.LENGTH_SHORT,
//                    ).show()
//                }
//            }
//    }

    private fun signUpUser(name: String, email: String, password: String, userType: String) {
        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    val id = databaseReference.push().key
                    val userData = UserData(id, name, email, password, userType)
                    databaseReference.child(id!!).setValue(userData)

                    Toast.makeText(activity, "Account Created", Toast.LENGTH_SHORT).show()
                    val fragmentManager = parentFragmentManager
                    fragmentManager.popBackStack()
                } else {
                    for (userSnaphot in dataSnapshot.children) {
                        val userData = userSnaphot.getValue(UserData::class.java)
                        val userId = userData?.id.toString()
                        val userName = userData?.name.toString()
                        val userEmail = userData?.email.toString()

                        if (userData != null && userData.password == "Google Account") {
                            val newDataUser = UserData(userId, userName, userEmail, password, userType)
                            databaseReference.child(userId).setValue(newDataUser)

                            Toast.makeText(activity, "Account Created", Toast.LENGTH_SHORT).show()
                            val fragmentManager = parentFragmentManager
                            fragmentManager.popBackStack()
                        } else {
                            Toast.makeText(activity, "Account already Exists", Toast.LENGTH_SHORT).show()
                            val fragmentManager = parentFragmentManager
                            fragmentManager.popBackStack()
                        }
                    }
//                    Toast.makeText(activity, "Account already Exists", Toast.LENGTH_SHORT).show()
//                    val fragmentManager = parentFragmentManager
//                    fragmentManager.popBackStack()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(activity, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun validEmail(): String? {
        val email = binding.edtEmail.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            return "Invalid Email Address"
        }
        return null
    }

    companion object {

        private const val TAG = "AuthActivity"

    }
}