package com.yulotts.blog.springboot.web;

import com.yulotts.blog.springboot.domain.posts.Posts;
import com.yulotts.blog.springboot.domain.posts.PostsRepository;
import com.yulotts.blog.springboot.web.dto.PostsSaveRequestDto;
import com.yulotts.blog.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
//import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

//    @Autowired
//    private WebApplicationContext context;

    //private MockMvc mvc;

//    @Before
//    public void setup() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }


    @Test
    //@WithMockUser(roles="USER")
    public void Posts_등록된다() throws Exception {
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
//        mvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(new ObjectMapper().writeValueAsString(requestDto)))
//                .andExpect(status().isOk());
        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void Posts_수정된다() {
        //given
        String title = "title";
        String content = "content";
        Posts savePosts = postsRepository.save(Posts.builder().title(title).content(content).author("author").build());

        Long updateId = savePosts.getId();
        title = "change title";
        content = "change content";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder().title(title).content(content).build();

        String url = "http://localhost:" + port + "/api/v1/posts/"+updateId;
        URI uri = URI.create(url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PostsUpdateRequestDto> entity = new HttpEntity(requestDto,headers);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, entity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

}
