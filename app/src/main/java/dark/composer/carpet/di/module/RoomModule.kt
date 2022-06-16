package dark.composer.carpet.di.module

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dark.composer.carpet.utils.Constants
import dark.composer.carpet.utils.SharedPref
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
object RoomModule {

//    @Singleton
//    @Provides
//    fun provideRoom(application: Application): Retrofit {
//        return Room.databaseBuilder(
//            application.applicationContext,
//            AppDatabase::class.java, "carpet"
//        ).build()
//    }
}