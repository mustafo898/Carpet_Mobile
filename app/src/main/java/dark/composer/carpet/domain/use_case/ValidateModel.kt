package dark.composer.carpet.domain.use_case

data class ValidateModel(
    val error: String? = null,
    val correct: Boolean? = null
)