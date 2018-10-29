package com.github.sstg.kotlinbbs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class KotlinBBSApplication

fun main(args: Array<String>) {
    runApplication<KotlinBBSApplication>(*args)
}
