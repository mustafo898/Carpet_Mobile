package dark.composer.carpet.presentation.fragment.profile.add.product

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dark.composer.carpet.data.repositories.ProductRepository
import dark.composer.carpet.data.retrofit.models.BaseNetworkResult
import dark.composer.carpet.data.retrofit.models.request.product.ProductCreateRequest
import dark.composer.carpet.data.retrofit.models.response.product.ProductResponse
import dark.composer.carpet.databinding.ItemFactoryBinding
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddProductViewModel @Inject constructor(private val repo: ProductRepository) : ViewModel() {
    private val createProduct = MutableLiveData<ProductResponse>()
    val createProductLiveData: MutableLiveData<ProductResponse> = createProduct

    private val _errorChannel = Channel<String?>()
    val errorFlow = _errorChannel.receiveAsFlow()

    private val _loadingChannel = Channel<Boolean?>()
    val loadingFlow = _loadingChannel.receiveAsFlow()

    private val nameChannel = Channel<String>()
    val nameFlow = nameChannel.receiveAsFlow()

    private val typeChannel = Channel<String>()
    val typeFlow = typeChannel.receiveAsFlow()

    private val ponChannel = Channel<String>()
    val ponFlow = ponChannel.receiveAsFlow()

    private val factoryIdChannel = Channel<String>()
    val factoryIdFlow = factoryIdChannel.receiveAsFlow()

    private val widthChannel = Channel<String>()
    val widthFlow = widthChannel.receiveAsFlow()

    private val heightChannel = Channel<String>()
    val heightFlow = heightChannel.receiveAsFlow()

    private val colorChannel = Channel<String>()
    val colorFlow = colorChannel.receiveAsFlow()

    private val amountChannel = Channel<String>()
    val amountFlow = amountChannel.receiveAsFlow()

    private val priceChannel = Channel<String>()
    val priceFlow = priceChannel.receiveAsFlow()

    private val designChannel = Channel<String>()
    val designFlow = designChannel.receiveAsFlow()

    fun createProduct(createRequest: ProductCreateRequest, isCount: Int) {
        if (!validAmount(createRequest.amount.toString()) &&
            !validColor(createRequest.colour) &&
            !validDesign(createRequest.design) &&
            !validFactoryId(createRequest.factoryId, isCount) &&
            !validName(createRequest.name) && !validPon(createRequest.pon) &&
            !validPrice(createRequest.price.toString()) &&
            !validType(isCount) && !validWidth(createRequest.weight.toString()) &&
            !validHeight(createRequest.height.toString())
        ){
            validAmount(createRequest.amount.toString())
            validColor(createRequest.colour)
            validWidth(createRequest.weight.toString())
            validHeight(createRequest.height.toString())
            validPrice(createRequest.price.toString())
            validPon(createRequest.pon)
            validFactoryId(createRequest.factoryId,isCount)
            validType(isCount)
        }else{
            viewModelScope.launch {
                repo.createProduct(createRequest).observeForever {
                    when (it) {
                        is BaseNetworkResult.Success -> {
                            createProduct.value = it.data
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
    }

    fun validName(name: String): Boolean {
        if (name.isEmpty()) {
            viewModelScope.launch {
                nameChannel.send("Name must be entered")
            }
            return false
        } else if (name.length < 4) {
            viewModelScope.launch {
                nameChannel.send("Minimum 4 Characters Name")
            }
            return false
        } else {
            viewModelScope.launch {
                nameChannel.send("Correct")
            }
            return true
        }
    }

    fun validDesign(design: String): Boolean {
        if (design.isEmpty()) {
            viewModelScope.launch {
                designChannel.send("Name must be entered")
            }
            return false
        } else if (design.length < 4) {
            viewModelScope.launch {
                designChannel.send("Minimum 4 Characters Name")
            }
            return false
        } else {
            viewModelScope.launch {
                designChannel.send("Correct")
            }
            return true
        }
    }

    fun validWidth(width: String): Boolean {
        if (width.isEmpty()) {
            viewModelScope.launch {
                widthChannel.send("Width must be entered")
            }
            return false
        } else if (width.toDouble() < 0.2) {
            viewModelScope.launch {
                widthChannel.send("Width minimum size 0.2 m")
            }
            return false
        } else {
            viewModelScope.launch {
                widthChannel.send("Correct")
            }
            return true
        }
    }

    fun validHeight(height: String): Boolean {
        if (height.isEmpty()) {
            viewModelScope.launch {
                heightChannel.send("Height must be entered")
            }
            return false
        } else if (height.toDouble() < 0.2) {
            viewModelScope.launch {
                heightChannel.send("Height minimum size 0.2 m")
            }
            return false
        } else {
            viewModelScope.launch {
                heightChannel.send("Correct")
            }
            return true
        }
    }

    fun validPon(pon: String): Boolean {
        if (pon.isEmpty()) {
            viewModelScope.launch {
                ponChannel.send("Pon must be entered")
            }
            return false
        } else if (pon.length < 6) {
            viewModelScope.launch {
                ponChannel.send("Pon was not entered true")
            }
            return false
        } else {
            viewModelScope.launch {
                ponChannel.send("Correct")
            }
            return true
        }
    }

    fun validPrice(price: String): Boolean {
        if (price.isEmpty()) {
            viewModelScope.launch {
                priceChannel.send("Price must be entered")
            }
            return false
        } else {
            viewModelScope.launch {
                priceChannel.send("Correct")
            }
            return true
        }
    }

    fun validColor(color: String): Boolean {
        if (color.isEmpty()) {
            viewModelScope.launch {
                colorChannel.send("Color must be entered")
            }
            return false
        } else if (color.length <= 2) {
            viewModelScope.launch {
                colorChannel.send("Color length minimum 2")
            }
            return false
        } else {
            viewModelScope.launch {
                colorChannel.send("Correct")
            }
            return true
        }
    }

    fun validAmount(amount: String): Boolean {
        if (amount.isEmpty()) {
            viewModelScope.launch {
                amountChannel.send("Color must be entered")
            }
            return false
        } else if (amount.toInt() == 0) {
            viewModelScope.launch {
                amountChannel.send("Amount can not be 0")
            }
            return false
        } else {
            viewModelScope.launch {
                amountChannel.send("Correct")
            }
            return true
        }
    }

    fun validFactoryId(id: Int, isCount: Int): Boolean {
        var d = false
        if (isCount == 2) {
            d = if (id == -1) {
                viewModelScope.launch {
                    factoryIdChannel.send("You must select Factory")
                }
                false
            } else {
                viewModelScope.launch {
                    factoryIdChannel.send("Correct")
                }
                true
            }
        } else if (isCount == 1) {
            viewModelScope.launch {
                factoryIdChannel.send("Correct")
            }
            d = true
        }
        return d
    }

    fun validType(id: Int): Boolean {
        return if (id == -1) {
            viewModelScope.launch {
                typeChannel.send("You must select Type")
            }
            false
        } else {
            viewModelScope.launch {
                typeChannel.send("Correct")
            }
            true
        }

    }
}