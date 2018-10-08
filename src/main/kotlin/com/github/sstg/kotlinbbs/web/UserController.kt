package com.github.sstg.kotlinbbs.web

import com.github.sstg.kotlinbbs.domain.UserInfo
import com.github.sstg.kotlinbbs.domain.UserInfoRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/user")
class UserController(val userInfoRepository: UserInfoRepository) {

    @GetMapping("/login")
    fun login() = "user/login"

    @GetMapping("/reg")
    fun reg() = "user/reg"

    @PostMapping("/reg")
    fun register(@RequestParam email: String, @RequestParam name: String, @RequestParam pass: String): String {
        val userInfo = UserInfo()
        userInfo.email = email
        userInfo.name = name
        userInfo.passwd = BCryptPasswordEncoder().encode(pass)
        userInfo.authorities = "USER"

        userInfoRepository.save(userInfo)
        return "redirect:/"
    }

    @GetMapping("/home", "")
    fun home() = "user/home"

    @GetMapping("/setting")
    fun set() = "user/set"

    @GetMapping("/message")
    fun message() = "user/message"

    @GetMapping("/index")
    fun index() = "user/index"
}