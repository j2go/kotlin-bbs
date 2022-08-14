package com.github.sstg.kotlinbbs.web

import com.github.sstg.kotlinbbs.domain.MessageRepository
import com.github.sstg.kotlinbbs.util.AuthUtil
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/message")
class MessageController(val messageRepository: MessageRepository) {

    @PostMapping("/num")
    fun num(@RequestParam time: Long): MessageResult {
        val userId = AuthUtil.currentUser().id

        return MessageResult(0, messageRepository.countByUserIdAndReadied(userId, false))
    }

    @PostMapping("/read")
    fun read(): MessageResult {
        val userId = AuthUtil.currentUser().id

        val unReadNum = messageRepository.countByUserIdAndReadied(userId, false)
        return if (unReadNum > 0) MessageResult(0, unReadNum) else MessageResult(-1, 0)
    }

    @PostMapping("/remove/{id}")
    fun remove(@PathVariable id: Long): ActionResult {
        val message = messageRepository.findById(id)
        return if (message.isPresent) {
            message.get().readied = true
            messageRepository.save(message.get())
            ActionResult(0, "")
        } else
            ActionResult(-1, "消息不存在，id： $id")
    }
}

data class MessageResult(val status: Int, val count: Long)