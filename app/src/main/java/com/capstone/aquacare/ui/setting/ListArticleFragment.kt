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
import com.capstone.aquacare.data.ArticleData
import com.capstone.aquacare.databinding.FragmentListArticleBinding
import com.google.firebase.database.*

class ListArticleFragment : Fragment() {

    private var _binding: FragmentListArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    val listInfo = mutableListOf<ArticleData>()

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
        _binding = FragmentListArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvAquascapeInfo = binding.rvAquascapeInfo

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        layoutManager.stackFromEnd = true

        rvAquascapeInfo.layoutManager = layoutManager
        rvAquascapeInfo.setHasFixedSize(true)

        binding.tvAddAquascapeInfo.setOnClickListener {
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
                    val dataAquascapeInfo = snapshot.getValue(ArticleData::class.java)
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
        val adapter = ArticleAdapter(listInfo)
        binding.rvAquascapeInfo.adapter = adapter
        Log.d("DataList", "Jumlah data dalam list aquascape info: ${listInfo.size}")

        adapter.setOnItemClickCallBack(object : ArticleAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ArticleData) {

                val infoId = data.id
                val title = data.title
                val image = data.image
                val type = data.type
                val body = data.body
                val link = data.link
                val edit = "true"

                val bundle = Bundle().apply {
                    putString("infoId", infoId)
                    putString("title", title)
                    putString("image", image)
                    putString("type", type)
                    putString("body", body)
                    putString("link", link)
                    putString("edit", edit)
                }

                findNavController().navigate(R.id.action_listAquascapeInfoFragment_to_AquascapeInfoFragment, bundle)
            }
        })
    }
}