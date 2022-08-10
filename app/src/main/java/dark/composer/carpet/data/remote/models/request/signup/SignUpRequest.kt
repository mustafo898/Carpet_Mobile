package dark.composer.carpet.data.remote.models.request.signup

data class SignUpRequest(
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val password: String,
    val configPassword: String
)
//ProfileRole role: ADMIN,CUSTOMER,EMPLOYEE