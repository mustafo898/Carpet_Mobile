package dark.composer.carpet.data.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dark.composer.carpet.data.di.scopes.ViewModelKey
import dark.composer.carpet.presentation.fragment.admin.AdminViewModel
import dark.composer.carpet.presentation.fragment.factory.FactoryViewModel
import dark.composer.carpet.presentation.fragment.product.ProductViewModel
import dark.composer.carpet.presentation.fragment.factory.add.factory.AddFactoryViewModel
import dark.composer.carpet.presentation.fragment.factory.details.FactoryDetailsViewModel
import dark.composer.carpet.presentation.fragment.factory.update.UpdateFactoryViewModel
import dark.composer.carpet.presentation.fragment.login.LogInViewModel
import dark.composer.carpet.presentation.fragment.product.add.product.AddProductViewModel
import dark.composer.carpet.presentation.fragment.product.update.UpdateViewModel
import dark.composer.carpet.presentation.fragment.product.details.ProductDetailsViewModel
import dark.composer.carpet.presentation.fragment.profile.ProfileViewModel
import dark.composer.carpet.presentation.fragment.profile.create.CreateUserViewModel
import dark.composer.carpet.presentation.fragment.signup.SignUpViewModel

@Module
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AdminViewModel::class)
    abstract fun splashViewModel(splashViewModel : AdminViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    abstract fun productViewModel(productViewModel : ProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailsViewModel::class)
    abstract fun productDetailsViewModel(productDetailsViewModel : ProductDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddFactoryViewModel::class)
    abstract fun addFactoryViewModel(addFactoryViewModel : AddFactoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddProductViewModel::class)
    abstract fun addProductViewModel(addProductViewModel : AddProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FactoryViewModel::class)
    abstract fun factoryViewModel(factoryViewModel : FactoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FactoryDetailsViewModel::class)
    abstract fun factoryDetailsViewModel(factoryDetailsViewModel : FactoryDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun signUpViewModel(signUpViewModel : SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LogInViewModel::class)
    abstract fun logInViewModel(logInViewModel : LogInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UpdateFactoryViewModel::class)
    abstract fun updateFactoryViewModel(updateFactoryViewModel : UpdateFactoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UpdateViewModel::class)
    abstract fun updateProductViewModel(updateViewModel : UpdateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun profileViewModel(profileViewModel : ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateUserViewModel::class)
    abstract fun createUserViewModel(createUserViewModel : CreateUserViewModel): ViewModel
}