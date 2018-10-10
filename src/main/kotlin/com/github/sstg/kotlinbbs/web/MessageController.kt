package com.github.sstg.kotlinbbs.web

import com.github.sstg.kotlinbbs.domain.MessageRepository
import com.github.sstg.kotlinbbs.util.AuthUtil
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/message")
class MessageController(val messageRepository: MessageRepository) {

    @PostMapping("/num")
    fun num(@RequestParam time: Long): MessageResult {
        val userId = AuthUtil.currentUser().id

        return MessageResult(0, messageRepository.countByUserIdAndReaded(userId, false))
    }

    @PostMapping("/read")
    fun read(): MessageResult {
        val userId = AuthUtil.currentUser().id

        val unReadNum = messageRepository.countByUserIdAndReaded(userId, false)
        return if (unReadNum > 0) MessageResult(0, unReadNum) else MessageResult(-1, 0)
    }
}

data class MessageResult(val status: Int, val count: Long)