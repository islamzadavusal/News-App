package com.islamzada.newsapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.viewModels
import com.islamzada.newsapp.databinding.FragmentMainBinding
import com.islamzada.newsapp.ui.adapter.MainAdapter
import com.islamzada.newsapp.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var newsAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
//        observeNewsData()
        viewModel.getAllNotes()
    }

//    private fun observeNewsData() {
//        viewModel.newsData.observe(viewLifecycleOwner) { response ->
//            when (response.status) {
//                LOADING -> {
//                    binding?.loading?.visibility = View.VISIBLE
//                }
//                SUCCESS -> {
//                    binding?.loading?.visibility = View.GONE
//                    response?.data?.articles?.let { newsAdapter.submitData(it) }
//                }
//                ERROR -> {
//                    binding?.loading?.visibility = View.GONE
//                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }

    private fun setupViews() {
        binding?.recyclerview?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = newsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
