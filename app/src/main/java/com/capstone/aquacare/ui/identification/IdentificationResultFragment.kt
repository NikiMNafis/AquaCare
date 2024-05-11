package com.capstone.aquacare.ui.identification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.capstone.aquacare.R
import com.capstone.aquacare.databinding.FragmentIdentificationResultBinding
import com.capstone.aquacare.fuzzy.FuzzyDutchStyle
import com.capstone.aquacare.fuzzy.FuzzyNaturalStyle

class IdentificationResultFragment : Fragment() {

    private var _binding: FragmentIdentificationResultBinding? = null
    private val binding get() = _binding!!

    private var aquascapeId: String? = null
    private var style: String? = null
    private var result: String? = null
    private var date: String? = null
    private var temperature: String? = null
    private var ph: String? = null
    private var ammonia: String? = null
    private var kh: String? = null
    private var gh: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        aquascapeId = arguments?.getString("aquascapeId")
        style = arguments?.getString("style")
        result = arguments?.getString("result")
        date = arguments?.getString("date")
        temperature = arguments?.getString("temperature")
        ph = arguments?.getString("ph")
        ammonia = arguments?.getString("ammonia")
        kh = arguments?.getString("kh")
        gh = arguments?.getString("gh")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentIdentificationResultBinding.inflate(inflater, container, false)
        setupOnBackPressed()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvHasil.text = result
        binding.tvTemperature.text = temperature
        binding.tvPh.text = ph
        binding.tvAmmonia.text = ammonia
        binding.tvKh.text = kh
        binding.tvGh.text = gh
        binding.tvDate.text = date

        checkParameter(style)
    }

    private fun setupOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val bundle = Bundle().apply {
                    putString("aquascapeId", aquascapeId.toString())
                }
                findNavController().navigate(R.id.action_resultFragment_to_historyFragment, bundle)
            }
        })
    }

    private fun checkParameter(style: String?) {

        if (temperature.isNullOrEmpty() || ph.isNullOrEmpty() || ammonia.isNullOrEmpty() || kh.isNullOrEmpty() || gh.isNullOrEmpty()) {
            return
        }

        val temperature = temperature?.toDouble()
        val ph = ph?.toDouble()
        val ammonia = ammonia?.toDouble()
        val kh = kh?.toDouble()
        val gh = gh?.toDouble()

        val fuzzyDutchStyle = FuzzyDutchStyle(requireContext())
        val fuzzyNaturalStyle = FuzzyNaturalStyle(requireContext())

        val good = getString(R.string.good)
        val medium = getString(R.string.medium)

        var tips = ""

        if (style == "Dutch Style") {
            val checkResult = fuzzyDutchStyle.checkParameter(temperature!!, ph!!, ammonia!!, kh!!, gh!!)
            binding.tvTemperature.text = "${checkResult.temperature} - $temperature"
            binding.tvPh.text = "${checkResult.ph} - $ph"
            binding.tvAmmonia.text = "${checkResult.ammonia} - $ammonia"
            binding.tvKh.text = "${checkResult.kh} - $kh"
            binding.tvGh.text = "${checkResult.gh} - $gh"

            tips += when (checkResult.temperature) {
                good -> {
                    " - Selalu jaga suhu air"
                }
                else -> " - Tambah kipas kecil untuk mendinginkan air"
            }

            tips += when (checkResult.ph) {
                good -> {
                    " - Selalu jaga PH optimal air"
                }
                medium -> {
                    " - Selalu jaga PH di batas optimal"
                }
                else -> " - Ganti sumber air untuk menyesuaikan PH atau injeksikan CO2 tambahan"
            }

            tips += when (checkResult.ammonia) {
                good -> {
                    " - Selalu jaga kadar ammonia air"
                }
                else -> " - Segera lakukan pergantian air dan bersihkan kotoran penyebab ammonia"
            }

            tips += when (checkResult.kh) {
                good -> {
                    " - Selalu jaga kadar KH air"
                }
                medium -> {
                    " - Selalu jaga kadar KH di batas optimal"
                }
                else -> " - Ganti sumber air untuk mengurangi kadar KH"
            }

            tips += when (checkResult.gh) {
                good -> {
                    " - Selalu jaga kadar GH air"
                }
                else -> " - Ganti sumber air untuk mengurangi kadar GH"
            }

            binding.tvDetailTips.text = tips
        } else {
            val checkResult = fuzzyNaturalStyle.checkParameter(temperature!!, ph!!, ammonia!!, kh!!, gh!!)
            binding.tvTemperature.text = "${checkResult.temperature} - $temperature"
            binding.tvPh.text = "${checkResult.ph} - $ph"
            binding.tvAmmonia.text = "${checkResult.ammonia} - $ammonia"
            binding.tvKh.text = "${checkResult.kh} - $kh"
            binding.tvGh.text = "${checkResult.gh} - $gh"

            tips += when (checkResult.temperature) {
                good -> {
                    " - Selalu jaga suhu air"
                }
                else -> " - Tambah kipas kecil untuk mendinginkan air"
            }

            tips += when (checkResult.ph) {
                good -> {
                    "- Selalu jaga PH optimal air"
                }
                medium -> {
                    "- Selalu jaga PH di batas optimal"
                }
                else -> "- Ganti sumber air untuk menyesuaikan PH atau injeksikan CO2 tambahan"
            }

            tips += when (checkResult.ammonia) {
                good -> {
                    " - Selalu jaga kadar ammonia air"
                }
                else -> " - Segera lakukan pergantian air dan bersihkan kotoran penyebab ammonia"
            }

            tips += when (checkResult.kh) {
                good -> {
                    " - Selalu jaga kadar KH air"
                }
                medium -> {
                    " - Selalu jaga kadar KH di batas optimal"
                }
                else -> " - Ganti sumber air untuk mengurangi kadar KH"
            }

            tips += when (checkResult.gh) {
                good -> {
                    " - Selalu jaga kadar GH air"
                }
                else -> " - Ganti sumber air untuk mengurangi kadar GH"
            }

            binding.tvDetailTips.text = tips
        }
    }

}
