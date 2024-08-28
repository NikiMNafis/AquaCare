package com.capstone.aquacare.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import android.widget.Toast
=======
import androidx.lifecycle.ViewModelProvider
>>>>>>> a11f989183b547098d3bbf9c53742786d6ba30af
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.aquacare.R
import com.capstone.aquacare.data.AquascapeData
import com.capstone.aquacare.data.ArticleData
<<<<<<< HEAD
import com.capstone.aquacare.databinding.FragmentHomeBinding
import com.google.android.play.integrity.internal.l
import com.google.firebase.database.*
=======
import com.capstone.aquacare.data.Repository
import com.capstone.aquacare.databinding.FragmentHomeBinding
import com.capstone.aquacare.viewModel.DataViewModel
import com.capstone.aquacare.viewModel.ViewModelFactory
>>>>>>> a11f989183b547098d3bbf9c53742786d6ba30af

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

<<<<<<< HEAD
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var databaseInfoReference: DatabaseReference

    val list = mutableListOf<AquascapeData>()
    val listInfo = mutableListOf<ArticleData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")
        databaseInfoReference = firebaseDatabase.reference.child("article")
    }
=======
    private lateinit var dataViewModel: DataViewModel

    private val listAquascape = mutableListOf<AquascapeData>()
    private val listArticle = mutableListOf<ArticleData>()
>>>>>>> a11f989183b547098d3bbf9c53742786d6ba30af

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val name = sharedPreferences?.getString("name", "")
        val userId = sharedPreferences?.getString("userId", "").toString()
        val hello = getString(R.string.hello)
        binding.tvName.text = "$hello, $name"

        val rvAquascape = binding.rvListAquascape
        val rvAquascapeInfo = binding.rvAquascapeInfo

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
        layoutManager.stackFromEnd = true

        rvAquascape.layoutManager = layoutManager
        rvAquascape.setHasFixedSize(true)

        val layoutManager2 = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        layoutManager2.stackFromEnd = true

        rvAquascapeInfo.layoutManager = layoutManager2
        rvAquascapeInfo.setHasFixedSize(true)

        binding.ivProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
        }

        binding.tvAdd.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_addAquascapeFragment)
        }

