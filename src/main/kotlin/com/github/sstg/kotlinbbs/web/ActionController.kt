package com.github.sstg.kotlinbbs.web

import com.github.sstg.kotlinbbs.domain.*
import com.github.sstg.kotlinbbs.util.AuthUtil
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/action")
class ActionController(val topicRepository: TopicRepository,
                       val topicReplyRepository: TopicReplyRepository,
                       val userLikeRepository: UserLikeRepository,
                       val userCollectRepository: UserCollectRepository,
                       val userRepository: UserRepository) {

    @PostMapping("/like")
    fun like(@RequestParam type: Int, @RequestParam id: Long): ActionResult {
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
            return ActionResult(0, "")
        }
        return ActionResult(-1, "不支持的操作")
    }

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

    @PostMapping("/topic-set")
    fun setTopic(@RequestParam id: Long, @RequestParam rank: Boolean, @RequestParam field: String): ActionResult {
        if (!AuthUtil.isAdmin()) {
            return ActionResult(-99, "需要管理员权限")
        }
        val topic = topicRepository.findById(id).get()
        if (field == "top") {
            topic.isTop = rank
        } else if (field == "nice") {
            topic.isNice = rank
        }
        topicRepository.save(topic)
        return ActionResult(0, "")
    }

    @PostMapping("/topic-del")
    fun delTopic(@RequestParam id: Long): ActionResult {
        if (!AuthUtil.isAdmin()) {
            return ActionResult(-99, "需要管理员权限")
        }
        val topic = topicRepository.findById(id).get()
        topic.status = 4
        topicRepository.save(topic)
        return ActionResult(0, "")
    }

    @PostMapping("/reply-accept")
    @Transactional
    fun acceptTopic(@RequestParam id: Long): ActionResult {

        val reply = topicReplyRepository.findById(id).get()
        val topic = topicRepository.findById(reply.topicId).get()
        val curUser = AuthUtil.currentUser()
        if (topic.userId != curUser.id) {
            return ActionResult(-99, "不是楼主不能采纳答案")
        }
        if (reply.userId == topic.userId) {
            return ActionResult(-98, "不能采纳自己的答案")
        }
        val replyUser = userRepository.findById(reply.userId).get()
        reply.helpful = true
        topicReplyRepository.save(reply)

        curUser.experience -= topic.experience
        replyUser.experience += topic.experience

        userRepository.save(curUser)
        userRepository.save(replyUser)
        return ActionResult(0, "")
    }
}

data class ActionResult(val status: Int, val msg: String)
data class CollectResult(val status: Int, val collected: Boolean)