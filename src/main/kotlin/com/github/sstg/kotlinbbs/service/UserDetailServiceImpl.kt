package com.github.sstg.kotlinbbs.service

import com.github.sstg.kotlinbbs.domain.UserInfo
import com.github.sstg.kotlinbbs.domain.UserInfoRepository
import org.springframework.context.event.EventListener
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION
import org.springframework.web.context.request.RequestContextHolder

@Service
class UserDetailServiceImpl(val userInfoRepository: UserInfoRepository) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        return userInfoRepository.findByEmail(email) ?: throw UsernameNotFoundException("email 不存在")
    }

    @EventListener
    fun handleLoginSuccess(event: InteractiveAuthenticationSuccessEvent) {
        var details = event.authentication.principal
        if (details is UserInfo) {
            RequestContextHolder.currentRequestAttributes().setAttribute("user", details, SCOPE_SESSION)
        }
    }
}