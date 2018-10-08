package com.github.sstg.kotlinbbs.web

import com.github.sstg.kotlinbbs.domain.TopicReply
import com.github.sstg.kotlinbbs.domain.TopicReplyRepository
import com.github.sstg.kotlinbbs.domain.TopicRepository
import com.github.sstg.kotlinbbs.util.AuthUtil
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class ReplyController(val topicRepository: TopicRepository,
                      val topicReplyRepository: TopicReplyRepository) {

    //    @RequestMapping("/reply", method = [RequestMethod.POST])
    @PostMapping("/reply")
    fun replyTopic(@RequestParam id: Long, @RequestParam content: String): String {
        val topic = topicRepository.findById(id).get()
        topic.replyNum += 1
        topicRepository.save(topic)

        val reply = TopicReply()
        reply.topicId = id
        reply.content = content
        reply.userId = AuthUtil.currentUser().id
        topicReplyRepository.save(reply)

        return "redirect:/topic/$id"
    }
}