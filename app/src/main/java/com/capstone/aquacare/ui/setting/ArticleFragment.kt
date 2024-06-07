package com.capstone.aquacare.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.capstone.aquacare.R
import com.capstone.aquacare.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
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
        val edit = arguments?.getString("edit").toString()

        val webView = binding.webView

        if (edit == "false") {
            binding.tvEdit.visibility = View.GONE
        }

        if (link.isNotEmpty()) {
            binding.tvTitle.visibility = View.GONE
            binding.ivImage.visibility = View.GONE
            binding.tvBody.visibility = View.GONE

            webView.apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
                settings.cacheMode = WebSettings.LOAD_NO_CACHE
                settings.domStorageEnabled = true
                settings.databaseEnabled = true
                setLayerType(WebView.LAYER_TYPE_HARDWARE, null) // atau WebView.LAYER_TYPE_SOFTWARE
            }

            webView.loadUrl(link)
        } else {
            webView.visibility = View.GONE
        }

        binding.tvTitle.text = title
        binding.tvBody.text = body
        if (image.isNotEmpty()) {
            Glide.with(requireActivity())
                .load(image)
                .into(binding.ivImage)
        } else {
            binding.ivImage.visibility = View.GONE
        }

        binding.tvEdit.setOnClickListener {
            val bundle = Bundle().apply {
                putString("infoId", infoId)
                putString("title", title)
                putString("image", image)
                putString("type", type)
                putString("body", body)
                putString("link", link)
            }
            findNavController().navigate(R.id.action_AquascapeInfoFragment_to_editAquascapeInfoFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Cleanup the WebView to prevent memory leaks
        val webView = binding.webView
        webView.apply {
            loadUrl("about:blank")
            stopLoading()
            settings.javaScriptEnabled = false
            clearHistory()
            removeAllViews()
            destroy()
        }
    }

}