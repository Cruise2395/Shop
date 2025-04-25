package com.example.shop.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shop.R
import com.example.shop.databinding.ItemProductBinding
import com.example.shop.data.Product
import com.squareup.picasso.Picasso

class ProductAdapter(var items: List<Product>, val onClick: (Int) -> Unit) : Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = items[position]
        holder.render(product)
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }
}

class ProductViewHolder(val binding: ItemProductBinding) : ViewHolder(binding.root) {

    fun render (product: Product) {
        binding.nameTextView.text = product.title
        Picasso.get().load(product.image).into(binding.productImageView)
    }
}