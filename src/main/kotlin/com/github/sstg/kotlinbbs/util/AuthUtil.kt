package com.github.sstg.kotlinbbs.util

import com.github.sstg.kotlinbbs.domain.User
import org.springframework.security.core.context.SecurityContextHolder

object AuthUtil {

    fun currentUser() = SecurityContextHolder.getContext().authentication.principal as User

    fun isAdmin(): Boolean {
        val info = SecurityContextHolder.getContext().authentication.principal
        if (info is User) {
            return info.authorities.contains("ADMIN")
        }
        return false
    }
}
