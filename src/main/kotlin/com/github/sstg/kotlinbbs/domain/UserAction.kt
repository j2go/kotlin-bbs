package com.github.sstg.kotlinbbs.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table(indexes = [Index(name = "idx_like", columnList = "userId,type,targetId", unique = true)])
class UserLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = 0L

    var userId = 0L
    // 1 帖子， 2 回复， 11 用户
    var type = 0
    var targetId = 0L

    var createTime = Date()
}

@Repository
interface UserLikeRepository : CrudRepository<UserLike, Long> {
    fun findByUserIdAndTypeAndTargetId(userId: Long, type: Int, targetId: Long): UserLike?
    fun findByUserIdAndType(userId: Long, type: Int): List<UserLike>
}


@Entity
@Table(indexes = [Index(name = "idx_collect", columnList = "userId,topicId", unique = true)])
class UserCollect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = 0L

    var userId = 0L
    var topicId = 0L

    var createTime = Date()
}

@Repository
interface UserCollectRepository : CrudRepository<UserCollect, Long> {
    fun findByUserId(userId: Long): List<UserCollect>
    fun findByUserIdAndTopicId(userId: Long, topicId: Long): UserCollect?
}