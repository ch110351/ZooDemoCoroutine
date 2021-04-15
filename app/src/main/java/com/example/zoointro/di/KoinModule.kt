package com.example.zoointro.di


import com.example.zoointro.BuildConfig
import com.example.zoointro.common.Constant
import com.example.zoointro.common.Constant.Companion.CONTENT_TYPE
import com.example.zoointro.common.Constant.Companion.CONTENT_VALUE
import com.example.zoointro.common.Constant.Companion.TIME_OUT
import com.example.zoointro.http.api.ApiService
import com.example.zoointro.repository.ApiRepository
import com.example.zoointro.ui.adapter.page.PlantDataSourceFactory
import com.example.zoointro.viewmodel.MainViewModel
import com.example.zoointro.viewmodel.VenueListViewModel
import com.example.zoointro.viewmodel.VenueViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {
    factory { AuthInterceptor() }
    factory { provideLoggingInterceptor() }
    factory { provideOkHttpClient(get(), get()) }
    factory { provideApi(get()) }
    single { provideRetrofit(get()) }
}

val viewModelModule = module {
    viewModel {
        MainViewModel()
    }

    viewModel {
        VenueListViewModel(apiRepository = get())
    }

    viewModel {
        VenueViewModel(plantDataSourceFactory = get())
    }
}

val repositoryModule = module {
    factory { ApiRepository(get()) }
}

val plantDataSourceFactory = module {
    single { PlantDataSourceFactory(apiRepository = get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(Constant.HOST_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}


fun provideOkHttpClient(
    authInterceptor: AuthInterceptor,
    loggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    return OkHttpClient().newBuilder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()
}

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader(CONTENT_TYPE, CONTENT_VALUE)
        return chain.proceed(request.build())
    }
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logger = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG)
        logger.level = HttpLoggingInterceptor.Level.BASIC
    else
        logger.level = HttpLoggingInterceptor.Level.NONE
    return logger
}

fun provideApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)