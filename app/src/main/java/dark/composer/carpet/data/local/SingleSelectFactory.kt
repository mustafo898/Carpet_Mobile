package dark.composer.carpet.data.local

import dark.composer.carpet.data.retrofit.models.response.factory.FactoryResponse

data class SingleSelectFactory (
    val content:List<FactoryResponse>,
    var isChecked:Boolean = false
)