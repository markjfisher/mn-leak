package jl.pc.v0

import io.micronaut.runtime.Micronaut
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.annotations.servers.ServerVariable
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
