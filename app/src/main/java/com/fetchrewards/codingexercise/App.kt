package com.fetchrewards.codingexercise

import android.app.Application
import com.fetchrewards.codingexercise.repository.Repository
import com.fetchrewards.codingexercise.repository.remoteDataSource.Endpoints
import com.fetchrewards.codingexercise.repository.remoteDataSource.RemoteDataSource
import com.fetchrewards.codingexercise.ui.MainViewModelFactory
import kotlinx.coroutines.Dispatchers
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application(), KodeinAware {

    override val kodein = Kodein.lazy {

        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build()
        }

        bind<RemoteDataSource>() with singleton {
            val retrofit = instance<Retrofit>().create(Endpoints::class.java)
            RemoteDataSource(retrofit)
        }

        bind<Repository>() with singleton {
            Repository(Dispatchers.IO, instance())
        }

        bind() from provider { MainViewModelFactory(instance()) }
    }
}
