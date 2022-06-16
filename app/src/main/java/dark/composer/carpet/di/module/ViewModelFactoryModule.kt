package dark.composer.carpet.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dark.composer.carpet.mvvm.DaggerViewModelFactory


@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactor(modelProviderFactory: DaggerViewModelFactory?): ViewModelProvider.Factory?
}