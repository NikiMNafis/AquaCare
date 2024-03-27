package com.capstone.aquacare.ui.identification

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.capstone.aquacare.R
import com.capstone.aquacare.data.IdentificationData
import com.capstone.aquacare.databinding.FragmentIdentificationBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class IdentificationFragment : Fragment() {

    private var _binding: FragmentIdentificationBinding? = null
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
        _binding = FragmentIdentificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aquascapeId = arguments?.getString("aquascapeId").toString()

        binding.btnIdentified.setOnClickListener {
            addIdentification(aquascapeId)
        }
    }

    private fun addIdentification(aquascapeId: String) {

        val result = "Status Baik"
        val currentDate = getCurrentDate()
        val temperature = binding.edtTemperature.text.toString()
        val ph = binding.edtPh.text.toString()
        val ammonia = binding.edtAmmonia.text.toString()
        val kh = binding.edtKh.text.toString()
        val gh = binding.edtGh.text.toString()

        if (aquascapeId.isNullOrEmpty()) {
            Toast.makeText(activity, "Aquascape ID not found", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val userId = sharedPreferences?.getString("userId", "")

        if (userId.isNullOrEmpty()) {
            Toast.makeText(activity, "User ID not found", Toast.LENGTH_SHORT).show()
            return
        }

        val identificationReference = databaseReference.child(userId).child("aquascapes").child(aquascapeId).child("identification")
        val newIdentificationId = identificationReference.push().key

        if (newIdentificationId != null) {
            val newIdentificationData = IdentificationData(newIdentificationId, result, currentDate, temperature, ph, ammonia, kh, gh)
            identificationReference.child(newIdentificationId).setValue(newIdentificationData)
                .addOnSuccessListener {
                    Toast.makeText(activity, "Success to Add Identification Aquascape", Toast.LENGTH_SHORT).show()
                    val fragmentManager = parentFragmentManager
                    fragmentManager.popBackStack()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(activity, "Failed to add Identification Aquascape: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(activity, "Failed to generate Identification ID", Toast.LENGTH_SHORT).show()
        }
    }

    fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        return dateFormat.format(calendar.time)
    }

    companion object {

    }
}