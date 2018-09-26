package com.github.sstg.kotlinbbs.service

import com.github.sstg.kotlinbbs.domain.UserInfoRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailServiceImpl(val userInfoRepository: UserInfoRepository) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        return userInfoRepository.findByEmail(email)
    }
}