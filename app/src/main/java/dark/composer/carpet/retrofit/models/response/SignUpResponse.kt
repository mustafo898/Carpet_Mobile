package dark.composer.carpet.retrofit.models.response

data class SignUpResponse(
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val jwt:String
)