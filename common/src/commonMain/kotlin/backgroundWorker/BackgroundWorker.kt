package de.niblette.common

interface Module {
    val backgroundWorker: List<BackgroundWorker> get() = emptyList()
}

@OptIn(ExperimentalStdlibApi::class)
interface BackgroundWorker : AutoCloseable {
    fun start()
}