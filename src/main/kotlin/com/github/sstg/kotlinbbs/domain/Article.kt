package com.github.sstg.kotlinbbs.domain

import org.springframework.data.repository.CrudRepository
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = 0L

    var type = 0
    var title = ""
    var content = ""
    var userId = 0L
    var top = 0
    var status = 0
    var createTime = Date()
    var updateTime = Date()
    var lastReplyTime = Date()
    var lastReplyUserId = 0L
    var lastReplyId = 0L
    var replyCount = 0
    var viewCount = 0
    var likeCount = 0
    var dislikeCount = 0
    var collectCount = 0
}

interface ArticleRepository : CrudRepository<Article, Long> {
    fun findByTypeAndStatus(type: Int, status: Int): List<Article>
}

@Entity
class ArticleNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = 0L

    var articleId = 0L
    var seq = 0L
    var type = 0
    var content = ""
    var status = 0
    var createTime = Date()
    var updateTime = Date()
}

interface ArticleNodeRepository : CrudRepository<ArticleNode, Long> {
    fun findByArticleIdAndStatus(articleId: Long, status: Int): List<ArticleNode>
}

@Entity
class ArticleNodeEdit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = 0L

    var articleId = 0L
    var preNodeId = 0L
    var nextNodeId = 0L
    var type = 0
    var content = ""
    var status = 0
    var createTime = Date()
    var updateTime = Date()
}

interface ArticleNodeEditRepository : CrudRepository<ArticleNodeEdit, Long> {
    fun findByArticleIdAndStatus(articleId: Long, status: Int): List<ArticleNodeEdit>
}