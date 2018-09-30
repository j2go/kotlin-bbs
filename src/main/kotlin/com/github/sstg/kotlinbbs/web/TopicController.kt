package com.github.sstg.kotlinbbs.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/topic")
class TopicController {

    @GetMapping
    fun index() = "topic/index"

    @GetMapping("/add")
    fun add() = "topic/add"

    @GetMapping("/detail")
    fun detail() = "topic/detail"
}
