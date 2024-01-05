package com.islamzada.newsapp.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.islamzada.newsapp.R
import com.islamzada.newsapp.data.model.Article
import com.islamzada.newsapp.data.model.NewsResponse
import com.islamzada.newsapp.databinding.ItemNewsBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class MainAdapter (private val context: Context, var onFavIconClick: (Article) -> Unit) :
    RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    private var newsList = emptyList<Article>()
    private var filteredList = emptyList<Article>()

    // Inner class: RecyclerView.ViewHolder'ın özelleştirilmiş sürümü
    inner class MyViewHolder(private val binding: ItemNewsBinding,var onFavIconClick: (Article) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // "Read More" TextView'ına tıklama olayı ekle
            binding.newsUrl.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // "Read More" TextView'sına tıklandığında URL'yi aç
                    val url = filteredList[position].url
                    openUrlInBrowser(url)
                }
            }

            binding.imageFav.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val article = filteredList[position]
                    onFavIconClick(article)
                    Toast.makeText(context, "Add to Favorite", Toast.LENGTH_SHORT).show()
                }
            }
        }



        // Verileri ViewHolder'a atayan fonksiyon
        fun setData(data: Article) {
            binding.apply {
                // Veri bağlama işlemleri
                newsTitle.text = data.title
                newsDescription.text = shortenString(data.description)
                loadImage(data.imageUrl, newsImage)

                // "Read More" yazısını güncelle
                val spannableString = SpannableString("Click to Read More")
                val clickableSpan = object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        // "Read More" TextView'sına tıklandığında URL'yi aç
                        val position = adapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                            val url = filteredList[position].url
                            openUrlInBrowser(url)
                        }
                    }
                }
                spannableString.setSpan(clickableSpan, 0, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                newsUrl.text = spannableString
                newsUrl.movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

    // ViewHolder'ı oluşturan fonksiyon
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // View oluşturucu
        val inflater = LayoutInflater.from(context)
        val binding = ItemNewsBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding, onFavIconClick)
    }

    // Veri setinin boyutunu döndüren fonksiyon
    override fun getItemCount(): Int {
        return filteredList.size
    }

    // Belirli bir konumdaki öğenin türünü döndüren fonksiyon
    override fun getItemViewType(position: Int): Int {
        return position
    }

    // ViewHolder'ın içeriğini belirli bir konumdaki veriyle güncelleyen fonksiyon
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(filteredList[position])
    }

    // Veri setini güncelleyen fonksiyon
    fun submitData(data: List<Article>) {
        val newsDiffUtil = NewsDiffUtils(filteredList, data)
        val diffUtils = DiffUtil.calculateDiff(newsDiffUtil)
        newsList = data
        filteredList = data
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

    // Filtreleme işlemi yapan fonksiyon
    fun filterByName(name: String) {
        filteredList = if (name.isEmpty()) {
            newsList
        } else {
            newsList.filter { it.title.contains(name, true) }
        }
        notifyDataSetChanged()
    }

    private fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}


