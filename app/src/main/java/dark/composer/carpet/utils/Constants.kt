package dark.composer.carpet.utils

object Constants {
    const val BASE_URL = "http://192.168.0.101:9001/"
    const val LOGIN = "auth/login"
    const val SIGNUP = "auth/registration"
    const val FACTORY_INFO = "factory/{id}"

    //    const val FACTORY_INFO_ADM = "factory/adm/{id}"
//    const val FACTORY_LIST = "factory/list"
    const val FACTORY_PAGINATION = "factory/public/pagination"
    const val FACTORY_PAGINATION_ADM = "factory/adm/pagination"
    const val FACTORY_UPDATE = "factory/adm/{id}"
    const val FACTORY_ADD = "factory/adm"
    const val USER_PAGINATION = "profile/adm/pagination/list"
}