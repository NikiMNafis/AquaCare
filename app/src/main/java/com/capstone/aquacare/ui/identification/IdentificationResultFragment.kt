package com.capstone.aquacare.ui.identification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.aquacare.R
import com.capstone.aquacare.databinding.FragmentIdentificationResultBinding

class IdentificationResultFragment : Fragment() {

    private var _binding: FragmentIdentificationResultBinding? = null
    private val binding get() = _binding!!

    private var result: String? = null
    private var date: String? = null
    private var temperature: String? = null
    private var ph: String? = null
    private var ammonia: String? = null
    private var kh: String? = null
    private var gh: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentIdentificationResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        result = arguments?.getString("result")
        date = arguments?.getString("date")
        temperature = arguments?.getString("temperature")
        ph = arguments?.getString("ph")
        ammonia = arguments?.getString("ammonia")
        kh = arguments?.getString("kh")
        gh = arguments?.getString("gh")

        // Sementara ===============================================
        binding.tvHasil.text = result
        binding.tvDetailTemperature.text = temperature
        binding.tvDetailPh.text = ph
        binding.tvDetailAmmonia.text = ammonia
        binding.tvDetailKh.text = kh
        binding.tvDetailGh.text = gh
        binding.tvDate.text = date

    }

    companion object {

    }
}