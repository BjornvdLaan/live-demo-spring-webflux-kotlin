package nl.bjornvanderlaan.livedemospringwebflux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringWebfluxKotlinFunctionalApplication

fun main(args: Array<String>) {
    runApplication<SpringWebfluxKotlinFunctionalApplication>(*args)
}
