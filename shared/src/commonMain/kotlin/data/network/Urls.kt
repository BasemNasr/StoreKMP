package data.network

object Urls {
    const val BASE_URL = "https://api.escuelajs.co/api/v1/"
    const val LOGIN = "${BASE_URL}auth/login"
    const val REGISTER = "${BASE_URL}users"
    const val PRODUCT_DETAILS = "${BASE_URL}products/{id}"
    const val CATEGORIES = "${BASE_URL}products/categories"
    const val GET_CATEGORY_PRODUCTS = "${BASE_URL}products/category/{cat_name}"
}