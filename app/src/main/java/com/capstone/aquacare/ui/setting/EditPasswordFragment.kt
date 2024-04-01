package com.capstone.aquacare.ui.setting

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.capstone.aquacare.data.UserData
import com.capstone.aquacare.databinding.FragmentEditPasswordBinding
import com.google.firebase.database.*

class EditPasswordFragment : Fragment() {

    private var _binding: FragmentEditPasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        binding.tvPassword.visibility = View.GONE
        binding.edtOldPassword.visibility = View.GONE
        binding.line1.visibility = View.GONE
        binding.tvForgotPassword.visibility = View.GONE

        binding.btnSave.setOnClickListener {
            editPassword()
        }
    }

    private fun editPassword() {
        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val userId = sharedPreferences?.getString("userId", "").toString()

        val password = binding.edtNewPassword.text.toString()
        val confirmPassword = binding.edtConfirmPassword.text.toString()

        if (password.isEmpty()) {
            binding.edtNewPassword.error = "Enter New Password"
            return
        }

        if (password == confirmPassword) {
            databaseReference.orderByChild("id").equalTo(userId).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (snapshot in dataSnapshot.children) {
                            val userData = snapshot.getValue(UserData::class.java)
                            if (userData != null) {

                                val updateData = mapOf("password" to password)

                                databaseReference.child(userId).updateChildren(updateData)
                                    .addOnSuccessListener {

                                        Toast.makeText(activity, "Success to Edit Password", Toast.LENGTH_SHORT).show()
                                        val fragmentManager = parentFragmentManager
                                        fragmentManager.popBackStack()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(activity, "Failed to Edit Password: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }

                                Log.d(TAG, "User ID: ${snapshot.key}, Name: ${userData.name}, Style: ${userData.email}")
                            }
                        }
                    } else {
                        Log.d(TAG, "No user data available")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        } else {
            binding.edtConfirmPassword.error = "Wrong Confirm Password"
        }

    }

    companion object {

        private const val TAG = "MainActivity"
    }
}