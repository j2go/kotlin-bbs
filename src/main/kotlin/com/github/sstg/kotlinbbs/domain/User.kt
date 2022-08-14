package com.github.sstg.kotlinbbs.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table(
    indexes = [
        Index(name = "idx_name", columnList = "name", unique = true),
        Index(name = "idx_email", columnList = "email", unique = true)]
)
class User : UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = 0L

    var email = ""
    var name = ""
    var sex = "男"
    var sign = "这个人很懒，没有签名"
    var city = "未知"
    var status = 0b00001111
    var authorities = ""
    var passwd = ""

    var avatar = "/images/avatar/11.jpg"
    var level = 1
    var experience = 666
    var createTime = Date()

    override fun getAuthorities(): List<GrantedAuthority> {
        return authorities.split(",").map { SimpleGrantedAuthority(it) }
    }

    override fun isEnabled() = (status and 1 > 0)

    override fun getUsername() = email

    override fun isCredentialsNonExpired() = (status and 2 > 0)

    override fun isAccountNonExpired() = (status and 4 > 0)

    override fun isAccountNonLocked() = (status and 8 > 0)

    override fun getPassword() = passwd
}

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByName(email: String): User?
    fun findByIdIn(ids: Collection<Long>): List<User>
}