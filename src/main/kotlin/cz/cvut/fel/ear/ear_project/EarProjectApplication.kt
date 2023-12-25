package cz.cvut.fel.ear.ear_project

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication // (exclude = [SecurityAutoConfiguration::class])
class EarProjectApplication

fun main(args: Array<String>) {
    runApplication<EarProjectApplication>(*args)
}
