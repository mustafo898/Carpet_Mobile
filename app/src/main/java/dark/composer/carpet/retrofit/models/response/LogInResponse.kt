package dark.composer.carpet.retrofit.models.response

data class LogInResponse(
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val jwt:String
)