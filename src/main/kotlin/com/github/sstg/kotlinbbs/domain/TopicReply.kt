package com.github.sstg.kotlinbbs.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table
class TopicReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = 0L

    var topicId = 0L
    var userId = 0L
    var toUserId = 0L

    var content = ""
    var experience = 0

    var helpful = false
    var status = 1

    var createTime = Date()
    var lastModifyTime = Date()
}

@Repository
interface TopicReplyRepository : CrudRepository<TopicReply, Long> {
    fun findByTopicId(id: Long): List<TopicReply>
    fun findByTopicIdAndStatus(id: Long, status: Int): List<TopicReply>
}