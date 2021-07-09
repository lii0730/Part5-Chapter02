package com.example.aop_part5_chapter02.presentation.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aop_part5_chapter02.R
import com.example.aop_part5_chapter02.data.entity.product.ProductEntity
import com.example.aop_part5_chapter02.databinding.ProductRecyclerviewItemBinding

class ProductListAdapter(val onProductItemClicked: (ProductEntity) -> Unit) :
	ListAdapter<ProductEntity, ProductListAdapter.ViewHolder>(differ) {

	inner class ViewHolder(
		private val binding: ProductRecyclerviewItemBinding
	) : RecyclerView.ViewHolder(binding.root) {

		fun bind(item: ProductEntity) = with(binding) {
			productNameTextView.text = item.product_name
			productPriceTextView.text = "${item.productPrice}원"
			Glide.with(productImageView)
				.load(item.productImage)
				.centerCrop()
				.into(productImageView)

			binding.root.setOnClickListener {
				onProductItemClicked(item)
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(
			ProductRecyclerviewItemBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			)
		)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(currentList[position])
	}

	companion object {
		val differ = object : DiffUtil.ItemCallback<ProductEntity>() {
			override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
				return oldItem.id == newItem.id
			}

			override fun areContentsTheSame(
				oldItem: ProductEntity,
				newItem: ProductEntity
			): Boolean {
				return oldItem == newItem
			}
		}
	}
}