package dark.composer.carpet.presentation.fragment.product.add.product

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.remote.models.request.product.ProductCreateRequest
import dark.composer.carpet.data.remote.models.response.factory.FactoryResponse
import dark.composer.carpet.data.remote.models.response.factory.PaginationResponse
import dark.composer.carpet.data.remote.models.response.product.ProductFileUploadResponse
import dark.composer.carpet.data.remote.models.response.product.ProductResponse
import dark.composer.carpet.domain.use_case.factory.FactoryUseCase
import dark.composer.carpet.domain.use_case.product.ProductUseCase
import dark.composer.carpet.utils.BaseNetworkResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

class AddProductViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    private val factoryUseCase: FactoryUseCase
) : ViewModel() {
    private val _createProduct = MutableSharedFlow<BaseNetworkResult<ProductResponse>>()
    val createProduct = _createProduct.asSharedFlow()

    private val _factory = MutableSharedFlow<BaseNetworkResult<List<FactoryResponse>>>()
    val factory = _factory.asSharedFlow()

    private val nameChannel = MutableLiveData<String>()
    val nameFlow: LiveData<String> = nameChannel

    private val typeChannel = MutableLiveData<String>()
    val typeFlow: LiveData<String> = typeChannel

    private val ponChannel = MutableLiveData<String>()
    val ponFlow: LiveData<String> = ponChannel

    private val factoryIdChannel = MutableLiveData<String>()
    val factoryIdFlow: LiveData<String> = factoryIdChannel

    private val widthChannel = MutableLiveData<String>()
    val widthFlow: LiveData<String> = widthChannel

    private val heightChannel = MutableLiveData<String>()
    val heightFlow: LiveData<String> = heightChannel

    private val colorChannel = MutableLiveData<String>()
    val colorFlow: LiveData<String> = colorChannel

    private val amountChannel = MutableLiveData<String>()
    val amountFlow: LiveData<String> = amountChannel

    private val priceChannel = MutableLiveData<String>()
    val priceFlow: LiveData<String> = priceChannel

    private val designChannel = MutableLiveData<String>()
    val designFlow: LiveData<String> = designChannel

    private val _uploadImage = MutableSharedFlow<BaseNetworkResult<ProductFileUploadResponse>>()
    val uploadImage = _uploadImage.asSharedFlow()

    fun createProduct(
        amount: String,
        colour: String,
        design: String,
        factoryId: Int,
        height: String,
        name: String,
        pon: String,
        price: String,
        type: String,
        weight: String,
        context: Context
    ) {
        if (validAmount(amount) &&
            validColor(colour) &&
            validDesign(design) &&
            validFactoryId(factoryId) &&
            validName(name) &&
            validPon(pon) &&
            validPrice(price) &&
            validType(type) &&
            validWidth(weight) &&
            validHeight(height)
        ) {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            viewModelScope.launch {
                productUseCase.createProduct(
                    ProductCreateRequest(
                        amount.toInt(),
                        colour,
                        design,
                        factoryId,
                        height.toDouble(),
                        name,
                        pon,
                        price.toDouble(),
                        type,
                        weight.toDouble()
                    )
                ).collect {
                    when (it) {
                        is BaseNetworkResult.Success -> {
                            it.data?.let { t ->
                                _createProduct.emit(BaseNetworkResult.Success(it.data))
                            }
                            Log.d("EEEEE", "getPagination: ${it.data}")
                        }
                        is BaseNetworkResult.Error -> {
                            viewModelScope.launch {
                                _createProduct.emit(BaseNetworkResult.Error(it.message))
                            }
                        }
                        is BaseNetworkResult.Loading -> {
                            viewModelScope.launch {
                                _createProduct.emit(BaseNetworkResult.Loading(it.isLoading))
                            }
                        }
                    }
                }
            }
        } else {
            validDesign(design)
            validName(name)
            validAmount(amount)
            validColor(colour)
            validWidth(weight)
            validHeight(height)
            validPrice(price)
            validPon(pon)
            validFactoryId(factoryId)
            validType(type)
        }
    }

    fun uploadFile(file: MultipartBody.Part, productId: String) {
        viewModelScope.launch {
            productUseCase.fileUploadProduct(file, productId).onEach {
                when (it) {
                    is BaseNetworkResult.Success -> {
                        it.data?.let { it1 ->
                            _uploadImage.emit(BaseNetworkResult.Success(it1))
                        }
                        Log.d("EEEEE", "getPagination: ${it.data}")
                    }
                    is BaseNetworkResult.Error -> {
                        _uploadImage.emit(BaseNetworkResult.Error(it.message))
                    }
                    is BaseNetworkResult.Loading -> {
                        _uploadImage.emit(BaseNetworkResult.Loading(it.isLoading))
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getFactoryList(page: Int, size: Int) {
        viewModelScope.launch {
            factoryUseCase.getFactoryList(page, size).onEach { result ->
                when(result){
                    is BaseNetworkResult.Error -> {
                        _factory.emit(BaseNetworkResult.Error(result.message?:"An unexpected error occurred"))
                    }
                    is BaseNetworkResult.Loading -> {
                        _factory.emit(BaseNetworkResult.Loading(result.isLoading))
                    }
                    is BaseNetworkResult.Success -> {
                        _factory.emit(BaseNetworkResult.Success(result.data?.content?: emptyList()))
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun validName(name: String): Boolean {
        if (name.isEmpty()) {
            nameChannel.value = "Name must be entered"
            return false
        } else if (name.length < 4) {
            nameChannel.value = "Minimum 4 Characters Name"
            return false
        } else {
            nameChannel.value = "Correct"
            return true
        }
    }

    fun validDesign(design: String): Boolean {
        if (design.isEmpty()) {
            designChannel.value = "Name must be entered"
            return false
        } else if (design.length < 4) {
            designChannel.value = "Minimum 4 Characters Name"
            return false
        } else {
            designChannel.value = "Correct"
            return true
        }
    }

    fun validWidth(width: String): Boolean {
        if (width.isEmpty()) {
            widthChannel.value = "Width must be entered"
            return false
        }
        if (width.toDouble() < 0.2) {
            widthChannel.value = "Width minimum size 0.2 m"
            return false
        }
        widthChannel.value = "Correct"
        return true
    }

    fun validHeight(height: String): Boolean {
        if (height.isEmpty()) {
            heightChannel.value = "Height must be entered"
            return false
        } else if (height.toDouble() < 0.2) {
            heightChannel.value = "Height minimum size 0.2 m"
            return false
        } else {
            heightChannel.value = "Correct"
            return true
        }
    }

    fun validPon(pon: String): Boolean {
        if (pon.isEmpty()) {
            ponChannel.value = "Pon must be entered"
            return false
        } else if (pon.length < 6) {
            ponChannel.value = "Pon was not entered true"
            return false
        } else {
            ponChannel.value = "Correct"
            return true
        }
    }

    fun validPrice(price: String): Boolean {
        if (price.isEmpty()) {
            priceChannel.value = "Price must be entered"
            return false
        } else {
            priceChannel.value = "Correct"
            return true
        }
    }

    fun validColor(color: String): Boolean {
        if (color.isEmpty()) {
            colorChannel.value = "Color must be entered"
            return false
        } else if (color.length <= 2) {
            colorChannel.value = "Color length minimum 2"
            return false
        } else {
            colorChannel.value = "Correct"
            return true
        }
    }

    fun validAmount(amount: String): Boolean {
        if (amount.isEmpty()) {
            amountChannel.value = "Amount must be entered"
            return false
        } else if (amount.toInt() == 0) {
            amountChannel.value = "Amount can not be 0"
            return false
        } else {
            amountChannel.value = "Correct"
            return true
        }
    }

    fun validFactoryId(id: Int): Boolean {
        if (id == -1) {
            factoryIdChannel.value = "You must select Factory"
            return false
        } else {
            factoryIdChannel.value = "Correct"
            return true
        }
    }

    fun validType(type: String): Boolean {
        return if (type.isNotEmpty()) {
            typeChannel.value = type
            true
        } else {
            typeChannel.value = "You must select Type"
            false
        }
    }
}