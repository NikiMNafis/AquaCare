package com.capstone.aquacare.ui.aquascape

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.capstone.aquacare.R
import com.capstone.aquacare.data.AquascapeData
import com.capstone.aquacare.databinding.FragmentAddAquascapeBinding
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class AddAquascapeFragment : Fragment() {

    private var _binding: FragmentAddAquascapeBinding? = null
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
        _binding = FragmentAddAquascapeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aquascapeStyle = listOf("Natural Style", "Dutch Style")
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, aquascapeStyle)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinStyle.adapter = arrayAdapter

        binding.spinStyle.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedStyle = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }

        binding.btnSelectDate.setOnClickListener {
            DatePickerDialog(requireContext(), datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.btnSave.setOnClickListener {
            if (checkForm()){
                addAquascape()
            }
        }
    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding.tvDateCreate.text = sdf.format(myCalendar.time)
    }

    private fun checkForm(): Boolean {
        val email = binding.edtName.text.toString()
        val date = binding.tvDateCreate.text.toString()

        if (TextUtils.isEmpty(email)) {
            binding.edtName.error = "Please enter name"
//            Toast.makeText(activity, "Please enter name", Toast.LENGTH_SHORT).show()
            return false
        }

        if (date == "Select Date") {
            Toast.makeText(activity, "Please enter date", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun addAquascape() {
        val name = binding.edtName.text.toString()
        val style = selectedStyle.toString()
        val date = binding.tvDateCreate.text.toString()
        val status = ""
        val lastCheckDate = ""

        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val userId = sharedPreferences?.getString("userId", "")

        if (userId.isNullOrEmpty()) {
            Toast.makeText(activity, "User ID not found", Toast.LENGTH_SHORT).show()
            return
        }

        val aquascapeReference = databaseReference.child(userId).child("aquascapes")
        val newAquascapeId = aquascapeReference.push().key

        if (newAquascapeId != null) {
            val newAquascapeData = AquascapeData(newAquascapeId, name, style, date, status, lastCheckDate)
            aquascapeReference.child(newAquascapeId).setValue(newAquascapeData)
                .addOnSuccessListener {
                    Toast.makeText(activity, "Success to Add Aquascape", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_addAquascapeFragment_to_homeFragment)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(activity, "Failed to add Aquascape: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(activity, "Failed to generate Aquascape ID", Toast.LENGTH_SHORT).show()
        }
    }

}