package de.niblette.common

import kotlinx.coroutines.runBlocking
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

abstract class Application : BackgroundWorker {

    private val backgroundWorkers = arrayListOf<BackgroundWorker>()

    fun <A : Module> module(module: () -> A): A =
        module().also { backgroundWorkers += it.backgroundWorker }

    override fun start() {
        logger.info("Registering shutdown")
        Runtime.getRuntime().addShutdownHook(kotlin.concurrent.thread(start = false) {
            runBlocking {
                logger.info("Shutting down...")
                close()
            }
        })
        backgroundWorkers.forEach { worker ->
            logger.info("Starting background worker ${worker::class.java.name}")
            worker.start()
        }
    }

    override fun close() {
        backgroundWorkers
            .reversed()
            .forEach { worker ->
                logger.info("Stopping background worker ${worker::class.java.name}")
                worker.close()
            }
    }
}