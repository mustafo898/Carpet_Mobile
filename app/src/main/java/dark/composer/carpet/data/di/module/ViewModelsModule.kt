package dark.composer.carpet.data.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dark.composer.carpet.presentation.fragment.login.LogInViewModel
import dark.composer.carpet.presentation.fragment.signup.SignUpViewModel
import dark.composer.carpet.presentation.fragment.splash.SplashViewModel
import dark.composer.carpet.data.scopes.ViewModelKey
import dark.composer.carpet.presentation.fragment.admin.AdminViewModel
import dark.composer.carpet.presentation.fragment.basket.BasketViewModel
import dark.composer.carpet.presentation.fragment.deafaults.DefaultViewModel
import dark.composer.carpet.presentation.fragment.factory.FactoryViewModel
import dark.composer.carpet.presentation.fragment.factory.factory_detail.FactoryDetailsViewModel
import dark.composer.carpet.presentation.fragment.product.deatils.ProductDetailsViewModel
import dark.composer.carpet.presentation.fragment.product.ProductViewModel
import dark.composer.carpet.presentation.fragment.profile.ProfileViewModel
import dark.composer.carpet.presentation.fragment.profile.add.factory.AddFactoryViewModel
import dark.composer.carpet.presentation.fragment.profile.add.product.AddProductViewModel
import dark.composer.carpet.presentation.fragment.profile.list.customer.ListViewModel
import dark.composer.carpet.presentation.fragment.profile.list.details.ListDetailsViewModel
import dark.composer.carpet.presentation.fragment.search.SearchViewModel
import dark.composer.carpet.presentation.fragment.search.filter.FilterProductViewModel

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

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun profileViewModel(profileViewModel : ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AdminViewModel::class)
    abstract fun adminViewModel(adminViewModel : AdminViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DefaultViewModel::class)
    abstract fun defaultViewModel(defaultViewModel : DefaultViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FactoryDetailsViewModel::class)
    abstract fun factoryDetailsViewModel(factoryDetailsViewModel : FactoryDetailsViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(CountableViewModel::class)
//    abstract fun provideCountableViewModel(countableViewModel : CountableViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    abstract fun provideUncountableViewModel(uncountableViewModel : ProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailsViewModel::class)
    abstract fun providerProductDetailsViewModel(productDetailsViewModel : ProductDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    abstract fun providerListViewModel(listViewModel : ListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListDetailsViewModel::class)
    abstract fun providerListDetailsViewModel(listDetailsViewModel : ListDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddProductViewModel::class)
    abstract fun providerAddProductViewModel(AddProductViewModel : AddProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddFactoryViewModel::class)
    abstract fun providerAddFactoryViewModel(AddFactoryViewModel : AddFactoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun providerSearchViewModel(SearchViewModel : SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FilterProductViewModel::class)
    abstract fun providerFilterProductViewModel(filterProductViewModel : FilterProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FactoryViewModel::class)
    abstract fun providerFactoryViewModel(factoryViewModel : FactoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BasketViewModel::class)
    abstract fun providerBasketViewModel(basketViewModel : BasketViewModel): ViewModel
}