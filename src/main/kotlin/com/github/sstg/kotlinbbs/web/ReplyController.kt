package com.github.sstg.kotlinbbs.web

import com.github.sstg.kotlinbbs.domain.TopicReply
import com.github.sstg.kotlinbbs.domain.TopicReplyRepository
import com.github.sstg.kotlinbbs.domain.TopicRepository
import com.github.sstg.kotlinbbs.util.AuthUtil
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ReplyController(val topicRepository: TopicRepository,
                      val topicReplyRepository: TopicReplyRepository) {

    //    @RequestMapping("/reply", method = [RequestMethod.POST])
    @PostMapping("/reply")
    fun replyTopic(@RequestParam id: Long, @RequestParam content: String): Result {
        val topic = topicRepository.findById(id).get()
        topic.replyNum += 1
        topicRepository.save(topic)

        val reply = TopicReply()
        reply.topicId = id
        reply.content = content
        reply.userId = AuthUtil.currentUser().id
        topicReplyRepository.save(reply)

        return Result(0, "")
    }

    @PostMapping("/reply/{id}")
    fun getReply(@PathVariable id: Long): ReplyResult {
        val reply = topicReplyRepository.findById(id).get()
        return ReplyResult(0, reply.content)
    }

    @PostMapping("/reply/update")
    fun updateReply(@RequestParam id: Long, @RequestParam content: String): Result {
        val reply = topicReplyRepository.findById(id).get()
        reply.content = content
        reply.lastModifyTime = Date()
        topicReplyRepository.save(reply)
        return Result(0, "")
    }

    @PostMapping("/reply/delete")
    fun deleteReply(@RequestParam id: Long): Result {
        val reply = topicReplyRepository.findById(id).get()
        if (reply.userId != AuthUtil.currentUser().id) {
            return Result(-2, "不能删除别人的回复")
        }
        reply.status = 4
        topicReplyRepository.save(reply)

        val topic = topicRepository.findById(reply.topicId).get()
        topic.replyNum -= 1
        topicRepository.save(topic)

        return Result(0, "")
    }
}

data class ReplyResult(val status: Int, val content: String)