package com.capstone.aquacare.ui.setting

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
import com.capstone.aquacare.data.AquascapeInfoData
import com.capstone.aquacare.databinding.FragmentListAquascapeInfoBinding
import com.google.firebase.database.*

class ListAquascapeInfoFragment : Fragment() {

    private var _binding: FragmentListAquascapeInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    val listInfo = mutableListOf<AquascapeInfoData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("aquascape_info")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListAquascapeInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvAquascapeInfo = binding.rvAquascapeInfo

        rvAquascapeInfo.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvAquascapeInfo.setHasFixedSize(true)

        binding.ivAddAquascapeInfo.setOnClickListener {
            findNavController().navigate(R.id.action_listAquascapeInfoFragment_to_addAquascapeInfoFragment)
        }

        getAquascapeInfoData()
    }

    private fun getAquascapeInfoData() {
        binding.rvAquascapeInfo.visibility = View.GONE

        databaseReference.addValueEventListener(object : ValueEventListener {
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
                val edit = "true"

                val bundle = Bundle().apply {
                    putString("infoId", infoId)
                    putString("title", title)
                    putString("image", image)
                    putString("body", body)
                    putString("edit", edit)
                }

                findNavController().navigate(R.id.action_listAquascapeInfoFragment_to_AquascapeInfoFragment, bundle)
            }
        })
    }
}