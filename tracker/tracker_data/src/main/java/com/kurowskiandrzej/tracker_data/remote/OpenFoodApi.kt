package com.kurowskiandrzej.tracker_data.remote

import com.kurowskiandrzej.tracker_data.remote.dto.SearchDto
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface OpenFoodApi {

    @GET("cgi/search.pl?search_simple=1&json=1&action=process&fields=product_name,nutriments,image_front_thumb_url")
    suspend fun searchFood(
        @Query("search_terms") query: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): SearchDto

    companion object {
        fun getLocalizedUrl(locale:String): String {
            var countryUrl = "us"
            if (locale.lowercase() == "pl") countryUrl = "pl"
            return "https://$countryUrl.openfoodfacts.org/"
        }
    }
}