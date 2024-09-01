package com.capstone.aquacare.ui.article

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.capstone.aquacare.R
import com.capstone.aquacare.data.Repository
import com.capstone.aquacare.databinding.FragmentAddArticleBinding
import com.capstone.aquacare.viewModel.ArticleViewModel
import com.capstone.aquacare.viewModel.ViewModelFactory

class AddArticleFragment : Fragment() {

    private var _binding: FragmentAddArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var articleViewModel: ArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            if (checkForm()) {
                addArticle()
            }
        }
    }

    private fun checkForm(): Boolean {
        if (binding.edtTitle.text.isEmpty()) {
            binding.edtTitle.error = getString(R.string.please_fill)
            return false
        }

        if (binding.edtImage.text.isEmpty()) {
            binding.edtImage.error = getString(R.string.please_fill)
            return false
        }

        if (binding.edtBody.text.isEmpty()) {
            binding.edtBody.error = getString(R.string.please_fill)
            return false
        }

        if (binding.edtType.text.isEmpty()) {
            binding.edtType.error = getString(R.string.please_fill)
            return false
        }

        return true
    }

    private fun addArticle() {

        val title = binding.edtTitle.text.toString()
        val image = binding.edtImage.text.toString()
        val type = binding.edtType.text.toString()
        val body = binding.edtBody.text.toString()
        val link = binding.edtLink.text.toString()

        val repository = Repository()
        articleViewModel = ViewModelProvider(this, ViewModelFactory(repository))[ArticleViewModel::class.java]

        articleViewModel.addArticle(title, image, type, body, link)
        articleViewModel.isSuccessA.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(activity, getString(R.string.success_to_add_aquascape_info), Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addAquascapeInfoFragment_to_aquascapeInfoFragment)
            } else {
                Toast.makeText(activity, "Failed to add Article", Toast.LENGTH_SHORT).show()
            }
        }
    }
}