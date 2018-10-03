package com.github.sstg.kotlinbbs.domain

import org.springframework.data.repository.CrudRepository
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
    var isTop = false
    var isNice = false

    var status = 1
    var createTime = Date()
    var lastModifyTime = Date()
}

@Repository
interface TopicRepository : CrudRepository<Topic, Long>