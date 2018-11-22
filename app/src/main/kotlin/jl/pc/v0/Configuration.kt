package jl.pc.v0

import io.micronaut.context.annotation.ConfigurationProperties
import jl.pc.v0.Configuration.Companion.PREFIX

@ConfigurationProperties(PREFIX)
class Configuration {
    companion object {
        const val PREFIX = "product-catalogue"
    }

    var controllerClientTimeout = 500L
    var latencyTesting = false
}