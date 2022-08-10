package dark.composer.carpet.data.remote.models.request.profile.create_customer

data class ProfileCreateRequest(
    val name: String,
    val password: String,
    val phoneNumber: String,
    val role: String,
    val surname: String,
)