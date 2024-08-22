package com.capstone.aquacare.ui.home

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
import com.capstone.aquacare.data.AquascapeData
import com.capstone.aquacare.data.ArticleData
import com.capstone.aquacare.data.Repository
import com.capstone.aquacare.databinding.FragmentHomeBinding
import com.capstone.aquacare.viewModel.DataViewModel
import com.capstone.aquacare.viewModel.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var dataViewModel: DataViewModel

    private val listAquascape = mutableListOf<AquascapeData>()
    private val listArticle = mutableListOf<ArticleData>()

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
            listAquascape.clear()
            for (data in aquascape) {
                listAquascape.add(data)
            }
            showAquascape()
        })

        dataViewModel.isLoadingB.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.pbArticle.visibility = View.VISIBLE
                binding.rvAquascapeInfo.visibility = View.GONE
            } else {
                binding.rvAquascapeInfo.visibility = View.VISIBLE
                binding.pbArticle.visibility = View.GONE
            }
        })

        dataViewModel.articleData.observe(viewLifecycleOwner, Observer { article ->
            listArticle.clear()
            for (data in article) {
                listArticle.add(data)
            }
            showArticle()
        })

        dataViewModel.getAquascapeData(userId)
        dataViewModel.getArticleData()
    }

    private fun showAquascape() {
        val adapter = AquascapeAdapter(listAquascape)
        binding.rvListAquascape.adapter = adapter
        Log.d("DataList", "Jumlah data dalam list aquascape: ${listAquascape.size}")

        adapter.setOnItemClickCallBack(object : AquascapeAdapter.OnItemClickCallback{
            override fun onItemClicked(data: AquascapeData) {

                val bundle = Bundle().apply {
                    putString("aquascapeId", data.id)
                }

                findNavController().navigate(R.id.action_homeFragment_to_historyFragment, bundle)

            }
        })
    }

    private fun showArticle() {
        val adapter = ArticleAdapter(listArticle)
        binding.rvAquascapeInfo.adapter = adapter
        Log.d("DataList", "Jumlah data dalam list aquascape info: ${listArticle.size}")

        adapter.setOnItemClickCallBack(object : ArticleAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ArticleData) {

                val bundle = Bundle().apply {
                    putString("infoId", data.id)
                    putString("title", data.title)
                    putString("image", data.image)
                    putString("body", data.body)
                    putString("link", data.link)
                    putString("edit", "false")
                }

                findNavController().navigate(R.id.action_homeFragment_to_AquascapeInfoFragment, bundle)
            }
        })
    }
}