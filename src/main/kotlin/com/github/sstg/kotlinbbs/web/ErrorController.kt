package com.github.sstg.kotlinbbs.web

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/error")
class MyErrorController : ErrorController {

    override fun getErrorPath() = "/error"

    @GetMapping(produces = ["text/html"])
    fun errorHtml(rep: HttpServletResponse) = if (rep.status == 404) "other/404" else "other/tips"
    
}