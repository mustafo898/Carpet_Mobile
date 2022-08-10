package dark.composer.carpet.data.remote.models.response.basket

data class BasketCreateResponse(
    val amount: Int,
    val createdDate: String,
    val getProfile: GiveProfile,
    val giveProfile: GiveProfile,
    val id: Int,
    val info: String,
    val product: Any,
    val returnedDate: Any,
    val status: String,
    val type: String,
    val visible: Boolean
)