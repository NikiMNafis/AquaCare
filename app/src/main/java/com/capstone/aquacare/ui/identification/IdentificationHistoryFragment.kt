package com.capstone.aquacare.ui.identification

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
import com.capstone.aquacare.data.IdentificationData
import com.capstone.aquacare.databinding.FragmentIdentificationHistoryBinding
import com.google.firebase.database.*

class IdentificationHistoryFragment : Fragment() {

    private var _binding: FragmentIdentificationHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    val list = mutableListOf<IdentificationData>()

    private var aquascapeId: String? = null
    private var aquascapeName: String? = null
    private var style: String? = null
    private var createDate: String? = null

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
        _binding = FragmentIdentificationHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val userId = sharedPreferences?.getString("userId", "").toString()

        aquascapeId = arguments?.getString("aquascapeId")

        val rvIdentification = binding.rvListIdentification

        rvIdentification.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvIdentification.setHasFixedSize(true)

        binding.btnAddIdentification.setOnClickListener {
            val bundle = Bundle().apply {
                putString("aquascapeId", aquascapeId)
                putString("aquascapeName", aquascapeName)
                putString("style", style)
            }
            findNavController().navigate(R.id.action_historyFragment_to_identificationFragment, bundle)
        }

        binding.tvEdit.setOnClickListener {
            val bundleEdit = Bundle().apply {
                putString("aquascapeId", aquascapeId)
                putString("aquascapeName", aquascapeName)
                putString("style", style)
                putString("createDate", createDate)
            }
            findNavController().navigate(R.id.action_historyFragment_to_editAquascapeFragment, bundleEdit)
        }

        getUpdateData(userId, aquascapeId!!)
        getIdentificationData(userId, aquascapeId!!)
    }

    private fun getUpdateData(userId: String, aquascapeId : String) {
        val aquascapeReference = databaseReference.child(userId).child("aquascapes")

        aquascapeReference.orderByChild("id").equalTo(aquascapeId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val aquascapeData = snapshot.getValue(AquascapeData::class.java)
                        if (aquascapeData != null) {

                            aquascapeName = aquascapeData.name
                            style = aquascapeData.style
                            createDate = aquascapeData.createDate
                            binding.tvName.text = aquascapeName

                            Log.d("Aquascape", "Aquascape ID: ${snapshot.key}, Name: ${aquascapeData.name}, Style: ${aquascapeData.style}, Date: ${aquascapeData.createDate}")
                        }
                    }
                } else {
                    Log.d("Aquascape", "No aquascape data available")
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun getIdentificationData(userId : String, aquascapeId : String) {
        if (aquascapeId.isEmpty()) {
            Toast.makeText(activity, "Aquascape ID not found", Toast.LENGTH_SHORT).show()
            return
        }

        if (userId.isEmpty()) {
            Toast.makeText(activity, "User ID not found", Toast.LENGTH_SHORT).show()
            return
        }

        val identificationReference = databaseReference.child(userId).child("aquascapes").child(aquascapeId).child("identification")

        identificationReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                for (snapshot in dataSnapshot.children) {
                    val dataIdentification = snapshot.getValue(IdentificationData::class.java)
                    if (dataIdentification != null) {
                        list.add(dataIdentification)
                    }
                }

                showIdentification()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to retrieve aquascape data: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showIdentification() {
        val adapter = IdentificationAdapter(list)
        binding.rvListIdentification.adapter = adapter
        Log.d("DataList", "Jumlah data dalam list identification: ${list.size}")

        adapter.setOnItemClickCallBack(object : IdentificationAdapter.OnItemClickCallback{
            override fun onItemClicked(data: IdentificationData) {
                val result = data.result
                val date = data.date
                val temperature = data.temperature
                val ph = data.ph
                val ammonia = data.ammonia
                val kh = data.kh
                val gh = data.gh

                val bundle = Bundle().apply {
                    putString("aquascapeId", aquascapeId.toString())
                    putString("style", style.toString())
                    putString("result", result)
                    putString("date", date)
                    putString("temperature", temperature)
                    putString("ph", ph)
                    putString("ammonia", ammonia)
                    putString("kh", kh)
                    putString("gh", gh)
                }
                findNavController().navigate(R.id.action_historyFragment_to_resultFragment, bundle)
            }
        })
    }
}