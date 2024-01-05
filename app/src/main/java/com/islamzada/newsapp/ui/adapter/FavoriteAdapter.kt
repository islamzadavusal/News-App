package com.islamzada.newsapp.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.islamzada.newsapp.R
import com.islamzada.newsapp.data.model.Favorite
import com.islamzada.newsapp.databinding.ItemFavBinding

class FavoriteAdapter (val context: Context, private var newsList: MutableList<Favorite>, var onDeleteClick: (Favorite) -> Unit
) : BaseAdapter() {

    private var filteredList: List<Favorite> = ArrayList()

    fun filterByName(name: String) {
        filteredList = if (name.isEmpty()) {
            newsList
        } else {
            newsList.filter { it.title!!.contains(name, true) }
        }
        notifyDataSetChanged()
    }

    fun addNewItem(new_newsList: List<Favorite>) {
        // Mevcut ürün listesini temizle ve yeni ürünleri ekleyerek güncelle
        newsList.clear()
        newsList.addAll(new_newsList)
        filterByName("")
    }

    override fun getCount(): Int {
        return filteredList.count()
    }

    override fun getItem(position: Int): Any {
        return filteredList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var newConvertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            val binding: ItemFavBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_fav,
                parent,
                false
            )

            newConvertView = binding.root
            holder = ViewHolder(binding, onDeleteClick, context)
            holder.bind(filteredList[position])

            newConvertView.tag = holder
        } else {

            holder = convertView.tag as ViewHolder

            holder.bind(filteredList[position])
        }

        return newConvertView!!
    }

    private class ViewHolder(
        var binding: ItemFavBinding,
        var onDeleteClick: (Favorite) -> Unit,
        var context: Context
    ) {
        fun bind(news: Favorite) {
            binding.newsTitle.text = news.title
            binding.newsDescription.text = news.description
            binding.newsUrl.text = createReadMoreLink(news.url)

            // Use Glide to load and display the image from the URL
            Glide.with(binding.newsImage)
                .load(news.imageUrl)
                .error(R.drawable.ic_news) // Placeholder image in case of an error
                .placeholder(R.drawable.ic_news) // Placeholder image while loading
                .into(binding.newsImage)

            binding.favorite = news

            binding.imageView2.setOnClickListener {
                Snackbar.make(it, "Do you want to delete ${news.title} ?", Snackbar.LENGTH_LONG)
                    .setAction("YES") {
                        onDeleteClick(binding.favorite as Favorite)
                    }.show()
            }

            // Open the URL when the "Read More" link is clicked
            binding.newsUrl.setOnClickListener {
                openUrlInBrowser(news.url)
            }
        }

        private fun createReadMoreLink(url: String): SpannableString {
            val spannableString = SpannableString("Click to Read More")
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    openUrlInBrowser(url)
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = true
                }
            }
            spannableString.setSpan(
                clickableSpan,
                0,
                spannableString.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return spannableString
        }

        private fun openUrlInBrowser(url: String) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }
    }
}