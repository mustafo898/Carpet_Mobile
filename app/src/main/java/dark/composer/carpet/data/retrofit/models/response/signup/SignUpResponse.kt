package dark.composer.carpet.data.retrofit.models.response.signup

data class SignUpResponse(
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val role: String = "CUSTOMER",
    val jwt:String
)