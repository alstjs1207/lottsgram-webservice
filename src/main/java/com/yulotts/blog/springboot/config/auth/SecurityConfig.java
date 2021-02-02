package com.yulotts.blog.springboot.config.auth;

import com.yulotts.blog.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //스프링 시큐리티 설정들을 활성화시켜줍니다
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()//h2-console화면을 사용하기위해 해당 옵션들을 disable합니다.
                .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll() //전체 열람
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())//USER 권한만 해당 API를 사용하도록 설정
                .anyRequest().authenticated()//나머지 URL은 인증된 사용자들에게만 허용
                .and()
                .logout()
                .logoutSuccessUrl("/") //로그아웃 성공시
                .and()
                .oauth2Login()//로그인 성공시 진입점
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}
