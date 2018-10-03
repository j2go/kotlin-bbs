package com.github.sstg.kotlinbbs.web

import com.github.sstg.kotlinbbs.domain.Topic
import com.github.sstg.kotlinbbs.domain.TopicReplyRepository
import com.github.sstg.kotlinbbs.domain.TopicRepository
import com.github.sstg.kotlinbbs.domain.UserInfoRepository
import com.github.sstg.kotlinbbs.util.AuthUtil
import org.springframework.cglib.beans.BeanCopier
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class TopicController(val topicRepository: TopicRepository,
                      val userInfoRepository: UserInfoRepository,
                      val topicReplyRepository: TopicReplyRepository) {

    @GetMapping("/topic")
    fun index() = "topic/index"

    @GetMapping("/topic/add")
    fun add() = "topic/add"

    @GetMapping("/detail")
    fun detail() = "topic/detail"

    val topicCopier = BeanCopier.create(TopicForm::class.java, Topic::class.java, false)!!

    @PostMapping("/topics")
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

        val currentUser = AuthUtil.currentUser()
        map["isAdmin"] = currentUser.authorities.contains("ADMIN")

        if (currentUser.id == topic.get().userId) {
            map["ofMine"] = true
            map["author"] = currentUser
        } else {
            map["ofMine"] = true
            map["author"] = userInfoRepository.findById(topic.get().userId).get()
        }

        var replies = topicReplyRepository.findByTopicId(topic.get().id)
        map["replyNum"] = replies.size

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
