package jl.pc.api.v0

import com.fasterxml.jackson.annotation.JsonProperty

data class Product(
    @JsonProperty("productId") val id: String,
    val title: String
)

