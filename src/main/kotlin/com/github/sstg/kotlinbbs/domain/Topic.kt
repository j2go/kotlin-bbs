package com.github.sstg.kotlinbbs.domain

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table(indexes = [Index(name = "idx_user", columnList = "userId")])
class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = 0L

    var userId = 0L

    // 1 提问，2 分享
    var type = 0
    var title = ""
    var project = ""
    var version = ""
    var browser = ""
    var content = ""
    var experience = 0

    var readNum = 0
    var replyNum = 0
    var likeNum = 0
    // 置顶
    var isTop = false
    // 加精
    var isNice = false

    // 1 有效，4 删除
    var status = 1
    // 热度评分
    var score = 0
    var createTime = Date()
    var lastModifyTime = Date()
}

val TOPIC_TYPE = mapOf(1 to "提问", 2 to "分享", 3 to "讨论", 4 to "建议", 5 to "公告", 6 to "动态")

@Repository
interface TopicRepository : PagingAndSortingRepository<Topic, Long> {
    fun findByTypeAndStatus(type: Int, status: Int, pageable: Pageable): Page<Topic>
    fun findByStatus(status: Int, pageable: Pageable): Page<Topic>
    fun findByUserIdAndStatus(userId: Long, status: Int): List<Topic>
}