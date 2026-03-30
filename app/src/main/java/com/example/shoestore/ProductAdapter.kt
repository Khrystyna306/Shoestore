package com.example.shoestore

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
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

        // Клік на товар → переходить на DetailActivity
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("product", product)
            context.startActivity(intent)
        }

        // Редагувати
        holder.btnEdit.setOnClickListener {
            Toast.makeText(context, "Редагувати: ${product.name}", Toast.LENGTH_SHORT).show()
        }

        // Видалити
        holder.btnDelete.setOnClickListener {
            products.removeAt(position)
            notifyItemRemoved(position)
            Toast.makeText(context, "Видалено: ${product.name}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = products.size
}