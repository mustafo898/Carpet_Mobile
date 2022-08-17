package dark.composer.carpet.data.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dark.composer.carpet.presentation.fragment.admin.AdminFragment
import dark.composer.carpet.presentation.fragment.basket.BasketFragment
import dark.composer.carpet.presentation.fragment.factory.FactoryFragment
import dark.composer.carpet.presentation.fragment.product.ProductFragment
import dark.composer.carpet.presentation.fragment.factory.add.factory.AddFactoryFragment
import dark.composer.carpet.presentation.fragment.factory.details.FactoryDetailsFragment
import dark.composer.carpet.presentation.fragment.factory.update.UpdateFactoryFragment
import dark.composer.carpet.presentation.fragment.login.LogInFragment
import dark.composer.carpet.presentation.fragment.product.add.product.AddProductFragment
import dark.composer.carpet.presentation.fragment.product.update.UpdateProductFragment
import dark.composer.carpet.presentation.fragment.product.details.ProductCountableFragment
import dark.composer.carpet.presentation.fragment.product.details.ProductUncountableFragment
import dark.composer.carpet.presentation.fragment.profile.ProfileFragment
import dark.composer.carpet.presentation.fragment.profile.update.UpdateProfileFragment
import dark.composer.carpet.presentation.fragment.profile.users.create.CreateUserFragment
import dark.composer.carpet.presentation.fragment.profile.users.UsersFragment
import dark.composer.carpet.presentation.fragment.profile.users.details.UserDetailsFragment
import dark.composer.carpet.presentation.fragment.profile.users.update.UpdateUserFragment
import dark.composer.carpet.presentation.fragment.signup.SignUpFragment

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun adminFragment(): AdminFragment

    @ContributesAndroidInjector
    abstract fun productFragment(): ProductFragment

    @ContributesAndroidInjector
    abstract fun productDetailsFragment(): ProductCountableFragment

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

    @ContributesAndroidInjector
    abstract fun updateFactoryFragment(): UpdateFactoryFragment

    @ContributesAndroidInjector
    abstract fun productUncountableFragment(): ProductUncountableFragment

    @ContributesAndroidInjector
    abstract fun updateProductFragment(): UpdateProductFragment

    @ContributesAndroidInjector
    abstract fun profileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun createUserFragment(): CreateUserFragment

    @ContributesAndroidInjector
    abstract fun usersFragment(): UsersFragment

    @ContributesAndroidInjector
    abstract fun userDetailsFragment(): UserDetailsFragment

    @ContributesAndroidInjector
    abstract fun updateUserFragment(): UpdateUserFragment

    @ContributesAndroidInjector
    abstract fun updateProfileFragment(): UpdateProfileFragment

    @ContributesAndroidInjector
    abstract fun basketFragment(): BasketFragment
}