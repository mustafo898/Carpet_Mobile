package dark.composer.carpet.data.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dark.composer.carpet.presentation.fragment.deafaults.DefaultFragment
import dark.composer.carpet.presentation.fragment.admin.AdminFragment
import dark.composer.carpet.presentation.fragment.customer.CustomerFragment
import dark.composer.carpet.presentation.fragment.employee.EmployeeFragment
import dark.composer.carpet.presentation.fragment.factory_detail.FactoryDetailsFragment
import dark.composer.carpet.presentation.fragment.login.LogInFragment
import dark.composer.carpet.presentation.fragment.product.ProductFragment
import dark.composer.carpet.presentation.fragment.profile.ProfileFragment
import dark.composer.carpet.presentation.fragment.settings.SettingsFragment
import dark.composer.carpet.presentation.fragment.signup.SignUpFragment
import dark.composer.carpet.presentation.fragment.splash.SplashFragment

@Module
public abstract class MainFragmentBuildersModule {
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

    @ContributesAndroidInjector
    abstract fun settingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun categoryDetailsFragment(): FactoryDetailsFragment

    @ContributesAndroidInjector
    abstract fun profileFragment():ProfileFragment

    @ContributesAndroidInjector
    abstract fun productFragment():ProductFragment
}