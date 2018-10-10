package com.github.sstg.kotlinbbs.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table
class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = 0L

    var userId = 0L
    var content = ""
    var readed = false

    var createTime = Date()

    var expireTime = Date()
}

@Repository
interface MessageRepository : CrudRepository<Message, Long> {
    fun findByUserId(userId: Long): List<Message>
    fun findByUserIdAndReaded(userId: Long, read: Boolean): List<Message>

    fun countByUserIdAndReaded(userId: Long, read: Boolean): Long
}