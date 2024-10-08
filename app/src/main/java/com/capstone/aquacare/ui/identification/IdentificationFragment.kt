package com.capstone.aquacare.ui.identification

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.capstone.aquacare.R
import com.capstone.aquacare.data.AquascapeData
import com.capstone.aquacare.data.Repository
import com.capstone.aquacare.databinding.FragmentIdentificationBinding
import com.capstone.aquacare.fuzzy.FuzzyIdentification
import com.capstone.aquacare.viewModel.AquascapeViewModel
import com.capstone.aquacare.viewModel.ViewModelFactory
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class IdentificationFragment : Fragment() {

    private var _binding: FragmentIdentificationBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    private lateinit var aquascapeViewModel: AquascapeViewModel

    private var aquascapeId: String? = null
    private var aquascapeName: String? = null
    private var style: String? = null
    private var result: String? = null
    private var currentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIdentificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        aquascapeId = arguments?.getString("aquascapeId").toString()
        aquascapeName = arguments?.getString("aquascapeName").toString()
        style = arguments?.getString("style").toString()

        binding.tvName.text = aquascapeName

        binding.btnIdentified.setOnClickListener {
            if (checkForm()) {
                addIdentification(aquascapeId!!)
            }
        }
    }

    private fun checkForm(): Boolean {
        if (binding.edtTemperature.text.isEmpty()) {
            binding.edtTemperature.error = getString(R.string.enter_temperature)
            return false
        }

        if (binding.edtPh.text.isEmpty()) {
            binding.edtPh.error = getString(R.string.enter_ph)
            return false
        }

        if (binding.edtAmmonia.text.isEmpty()) {
            binding.edtAmmonia.error = getString(R.string.enter_ammonia)
            return false
        }

        if (binding.edtKh.text.isEmpty()) {
            binding.edtKh.error = getString(R.string.enter_kh)
            return false
        }

        if (binding.edtGh.text.isEmpty()) {
            binding.edtGh.error = getString(R.string.enter_gh)
            return false
        }
        return true
    }

    private fun addIdentification(aquascapeId: String) {

        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val userId = sharedPreferences?.getString("userId", "")

        currentDate = getCurrentDate()
        val temperature = binding.edtTemperature.text.toString().toDoubleOrNull() ?: 0.0
        val ph = binding.edtPh.text.toString().toDoubleOrNull() ?: 0.0
        val ammonia = binding.edtAmmonia.text.toString().toDoubleOrNull() ?: 0.0
        val kh = binding.edtKh.text.toString().toDoubleOrNull() ?: 0.0
        val gh = binding.edtGh.text.toString().toDoubleOrNull() ?: 0.0

        val fuzzyIdentification = FuzzyIdentification(requireContext(), style.toString())

        result = fuzzyIdentification.calculateWaterQuality(temperature, ph, ammonia, kh, gh)

        if (aquascapeId.isEmpty()) {
            Toast.makeText(activity, "Aquascape ID not found", Toast.LENGTH_SHORT).show()
            return
        }

        if (userId.isNullOrEmpty()) {
            Toast.makeText(activity, "User ID not found", Toast.LENGTH_SHORT).show()
            return
        }

        val repository = Repository()
        aquascapeViewModel = ViewModelProvider(this, ViewModelFactory(repository))[AquascapeViewModel::class.java]

        aquascapeViewModel.addNewIdentification(userId, aquascapeId, result!!, currentDate!!, temperature.toString(), ph.toString(), ammonia.toString(), kh.toString(), gh.toString())
        aquascapeViewModel.isSuccessD.observe(viewLifecycleOwner) {
            if (it) {
                updateAquascapeData(userId, aquascapeId)

                val bundle = Bundle().apply {
                    putString("aquascapeId", aquascapeId)
                    putString("style", style)
                    putString("result", result)
                    putString("date", currentDate)
                    putString("temperature", temperature.toString())
                    putString("ph", ph.toString())
                    putString("ammonia", ammonia.toString())
                    putString("kh", kh.toString())
                    putString("gh", gh.toString())
                }
                findNavController().navigate(R.id.action_identificationFragment_to_resultFragment, bundle)
            } else {
                Toast.makeText(activity, "Failed to Identification Water Quality", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateAquascapeData(userId: String, aquascapeId: String) {
        val aquascapeReference = databaseReference.child(userId).child("aquascapes")
        aquascapeReference.orderByChild("id").equalTo(aquascapeId).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val aquascapeData = snapshot.getValue(AquascapeData::class.java)
                        if (aquascapeData != null) {

                            val updateData = mapOf("status" to result, "lastCheckDate" to currentDate)

                            aquascapeReference.child(aquascapeId).updateChildren(updateData)
                                .addOnSuccessListener {
                                    Toast.makeText(activity, getString(R.string.successful_identification), Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(activity, "Failed to Identification Water Quality: ${e.message}", Toast.LENGTH_SHORT).show()
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

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        return dateFormat.format(calendar.time)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}