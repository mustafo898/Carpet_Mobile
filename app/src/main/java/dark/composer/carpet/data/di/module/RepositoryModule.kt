package dark.composer.carpet.data.di.module

import dagger.Module
import dagger.Provides
import dark.composer.carpet.data.remote.ApiService
import dark.composer.carpet.data.repositories.*
import dark.composer.carpet.domain.repository.basket.BasketRepository
import dark.composer.carpet.domain.repository.factory.FactoryRepository
import dark.composer.carpet.domain.repository.login.LogInRepository
import dark.composer.carpet.domain.repository.product.ProductRepository
import dark.composer.carpet.domain.repository.profile.ProfileRepository
import dark.composer.carpet.domain.repository.signup.SignUpRepository
import dark.composer.carpet.utils.SharedPref
import javax.inject.Singleton

@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideProductRepo(mainService: ApiService): ProductRepository {
        return ProductRepositoryImpl(mainService)
    }

    @Singleton
    @Provides
    fun provideFactoryRepo(mainService: ApiService): FactoryRepository {
        return FactoryRepositoryImpl(mainService)
    }

    @Singleton
    @Provides
    fun provideBasketRepo(mainService: ApiService): BasketRepository {
        return BasketRepositoryImpl(mainService)
    }

    @Singleton
    @Provides
    fun provideProfileRepo(mainService: ApiService): ProfileRepository {
        return ProfileRepositoryImpl(mainService)
    }

    @Singleton
    @Provides
    fun provideLogInRepo(mainService: ApiService,sharedPref: SharedPref): LogInRepository {
        return LogInRepositoryImpl(mainService,sharedPref)
    }

    @Singleton
    @Provides
    fun provideSignUpRepo(mainService: ApiService, sharedPref: SharedPref): SignUpRepository {
        return SignUpRepositoryImpl(mainService,sharedPref)
    }
}