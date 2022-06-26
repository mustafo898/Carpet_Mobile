package dark.composer.carpet.data.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dark.composer.carpet.presentasion.fragment.deafaults.DefaultFragment
import dark.composer.carpet.presentasion.fragment.admin.AdminFragment
import dark.composer.carpet.presentasion.fragment.customer.CustomerFragment
import dark.composer.carpet.presentasion.fragment.employee.EmployeeFragment
import dark.composer.carpet.presentasion.fragment.itemfragment.CategoryDetailsFragment
import dark.composer.carpet.presentasion.fragment.login.LogInFragment
import dark.composer.carpet.presentasion.fragment.settings.SettingsFragment
import dark.composer.carpet.presentasion.fragment.signup.SignUpFragment
import dark.composer.carpet.presentasion.fragment.splash.SplashFragment

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
    abstract fun categoryDetailsFragment(): CategoryDetailsFragment
}