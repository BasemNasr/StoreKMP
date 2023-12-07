package utils

import kotlinx.serialization.json.Json

object CommonUtil {
    fun isValidEmail(email: String): Boolean {
        val emailPattern = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return emailPattern.matches(email)
    }

    inline fun <reified T> jsonToObject(jsonString: String): T {
        return Json.decodeFromString(jsonString)
    }


}