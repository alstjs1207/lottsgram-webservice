package com.yulotts.blog.springboot.config.auth.dto;

import com.yulotts.blog.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

//직렬화를 구현한 사용자 엔티티

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
