package com.fetchrewards.codingexercise

import android.app.Application
import com.fetchrewards.codingexercise.repository.Repository
import com.fetchrewards.codingexercise.repository.remoteDataSource.Endpoints
import com.fetchrewards.codingexercise.repository.remoteDataSource.RemoteDataSource
import com.fetchrewards.codingexercise.ui.MainViewModelFactory
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application(), DIAware {

    override val di = DI.lazy {

        bindSingleton<Retrofit> {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build()
        }

        bindSingleton {
            val retrofit = instance<Retrofit>().create(Endpoints::class.java)
            RemoteDataSource(retrofit)
        }

        bindSingleton {
            Repository(Dispatchers.IO, instance())
        }

        bindProvider { MainViewModelFactory(instance()) }
    }
}
