package dark.composer.carpet.retrofit.models.request

data class SignUpRequest(
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val password: String,
    val configPassword: String
)