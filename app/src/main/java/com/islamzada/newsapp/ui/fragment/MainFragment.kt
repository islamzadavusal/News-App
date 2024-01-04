package com.islamzada.newsapp.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.islamzada.newsapp.utils.MyResponse.Status
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

        binding.editTextSearchMain.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                newsAdapter.filterByName(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

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
                Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.loading.visibility = View.GONE
                    response.data?.articles?.let { newsAdapter.submitData(it) }
                }
                Status.ERROR -> {
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

