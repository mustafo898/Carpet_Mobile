package dark.composer.carpet.data.remote.models.request.login

data class LogInRequest(
    val password: String,
    val phoneNumber: String,
)