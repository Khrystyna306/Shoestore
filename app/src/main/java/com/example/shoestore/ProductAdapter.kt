package com.example.shoestore

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_product, null)
            val etName = dialogView.findViewById<EditText>(R.id.etName)
            val etPrice = dialogView.findViewById<EditText>(R.id.etPrice)
            val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)

            // Встановлюємо поточні значення товару
            etName.setText(product.name)
            etPrice.setText(product.price.toString())
            etDescription.setText(product.description)

            val dialog = androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle("Редагувати товар")
                .setView(dialogView)
                .setPositiveButton("Зберегти") { _, _ ->
                    // Зчитуємо нові значення
                    val newName = etName.text.toString()
                    val newPrice = etPrice.text.toString().toDoubleOrNull() ?: product.price
                    val newDescription = etDescription.text.toString()

                    // Оновлюємо товар
                    products[position] = product.copy(
                        name = newName,
                        price = newPrice,
                        description = newDescription
                    )
                    notifyItemChanged(position)
                    Toast.makeText(context, "Товар оновлено", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Скасувати", null)
                .create()

            dialog.show()
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