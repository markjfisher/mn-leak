package jl.pc.v0

import io.micronaut.runtime.Micronaut
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import mu.KLogging

class ProductCatalogueApplication {
    companion object : KLogging() {
        @JvmStatic
        fun main(args: Array<String>) {
            Micronaut.build()
                    .packages("jl.pc.v0")
                    .mainClass(ProductCatalogueApplication::class.java)
                    .start()
        }
    }

    @EventListener
    fun onStartup(e: ServerStartupEvent) {
        logger.info { "starting product catalogue service for url: ${e.source.url}" }
    }

}
