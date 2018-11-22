package jl.pc.api.v0

import io.micronaut.http.annotation.Get
import io.reactivex.Maybe

interface ProductOperations {
    @Get("/{id}")
    fun getProduct(id: String): Maybe<Product>
}
