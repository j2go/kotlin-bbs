package com.github.sstg.kotlinbbs.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@EnableWebSecurity
@Configuration
class SecurityConfig(val userDetailService: UserDetailsService) : WebSecurityConfigurerAdapter(false) {

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailService).passwordEncoder(BCryptPasswordEncoder())
    }


    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers("/css/**", "/images/**", "/layui/**", "/mods/**", "/reg")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login") //注1
                .usernameParameter("email")
                .permitAll()
    }


}