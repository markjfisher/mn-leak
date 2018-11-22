package jl.pc.v0.service

import io.micronaut.retry.annotation.Recoverable
import io.reactivex.Maybe
import jl.pc.api.v0.Product
import mu.KotlinLogging
import javax.inject.Singleton

private val logger = KotlinLogging.logger {}

// @Fallback
@Singleton
@Recoverable
class DefaultProductCatalogueService : ProductCatalogueService {
    override fun getProduct(id: String): Maybe<Product> {
        return Maybe.error(NotImplementedError("default not implemented yet"))
    }
}
