package com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.Interfaz

import com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.Clases.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("appclienteservices/services/v7/plp/sf")
    fun searchProducts(
        @Query("page-number") pageNumber: Int,
        @Query("search-string") searchString: String,
        @Query("force-plp") forcePlp: Boolean = false,
        @Query("number-of-items-per-page") numberOfItemsPerPage: Int = 40,
        @Query("cleanProductName") cleanProductName: Boolean = false
    ): Call<List<Product>>
}


