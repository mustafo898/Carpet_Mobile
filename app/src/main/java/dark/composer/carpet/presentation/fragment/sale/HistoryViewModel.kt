package dark.composer.carpet.presentation.fragment.sale

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.remote.models.request.sale.SaleCreateDateRequest
import dark.composer.carpet.data.remote.models.request.sale.SaleUpdateRequest
import dark.composer.carpet.data.remote.models.response.sale.SaleListByDate
import dark.composer.carpet.data.remote.models.response.sale.SalePaginationResponse
import dark.composer.carpet.data.remote.models.response.sale.SaleResponse
import dark.composer.carpet.domain.use_case.sale.SaleUseCase
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class HistoryViewModel @Inject constructor(private val useCase: SaleUseCase) : ViewModel() {
    private val _list = MutableSharedFlow<BaseNetworkResult<List<SalePaginationResponse>>>()
    val list = _list.asSharedFlow()

    private val _update = MutableSharedFlow<BaseNetworkResult<SaleResponse>>()
    val update = _update.asSharedFlow()

    private val _delete = MutableSharedFlow<BaseNetworkResult<SaleResponse>>()
    val delete = _delete.asSharedFlow()

    private val _listByCreateDate = MutableSharedFlow<BaseNetworkResult<List<SaleListByDate>>>()
    val listByCreateDate = _listByCreateDate.asSharedFlow()

    fun getList(page:Int, size:Int, type:String) {
        viewModelScope.launch {
            useCase.listSale(page = page, size = size, type = type).onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _list.emit(BaseNetworkResult.Error(result.message?:"An unexpected error occurred"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _list.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _list.emit(BaseNetworkResult.Success(result.data!!))
                        Log.d("Sale viewModel","getList:  ${result.data}")
                    }
                }
            }.catch {t->
                Log.d("Mistake ViewModel", "getList: ${t.message}")
            }.launchIn(viewModelScope)
        }
    }

    fun getListByCreateDate(dateTime: SaleCreateDateRequest) {
        viewModelScope.launch {
            useCase.listByCreateDateSale(dateTime).onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _listByCreateDate.emit(BaseNetworkResult.Error(result.message?:"An unexpected error occurred"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _listByCreateDate.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _listByCreateDate.emit(BaseNetworkResult.Success(result.data!!))
                        Log.d("Sale viewModel","getListByCreateDate:  ${result.data}")
                    }
                }
            }.catch {t->
                Log.d("Mistake ViewModel", "getListByCreateDate: ${t.message}")
            }.launchIn(viewModelScope)
        }
    }

    fun updateSale(id:Int, updateRequest: SaleUpdateRequest, type:String) {
        viewModelScope.launch {
            useCase.updateSale(id = id, type = type, saleUpdateRequest = updateRequest).onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _update.emit(BaseNetworkResult.Error(result.message?:"An unexpected error occurred"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _update.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _update.emit(BaseNetworkResult.Success(result.data!!))
                        Log.d("Sale viewModel","updateSale:  ${result.data}")
                    }
                }
            }.catch {t->
                Log.d("Mistake ViewModel", "updateSale: ${t.message}")
            }.launchIn(viewModelScope)
        }
    }

    fun deleteSale(id:Int, type:String) {
        viewModelScope.launch {
            useCase.deleteSale(id = id, type = type).onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _delete.emit(BaseNetworkResult.Error(result.message?:"An unexpected error occurred"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _delete.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _delete.emit(BaseNetworkResult.Success(result.data!!))
                        Log.d("Sale viewModel","deleteSale:  ${result.data}")
                    }
                }
            }.catch {t->
                Log.d("Mistake ViewModel", "deleteSale: ${t.message}")
            }.launchIn(viewModelScope)
        }
    }
}