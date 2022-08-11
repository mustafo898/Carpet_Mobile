package dark.composer.carpet.data.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import dark.composer.carpet.utils.SharedPref
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class,
        RepositoryModule::class
    ]
)
object AppModule {

    @Singleton
    @Provides
    fun provideShared(application: Application) = SharedPref(application.applicationContext)
}