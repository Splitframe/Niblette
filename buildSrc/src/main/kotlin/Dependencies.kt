import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

object Version {

    val kotlin                   = "1.9.22"
    // Common
    val kotlinxCoroutines        = "1.7.3"
    val kotlinxSerializationCore = "1.6.2"
    val kotlinxSerializationJson = "1.6.2"
    val koltinxDateTime          = "0.5.0"
    // Frontend
    val kotlinWrappers           = "1.0.0-pre.690" //1.0.0-pre.706
    // Backend
    val exposed                  = "0.47.0"
    val flyway                   = "9.10.0"
    val postgres                 = "42.5.1"
    val hikari                   = "5.0.1"
    val micrometer               = "1.12.2"
    val ktor                     = "2.3.8"
    val pircbotx                 = "2.3.1"
    val kotlinLogging            = "3.0.4"
    val slf4j                    = "2.0.3"
}

object Libs {

    // region direct dependencies

    // Common
    val kotlinxSerializationCore = "org.jetbrains.kotlinx:kotlinx-serialization-core:${Version.kotlinxSerializationCore}"
    val kotlinxSerializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Version.kotlinxSerializationJson}"
    val kotlinxDatetime = "org.jetbrains.kotlinx:kotlinx-datetime:${Version.koltinxDateTime}"
    // Frontend
    val kotlinCoroutinesJs = "org.jetbrains.kotlinx:kotlinx-coroutines-core-js:${Version.kotlinxCoroutines}"
    // Backend

    val kotlinLogging = "io.github.microutils:kotlin-logging:${Version.kotlinLogging}"
    val slf4j = "org.slf4j:slf4j-simple:${Version.slf4j}"
    val pircbotx = "org.pircbotx:pircbotx:${Version.pircbotx}"

    // Flyway
    val flywayCore = "org.flywaydb:flyway-core:${Version.flyway}"
    // Hikari Connection Pool
    val hikari = "com.zaxxer:HikariCP:${Version.hikari}"
    val postgres = "org.postgresql:postgresql:${Version.postgres}"

    // endregion

    // region BOM dependencies
    // Common
    val kotlinCoroutinesCommon = BomConfig("kotlinx-coroutines", "org.jetbrains.kotlinx", Version.kotlinxCoroutines, listOf("kotlinx-coroutines-core","kotlinx-coroutines-debug"))

    // Frontend
    val kotlinWrappers = BomConfig("kotlin-wrappers", "org.jetbrains.kotlin-wrappers", Version.kotlinWrappers, listOf(
        "kotlin-react",
        "kotlin-react-dom",
        "kotlin-react-router-dom",
        "kotlin-mui-base",
        "kotlin-mui-material",
        "kotlin-mui-icons-material",
        "kotlin-mui-lab",
        "kotlin-mui-system",
        "kotlin-emotion",
        "kotlin-css",
        "kotlin-csstype",
        "kotlin-tanstack-react-query",
        "kotlin-tanstack-react-query-devtools",
        "kotlin-tanstack-react-table",
        "kotlin-tanstack-table-core",
    ))

    // Backend
    val kotlinCoroutinesJvm = BomConfig("kotlinx-coroutines", "org.jetbrains.kotlinx", Version.kotlinxCoroutines, listOf(
        "kotlinx-coroutines-core-jvm",
        "kotlinx-coroutines-jdk8",
        "kotlinx-coroutines-reactor",
        "kotlinx-coroutines-rx2",
        "kotlinx-coroutines-slf4j"
    ))

    val exposed = BomConfig("exposed","org.jetbrains.exposed", Version.exposed, listOf("exposed-core","exposed-jdbc","exposed-jdbc","exposed-java-time"))
    val ktor = BomConfig("ktor","io.ktor", Version.ktor, listOf("ktor-server-netty","ktor-server-core", "ktor-server-html-builder-jvm")  )


    // endregion

}

data class BomConfig(
    private val name: String,
    private val path: String,
    private val version: String,
    private val targets: List<String> = emptyList(),

    val platform: String = "$path:$name-bom:$version",
    val fullTargets: Map<String, String> = targets.associateWith { "$path:$it" }
) {

    /**
     * For use with BOM that have multiple paths like Jackson.
     * Requires the full BOM path as well as the full path for each target.
     */
    constructor(bom: String, fullTargets: List<String>, bomSplit: List<String> = bom.split(":")) : this(
        name = bomSplit[1].removeSuffix("-bom"),
        path = bomSplit[0],
        version = bomSplit[2],
        platform = bom,
        fullTargets = fullTargets.associateBy { it.split(":").last() }
    )
}

fun KotlinDependencyHandler.bomImplementation(config: BomConfig, limitTo: List<String> = emptyList()) {
    implementation(enforcedPlatform(config.platform))
    config.fullTargets
        .filter { if (limitTo.isEmpty()) true else limitTo.contains(it.key) }
        .forEach {
            implementation(it.value)
        }
}