package dark.composer.carpet.data.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dark.composer.carpet.presentation.fragment.deafaults.DefaultFragment
import dark.composer.carpet.presentation.fragment.admin.AdminFragment
import dark.composer.carpet.presentation.fragment.factory_detail.FactoryDetailsFragment
import dark.composer.carpet.presentation.fragment.login.LogInFragment
import dark.composer.carpet.presentation.fragment.product.ProductFragment
import dark.composer.carpet.presentation.fragment.product.veiwpager_fragments.countable.CountableFragment
import dark.composer.carpet.presentation.fragment.product.veiwpager_fragments.deatils.ProductDetailsFragment
import dark.composer.carpet.presentation.fragment.product.veiwpager_fragments.uncountable.UncountableFragment
import dark.composer.carpet.presentation.fragment.profile.ProfileFragment
import dark.composer.carpet.presentation.fragment.profile.list.customer.ListFragment
import dark.composer.carpet.presentation.fragment.profile.list.details.ListDetailsFragment
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
    abstract fun adminFragment(): AdminFragment

    @ContributesAndroidInjector
    abstract fun settingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun categoryDetailsFragment(): FactoryDetailsFragment

    @ContributesAndroidInjector
    abstract fun profileFragment():ProfileFragment

    @ContributesAndroidInjector
    abstract fun productFragment():ProductFragment

    @ContributesAndroidInjector
    abstract fun productCountable():CountableFragment

    @ContributesAndroidInjector
    abstract fun productUncountable():UncountableFragment

    @ContributesAndroidInjector
    abstract fun productDetails():ProductDetailsFragment

    @ContributesAndroidInjector
    abstract fun listFragment():ListFragment

    @ContributesAndroidInjector
    abstract fun listDetailsFragment():ListDetailsFragment
}