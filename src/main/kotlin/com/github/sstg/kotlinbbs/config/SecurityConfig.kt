package com.github.sstg.kotlinbbs.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


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
                .antMatchers("/css/**", "/images/**", "/layui/**", "/mods/**", "/user/reg")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .successHandler(SavedRequestAwareAuthenticationSuccessHandler())
                .usernameParameter("email")
                .permitAll()
                .and()
                .logout()
                // 使用 `.logoutUrl("/user/logout")` 的方式只支持 POST 方式登出
                .logoutRequestMatcher(AntPathRequestMatcher("/user/logout", "GET"))
                .deleteCookies("remember-me")
                .permitAll()
                .and()
                .rememberMe()
    }
}