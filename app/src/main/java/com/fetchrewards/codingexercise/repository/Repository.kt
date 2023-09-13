package com.fetchrewards.codingexercise.repository

import com.fetchrewards.codingexercise.repository.remoteDataSource.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class Repository(
    private val ioDispatcher: CoroutineDispatcher,
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun loadHirings() = withContext(ioDispatcher) {
        remoteDataSource.getHirings()
    }
}
