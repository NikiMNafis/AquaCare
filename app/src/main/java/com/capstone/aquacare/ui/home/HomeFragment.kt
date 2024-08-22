package com.capstone.aquacare.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.aquacare.R
import com.capstone.aquacare.data.AquascapeData
import com.capstone.aquacare.data.ArticleData
import com.capstone.aquacare.data.Repository
import com.capstone.aquacare.databinding.FragmentHomeBinding
import com.capstone.aquacare.viewModel.DataViewModel
import com.capstone.aquacare.viewModel.ViewModelFactory
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var databaseArticleReference: DatabaseReference

    private lateinit var dataViewModel: DataViewModel

    val list = mutableListOf<AquascapeData>()
    val listInfo = mutableListOf<ArticleData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")
        databaseArticleReference = firebaseDatabase.reference.child("article")
    }

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

        val repository = Repository()
        dataViewModel = ViewModelProvider(this, ViewModelFactory(repository))[DataViewModel::class.java]

        dataViewModel.isLoadingA.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.pbAquascape.visibility = View.VISIBLE
                binding.rvListAquascape.visibility = View.GONE
            } else {
                binding.pbAquascape.visibility = View.GONE
                binding.rvListAquascape.visibility = View.VISIBLE
            }
        })

        dataViewModel.aquascapeData.observe( viewLifecycleOwner, Observer { aquascape ->
            list.clear()
            for (data in aquascape) {
                list.add(data)
            }
            showAquascape()
        })
        dataViewModel.getAquascapeData(userId)

        getAquascapeInfoData()
    }

    private fun getAquascapeInfoData() {
        binding.pbArticle.visibility = View.VISIBLE
        binding.rvAquascapeInfo.visibility = View.GONE

        databaseArticleReference.addValueEventListener(object : ValueEventListener {
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

        adapter.setOnItemClickCallBack(object : AquascapeAdapter.OnItemClickCallback{
            override fun onItemClicked(data: AquascapeData) {

                val aquascapeId = data.id

                val bundle = Bundle().apply {
                    putString("aquascapeId", aquascapeId)
                }

                findNavController().navigate(R.id.action_homeFragment_to_historyFragment, bundle)

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
            }
        })
    }

    private fun showArticle() {
        val adapter = ArticleAdapter(listInfo)
        binding.rvAquascapeInfo.adapter = adapter
        Log.d("DataList", "Jumlah data dalam list aquascape info: ${listInfo.size}")

        adapter.setOnItemClickCallBack(object : ArticleAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ArticleData) {

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
                }

                findNavController().navigate(R.id.action_homeFragment_to_AquascapeInfoFragment, bundle)
            }
        })
    }
}