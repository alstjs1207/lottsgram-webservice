package com.yulotts.blog.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    //가입한 email을 통해 사용자 판단
    Optional<User> findByEmail(String email);
}
