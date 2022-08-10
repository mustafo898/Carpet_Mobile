package dark.composer.carpet.data.remote.models.request.profile

data class ProfileRequest(
    val password: String,
    val name: String,
    val surname: String,
)