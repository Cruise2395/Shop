package com.example.shop.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyShopService {

    @GET("search")
    suspend fun findShopByName(@Query("q") query: String, @Query("limit") limit: Int= 0): ProductResponse

    @GET("{id}")
    suspend fun findProductById(@Path("{id}") id: Int): Product


    companion object{
        fun getInstance(): MyShopService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://dummyjson.com/products/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(MyShopService::class.java)
        }
    }
}
