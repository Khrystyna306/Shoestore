package com.example.shoestore

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private val context: Context,
    private val products: MutableList<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProduct: ImageView = view.findViewById(R.id.ivProduct)
        val tvName: TextView = view.findViewById(R.id.tvProductName)
        val tvPrice: TextView = view.findViewById(R.id.tvProductPrice)
        val tvDescription: TextView = view.findViewById(R.id.tvProductDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.ivProduct.setImageResource(product.imageResId)
        holder.tvName.text = product.name
        holder.tvPrice.text = "$${product.price}"
        holder.tvDescription.text = product.description

        // Клік на фото — показуємо деталі через Toast
        holder.ivProduct.setOnClickListener {
            Toast.makeText(context, "${product.name}\n${product.description}\n$${product.price}", Toast.LENGTH_LONG).show()
        }

        // Клік на назву — редагування (приклад)
        holder.tvName.setOnClickListener {
            Toast.makeText(context, "Редагувати назву: ${product.name}", Toast.LENGTH_SHORT).show()
        }

        // Довге натискання — видалення
        holder.itemView.setOnLongClickListener {
            products.removeAt(position)
            notifyItemRemoved(position)
            Toast.makeText(context, "${product.name} видалено", Toast.LENGTH_SHORT).show()
            true
        }
    }

    override fun getItemCount(): Int = products.size
}