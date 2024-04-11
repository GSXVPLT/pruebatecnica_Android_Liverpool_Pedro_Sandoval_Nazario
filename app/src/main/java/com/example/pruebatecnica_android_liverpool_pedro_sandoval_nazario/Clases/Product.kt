package com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.Clases

import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("status") val status: String,
    @SerializedName("statusCode") val statusCode: Int
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
data class VariantsColor(
    @SerializedName("colorName") val colorName: String,
    @SerializedName("colorHex") val colorHex: String,
    @SerializedName("colorImageURL") val colorImageURL: String,
    @SerializedName("colorMainURL") val colorMainURL: String,
    @SerializedName("skuId") val skuId: String,
    @SerializedName("enableTryOn") val enableTryOn: Double,
)

data class RecommendedItem(
    @SerializedName("productDisplayName") val displayName: String,
    @SerializedName("smImage") val largeImage: String,
    @SerializedName("productId") val productId: String,
    @SerializedName("listPrice") val listPrice: Double,
    @SerializedName("promoPrice") val promoPrice: Double,
    @SerializedName("salePrice") val salePrice: Double,
    @SerializedName("groupType") val groupType: String,
    @SerializedName("isMarketPlace") val isMarketPlace: Boolean,
    @SerializedName("brand") val brand: String,
    @SerializedName("seller") val seller: String,
    @SerializedName("category") val category: String,
    @SerializedName("minimumListPrice") val minimumListPrice: Double,
    @SerializedName("maximumListPrice") val maximumListPrice: Double,
    @SerializedName("minimumPromoPrice") val minimumPromoPrice: Double,
    @SerializedName("maximumPromoPrice") val maximumPromoPrice: Double,
    @SerializedName("variantsColor") val variantsColor: List<VariantsColor>?,
    val colorVariants: MutableList<String> = mutableListOf()
)


data class CarouselContent(
    @SerializedName("moreLinkCategory") val moreLinkCategory: String?,
    @SerializedName("minNumRecords") val minNumRecords: Int?,
    @SerializedName("maximumNumRecords") val maximumNumRecords: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("recommendedItems") val recommendedItems: List<RecommendedItem>?
)

data class PlpResults(
    @SerializedName("plpState") val plpState: PlpState,
    @SerializedName("records") val records: List<RecommendedItem>?,
    @SerializedName("carouselContent") val carouselContent: CarouselContent?
)

data class ApiResponse(
    @SerializedName("status") val status: Status,
    @SerializedName("pageType") val pageType: String,
    @SerializedName("plpResults") val plpResults: PlpResults,
    @SerializedName("nullSearch") val nullSearch: String,
    @SerializedName("nullPageContent") val nullPageContent: List<NullPageContent>
)

data class NullPageContent(
    @SerializedName("carouselContent") val carouselContent: CarouselContent?
)