<<<<<<< HEAD
        getAquascapeData(userId)
        getAquascapeInfoData()
    }

    private fun getAquascapeData(userId: String) {
        binding.pbAquascape.visibility = View.VISIBLE
        binding.rvListAquascape.visibility = View.GONE

        val aquascapeReference = databaseReference.child(userId).child("aquascapes")

        aquascapeReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                for (snapshot in dataSnapshot.children) {
                    val dataAquascape = snapshot.getValue(AquascapeData::class.java)
                    if (dataAquascape != null) {
                        list.add(dataAquascape)
                    }
                }
                binding.pbAquascape.visibility = View.GONE
                binding.rvListAquascape.visibility = View.VISIBLE
                showAquascape()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to retrieve aquascape data: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getAquascapeInfoData() {
        binding.pbArticle.visibility = View.VISIBLE
        binding.rvAquascapeInfo.visibility = View.GONE

        databaseInfoReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listInfo.clear()
                for (snapshot in dataSnapshot.children) {
                    val dataAquascapeInfo = snapshot.getValue(ArticleData::class.java)
                    if (dataAquascapeInfo != null) {
                        listInfo.add(dataAquascapeInfo)
                    }
                }

                binding.rvAquascapeInfo.visibility = View.VISIBLE
                binding.pbArticle.visibility = View.GONE
                showArticle()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to retrieve aquascape info data: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showAquascape() {
        val adapter = AquascapeAdapter(list)
        binding.rvListAquascape.adapter = adapter
        Log.d("DataList", "Jumlah data dalam list aquascape: ${list.size}")
=======
        val repository = Repository()
        dataViewModel = ViewModelProvider(this, ViewModelFactory(repository))[DataViewModel::class.java]

        dataViewModel.isLoadingA.observe(viewLifecycleOwner) {
            if (it) {
                binding.pbAquascape.visibility = View.VISIBLE
                binding.rvListAquascape.visibility = View.GONE
            } else {
                binding.pbAquascape.visibility = View.GONE
                binding.rvListAquascape.visibility = View.VISIBLE
            }
        }

        dataViewModel.aquascapeData.observe( viewLifecycleOwner) { aquascape ->
            listAquascape.clear()
            for (data in aquascape) {
                listAquascape.add(data)
            }
            showAquascape()
        }

        dataViewModel.isLoadingB.observe(viewLifecycleOwner) {
            if (it) {
                binding.pbArticle.visibility = View.VISIBLE
                binding.rvAquascapeInfo.visibility = View.GONE
            } else {
                binding.rvAquascapeInfo.visibility = View.VISIBLE
                binding.pbArticle.visibility = View.GONE
            }
        }

        dataViewModel.articleData.observe(viewLifecycleOwner) { article ->
            listArticle.clear()
            for (data in article) {
                listArticle.add(data)
            }
            showArticle()
        }

        dataViewModel.getAquascapeData(userId)
        dataViewModel.getArticleData()
    }

    private fun showAquascape() {
        val adapter = AquascapeAdapter(listAquascape)
        binding.rvListAquascape.adapter = adapter
        Log.d("DataList", "Jumlah data dalam list aquascape: ${listAquascape.size}")
>>>>>>> a11f989183b547098d3bbf9c53742786d6ba30af

        adapter.setOnItemClickCallBack(object : AquascapeAdapter.OnItemClickCallback{
            override fun onItemClicked(data: AquascapeData) {

<<<<<<< HEAD
                val aquascapeId = data.id

                val bundle = Bundle().apply {
                    putString("aquascapeId", aquascapeId)
=======
                val bundle = Bundle().apply {
                    putString("aquascapeId", data.id)
>>>>>>> a11f989183b547098d3bbf9c53742786d6ba30af
                }

                findNavController().navigate(R.id.action_homeFragment_to_historyFragment, bundle)

<<<<<<< HEAD
//                    val identificationHistoryFragment = IdentificationHistoryFragment()
//                    identificationHistoryFragment.arguments = bundle
//                    val fragmentManager = parentFragmentManager
//                    fragmentManager.beginTransaction().apply {
//                        replace(
//                            R.id.main_frame_container,
//                            identificationHistoryFragment,
//                            IdentificationHistoryFragment::class.java.simpleName
//                        )
//                        addToBackStack(null)
//                        commit()
//                    }
=======
>>>>>>> a11f989183b547098d3bbf9c53742786d6ba30af
            }
        })
    }

    private fun showArticle() {
<<<<<<< HEAD
        val adapter = ArticleAdapter(listInfo)
        binding.rvAquascapeInfo.adapter = adapter
        Log.d("DataList", "Jumlah data dalam list aquascape info: ${listInfo.size}")
=======
        val adapter = ArticleAdapter(listArticle)
        binding.rvAquascapeInfo.adapter = adapter
        Log.d("DataList", "Jumlah data dalam list aquascape info: ${listArticle.size}")
>>>>>>> a11f989183b547098d3bbf9c53742786d6ba30af

        adapter.setOnItemClickCallBack(object : ArticleAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ArticleData) {

<<<<<<< HEAD
                val infoId = data.id
                val title = data.title
                val image = data.image
                val body = data.body
                val link = data.link
                val edit = "false"

                val bundle = Bundle().apply {
                    putString("infoId", infoId)
                    putString("title", title)
                    putString("image", image)
                    putString("body", body)
                    putString("link", link)
                    putString("edit", edit)
=======
                val bundle = Bundle().apply {
                    putString("infoId", data.id)
                    putString("title", data.title)
                    putString("image", data.image)
                    putString("body", data.body)
                    putString("link", data.link)
                    putString("edit", "false")
>>>>>>> a11f989183b547098d3bbf9c53742786d6ba30af
                }

                findNavController().navigate(R.id.action_homeFragment_to_AquascapeInfoFragment, bundle)
            }
        })
    }
}