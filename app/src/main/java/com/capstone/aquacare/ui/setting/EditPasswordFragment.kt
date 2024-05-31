package com.capstone.aquacare.ui.setting

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.capstone.aquacare.R
import com.capstone.aquacare.data.UserData
import com.capstone.aquacare.databinding.FragmentEditPasswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EditPasswordFragment : Fragment() {

    private var _binding: FragmentEditPasswordBinding? = null
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
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val email = sharedPreferences?.getString("email", "").toString()

        binding.btnSave.setOnClickListener {
//            editPassword()
            val oldPassword = binding.edtOldPassword.text.toString()
            val newPassword = binding.edtNewPassword.text.toString()

            if (checkForm()) {
                changePassword(oldPassword, newPassword)
            }
        }

        binding.tvForgotPassword.setOnClickListener{
            forgotPassword(email)
        }
    }

    private fun checkForm(): Boolean {
        val oldPassword = binding.edtOldPassword.text.toString()
        val newPassword = binding.edtNewPassword.text.toString()
        val confirmPassword = binding.edtConfirmPassword.text.toString()

        if (TextUtils.isEmpty(oldPassword)) {
            binding.edtOldPassword.error = getString(R.string.please_enter_password)
            return false
        }

        if (TextUtils.isEmpty(newPassword)) {
            binding.edtNewPassword.error = getString(R.string.please_enter_password)
            return false
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            binding.edtConfirmPassword.error = getString(R.string.please_enter_confirm_password)
            return false
        }

        if (newPassword != confirmPassword) {
            binding.edtConfirmPassword.error = getString(R.string.confirm_password_wrong)
            return false
        }

        return true
    }

    private fun changePassword(oldPassword: String, newPassword: String) {
        val user = auth.currentUser
        if (user != null && user.email != null) {
            val credential = EmailAuthProvider.getCredential(user.email!!, oldPassword)

            user.reauthenticate(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user.updatePassword(newPassword)
                            .addOnCompleteListener { task2 ->
                                if (task2.isSuccessful) {
                                    Toast.makeText(activity, getString(R.string.success_to_edit_password), Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.action_editPasswordFragment_to_settingFragment)
                                } else {
                                    Toast.makeText(activity, "Password change failed.", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(activity, "Re-authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(activity, "No user is logged in.", Toast.LENGTH_SHORT).show()
        }
    }

//    private fun editPassword() {
//        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
//        val userId = sharedPreferences?.getString("userId", "").toString()
//
//        val password = binding.edtNewPassword.text.toString()
//        val confirmPassword = binding.edtConfirmPassword.text.toString()
//
//        if (password.isEmpty()) {
//            binding.edtNewPassword.error = getString(R.string.enter_new_password)
//            return
//        }
//
//        if (password == confirmPassword) {
//            databaseReference.orderByChild("id").equalTo(userId).addListenerForSingleValueEvent(object :
//                ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        for (snapshot in dataSnapshot.children) {
//                            val userData = snapshot.getValue(UserData::class.java)
//                            if (userData != null) {
//
//                                val updateData = mapOf("password" to password)
//
//                                databaseReference.child(userId).updateChildren(updateData)
//                                    .addOnSuccessListener {
//
//                                        Toast.makeText(activity, getString(R.string.success_to_edit_password), Toast.LENGTH_SHORT).show()
//                                        findNavController().navigate(R.id.action_editPasswordFragment_to_settingFragment)
//                                    }
//                                    .addOnFailureListener { e ->
//                                        Toast.makeText(activity, "Failed to Edit Password: ${e.message}", Toast.LENGTH_SHORT).show()
//                                    }
//
//                                Log.d(TAG, "User ID: ${snapshot.key}, Name: ${userData.name}, Style: ${userData.email}")
//                            }
//                        }
//                    } else {
//                        Log.d(TAG, "No user data available")
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
//                }
//            })
//        } else {
//            binding.edtConfirmPassword.error = getString(R.string.please_enter_confirm_password)
//        }
//
//    }

    private fun forgotPassword(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(activity, "Reset password email sent.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {

        private const val TAG = "MainActivity"
    }
}