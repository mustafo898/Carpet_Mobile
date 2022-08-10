package dark.composer.carpet.data.remote.models.request.filter

data class ProductFilterRequest(
    val factoryName: String? = null,
    val name: String? = null,
    val design: String? = null,
    val colour: String? = null,
    val height: Double? = null,
    val weight: Double? = null,
    val pon: String? = null,
    val publishedDateTo: String? = null,
    val publishedDateFrom: String? = null,
    val type: String? = null
)