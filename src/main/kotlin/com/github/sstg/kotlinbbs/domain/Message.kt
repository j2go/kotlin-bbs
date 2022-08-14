package com.github.sstg.kotlinbbs.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table(indexes = [Index(name = "idx_user_read", columnList = "userId,readied")])
class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = 0L

    var userId = 0L
    var fromUserId = 0L
    var topicId = 0L
    var source = 0
    var content = ""
    var readied = false

    var createTime = Date()

    var expireTime = Date()
}

object MessageSource {
    const val USER = 1
    const val SYSTEM = 2
}

@Repository
interface MessageRepository : CrudRepository<Message, Long> {
    fun findByUserId(userId: Long): List<Message>

    fun findByUserIdAndReadied(userId: Long, read: Boolean): List<Message>

    fun countByUserIdAndReadied(userId: Long, read: Boolean): Long
}