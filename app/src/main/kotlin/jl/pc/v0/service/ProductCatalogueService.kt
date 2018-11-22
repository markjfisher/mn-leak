package jl.pc.v0.service

import io.reactivex.Maybe
import jl.pc.api.v0.Product

interface ProductCatalogueService {
    fun getProduct(id: String): Maybe<Product>
}
