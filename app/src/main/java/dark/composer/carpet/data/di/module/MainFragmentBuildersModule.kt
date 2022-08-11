package dark.composer.carpet.data.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dark.composer.carpet.presentation.fragment.admin.AdminFragment
import dark.composer.carpet.presentation.fragment.factory.FactoryFragment
import dark.composer.carpet.presentation.fragment.product.ProductFragment
import dark.composer.carpet.presentation.fragment.factory.add.factory.AddFactoryFragment
import dark.composer.carpet.presentation.fragment.factory.details.FactoryDetailsFragment
import dark.composer.carpet.presentation.fragment.login.LogInFragment
import dark.composer.carpet.presentation.fragment.product.add.product.AddProductFragment
import dark.composer.carpet.presentation.fragment.product.details.ProductDetailsFragment
import dark.composer.carpet.presentation.fragment.signup.SignUpFragment

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun adminFragment(): AdminFragment

    @ContributesAndroidInjector
    abstract fun productFragment(): ProductFragment

    @ContributesAndroidInjector
    abstract fun productDetailsFragment(): ProductDetailsFragment

    @ContributesAndroidInjector
    abstract fun adProductFragment(): AddProductFragment

    @ContributesAndroidInjector
    abstract fun addFactoryFragment(): AddFactoryFragment

    @ContributesAndroidInjector
    abstract fun factoryFragment(): FactoryFragment

    @ContributesAndroidInjector
    abstract fun factoryDetailsFragment(): FactoryDetailsFragment

    @ContributesAndroidInjector
    abstract fun signUpFragment(): SignUpFragment

    @ContributesAndroidInjector
    abstract fun logInFragment(): LogInFragment
}