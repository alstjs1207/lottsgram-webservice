package com.yulotts.blog.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트게시글";
        String content = "테스트본문";
        // id가 있으면 update, 없으면 insert
        postsRepository.save(Posts.builder()
                                    .title(title)
                                    .content(content)
                                    .author("msyu1207@gmail.com")
                                    .build());
        //when
        // 테이블 posts의 모든 데이터조회
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void 게시글_하나_불러오기() {
        //given
        String title = "테스트게시글";
        String content = "테스트본문";
        // id가 있으면 update, 없으면 insert
        Posts post = postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("msyu1207@gmail.com")
                .build());

        Posts prs = postsRepository.findById(post.getId()).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id="));

        assertThat(prs.getTitle()).isEqualTo(title);
    }

    @Test
    public void BaseTimeEntity_등록() {

        LocalDateTime now = LocalDateTime.of(2021,1,18,0,0,0,0);
        postsRepository.save(Posts.builder().title("title").content("content").author("author").build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        System.out.println("date : "+posts.getCreatedDate());
        System.out.println("date : "+posts.getModifiedDate());
    }
}
