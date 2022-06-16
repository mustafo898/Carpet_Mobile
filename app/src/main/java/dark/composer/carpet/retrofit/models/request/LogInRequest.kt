package dark.composer.carpet.retrofit.models.request

data class LogInRequest(
    val password: String,
    val phoneNumber: String,
)