package com.github.sstg.kotlinbbs.web

import com.github.sstg.kotlinbbs.domain.UserInfo
import com.github.sstg.kotlinbbs.domain.UserInfoRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class IndexController(val userInfoRepository: UserInfoRepository) {

    @GetMapping("/")
    fun index() = "index"

    @GetMapping("/login")
    fun login() = "user/login"

    @GetMapping("/reg")
    fun reg() = "user/reg"

    @PostMapping("/reg")
    fun register(@RequestParam email: String, @RequestParam username: String, @RequestParam pass: String): String {
        val userInfo = UserInfo()
        userInfo.email = email
        userInfo.name = username
        userInfo.passwd = BCryptPasswordEncoder().encode(pass)
        userInfo.authorities = "USER"

        userInfoRepository.save(userInfo)
        return "redirect:/login"
    }
}