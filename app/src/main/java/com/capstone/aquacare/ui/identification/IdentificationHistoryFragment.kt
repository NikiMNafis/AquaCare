package com.capstone.aquacare.ui.identification

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.aquacare.R
import com.capstone.aquacare.data.IdentificationData
import com.capstone.aquacare.data.Repository
import com.capstone.aquacare.databinding.FragmentIdentificationHistoryBinding
import com.capstone.aquacare.viewModel.DataViewModel
import com.capstone.aquacare.viewModel.ViewModelFactory

class IdentificationHistoryFragment : Fragment() {

    private var _binding: FragmentIdentificationHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var dataViewModel: DataViewModel

    private val list = mutableListOf<IdentificationData>()

    private var aquascapeId: String? = null
    private var aquascapeName: String? = null
    private var style: String? = null
    private var createDate: String? = null

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

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        layoutManager.stackFromEnd = true

        rvIdentification.layoutManager = layoutManager
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

        val repository = Repository()
        dataViewModel = ViewModelProvider(this, ViewModelFactory(repository))[DataViewModel::class.java]

        dataViewModel.isLoadingC.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.pbHistory.visibility = View.VISIBLE
            } else {
                binding.pbHistory.visibility = View.GONE
            }
        })

        dataViewModel.identificationData.observe( viewLifecycleOwner, Observer { identification ->
            list.clear()
            for (data in identification) {
                list.add(data)
            }
            showIdentification()
        })

        dataViewModel.aquascapeData.observe(viewLifecycleOwner, Observer { aquascape ->
            val selectedAquascape = aquascape.find { it.id == aquascapeId }
            if (selectedAquascape != null) {
                aquascapeName = selectedAquascape.name
                style = selectedAquascape.style
                createDate = selectedAquascape.createDate
                binding.tvName.text = aquascapeName
            } else {
                Log.d("DataViewModel", "No aquascape data available")
            }
        })

        dataViewModel.getAquascapeData(userId)
        dataViewModel.getIdentificationData(userId, aquascapeId!!)
    }

    private fun showIdentification() {
        val adapter = IdentificationHistoryAdapter(list)
        binding.rvListIdentification.adapter = adapter
        Log.d("DataList", "Jumlah data dalam list identification: ${list.size}")

        adapter.setOnItemClickCallBack(object : IdentificationHistoryAdapter.OnItemClickCallback{
            override fun onItemClicked(data: IdentificationData) {

                val bundle = Bundle().apply {
                    putString("aquascapeId", aquascapeId.toString())
                    putString("style", style.toString())
                    putString("result", data.result)
                    putString("date", data.date)
                    putString("temperature", data.temperature)
                    putString("ph", data.ph)
                    putString("ammonia", data.ammonia)
                    putString("kh", data.kh)
                    putString("gh", data.gh)
                }
                findNavController().navigate(R.id.action_historyFragment_to_resultFragment, bundle)
            }
        })
    }
}