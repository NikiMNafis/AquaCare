package com.capstone.aquacare.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.aquacare.R
import com.capstone.aquacare.data.AquascapeData
import com.capstone.aquacare.databinding.FragmentHomeBinding
import com.capstone.aquacare.ui.aquascape.AddAquascapeFragment
import com.capstone.aquacare.ui.auth.SignUpFragment
import com.capstone.aquacare.ui.setting.SettingFragment
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
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
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val name = sharedPreferences?.getString("name", "")
        val userId = sharedPreferences?.getString("userId", "")
        binding.tvName.text = "Halo, $name"

        val rvAquascape = binding.rvListAquascape

        getAquascapeData(userId.toString())

        rvAquascape.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvAquascape.setHasFixedSize(true)

        binding.ivProfile.setOnClickListener {
            val settingFragment = SettingFragment()
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().apply {
                replace(
                    R.id.main_frame_container,
                    settingFragment,
                    SettingFragment::class.java.simpleName
                )
                addToBackStack(null)
                commit()
            }
        }

        binding.btnAddAquascape.setOnClickListener {
            val addAquascapeFragment = AddAquascapeFragment()
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().apply {
                replace(
                    R.id.main_frame_container,
                    addAquascapeFragment,
                    AddAquascapeFragment::class.java.simpleName
                )
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun getAquascapeData(userId: String) {
        binding.rvListAquascape.visibility = View.GONE

        val aquascapeReference = databaseReference.child(userId).child("aquascapes")

        aquascapeReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list = mutableListOf<AquascapeData>()
                list.clear()
                for (snapshot in dataSnapshot.children) {
                    val dataAquascape = snapshot.getValue(AquascapeData::class.java)
                    if (dataAquascape != null) {
                        list.add(dataAquascape)
                    }
                }

                binding.rvListAquascape.visibility = View.VISIBLE

                val recyclerView = binding.rvListAquascape
                val adapter = AquascapeAdapter(list)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to retrieve aquascape data: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {

    }
}