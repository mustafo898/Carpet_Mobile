package dark.composer.carpet.data.retrofit.models.request

data class LogInRequest(
    val password: String,
    val phoneNumber: String,
)