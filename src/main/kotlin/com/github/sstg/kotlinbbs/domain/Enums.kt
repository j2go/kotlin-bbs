package com.github.sstg.kotlinbbs.domain

object Status {
    const val SHOW = 1
    const val DELETE = 4
}

object TopicType {
    const val QUESTION = 1
    const val SHARE = 2
    const val DISCUSS = 3
    const val SUGGESTION = 4
    const val POST = 5
    const val NOTIFICATION = 6

    private val map = mapOf(1 to "提问", 2 to "分享", 3 to "讨论", 4 to "建议", 5 to "公告", 6 to "动态")

    fun getStr(key: Int): String {
        return map[key] ?: ""
    }
}