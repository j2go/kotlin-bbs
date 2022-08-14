package com.github.sstg.kotlinbbs.web

import com.github.sstg.kotlinbbs.domain.*
import com.github.sstg.kotlinbbs.util.AuthUtil
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Controller
@RequestMapping("/user")
class UserController(val userRepository: UserRepository,
                     val topicRepository: TopicRepository,
                     val topicReplyRepository: TopicReplyRepository,
                     val userCollectRepository: UserCollectRepository,
                     val messageRepository: MessageRepository) {

    /**
     * 用户登录页
     */
    @GetMapping("/login")
    fun login() = "user/login"

    /**
     * 用户注册页
     */
    @GetMapping("/reg")
    fun reg() = "user/reg"

    /**
     * 提交注册信息
     */
    @PostMapping("/reg")
    fun register(@RequestParam email: String, @RequestParam name: String, @RequestParam pass: String): String {
        val user = User()
        user.email = email
        user.name = name
        user.passwd = BCryptPasswordEncoder().encode(pass)
        user.authorities = "USER"

        userRepository.save(user)
        return "redirect:/"
    }

    /**
     * 我的主页
     */
    @GetMapping("/home", "")
    fun home(): ModelAndView {

        val model = mutableMapOf<String, Any>()
        model["isAdmin"] = AuthUtil.isAdmin()

        val curUserId = AuthUtil.currentUser().id
        val page = PageRequest.of(0, 5, Sort.by("createTime").descending())
        val topics = topicRepository.findByTypeAndUserIdAndStatus(TopicType.QUESTION, curUserId, Status.SHOW, page).content
        model["topics"] = topics

        val replies = topicReplyRepository.findByUserIdAndStatus(curUserId, Status.SHOW, page).content
        val topicIds = replies.map { it.topicId }
        val topicIdTitleMap = topicRepository.findAllById(topicIds).map { it.id to it.title }.toMap()
        model["replies"] = replies.map { ReplyDesc(it.topicId, topicIdTitleMap[it.topicId]!!, it.content, it.createTime) }

        return ModelAndView("user/home", model)
    }

    /**
     * 个人信息设置页
     */
    @GetMapping("/setting")
    fun set() = "user/set"

    /**
     * 我的消息
     */
    @GetMapping("/message")
    fun message(): ModelAndView {
        val model = mutableMapOf<String, Any>()

        val currentUserId = AuthUtil.currentUser().id
        model["messages"] = messageRepository.findByUserIdAndReadied(currentUserId, false)
        return ModelAndView("user/message", model)
    }

    /**
     * 我发的贴 & 我收藏的贴
     */
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

    @PostMapping("/info")
    @ResponseBody
    fun updateInfo(@RequestParam name: String,
                   @RequestParam sex: String,
                   @RequestParam city: String,
                   @RequestParam sign: String): ActionResult {
        val currentUser = AuthUtil.currentUser()
        currentUser.name = name
        currentUser.sex = sex
        currentUser.city = city
        currentUser.sign = sign
        userRepository.save(currentUser)
        return ActionResult(0, "")
    }
}

data class Collection(val id: Long, val title: String, val time: Date)

data class ReplyDesc(val topicId: Long, val title: String, val content: String, val time: Date)