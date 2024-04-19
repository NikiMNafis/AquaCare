package com.capstone.aquacare.ui.setting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.capstone.aquacare.R
import com.capstone.aquacare.data.AquascapeInfoData
import com.capstone.aquacare.data.UserData
import com.capstone.aquacare.databinding.FragmentEditAquascapeInfoBinding
import com.google.firebase.database.*

class EditAquascapeInfoFragment : Fragment() {

    private var _binding: FragmentEditAquascapeInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("aquascape_info")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditAquascapeInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val infoId = arguments?.getString("infoId").toString()
        val title = arguments?.getString("title").toString()
        val image = arguments?.getString("image").toString()
        val body = arguments?.getString("body").toString()

        binding.edtTitle.setText(title)
        binding.edtImage.setText(image)
        binding.edtBody.setText(body)

        binding.btnSave.setOnClickListener {
            editAquascapeInfo(infoId)
        }

        binding.btnDelete.setOnClickListener {
            deleteAquascapeInfo(infoId)
        }
    }

    private fun editAquascapeInfo(id: String) {

        val title = binding.edtTitle.text.toString()
        val image = binding.edtImage.text.toString()
        val body = binding.edtBody.text.toString()

        databaseReference.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val infoData = snapshot.getValue(AquascapeInfoData::class.java)
                        if (infoData != null) {

                            val updateData = mapOf("title" to title, "image" to image, "body" to body)

                            databaseReference.child(id).updateChildren(updateData)
                                .addOnSuccessListener {

                                    Toast.makeText(activity, "Success to Edit Aquascape Info", Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.action_editAquascapeInfoFragment_to_aquascapeInfoFragment)
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(activity, "Failed to Edit Aquascape Info: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
                } else {
                    Log.d(TAG, "No Aquascape Info data available")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun deleteAquascapeInfo(id: String) {
        databaseReference.child(id).removeValue().addOnSuccessListener {
            findNavController().navigate(R.id.action_editAquascapeInfoFragment_to_aquascapeInfoFragment)
        }
    }

    companion object {

        private const val TAG = "MainActivity"
    }

}