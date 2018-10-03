package com.github.sstg.kotlinbbs.web

import com.github.sstg.kotlinbbs.domain.TopicReply
import com.github.sstg.kotlinbbs.domain.TopicReplyRepository
import com.github.sstg.kotlinbbs.domain.TopicRepository
import com.github.sstg.kotlinbbs.util.AuthUtil
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ReplyController(val topicRepository: TopicRepository,
                      val topicReplyRepository: TopicReplyRepository) {

    @PostMapping("/topic/{id}/reply")
    fun replyTopic(@PathVariable id: Long, @RequestParam content: String): Long {
        if (!topicRepository.findById(id).isPresent) {
            throw RuntimeException("帖子不存在")
        }
        val reply = TopicReply()
        reply.topicId = id
        reply.content = content
        reply.userId = AuthUtil.currentUser().id

        return topicReplyRepository.save(reply).id
    }
}