package dark.composer.carpet.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dark.composer.carpet.fragments.*


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

    @ContributesAndroidInjector
    abstract fun customerFragment(): CustomerFragment

    @ContributesAndroidInjector
    abstract fun employeeFragment(): EmployeeFragment

    @ContributesAndroidInjector
    abstract fun adminFragment(): AdminFragment
}