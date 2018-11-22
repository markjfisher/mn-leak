package jl.pc.v0.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.micronaut.retry.annotation.Fallback
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import jl.pc.api.v0.Product
import jl.pc.v0.Configuration
import mu.KotlinLogging
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Singleton

private val logger = KotlinLogging.logger {}

@Fallback
@Singleton
class StaticProductCatalogueService(private val config: Configuration) : ProductCatalogueService {

    override fun getProduct(id: String): Maybe<Product> {
        return if (config.latencyTesting) getProductLatencyTest(id) else Maybe.just(fixedProducts[id])
    }

    private fun getProductLatencyTest(id: String): Maybe<Product> {
        return Maybe
                .fromCallable {
                    try {
                        Thread.sleep(random.nextInt(1000).toLong())
                        fixedProducts[id]
                    } catch (ie: InterruptedException) {
                        null
                    }
                }
                .subscribeOn(Schedulers.io())
                .map { it }

    }

    companion object {
        private val fixedProducts = loadStaticProducts()

        private fun loadStaticProducts(): Map<String, Product> {
            val productsResource = StaticProductCatalogueService::class.java.getResource("/1-product-v0.json")
            val products: List<Product> = jacksonObjectMapper().readValue(productsResource.readBytes())
            logger.info { "read ${products.size} products into static cache" }
            return products.map { product -> product.id to product }.toMap(ConcurrentHashMap())
        }

        private val random = Random()
    }
}
