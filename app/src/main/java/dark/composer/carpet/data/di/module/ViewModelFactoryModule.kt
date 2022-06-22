package dark.composer.carpet.data.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dark.composer.carpet.presentasion.DaggerViewModelFactory


@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactor(modelProviderFactory: DaggerViewModelFactory?): ViewModelProvider.Factory?
}