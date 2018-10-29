package com.github.sstg.kotlinbbs.event

import com.github.sstg.kotlinbbs.domain.Topic
import com.github.sstg.kotlinbbs.domain.TopicReply

data class TopicCreateEvent(val id: Long, val userId: Long)

data class TopicUpdateEvent(val id: Long, val userId: Long)

data class TopicReplyEvent(val topic: Topic, val reply: TopicReply)