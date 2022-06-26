package dark.composer.carpet.domain.use_case.login

import dark.composer.carpet.data.repositories.SignUpRepositoryImpl
import dark.composer.carpet.domain.use_case.ValidateModel
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val signUpRepositoryImpl: SignUpRepositoryImpl
){
    fun validPhone(phone: String):ValidateModel {
        if (phone.isEmpty()) {
            return ValidateModel(
                error = "Phone Number must be entered",
                correct = false
            )
        } else if (phone.length != 9) {
            return ValidateModel(
                error = "Please Enter Correct Phone Number",
                correct = false
            )
        } else {
            return ValidateModel(
                correct = true
            )
        }
    }

    fun validPassword(password: String): ValidateModel {
        if (password.length <= 6) {
            return ValidateModel(
                error = "Minimum 6 Character Password",
                correct = false
            )
        } else if (!password.matches(".*[A-Z].*".toRegex())) {
            return ValidateModel(
                error = "Must Contain 1 Upper-case Character",
                correct = false
            )
        } else if (!password.matches(".*[a-z].*".toRegex())) {
            return ValidateModel(
                error = "Must Contain 1 Lower-case Character",
                correct = false
            )
        } else if (!password.matches(".*[@#\$%^_].*".toRegex())) {
            return ValidateModel(
                error = "Must Contain 1 Special Character (@#\$%^_)",
                correct = false
            )
        } else {
            return ValidateModel(
                correct = true
            )
        }
    }
}