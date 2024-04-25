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
import com.capstone.aquacare.data.AquascapeInfoData
import com.capstone.aquacare.databinding.FragmentHomeBinding
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var databaseInfoReference: DatabaseReference

    val list = mutableListOf<AquascapeData>()
    val listInfo = mutableListOf<AquascapeInfoData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")
        databaseInfoReference = firebaseDatabase.reference.child("aquascape_info")
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
        val rvAquascapeInfo = binding.rvAquascapeInfo

        rvAquascape.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvAquascape.setHasFixedSize(true)

        rvAquascapeInfo.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvAquascapeInfo.setHasFixedSize(true)

        binding.ivProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
        }

        binding.tvAdd.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_addAquascapeFragment)
        }

        getAquascapeData(userId)
        getAquascapeInfoData()
    }

    private fun getAquascapeData(userId: String) {
//        binding.rvListAquascape.visibility = View.GONE

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
//                binding.rvListAquascape.visibility = View.VISIBLE

                showAquascape()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to retrieve aquascape data: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getAquascapeInfoData() {
        binding.rvAquascapeInfo.visibility = View.GONE

        databaseInfoReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listInfo.clear()
                for (snapshot in dataSnapshot.children) {
                    val dataAquascapeInfo = snapshot.getValue(AquascapeInfoData::class.java)
                    if (dataAquascapeInfo != null) {
                        listInfo.add(dataAquascapeInfo)
                    }
                }

                binding.rvAquascapeInfo.visibility = View.VISIBLE

                showAquascapeInfo()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to retrieve aquascape info data: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showAquascape() {
        val adapter = AquascapeAdapter(list)
        binding.rvListAquascape.adapter = adapter
        Log.d("DataList", "Jumlah data dalam list aquascape: ${list.size}")

        adapter.setOnItemClickCallBack(object : AquascapeAdapter.OnItemClickCallback{
            override fun onItemClicked(data: AquascapeData) {

                val aquascapeId = data.id

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
        })
    }

    private fun showAquascapeInfo() {
        val adapter = AquascapeInfoAdapter(listInfo)
        binding.rvAquascapeInfo.adapter = adapter
        Log.d("DataList", "Jumlah data dalam list aquascape info: ${listInfo.size}")

        adapter.setOnItemClickCallBack(object : AquascapeInfoAdapter.OnItemClickCallback{
            override fun onItemClicked(data: AquascapeInfoData) {

                val infoId = data.id
                val title = data.title
                val image = data.image
                val body = data.body
                val edit = "false"

                val bundle = Bundle().apply {
                    putString("infoId", infoId)
                    putString("title", title)
                    putString("image", image)
                    putString("body", body)
                    putString("edit", edit)
                }

                findNavController().navigate(R.id.action_homeFragment_to_AquascapeInfoFragment, bundle)
            }
        })
    }
}