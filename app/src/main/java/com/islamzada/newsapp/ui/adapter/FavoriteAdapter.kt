package com.islamzada.newsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.islamzada.newsapp.data.model.Favorite
import com.islamzada.newsapp.databinding.ItemFavBinding

class FavoriteAdapter (var mContext: Context, var favList: List<Favorite>, /*var viewModel: FavouriteViewModel*/)
    : RecyclerView.Adapter<FavoriteAdapter.CardDesignHolder>() {

        inner class CardDesignHolder(var binding: ItemFavBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignHolder {
            val binding = ItemFavBinding.inflate(LayoutInflater.from(mContext), parent, false)
            return CardDesignHolder(binding)
        }

        override fun onBindViewHolder(holder: CardDesignHolder, position: Int) {
            val item = favList[position]
            val b = holder.binding


        }

        override fun getItemCount(): Int {
            return favList.size
        }
    }