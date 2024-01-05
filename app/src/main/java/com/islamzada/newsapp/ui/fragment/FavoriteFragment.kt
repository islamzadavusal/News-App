package com.islamzada.newsapp.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.islamzada.newsapp.databinding.FragmentFavoriteBinding
import com.islamzada.newsapp.ui.adapter.FavoriteAdapter
import com.islamzada.newsapp.ui.viewModel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var adapter: FavoriteAdapter

    private val favViewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {


        binding = FragmentFavoriteBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = favViewModel

        // ProductListAdapter nesnesini oluşturun ve ayarlayın
        adapter = FavoriteAdapter(requireContext(), mutableListOf(),

            // "onDeleteClick" fonksiyonu: Listedeki bir öğeyi sil
            onDeleteClick = { fav ->
                favViewModel.deleteFav(fav)
            }
        )

        binding.rvFav.adapter = adapter

        // ViewModel'den LiveData nesnesini alın ve nesneyi  obzerv etmek
        favViewModel.getAllDataFav().observe(viewLifecycleOwner, Observer { productList ->
            // Adaptöre yeni ürünler eklemek ve listeyi güncellemek
            adapter.addNewItem(productList)
        })

        binding.editTextSearchFav.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filterByName(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return binding.root
    }
}
