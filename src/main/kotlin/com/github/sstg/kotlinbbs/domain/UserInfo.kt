package com.github.sstg.kotlinbbs.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table
class UserInfo : UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = 0L

    var email = ""
    var name = ""
    var sex = "男"
    var enable = true
    var credentialsNonExpired = true
    var accountNonExpired = true
    var accountNonLocked = true
    var authorities = ""
    var passwd = ""

    var avatorUrl = ""
    var level = 1
    var createTime = Date()

    override fun getAuthorities(): List<GrantedAuthority> {
        return authorities.split(",").map { SimpleGrantedAuthority(it) }
    }

    override fun isEnabled() = enable

    override fun getUsername() = email

    override fun isCredentialsNonExpired() = credentialsNonExpired

    override fun getPassword() = passwd

    override fun isAccountNonExpired() = accountNonExpired

    override fun isAccountNonLocked() = accountNonLocked
}

@Repository
interface UserInfoRepository : CrudRepository<UserInfo, Long> {
    fun findByEmail(email: String): UserInfo
    fun findByIdIn(ids: Collection<Long>): List<UserInfo>
}