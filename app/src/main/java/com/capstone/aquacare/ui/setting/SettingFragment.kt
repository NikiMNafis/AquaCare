package com.capstone.aquacare.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.capstone.aquacare.R
import com.capstone.aquacare.data.UserData
import com.capstone.aquacare.databinding.FragmentSettingBinding
import com.capstone.aquacare.ui.auth.AuthActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
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
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val userId = sharedPreferences?.getString("userId", "").toString()
        val userType = sharedPreferences?.getString("userType", "")
        val name = sharedPreferences?.getString("name", "")
        val email = sharedPreferences?.getString("email", "")

        binding.tvName.text = name
        binding.tvEmail.text = email

        if (userType != "admin") {
            binding.viewInfo.visibility = View.GONE
            binding.btnAquascapeInfo.visibility = View.GONE
        }

        val currentUser = auth.currentUser
        if (currentUser != null) {
            binding.viewPassword.visibility = View.GONE
            binding.btnChangePassword.visibility = View.GONE
        }

        getUpdateData(userId)

        val fragmentManager = parentFragmentManager

        binding.btnEditProfile.setOnClickListener {
            val editProfileFragment = EditProfileFragment()
            fragmentManager.beginTransaction().apply {
                replace(
                    R.id.main_frame_container,
                    editProfileFragment,
                    EditProfileFragment::class.java.simpleName
                )
                addToBackStack(null)
                commit()
            }
        }

        binding.btnChangePassword.setOnClickListener {
            val editPasswordFragment = EditPasswordFragment()
            fragmentManager.beginTransaction().apply {
                replace(
                    R.id.main_frame_container,
                    editPasswordFragment,
                    EditPasswordFragment::class.java.simpleName
                )
                addToBackStack(null)
                commit()
            }
        }

        binding.btnAquascapeInfo.setOnClickListener {
            val listAquascapeInfoFragment = ListAquascapeInfoFragment()
            fragmentManager.beginTransaction().apply {
                replace(
                    R.id.main_frame_container,
                    listAquascapeInfoFragment,
                    ListAquascapeInfoFragment::class.java.simpleName
                )
                addToBackStack(null)
                commit()
            }
        }

        binding.btnLogOut.setOnClickListener {
            Firebase.auth.signOut()
            deleteLoginSession(requireContext())
            startActivity(
                Intent(
                    activity, AuthActivity::class.java
                )
            )
            activity?.finish()
        }

    }

    private fun getUpdateData(userId: String) {
        databaseReference.orderByChild("id").equalTo(userId).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        val userData = userSnapshot.getValue(UserData::class.java)

                        if (userData != null){
                            val name = userData.name
                            val email = userData.email

                            binding.tvName.text = name
                            binding.tvEmail.text = email

                        } else {
                            Toast.makeText(activity, "Failed to get user data", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(activity, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
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

    }
}