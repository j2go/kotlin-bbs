package com.github.sstg.kotlinbbs.web

import com.github.sstg.kotlinbbs.domain.*
import com.github.sstg.kotlinbbs.util.AuthUtil
import org.springframework.cglib.beans.BeanCopier
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Controller
class TopicController(val topicRepository: TopicRepository,
                      val userInfoRepository: UserInfoRepository,
                      val topicReplyRepository: TopicReplyRepository,
                      val userLikeRepository: UserLikeRepository) {

    val timeDesc = Sort.Order.desc("createTime")
    val top = Sort.Order.desc("isTop")
    val scoreDesc = Sort.Order.desc("score")

    @GetMapping("/topic", "/")
    fun list(@RequestParam(defaultValue = "0") type: Int,
             @RequestParam(defaultValue = "0") sort: Int,
             @RequestParam(defaultValue = "0") page: Int,
             @RequestParam(defaultValue = "10") size: Int): ModelAndView {
        val model = mutableMapOf<String, Any>()
        model["type"] = type
        model["sort"] = sort
        model["page"] = page + 1

        val pageRequest = pageRequestOfSort(sort, page, size)

        val topicPage = topicPageOfType(type, pageRequest)

        val userIds = topicPage.content.asSequence().map { it.userId }.toSet()
        val userMap = userMapOfUserIds(userIds)

        model["lastPage"] = topicPage.number
        model["hasNext"] = topicPage.hasNext()
        model["topics"] = topicPage.content.map {
            TopicDesc(it.id, TOPIC_TYPE[it.type]!!, it.title, it.isTop, it.isNice,
                    userMap[it.userId]!!, it.createTime, it.experience, it.replyNum)
        }
        return ModelAndView("topic/index", model)
    }

    private fun userMapOfUserIds(userIds: Set<Long>) =
            userInfoRepository.findByIdIn(userIds).map { it.id to it }.toMap()

    private fun pageRequestOfSort(sort: Int, page: Int, size: Int): PageRequest {
        return if (sort == 0)
            PageRequest.of(page, size, Sort.by(top, timeDesc))
        else
            PageRequest.of(page, size, Sort.by(top, scoreDesc))
    }

    private fun topicPageOfType(type: Int, pageRequest: PageRequest): Page<Topic> {
        return if (type > 0) {
            topicRepository.findByTypeAndStatus(type, 1, pageRequest)
        } else {
            topicRepository.findByStatus(1, pageRequest)
        }
    }

    @GetMapping("/topic/add")
    fun add() = "topic/add"

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
        //TODO 处理不存在的异常
        val topic = topicRepository.findById(id).get()
        topic.readNum += 1
        topicRepository.save(topic)

        map["topic"] = topic
        map["sort"] = 0
        map["type"] = topic.type

        val currentUser = AuthUtil.currentUser()
        map["isAdmin"] = currentUser.authorities.contains("ADMIN")

        if (currentUser.id == topic.userId) {
            map["ofMine"] = true
            map["author"] = currentUser
        } else {
            map["ofMine"] = false
            map["author"] = userInfoRepository.findById(topic.userId).get()
        }

        var replies = topicReplyRepository.findByTopicIdAndStatus(topic.id, 1)
        map["replyNum"] = replies.size

        val userIds = replies.asSequence().map { it.userId }.toSet()
        val userMap = userMapOfUserIds(userIds)

        val likedReplySet = userLikeRepository.findByUserIdAndType(currentUser.id, 2).asSequence().map { it.targetId }.toSet()

        map["replies"] = replies.map { ReplyDto(it, userMap[it.userId]!!, likedReplySet.contains(it.id), it.userId == currentUser.id) }

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

data class ReplyDto(val data: TopicReply, val user: UserInfo, val liked: Boolean, val canEdit: Boolean)

data class TopicDesc(
        val id: Long,
        val typeName: String,
        val title: String,
        val isTop: Boolean,
        val isNice: Boolean,
        val user: UserInfo,
        val createTime: Date,
        val experience: Int,
        val replyNum: Int
)