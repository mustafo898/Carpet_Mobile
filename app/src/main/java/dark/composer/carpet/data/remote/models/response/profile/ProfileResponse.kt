package dark.composer.carpet.data.remote.models.response.profile

data class ProfileResponse(
    val id: Int,
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val status: String,
    val role: String,
    val visible: Boolean,
    val url: String,
    val jwt: String
)