package dark.composer.carpet.data.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dark.composer.carpet.presentation.fragment.login.LogInViewModel
import dark.composer.carpet.presentation.fragment.signup.SignUpViewModel
import dark.composer.carpet.presentation.fragment.splash.SplashViewModel
import dark.composer.carpet.data.scopes.ViewModelKey

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
    @ViewModelKey(SignUpViewModel::class)
    abstract fun sigUpViewModel(signUpViewModel : SignUpViewModel): ViewModel
}