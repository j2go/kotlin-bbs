package com.github.sstg.kotlinbbs.web

import com.github.sstg.kotlinbbs.domain.*
import com.github.sstg.kotlinbbs.util.AuthUtil
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/action")
class ActionController(val topicRepository: TopicRepository,
                       val topicReplyRepository: TopicReplyRepository,
                       val userLikeRepository: UserLikeRepository,
                       val userCollectRepository: UserCollectRepository) {

    @PostMapping("/like")
    fun like(@RequestParam type: Int, @RequestParam id: Long): Result {
        val curUserId = AuthUtil.currentUser().id
        if (type == 2) {
            val topicReply = topicReplyRepository.findById(id).get()
            val entity = userLikeRepository.findByUserIdAndTypeAndTargetId(curUserId, type, id)
            if (entity != null) {
                userLikeRepository.delete(entity)

                topicReply.likeNum -= 1
            } else {
                val userLike = UserLike()
                userLike.type = 2
                userLike.targetId = id
                userLike.userId = curUserId
                userLikeRepository.save(userLike)

                topicReply.likeNum += 1
            }
            topicReplyRepository.save(topicReply)
            return Result(0, "")
        }
        return Result(-1, "不支持的操作")
    }

    // 是否有收藏权限
    @PostMapping("/collection")
    fun collection(@RequestParam cid: Long): CollectResult {
        val userId = AuthUtil.currentUser().id

        val collection = userCollectRepository.findByUserIdAndTopicId(userId, cid)
        return if (collection != null) {
            CollectResult(0, true)
        } else {
            CollectResult(0, false)
        }
    }


    @PostMapping("/collection/add")
    fun addCollection(@RequestParam cid: Long): CollectResult {
        val userId = AuthUtil.currentUser().id

        val collection = userCollectRepository.findByUserIdAndTopicId(userId, cid)
        if (collection != null) {
            return CollectResult(-1, true)
        }
        val userCollect = UserCollect()
        userCollect.userId = userId
        userCollect.topicId = cid

        userCollectRepository.save(userCollect)
        return CollectResult(0, true)
    }

    @PostMapping("/collection/remove")
    fun removeCollection(@RequestParam cid: Long): CollectResult {
        val userId = AuthUtil.currentUser().id

        val collection = userCollectRepository.findByUserIdAndTopicId(userId, cid)
        if (collection != null) {
            userCollectRepository.delete(collection)
            return CollectResult(0, false)
        }
        return CollectResult(-1, false)
    }
}

data class Result(val status: Int, val msg: String)
data class CollectResult(val status: Int, val collected: Boolean)