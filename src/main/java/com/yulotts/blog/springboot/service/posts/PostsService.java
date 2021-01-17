package com.yulotts.blog.springboot.service.posts;

import com.yulotts.blog.springboot.domain.posts.Posts;
import com.yulotts.blog.springboot.domain.posts.PostsRepository;
import com.yulotts.blog.springboot.web.dto.PostsSaveRequestDto;
import com.yulotts.blog.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostsService {

    @Autowired
    private PostsRepository postsRepository;

    public Long save(PostsSaveRequestDto requestDto) {
       return postsRepository.save(requestDto.toEntity()).getId();
    }

    public Long update(Long id, PostsUpdateRequestDto requestDto) {
         Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id="+ id) );
         posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

}
