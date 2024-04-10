package com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.Clases

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("status") val status: Status,
    @SerializedName("nullSearch") val nullSearch: Boolean,
    @SerializedName("nullPageContent") val nullPageContent: List<NullPageContent>,
    @SerializedName("plpResults") val plpResults: PlpResults
)

data class Status(
    @SerializedName("status") val status: String,
    @SerializedName("statusCode") val statusCode: Int
)

data class NullPageContent(
    val placeholder: String = ""
)

data class PlpResults(
    @SerializedName("plpState") val plpState: PlpState,
    @SerializedName("records") val records: List<Product>,
)

data class PlpState(
    @SerializedName("currentFilters") val currentFilters: String,
    @SerializedName("firstRecNum") val firstRecNum: Int,
    @SerializedName("lastRecNum") val lastRecNum: Int,
    @SerializedName("recsPerPage") val recsPerPage: Int,
    @SerializedName("totalNumRecs") val totalNumRecs: Int,
    @SerializedName("originalSearchTerm") val originalSearchTerm: String,
    @SerializedName("area") val area: String,
    @SerializedName("id") val id: String
)

data class Product(
    @SerializedName("displayName") val displayName: String?,
    @SerializedName("largeImage") val largeImage: String?,
    @SerializedName("productId") val productId: String,
    @SerializedName("listPrice") val listPrice: Double,
    @SerializedName("promoPrice") val promoPrice: Double,
    @SerializedName("salePrice") val salePrice: Double,
    @SerializedName("brand") val brand: String,
    @SerializedName("category") val category: String
)
