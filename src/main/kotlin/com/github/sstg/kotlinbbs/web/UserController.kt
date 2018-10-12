package com.github.sstg.kotlinbbs.web

import com.github.sstg.kotlinbbs.domain.TopicRepository
import com.github.sstg.kotlinbbs.domain.UserCollectRepository
import com.github.sstg.kotlinbbs.domain.UserInfo
import com.github.sstg.kotlinbbs.domain.UserInfoRepository
import com.github.sstg.kotlinbbs.util.AuthUtil
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Controller
@RequestMapping("/user")
class UserController(val userInfoRepository: UserInfoRepository,
                     val topicRepository: TopicRepository,
                     val userCollectRepository: UserCollectRepository) {

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
    fun index(): ModelAndView {
        val model = mutableMapOf<String, Any>()
        val currentUserId = AuthUtil.currentUser().id

        val topicsOfMine = topicRepository.findByUserIdAndStatus(currentUserId, 1)
        model["myTopics"] = topicsOfMine

        val collections = userCollectRepository.findByUserId(currentUserId)
        val idTitleMap = topicRepository.findAllById(collections.map { it.topicId })
                .map { it.id to it.title }
                .toMap()
        model["myCollections"] = collections.asSequence()
                .map { Collection(it.topicId, idTitleMap[it.topicId]!!, it.createTime) }
                .sortedByDescending { it.time }.toList()
        return ModelAndView("user/index", model)
    }
}

data class Collection(val id:Long, val title: String, val time: Date)