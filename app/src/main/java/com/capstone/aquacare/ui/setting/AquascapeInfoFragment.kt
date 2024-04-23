package com.capstone.aquacare.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.capstone.aquacare.R
import com.capstone.aquacare.databinding.FragmentAquascapeInfoBinding

class AquascapeInfoFragment : Fragment() {

    private var _binding: FragmentAquascapeInfoBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAquascapeInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val infoId = arguments?.getString("infoId").toString()
        val title = arguments?.getString("title").toString()
        val image = arguments?.getString("image").toString()
        val body = arguments?.getString("body").toString()
        val edit = arguments?.getString("edit").toString()

        if (edit == "false") {
            binding.tvEdit.visibility = View.GONE
        }

        binding.tvTitle.text = title
        binding.tvBody.text = body
        Glide.with(requireActivity())
            .load(image)
            .into(binding.ivImage)

        binding.tvEdit.setOnClickListener {
            val bundle = Bundle().apply {
                putString("infoId", infoId)
                putString("title", title)
                putString("image", image)
                putString("body", body)
            }
            findNavController().navigate(R.id.action_AquascapeInfoFragment_to_editAquascapeInfoFragment, bundle)
        }
    }

}