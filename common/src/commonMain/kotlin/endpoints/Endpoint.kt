package endpoints

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import util.getRandomString


object Endpoints {
    // Server
    val devices = _getShows
}


enum class EndpointMethod(val lowerCase: String) {
    GET("get"),
    POST("post"),
    PUT("put"),
    DELETE("delete")
}

@Serializable
data class GenericResponse(
    val message: String,
    val isError: Boolean = false
)

data class Endpoint<T>(
    val url: String,
    val method: EndpointMethod,
    val header: Map<String, String> = mapOf(),
    val queryKey: String = getRandomString(10),
    val requiredParams: Map<String, String> = mapOf(),
    val optionalParams: Map<String, String> = mapOf(),
    val requiredUrlEncodedBody: Map<String, String> = mapOf(),
    val optionalUrlEncodedBody: Map<String, String> = mapOf(),
    val body: String? = null,
    val serializer: KSerializer<T>,
    var placeholder: String? = null,
) {
    fun getUsableUrl(): String {  // TODO: Use String.format instead of replace
        return placeholder?.let {
            url.replace("<realm>", it)
        } ?: url
    }
}



