package com.capstone.aquacare.ui.article

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.capstone.aquacare.R
import com.capstone.aquacare.data.ArticleData
import com.capstone.aquacare.data.Repository
import com.capstone.aquacare.databinding.FragmentEditArticleBinding
import com.capstone.aquacare.viewModel.ArticleViewModel
import com.capstone.aquacare.viewModel.ViewModelFactory
import com.google.firebase.database.*

class EditArticleFragment : Fragment() {

    private var _binding: FragmentEditArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    private lateinit var articleViewModel: ArticleViewModel

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
        _binding = FragmentEditArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val infoId = arguments?.getString("infoId").toString()
        val title = arguments?.getString("title").toString()
        val image = arguments?.getString("image").toString()
        val type = arguments?.getString("type").toString()
        val body = arguments?.getString("body").toString()
        val link = arguments?.getString("link").toString()

        binding.edtTitle.setText(title)
        binding.edtImage.setText(image)
        binding.edtType.setText(type)
        binding.edtBody.setText(body)
        binding.edtLink.setText(link)

        binding.btnSave.setOnClickListener {
            if (checkForm()) {
                editArticle(infoId)
            }
        }

        binding.btnDelete.setOnClickListener {
            deleteArticle(infoId)
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

    private fun editArticle(id: String) {

        val title = binding.edtTitle.text.toString()
        val image = binding.edtImage.text.toString()
        val type = binding.edtType.text.toString()
        val body = binding.edtBody.text.toString()
        val link = binding.edtLink.text.toString()

        databaseReference.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val infoData = snapshot.getValue(ArticleData::class.java)
                        if (infoData != null) {

                            val updateData = mapOf("title" to title, "image" to image, "type" to type, "body" to body, "link" to link)

                            databaseReference.child(id).updateChildren(updateData)
                                .addOnSuccessListener {

                                    Toast.makeText(activity, getString(R.string.success_to_edit_aquascape_info), Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.action_editAquascapeInfoFragment_to_aquascapeInfoFragment)
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(activity, "Failed to Edit Aquascape Info: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
                } else {
                    Log.d(TAG, "No Aquascape Info data available")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun deleteArticle(id: String) {

        val repository = Repository()
        articleViewModel = ViewModelProvider(this, ViewModelFactory(repository))[ArticleViewModel::class.java]

        articleViewModel.deleteArticle(id)
        articleViewModel.isSuccessB.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_editAquascapeInfoFragment_to_aquascapeInfoFragment)

            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}