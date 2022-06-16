package dark.composer.carpet.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dark.composer.carpet.fragments.DefaultFragment
import dark.composer.carpet.fragments.LogInFragment
import dark.composer.carpet.fragments.SignUpFragment
import dark.composer.carpet.fragments.SplashFragment


@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun splashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun logInFragment(): LogInFragment

    @ContributesAndroidInjector
    abstract fun signUpFragment(): SignUpFragment

    @ContributesAndroidInjector
    abstract fun defaultFragment(): DefaultFragment

}