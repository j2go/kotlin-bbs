package com.github.sstg.kotlinbbs.service

import com.github.sstg.kotlinbbs.domain.Message
import com.github.sstg.kotlinbbs.domain.MessageRepository
import com.github.sstg.kotlinbbs.domain.MessageSource
import com.github.sstg.kotlinbbs.domain.UserInfoRepository
import com.github.sstg.kotlinbbs.event.TopicReplyEvent
import com.github.sstg.kotlinbbs.util.AuthUtil
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class MessageService(val messageRepository: MessageRepository,
                     val userInfoRepository: UserInfoRepository) {

    @EventListener
    @Async
    fun handleReply(event: TopicReplyEvent) {
        val topic = event.topic
        if (AuthUtil.currentUser().id == topic.userId) {
            return
        }
        val message = Message()
        message.source = MessageSource.USER
        message.readed = false

        message.topicId = topic.id
        message.userId = topic.userId

        val reply = event.reply
        message.fromUserId = reply.userId

        messageRepository.save(message)

        val user = userInfoRepository.findById(reply.userId).get()
        message.content = "<a href='/user/home?username=${user.name}' target='_blank'><cite>${user.name}</cite></a>" +
                "在<a href='/topic/${topic.id}?messageId=${message.id}' target='_blank'><cite>${topic.title}</cite></a>中回答：" +
                "<a target='_blank' href='/topic/${topic.id}?messageId=${message.id}#item-${reply.id}'><cite>${reply.content}</cite></a>"

        messageRepository.save(message)
    }

    fun read(id: Long) {
        messageRepository.findById(id).ifPresent {
            if (!it.readed) {
                it.readed = true
                messageRepository.save(it)
            }
        }
    }
}