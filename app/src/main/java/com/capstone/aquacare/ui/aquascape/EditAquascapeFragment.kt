package com.capstone.aquacare.ui.aquascape

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.capstone.aquacare.R
import com.capstone.aquacare.data.AquascapeData
import com.capstone.aquacare.databinding.FragmentEditAquascapeBinding
import com.capstone.aquacare.ui.home.HomeFragment
import com.google.firebase.database.*

class EditAquascapeFragment : Fragment() {

    private var _binding: FragmentEditAquascapeBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    private var selectedStyle: String? = null

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
        _binding = FragmentEditAquascapeBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aquascapeId = arguments?.getString("aquascapeId").toString()
        val aquascapeName = arguments?.getString("aquascapeName").toString()
        val style = arguments?.getString("style").toString()
        val createDate = arguments?.getString("createDate").toString()

        binding.edtName.setText(aquascapeName)
        binding.edtDateCreate.setText(createDate)

        val spinner = binding.spinStyle

        val aquascapeStyle = listOf("Natural Style", "Dutch Style")
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, aquascapeStyle)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter


        val index = aquascapeStyle.indexOf(style)
        if (index != -1) {
            spinner.setSelection(index)
        }

        selectedStyle = style

        binding.spinStyle.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedStyle = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.btnSave.setOnClickListener {
            editAquascape(aquascapeId)
        }

        binding.btnDelete.setOnClickListener {
            deleteAquascape(aquascapeId)
        }

    }

    private fun editAquascape(aquascapeId : String) {
        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val userId = sharedPreferences?.getString("userId", "").toString()

        val name = binding.edtName.text.toString()
        val style = selectedStyle.toString()

        val aquascapeReference = databaseReference.child(userId).child("aquascapes")

        aquascapeReference.orderByChild("id").equalTo(aquascapeId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val aquascapeData = snapshot.getValue(AquascapeData::class.java)
                        if (aquascapeData != null) {

                            val status = aquascapeData.status.toString()
                            val lastCheckDate = aquascapeData.lastCheckDate.toString()
                            val createDate = aquascapeData.createDate.toString()

                            val updateData = mapOf("id" to aquascapeId, "name" to name, "style" to style, "createDate" to createDate, "status" to status, "lastCheckDate" to lastCheckDate)

                            aquascapeReference.child(aquascapeId).updateChildren(updateData)
                                .addOnSuccessListener {

                                    Toast.makeText(activity, "Success to Edit Aquascape", Toast.LENGTH_SHORT).show()
                                    val fragmentManager = parentFragmentManager
                                    fragmentManager.popBackStack()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(activity, "Failed to Edit Aquascape: ${e.message}", Toast.LENGTH_SHORT).show()
                                }

                            Log.d(TAG, "Aquascape ID: ${snapshot.key}, Name: ${aquascapeData.name}, Style: ${aquascapeData.style}, Date: ${aquascapeData.createDate}")
                        }
                    }
                } else {
                    Log.d(TAG, "No aquascape data available")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun deleteAquascape(aquascapeId : String) {
        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val userId = sharedPreferences?.getString("userId", "").toString()

        val aquascapeReference = databaseReference.child(userId).child("aquascapes")

        aquascapeReference.child(aquascapeId).removeValue().addOnSuccessListener {
            val homeFragment = HomeFragment()
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().apply {
                replace(
                    R.id.main_frame_container,
                    homeFragment,
                    HomeFragment::class.java.simpleName
                )
                commit()
            }
        }

    }

    companion object {

        private const val TAG = "MainActivity"
    }
}
