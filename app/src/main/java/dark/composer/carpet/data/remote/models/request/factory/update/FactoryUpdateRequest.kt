package dark.composer.carpet.data.remote.models.request.factory.update

data class FactoryUpdateRequest(
    val id:Int,
    val name: String,
    val status: String,
    val visible: Boolean
)