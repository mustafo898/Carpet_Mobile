package dark.composer.carpet.presentation.fragment.profile.add.product

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.FactoryRepository
import dark.composer.carpet.data.repositories.ProductRepository
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.product.ProductCreateRequest
import dark.composer.carpet.data.retrofit.models.response.factory.PaginationResponse
import dark.composer.carpet.data.retrofit.models.response.product.ProductFileUploadResponse
import dark.composer.carpet.data.retrofit.models.response.product.ProductResponse
import dark.composer.carpet.databinding.ItemFactoryBinding
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

class AddProductViewModel @Inject constructor(private val repo: ProductRepository,private val factoryRepo:FactoryRepository) : ViewModel() {
    private val createProduct = MutableLiveData<ProductResponse>()
    val createProductLiveData: MutableLiveData<ProductResponse> = createProduct

    private val _errorChannel = Channel<String?>()
    val errorFlow = _errorChannel.receiveAsFlow()

    private val _loadingChannel = Channel<Boolean?>()
    val loadingFlow = _loadingChannel.receiveAsFlow()

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

    private val successChangeChannel = Channel<ProductFileUploadResponse>()
    val successChangeFlow = successChangeChannel.receiveAsFlow()

    private val listPagination = MutableLiveData<PaginationResponse?>()
    val liveDataListPagination: MutableLiveData<PaginationResponse?> = listPagination

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
            Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show()
            viewModelScope.launch {
                repo.createProduct(
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
                ).observeForever {
                    when (it) {
                        is BaseNetworkResult.Success -> {
                            it.data?.let {t->
                                createProduct.value = t
                            }
                            Log.d("EEEEE", "getPagination: ${it.data}")
                        }
                        is BaseNetworkResult.Error -> {
                            viewModelScope.launch {
                                _errorChannel.send(it.message)
                            }
                        }
                        is BaseNetworkResult.Loading -> {
                            viewModelScope.launch {
                                _loadingChannel.send(it.isLoading)
                            }
                        }
                        else -> {
                            Log.d("Admin", "getPagination: Kemadi")
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
            repo.fileUploadProduct(productId, file).observeForever {
                when (it) {
                    is BaseNetworkResult.Success -> {
                        viewModelScope.launch {
                            it.data?.let { it1 -> successChangeChannel.send(it1) }
                        }
                        Log.d("EEEEE", "getPagination: ${it.data}")
                    }
                    is BaseNetworkResult.Error -> {
                        viewModelScope.launch {
                            _errorChannel.send(it.message)
                        }
                    }
                    is BaseNetworkResult.Loading -> {
                        viewModelScope.launch {
                            _loadingChannel.send(it.isLoading)
                        }
                    }
                    else -> {
                        Log.d("Admin", "getPagination: Kemadi")
                    }
                }
            }
        }
    }

    fun getPagination(page: Int, size: Int) {
        viewModelScope.launch {
            factoryRepo.getPagination(page, size).observeForever {
                when (it) {
                    is BaseNetworkResult.Success -> {
                        listPagination.value = it.data
                        Log.d("EEEEE", "getPagination: ${it.data?.content}")
                    }
                    is BaseNetworkResult.Error -> {
                        viewModelScope.launch {
                            _errorChannel.send(it.message)
                        }
                    }
                    is BaseNetworkResult.Loading -> {
                        viewModelScope.launch {
                            _loadingChannel.send(it.isLoading)
                        }
                    }
                    else -> {
                        Log.d("Admin", "getPagination: Kemadi")
                    }
                }
            }
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
        } else if (width.toDouble() < 0.2) {
            widthChannel.value = "Width minimum size 0.2 m"
            return false
        } else {
            widthChannel.value = "Correct"
            return true
        }
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