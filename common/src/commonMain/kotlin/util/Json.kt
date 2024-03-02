package util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
val ignoreUnknownKeysInJson: Json = Json {
    encodeDefaults = true
    isLenient = true
    ignoreUnknownKeys = true
    explicitNulls = false
}