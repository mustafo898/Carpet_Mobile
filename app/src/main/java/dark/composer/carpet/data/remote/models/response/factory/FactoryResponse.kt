package dark.composer.carpet.data.remote.models.response.factory

data class FactoryResponse(
    val createdDate: String,
    val id: Int,
    val key: String,
    val name: String,
    val photoUrl:String?=null,
    val status: String,
    val visible: Boolean
)