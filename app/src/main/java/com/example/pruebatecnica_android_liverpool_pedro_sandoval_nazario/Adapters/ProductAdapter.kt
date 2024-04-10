package com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.Adapters

import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.Clases.Product
import com.squareup.picasso.Picasso
import com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.R

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val productList = mutableListOf<Product>()

    fun setProducts(products: List<Product>) {
        productList.clear()
        productList.addAll(products)
        notifyDataSetChanged()
    }

    fun addProducts(newProducts: List<Product>) {
        productList.addAll(newProducts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_inflate, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewProducto: ImageView = itemView.findViewById(R.id.imageViewProducto)
        private val textViewDescripcion: TextView = itemView.findViewById(R.id.textViewDescripcion)
        private val textViewPrecioSinDescuento: TextView = itemView.findViewById(R.id.textViewPrecioSinDescuento)
        private val textViewPrecioConDescuento: TextView = itemView.findViewById(R.id.textViewPrecioConDescuento)
        private val layoutVariantesColor: LinearLayout = itemView.findViewById(R.id.layoutVariantesColor)

        fun bind(product: Product) {
            Picasso.get()
                .load(product.largeImage)
                .placeholder(R.drawable.item_producto_inflate   )
                .into(imageViewProducto)

            textViewDescripcion.text = product.displayName

            if (product.promoPrice != null && product.promoPrice != 0.0) {
                textViewPrecioConDescuento.visibility = View.VISIBLE
                textViewPrecioConDescuento.text = "$${product.promoPrice}"
            } else {
                textViewPrecioConDescuento.visibility = View.GONE
            }

            if (product.salePrice != product.promoPrice) {
                textViewPrecioSinDescuento.visibility = View.VISIBLE
                textViewPrecioSinDescuento.text = "$${product.salePrice}"
                textViewPrecioSinDescuento.paintFlags = if (product.promoPrice != null && product.promoPrice != 0.0) {
                    textViewPrecioSinDescuento.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    textViewPrecioSinDescuento.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            } else {
                textViewPrecioSinDescuento.visibility = View.GONE
            }

            layoutVariantesColor.removeAllViews()

            /*if (product.colorVariants.isNotEmpty()) {
                for (color in product.colorVariants) {
                    val colorCircle = createColorCircle(color)
                    layoutVariantesColor.addView(colorCircle)
                }
            }
            */
        }

        private fun createColorCircle(color: String): View {
            val circle = View(itemView.context)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.width = 40 // Ancho del círculo
            params.height = 40 // Alto del círculo
            params.marginEnd = 8 // Margen entre círculos
            circle.layoutParams = params
            circle.background = createCircleBackground(color)
            return circle
        }
        private fun createCircleBackground(color: String): Drawable {
            val drawable = ContextCompat.getDrawable(itemView.context, R.drawable.circle_background)
            drawable?.let {
                val shape = it.mutate() as GradientDrawable
                shape.setColor(Color.parseColor(color))
                return shape
            }
            return ContextCompat.getDrawable(itemView.context, R.drawable.circle_background)!!
        }
    }

}
