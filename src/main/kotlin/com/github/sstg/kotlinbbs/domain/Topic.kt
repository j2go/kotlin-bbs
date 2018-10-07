package com.github.sstg.kotlinbbs.domain

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table
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

@Repository
interface TopicRepository : PagingAndSortingRepository<Topic, Long> {
    fun findByTypeAndStatus(type: Int, status: Int, pageable: Pageable): Page<Topic>
    fun findByStatus(status: Int, pageable: Pageable): Page<Topic>
}