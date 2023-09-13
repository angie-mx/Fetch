package com.fetchrewards.codingexercise.repository.remoteDataSource

import retrofit2.http.GET

interface Endpoints {

    @GET("hiring.json")
    suspend fun getHirings(): List<ApiHiring>
}