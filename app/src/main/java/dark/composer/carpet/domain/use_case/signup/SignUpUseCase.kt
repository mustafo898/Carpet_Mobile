package dark.composer.carpet.domain.use_case.signup

import dark.composer.carpet.data.retrofit.models.auth.ResultModelAuth
import dark.composer.carpet.data.retrofit.models.request.SignUpRequest
import dark.composer.carpet.domain.use_case.ValidateModel
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {
    private fun validName(name: String): ValidateModel {
        if (name.isEmpty()) {
            return ValidateModel(
                error = "Name must be entered",
                correct = false
            )
        } else if (name.length <= 4) {
            return ValidateModel(
                error = "Minimum 4 Characters Name",
                correct = false
            )
        } else {
            return ValidateModel(
                correct = false
            )
        }
    }

    private fun validSurname(surName: String): ValidateModel {
        if (surName.isEmpty()) {
            return ValidateModel(
                error = "Last Name must be entered",
                correct = false
            )
        } else if (surName.length <= 4) {
            return ValidateModel(
                error = "Minimum 4 Characters LastName",
                correct = false
            )
        } else {
            return ValidateModel(
                correct = true
            )
        }
    }

    private fun validPhone(phone: String): ValidateModel {
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

    private fun validPassword(password: String): ValidateModel {
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

    private fun validConfirmPassword(configPassword: String, password: String): ValidateModel {
        if (password.isEmpty()) {
            return ValidateModel(
                error = "Password must be Entered",
                correct = false
            )
        } else if (configPassword != password) {
            return ValidateModel(
                error = "Confirm Password must be Equal Password",
                correct = false
            )
        } else {
            return ValidateModel(
                correct = true
            )
        }
    }

//    suspend fun register(
//        name: String,
//        surname: String,
//        phoneNumber: String,
//        password: String,
//        configPassword: String
//    ) : ResultModelAuth {
//        if (!validName(name).correct!! && !validPhone(phoneNumber).correct!! && !validPassword(password).correct!! && !validSurname(surname).correct!! && !validConfirmPassword(configPassword, password).correct!!){
//            validName(name)
//            validPhone(phoneNumber)
//            validPassword(password)
//            validSurname(surname)
//            validConfirmPassword(configPassword, password)
//        }else{
//
//        }
//        return ResultModelAuth(
//            signIp = signUpRepository.signUp(SignUpRequest(name = name, surname = surname, phoneNumber = phoneNumber, password = password, configPassword = configPassword))
//        )
//    }
}