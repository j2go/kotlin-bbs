package com.github.sstg.kotlinbbs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinBBSApplication

fun main(args: Array<String>) {
    runApplication<KotlinBBSApplication>(*args)
}
