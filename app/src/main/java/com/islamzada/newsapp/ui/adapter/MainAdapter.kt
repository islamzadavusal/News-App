package com.islamzada.newsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.islamzada.newsapp.R
import com.islamzada.newsapp.data.model.Article
import com.islamzada.newsapp.databinding.ItemNewsBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class MainAdapter @Inject constructor(@ActivityContext private val context: Context) :
    RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    private var newsList = emptyList<Article>()

    // Inner class: RecyclerView.ViewHolder'ın özelleştirilmiş sürümü
    inner class MyViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Verileri ViewHolder'a atayan fonksiyon
        fun setData(data: Article) {
            binding.apply {
                // Veri bağlama işlemleri
                newsTitle.text = data.title
                newsDescription.text = shortenString(data.description)
                loadImage(data.imageUrl, newsImage)
            }
        }
    }

    // ViewHolder'ı oluşturan fonksiyon
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // View oluşturucu
        val inflater = LayoutInflater.from(context)
        val binding = ItemNewsBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    // Veri setinin boyutunu döndüren fonksiyon
    override fun getItemCount(): Int {
        return newsList.size
    }

    // Belirli bir konumdaki öğenin türünü döndüren fonksiyon
    override fun getItemViewType(position: Int): Int {
        return position
    }

    // ViewHolder'ın içeriğini belirli bir konumdaki veriyle güncelleyen fonksiyon
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(newsList[position])
    }

    // Veri setini güncelleyen fonksiyon
    fun submitData(data: List<Article>) {
        val newsDiffUtil = NewsDiffUtils(newsList, data)
        val diffUtils = DiffUtil.calculateDiff(newsDiffUtil)
        newsList = data
        diffUtils.dispatchUpdatesTo(this)
    }

    // Resmi yükleyen Glide kütüphanesi kullanılan yardımcı fonksiyon
    private fun loadImage(imageUrl: String?, imageView: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .error(R.drawable.ic_news)
            .placeholder(R.drawable.ic_news)
            .into(imageView)
    }

    // String'i kısaltan yardımcı fonksiyon
    private fun shortenString(input: String?): String {
        return input?.let {
            if (it.length > 100) {
                val shortenedText = it.substring(0, 100)
                val lastSpaceIndex = shortenedText.lastIndexOf(' ')
                if (lastSpaceIndex != -1) {
                    "${shortenedText.substring(0, lastSpaceIndex)} ..."
                } else {
                    "$shortenedText ..."
                }
            } else {
                it
            }
        } ?: ""
    }

    // RecyclerView için DiffUtil sınıfı
    class NewsDiffUtils(
        private val oldItems: List<Article>,
        private val newItems: List<Article>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] === newItems[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}
