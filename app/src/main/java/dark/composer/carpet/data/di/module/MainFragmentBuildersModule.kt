package dark.composer.carpet.data.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dark.composer.carpet.presentation.fragment.admin.AdminFragment
import dark.composer.carpet.presentation.fragment.basket.BasketFragment
import dark.composer.carpet.presentation.fragment.deafaults.DefaultFragment
import dark.composer.carpet.presentation.fragment.factory.FactoryFragment
import dark.composer.carpet.presentation.fragment.factory.factory_detail.FactoryDetailsFragment
import dark.composer.carpet.presentation.fragment.login.LogInFragment
import dark.composer.carpet.presentation.fragment.product.ProductFragment
import dark.composer.carpet.presentation.fragment.product.deatils.ProductDetailsFragment
import dark.composer.carpet.presentation.fragment.profile.ProfileFragment
import dark.composer.carpet.presentation.fragment.profile.add.factory.AddFactoryFragment
import dark.composer.carpet.presentation.fragment.profile.add.product.AddProductFragment
import dark.composer.carpet.presentation.fragment.profile.list.customer.ListFragment
import dark.composer.carpet.presentation.fragment.profile.list.details.ListDetailsFragment
import dark.composer.carpet.presentation.fragment.sale.SaleFragment
import dark.composer.carpet.presentation.fragment.search.SearchFragment
import dark.composer.carpet.presentation.fragment.search.filter.FilterProductFragment
import dark.composer.carpet.presentation.fragment.settings.SettingsFragment
import dark.composer.carpet.presentation.fragment.signup.SignUpFragment

@Module
abstract class MainFragmentBuildersModule {

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
    abstract fun profileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun productFragment(): ProductFragment

    @ContributesAndroidInjector
    abstract fun productDetails(): ProductDetailsFragment

    @ContributesAndroidInjector
    abstract fun listFragment(): ListFragment

    @ContributesAndroidInjector
    abstract fun listDetailsFragment(): ListDetailsFragment

    @ContributesAndroidInjector
    abstract fun addProductFragment(): AddProductFragment

    @ContributesAndroidInjector
    abstract fun addFactoryFragment(): AddFactoryFragment

    @ContributesAndroidInjector
    abstract fun addFilterProductFragment(): FilterProductFragment

    @ContributesAndroidInjector
    abstract fun addSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun FactoryFragment(): FactoryFragment

    @ContributesAndroidInjector
    abstract fun basketFragment(): BasketFragment

    @ContributesAndroidInjector
    abstract fun saleFragment(): SaleFragment
}