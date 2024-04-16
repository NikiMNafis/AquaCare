package com.capstone.aquacare.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.aquacare.R
import com.capstone.aquacare.data.AquascapeData
import com.capstone.aquacare.databinding.FragmentHomeBinding
import com.capstone.aquacare.ui.aquascape.AddAquascapeFragment
import com.capstone.aquacare.ui.identification.IdentificationHistoryFragment
import com.capstone.aquacare.ui.setting.SettingFragment
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    val list = mutableListOf<AquascapeData>()

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val name = sharedPreferences?.getString("name", "")
        val userId = sharedPreferences?.getString("userId", "").toString()
        binding.tvName.text = "Halo, $name"

        val rvAquascape = binding.rvListAquascape

        rvAquascape.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvAquascape.setHasFixedSize(true)

        binding.ivProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
        }

        binding.btnAddAquascape.visibility = View.GONE
//        binding.btnAddAquascape.setOnClickListener {
//            val addAquascapeFragment = AddAquascapeFragment()
//            val fragmentManager = parentFragmentManager
//            fragmentManager.beginTransaction().apply {
//                replace(
//                    R.id.main_frame_container,
//                    addAquascapeFragment,
//                    AddAquascapeFragment::class.java.simpleName
//                )
//                addToBackStack(null)
//                commit()
//            }
//        }

        getAquascapeData(userId)
    }

    private fun getAquascapeData(userId: String) {
        binding.rvListAquascape.visibility = View.GONE

        val aquascapeReference = databaseReference.child(userId).child("aquascapes")

        aquascapeReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                for (snapshot in dataSnapshot.children) {
                    val dataAquascape = snapshot.getValue(AquascapeData::class.java)
                    if (dataAquascape != null) {
                        list.add(dataAquascape)
                    }
                }

                binding.rvListAquascape.visibility = View.VISIBLE

                showAquascape()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to retrieve aquascape data: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showAquascape() {
        val btnAddAquascape = AquascapeData("AddAquascape", "", "Add", "", "Add Aquascape")
        list.add(btnAddAquascape)
        val adapter = AquascapeAdapter(list)
        binding.rvListAquascape.adapter = adapter
        Log.d("DataList", "Jumlah data dalam list aquascape: ${list.size}")

        adapter.setOnItemClickCallBack(object : AquascapeAdapter.OnItemClickCallback{
            override fun onItemClicked(data: AquascapeData) {

                val aquascapeId = data.id

                if (aquascapeId == "AddAquascape") {
                    findNavController().navigate(R.id.action_homeFragment_to_addAquascapeFragment)
                } else {
                    val bundle = Bundle().apply {
                        putString("aquascapeId", aquascapeId)
                    }

                    findNavController().navigate(R.id.action_homeFragment_to_historyFragment, bundle)

//                    val identificationHistoryFragment = IdentificationHistoryFragment()
//                    identificationHistoryFragment.arguments = bundle
//                    val fragmentManager = parentFragmentManager
//                    fragmentManager.beginTransaction().apply {
//                        replace(
//                            R.id.main_frame_container,
//                            identificationHistoryFragment,
//                            IdentificationHistoryFragment::class.java.simpleName
//                        )
//                        addToBackStack(null)
//                        commit()
//                    }
                }
            }
        })
    }

    companion object {

    }
}