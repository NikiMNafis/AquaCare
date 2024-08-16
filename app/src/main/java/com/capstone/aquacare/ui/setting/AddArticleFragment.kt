package com.capstone.aquacare.ui.setting

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.capstone.aquacare.R
import com.capstone.aquacare.data.ArticleData
import com.capstone.aquacare.databinding.FragmentAddArticleBinding
import com.google.firebase.database.*

class AddArticleFragment : Fragment() {

    private var _binding: FragmentAddArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("article")
    }

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
                addAquascapeInfo()
            }
        }

    }

    private fun checkForm(): Boolean {
        val title = binding.edtTitle.text.toString()
        val image = binding.edtImage.text.toString()
        val body = binding.edtBody.text.toString()
        val type = binding.edtType.text.toString()

        if (TextUtils.isEmpty(title)) {
            binding.edtTitle.error = getString(R.string.please_fill)
            return false
        }

        if (TextUtils.isEmpty(image)) {
            binding.edtImage.error = getString(R.string.please_fill)
            return false
        }

        if (TextUtils.isEmpty(body)) {
            binding.edtBody.error = getString(R.string.please_fill)
            return false
        }

        if (TextUtils.isEmpty(type)) {
            binding.edtType.error = getString(R.string.please_fill)
            return false
        }

        return true
    }

    private fun addAquascapeInfo() {

        val title = binding.edtTitle.text.toString()
        val image = binding.edtImage.text.toString()
        val type = binding.edtType.text.toString()
        val body = binding.edtBody.text.toString()
        val link = binding.edtLink.text.toString()

        val infoId = databaseReference.push().key

        if (infoId != null) {
            val infoData = ArticleData(infoId, title, image, type, body, link)
            databaseReference.child(infoId).setValue(infoData)
                .addOnSuccessListener {
                    Toast.makeText(activity, getString(R.string.success_to_add_aquascape_info), Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_addAquascapeInfoFragment_to_aquascapeInfoFragment)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(activity, "Failed to add Aquascape info: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(activity, "Failed to generate Aquascape Info ID", Toast.LENGTH_SHORT).show()
        }
    }
}