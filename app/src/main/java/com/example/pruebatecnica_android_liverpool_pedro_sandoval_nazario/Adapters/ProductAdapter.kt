package com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.Adapters

import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.Clases.RecommendedItem
import com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.Clases.VariantsColor
import com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.R
import com.squareup.picasso.Picasso

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val productList = mutableListOf<RecommendedItem>()

    fun setProducts(products: List<RecommendedItem>) {
        productList.clear()
        productList.addAll(products)
        notifyDataSetChanged()
    }

    fun addProducts(newProducts: List<RecommendedItem>) {
        productList.addAll(newProducts)
        notifyDataSetChanged()
    }

    fun addVariantInfo(variantInfo: VariantsColor) {
        val productToUpdate = productList.find { it.productId == variantInfo.skuId }
        productToUpdate?.let { product ->
            product.colorVariants?.let { colorVariants ->
                if (!colorVariants.contains(variantInfo.colorHex)) {
                    colorVariants.add(variantInfo.colorHex)
                    Log.e("PruebaInfo", "Se agregó colorHex: ${variantInfo.colorHex} al producto ${product.displayName}")
                    notifyDataSetChanged()
                } else {
                    Log.e("PruebaInfo", "El colorHex: ${variantInfo.colorHex} ya existe en el producto ${product.displayName}")
                }
            }
        }
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

        fun bind(product: RecommendedItem) {
            Picasso.get()
                .load(product.largeImage)
                .placeholder(R.drawable.item_producto_inflate)
                .into(imageViewProducto)

            textViewDescripcion.text = product.displayName

            if (product.promoPrice != null && product.promoPrice != 0.0) {
                textViewPrecioConDescuento.visibility = View.VISIBLE
                textViewPrecioConDescuento.text = "$${product.promoPrice}"

                if (product.listPrice == product.promoPrice) {
                    textViewPrecioSinDescuento.visibility = View.GONE
                } else {
                    textViewPrecioSinDescuento.visibility = View.VISIBLE
                    textViewPrecioSinDescuento.text = "$${product.listPrice}"
                    textViewPrecioSinDescuento.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }
            } else {
                textViewPrecioConDescuento.visibility = View.GONE
                textViewPrecioSinDescuento.visibility = View.VISIBLE
                textViewPrecioSinDescuento.text = "$${product.listPrice}"
                textViewPrecioSinDescuento.paintFlags = 0
            }

            layoutVariantesColor.removeAllViews()

            product.colorVariants?.forEach { color ->
                val colorCircle = createColorCircle(color)
                layoutVariantesColor.addView(colorCircle)
            }
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
            val drawable = GradientDrawable()
            drawable.shape = GradientDrawable.OVAL
            drawable.setColor(Color.parseColor(color))

            val sizeInDp = 40
            val scale = itemView.context.resources.displayMetrics.density
            val sizeInPixels = (sizeInDp * scale + 0.5f).toInt()
            drawable.setSize(sizeInPixels, sizeInPixels)

            return drawable
        }
    }
}
