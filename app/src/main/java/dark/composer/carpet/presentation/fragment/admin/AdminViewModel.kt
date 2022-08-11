package dark.composer.carpet.presentation.fragment.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.remote.models.response.factory.FactoryResponse
import dark.composer.carpet.data.remote.models.response.product.pagination.ProductPaginationResponse
import dark.composer.carpet.data.remote.models.response.profile.ProfileResponse
import dark.composer.carpet.domain.use_case.factory.FactoryUseCase
import dark.composer.carpet.domain.use_case.product.ProductUseCase
import dark.composer.carpet.domain.use_case.profile.ProfileUseCase
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class AdminViewModel @Inject constructor(
    private val useCaseProfile: ProfileUseCase,
    private val useCaseFactory: FactoryUseCase,
    private val useCaseProduct: ProductUseCase
) : ViewModel() {
    private val _profile = MutableSharedFlow<BaseNetworkResult<ProfileResponse>>()
    val profile = _profile.asSharedFlow()

    private val _product = MutableSharedFlow<BaseNetworkResult<List<ProductPaginationResponse>>>()
    val product = _product.asSharedFlow()

    private val _factory = MutableSharedFlow<BaseNetworkResult<List<FactoryResponse>>>()
    val factory = _factory.asSharedFlow()

    fun getProfile() {
        viewModelScope.launch {
            useCaseProfile.getProfile().onEach { result ->
                when (result) {
                    is BaseNetworkResult.Error -> _profile.emit(
                        BaseNetworkResult.Error(
                            result.message ?: "An unexpected error occurred"
                        )
                    )
                    is BaseNetworkResult.Loading -> _profile.emit(
                        BaseNetworkResult.Loading(result.isLoading
                        )
                    )
                    is BaseNetworkResult.Success -> result.data?.let { t ->
                        _profile.emit(
                            BaseNetworkResult.Success(t)
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getFactoryList(page: Int, size: Int) {
        viewModelScope.launch {
            useCaseFactory.getFactoryList(page, size).onEach { result ->
                when (result) {
                    is BaseNetworkResult.Error -> _profile.emit(
                        BaseNetworkResult.Error(
                            result.message ?: "An unexpected error occurred"
                        )
                    )
                    is BaseNetworkResult.Loading -> _profile.emit(
                        BaseNetworkResult.Loading(
                            result.isLoading
                        )
                    )
                    is BaseNetworkResult.Success -> result.data?.content.let { t ->
                        _factory.emit(
                            BaseNetworkResult.Success(t?: emptyList())
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getProductList(type: String, page: Int, size: Int) {
        viewModelScope.launch {
            useCaseProduct.getProductList(type, page, size).onEach { result ->
                when (result) {
                    is BaseNetworkResult.Error -> _product.emit(
                        BaseNetworkResult.Error(
                            result.message ?: "An unexpected error occurred"
                        )
                    )
                    is BaseNetworkResult.Loading -> _product.emit(
                        BaseNetworkResult.Loading(
                            result.isLoading
                        )
                    )
                    is BaseNetworkResult.Success -> result.data?.let { t ->
                        _product.emit(
                            BaseNetworkResult.Success(t?: emptyList())
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}