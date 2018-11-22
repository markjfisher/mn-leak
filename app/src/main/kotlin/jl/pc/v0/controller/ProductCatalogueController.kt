package jl.pc.v0.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.validation.Validated
import io.reactivex.Maybe
import jl.pc.api.v0.Product
import jl.pc.api.v0.ProductOperations
import jl.pc.v0.Configuration
import jl.pc.v0.service.ProductCatalogueService
import mu.KotlinLogging
import java.util.concurrent.TimeUnit
import javax.validation.Valid

private val logger = KotlinLogging.logger {}

@Validated
@Controller("/v0/product")
class ProductCatalogueController(private val service: ProductCatalogueService,
                                 private val config: Configuration) : ProductOperations {

    override fun getProduct(@Valid id: String): Maybe<Product> {
        logger.debug { "ProductCatalogueController.getProduct($id)"}
        return if(config.latencyTesting) getProductLatencyTest(id) else service.getProduct(id)
    }

    private fun getProductLatencyTest(id: String): Maybe<Product> {
        return Maybe.just(id)
                .flatMap {
                    service.getProduct(it)
                            .timeout(config.controllerClientTimeout, TimeUnit.MILLISECONDS, Maybe.empty())
                }.toObservable()
                .singleElement()
    }

}
