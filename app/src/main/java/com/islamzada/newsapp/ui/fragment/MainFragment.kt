package com.islamzada.newsapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.viewModels
import com.islamzada.newsapp.databinding.FragmentMainBinding
import com.islamzada.newsapp.ui.adapter.MainAdapter
import com.islamzada.newsapp.ui.viewModel.MainViewModel
import com.islamzada.newsapp.utils.MyResponse
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var newsAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Görünümlerin yapılandırılması
        setupViews()

        // Haber verilerinin gözlemlenmesi
        observeNewsData()

        viewModel.getAllNotes()
    }

    private fun observeNewsData() {
        viewModel.newsData.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                MyResponse.Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                }
                MyResponse.Status.SUCCESS -> {
                    binding.loading.visibility = View.GONE
                    response.data?.articles?.let { newsAdapter.submitData(it) }
                }
                MyResponse.Status.ERROR -> {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupViews() {
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = newsAdapter
        }
    }
}

