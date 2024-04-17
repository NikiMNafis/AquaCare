package com.capstone.aquacare.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.capstone.aquacare.R
import com.capstone.aquacare.data.UserData
import com.capstone.aquacare.databinding.FragmentEditProfileBinding
import com.capstone.aquacare.ui.auth.AuthActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val name = sharedPreferences?.getString("name", "")
        val email = sharedPreferences?.getString("email", "")

        binding.edtName.setText(name)
        binding.edtEmail.setText(email)

        binding.btnSave.setOnClickListener {
            editUserProfile()
        }

        binding.btnDelete.setOnClickListener {
//            deleteUser()
        }
    }

    private fun editUserProfile() {
        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val userId = sharedPreferences?.getString("userId", "").toString()
        val userType = sharedPreferences?.getString("userType", "").toString()
        val photo = sharedPreferences?.getString("photo", "").toString()

        val name = binding.edtName.text.toString()
        val email = binding.edtEmail.text.toString()

        if (name.isEmpty()) {
            binding.edtName.error = "Enter Name"
            return
        }

        if (email.isEmpty()) {
            binding.edtEmail.error = "Enter Email"
            return
        }

        databaseReference.orderByChild("id").equalTo(userId).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val userData = snapshot.getValue(UserData::class.java)
                        if (userData != null) {

                            val updateData = mapOf("name" to name, "email" to email)

                            databaseReference.child(userId).updateChildren(updateData)
                                .addOnSuccessListener {

                                    saveLoginSession(userId, name, email, photo, userType)

                                    Toast.makeText(activity, "Success to Edit User Profile", Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.action_editProfileFragment_to_settingFragment)
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(activity, "Failed to Edit User Profile: ${e.message}", Toast.LENGTH_SHORT).show()
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
    }

    private fun deleteUser() {
        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val userId = sharedPreferences?.getString("userId", "").toString()

        databaseReference.child(userId).removeValue().addOnSuccessListener {
            Firebase.auth.signOut()
            deleteLoginSession(requireContext())
            startActivity(
                Intent(
                    activity, AuthActivity::class.java
                )
            )
            activity?.finish()
            Toast.makeText(activity, "Success to Delete User Profile", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(activity, "Failed to Delete User Profile", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveLoginSession(userId: String, name: String, email: String, photo: String, userType: String) {
        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("userId", userId)
        editor?.putString("name", name)
        editor?.putString("email", email)
        editor?.putString("photo", photo)
        editor?.putString("userType", userType)
        editor?.apply()
    }

    private fun deleteLoginSession(context: Context) {
        val sharedPreferences = context.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("userId", "")
        editor?.putString("name", "")
        editor?.putString("email", "")
        editor?.putString("photo", "")
        editor?.apply()
    }

    companion object {

        private const val TAG = "MainActivity"
    }
}