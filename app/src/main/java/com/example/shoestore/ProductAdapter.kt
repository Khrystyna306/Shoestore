package com.example.shoestore

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private val context: Context,
    private val products: MutableList<Product>,
    private val onDataChanged: () -> Unit,
    private val onChangePhotoRequested: (position: Int) -> Unit
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

        // Фото
        if (!product.imageUri.isNullOrEmpty()) {
            holder.ivProduct.setImageURI(Uri.parse(product.imageUri))
        } else {
            holder.ivProduct.setImageResource(product.imageResId)
        }

        holder.tvName.text = product.name
        holder.tvPrice.text = "$${product.price}"
        holder.tvDescription.text = product.description

        // Деталі
        holder.itemView.setOnClickListener {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_product_detail, null)
            val tvName = dialogView.findViewById<TextView>(R.id.tvDetailName)
            val tvPrice = dialogView.findViewById<TextView>(R.id.tvDetailPrice)
            val tvDescription = dialogView.findViewById<TextView>(R.id.tvDetailDescription)
            val ivProduct = dialogView.findViewById<ImageView>(R.id.ivDetailProduct)

            tvName.text = product.name
            tvPrice.text = "$${product.price}"
            tvDescription.text = product.description
            if (!product.imageUri.isNullOrEmpty()) {
                ivProduct.setImageURI(Uri.parse(product.imageUri))
            } else {
                ivProduct.setImageResource(product.imageResId)
            }

            AlertDialog.Builder(context)
                .setTitle("Деталі товару")
                .setView(dialogView)
                .setPositiveButton("Ок", null)
                .show()
        }

        // Редагування
        holder.btnEdit.setOnClickListener {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_product, null)
            val etName = dialogView.findViewById<EditText>(R.id.etName)
            val etPrice = dialogView.findViewById<EditText>(R.id.etPrice)
            val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)
            val btnChangePhoto = dialogView.findViewById<Button>(R.id.btnChangePhoto)

            etName.setText(product.name)
            etPrice.setText(product.price.toString())
            etDescription.setText(product.description)

            btnChangePhoto.setOnClickListener {
                onChangePhotoRequested(position)
            }

            AlertDialog.Builder(context)
                .setTitle("Редагувати товар")
                .setView(dialogView)
                .setPositiveButton("Зберегти") { _, _ ->
                    product.name = etName.text.toString()
                    product.price = etPrice.text.toString().toDoubleOrNull() ?: product.price
                    product.description = etDescription.text.toString()
                    notifyItemChanged(position)
                    onDataChanged()
                }
                .setNegativeButton("Відміна", null)
                .show()
        }

        // Видалення
        holder.btnDelete.setOnClickListener {
            products.removeAt(position)
            notifyItemRemoved(position)
            onDataChanged()
        }
    }

    override fun getItemCount(): Int = products.size
}