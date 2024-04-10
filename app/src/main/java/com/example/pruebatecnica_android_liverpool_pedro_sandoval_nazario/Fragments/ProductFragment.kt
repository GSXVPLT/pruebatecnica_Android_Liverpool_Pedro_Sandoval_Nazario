package com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.Adapters.ProductAdapter
import com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.Clases.ApiResponse
import com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.Clases.Product
import com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.Interfaz.ApiService
import com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

class ProductFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var apiService: ApiService
    private lateinit var searchView: EditText
    private lateinit var sortBySpinner: Spinner
    private val sortByOptions = listOf("Predefinida", "Menor Precio", "Mayor Precio", "Relevancia", "Lo Más Nuevo", "Calificaciones")
    private var currentPage = 1
    private var isLoading = false
    private var productList = mutableListOf<Product>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewProductos)
        searchView = view.findViewById(R.id.searchView)
        sortBySpinner = view.findViewById(R.id.sortBySpinner)
        adapter = ProductAdapter()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        setupSortBySpinner()
        apiService = createProductService()
        fetchData("")
        setupPagination()
        setupSearch()
    }

    private fun createProductService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://shoppapp.liverpool.com.mx/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    private fun fetchData(searchString: String) {
        isLoading = true
        val call: Call<ApiResponse> = apiService.getData(currentPage, searchString)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null) {
                        apiResponse.plpResults.records?.let { products ->
                            productList.addAll(products)
                            adapter.addProducts(products)
                            currentPage++
                        } ?: run {
                            Log.e("PruebaError", "La lista de productos está vacía")
                        }
                    } else {
                        Log.e("PruebaError", "Respuesta nula del servidor")
                    }
                } else {
                    val errorMessage = "Error al obtener los productos. Código de error: ${response.code()}"
                    Log.e("PruebaError", errorMessage)
                }
                isLoading = false
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                val errorMessage = "Error de red al obtener los productos: ${t.message}"
                Log.e("PruebaError", errorMessage)
                isLoading = false
            }
        })
    }

    private fun setupPagination() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    fetchData("")
                }
            }
        })
    }

    private fun setupSearch() {
        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterProducts(s.toString().trim().toLowerCase(Locale.getDefault()))
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterProducts(searchText: String) {
        val filteredList = productList.filter { product ->
            product.displayName?.toLowerCase(Locale.getDefault())?.contains(searchText) ?: false
        }
        adapter.setProducts(filteredList)
    }

    private fun setupSortBySpinner() {
        val sortByAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, sortByOptions)
        sortBySpinner.adapter = sortByAdapter
        sortBySpinner.setSelection(0)

        sortBySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                fetchData("")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}
