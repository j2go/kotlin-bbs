package com.github.sstg.kotlinbbs.web

import com.github.sstg.kotlinbbs.domain.*
import com.github.sstg.kotlinbbs.util.AuthUtil
import org.springframework.cglib.beans.BeanCopier
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@Controller
class TopicController(val topicRepository: TopicRepository,
                      val userInfoRepository: UserInfoRepository,
                      val topicReplyRepository: TopicReplyRepository) {

    @GetMapping("/topic", "/")
    fun index(@RequestParam(defaultValue = "0") type: Int,
              @RequestParam(defaultValue = "0") sort: Int,
              @RequestParam(defaultValue = "0") page: Int,
              @RequestParam(defaultValue = "10") size: Int): ModelAndView {
        val model = mutableMapOf<String, Any>()
        model["type"] = type
        model["sort"] = sort
        model["page"] = page + 1

        Sort(Sort.Direction.DESC, "createTime")
        val pageRequest =
                if (sort == 0)
                    PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"))
                else
                    PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "score"))

        model["topicPage"] =
                if (type > 0) {
                    topicRepository.findByTypeAndStatus(type, 1, pageRequest)
                } else {
                    topicRepository.findByStatus(1, pageRequest)
                }
        return ModelAndView("topic/index", model)
    }

    @GetMapping("/topic/add")
    fun add() = "topic/add"

    @GetMapping("/detail")
    fun detail() = "topic/detail"


    val topicCopier = BeanCopier.create(TopicForm::class.java, Topic::class.java, false)!!

    @PostMapping("/topic")
    fun newTopic(@ModelAttribute topicForm: TopicForm): String {
        val topic = Topic()
        topicCopier.copy(topicForm, topic, null)
        topic.userId = AuthUtil.currentUser().id

        val topicId = topicRepository.save(topic).id

        return "redirect:/topic/$topicId"
    }

    @GetMapping("/topic/{id}")
    fun getTopics(@PathVariable id: Long): ModelAndView {
        val map = mutableMapOf<String, Any>()
        val topic = topicRepository.findById(id)
        if (!topic.isPresent) {
            throw RuntimeException("主题不存在")
        }
        map["topic"] = topic.get()
        map["sort"] = 0
        map["type"] = topic.get().type

        val currentUser = AuthUtil.currentUser()
        map["isAdmin"] = currentUser.authorities.contains("ADMIN")

        if (currentUser.id == topic.get().userId) {
            map["ofMine"] = true
            map["author"] = currentUser
        } else {
            map["ofMine"] = false
            map["author"] = userInfoRepository.findById(topic.get().userId).get()
        }

        var replies = topicReplyRepository.findByTopicId(topic.get().id)
        map["replyNum"] = replies.size


        val userIds = replies.map { it.userId }
        val userMap = userInfoRepository.findByIdIn(userIds).map { it.id to it }.toMap()
        map["replies"] = replies.map { ReplyDto(it, userMap[it.userId]!!) }

        return ModelAndView("topic/detail", map)
    }

}


class TopicForm {
    var type = 0
    var title = ""
    var project = ""
    var version = ""
    var browser = ""
    var content = ""
    var experience = 0
}

data class ReplyDto(
        val data: TopicReply,
        val user: UserInfo
)
