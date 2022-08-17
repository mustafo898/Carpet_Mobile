package dark.composer.carpet.data.di.module

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dark.composer.carpet.data.remote.ApiService
import dark.composer.carpet.utils.Constants
import dark.composer.carpet.utils.SharedPref
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        shared: SharedPref
    ): Retrofit {
//        val httpLoggingInterceptor = HttpLoggingInterceptor()
//        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(
                OkHttpClient.Builder().addNetworkInterceptor(httpLoggingInterceptor)
                    .connectTimeout(60,TimeUnit.SECONDS)
                    .readTimeout(60,TimeUnit.SECONDS)
                    .writeTimeout(60,TimeUnit.SECONDS)
                    .addInterceptor { chain ->
                        val request = chain.request()
                        val newRequest = if (shared.getToken().isNullOrEmpty())
                            request.newBuilder()
                        else request.newBuilder()
                            .header("Authorization", "Bearer ${shared.getToken()}")
                        chain.proceed(newRequest.build())
//                            .also {
//                        if (it.code == 401) {
//                              Handler(Looper.getMainLooper()).post { shared.setAccessToken("empty") }
//                        }
//                        }
                    }
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideLogin(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
}