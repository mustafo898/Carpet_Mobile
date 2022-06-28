package dark.composer.carpet.data.retrofit.models.request.factory.update

data class FactoryUpdateRequest(
    val id:Int,
    val name: String,
    val status: String,
    val visible: Boolean
)