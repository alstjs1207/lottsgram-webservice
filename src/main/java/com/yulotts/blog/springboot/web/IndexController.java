package com.yulotts.blog.springboot.web;

import com.yulotts.blog.springboot.config.auth.LoginUser;
import com.yulotts.blog.springboot.config.auth.dto.SessionUser;
import com.yulotts.blog.springboot.service.posts.PostsService;
import com.yulotts.blog.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts",postsService.findAllDesc());

        if(user != null) {
            model.addAttribute("userName",user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(Model model, @LoginUser SessionUser user) {

        if(user != null) {
            model.addAttribute("userName",user.getName());
        }

        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        model.addAttribute("post",postsService.findById(id));
        return "posts-update";
    }

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model, @LoginUser SessionUser user) {

        PostsResponseDto entity = postsService.findById(id);

        if(user != null) {
            if((entity.getAuthor()).equals(user.getName())) {
                model.addAttribute("userName",user.getName());
            }
        }

        model.addAttribute("post", entity);

        return "posts-view";
    }
}
