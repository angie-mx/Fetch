package com.fetchrewards.codingexercise.repository.remoteDataSource

import com.fetchrewards.codingexercise.extension.formatResult
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class RemoteDataSource(
    private val endpoints: Endpoints
) {
    suspend fun getHirings() = try {
        success(endpoints.getHirings().formatResult())
    } catch (exception: Exception) {
        failure(exception)
    }
}
