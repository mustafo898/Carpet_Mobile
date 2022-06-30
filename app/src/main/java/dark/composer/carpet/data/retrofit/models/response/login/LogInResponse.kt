package dark.composer.carpet.data.retrofit.models.response.login

data class LogInResponse(
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val jwt:String,
    val url:String? = null,
    val role:String
)