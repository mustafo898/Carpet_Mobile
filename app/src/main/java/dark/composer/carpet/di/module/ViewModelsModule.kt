package dark.composer.carpet.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dark.composer.carpet.mvvm.LogInViewModel
import dark.composer.carpet.mvvm.SigUpViewModel
import dark.composer.carpet.mvvm.SplashViewModel
import dark.composer.carpet.scopes.ViewModelKey

@Module
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun splashViewModel(splashViewModel : SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LogInViewModel::class)
    abstract fun logInViewModel(logInViewModel : LogInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SigUpViewModel::class)
    abstract fun sigUpViewModel(sigUpViewModel : SigUpViewModel): ViewModel
}