package org.example.firstproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.firstproject.dto.ArticleForm;
import org.example.firstproject.entity.Article;
import org.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    // 입력 폼 페이지 띄우기
    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    // form에서 받은 데이터 db에 저장
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        //System.out.println(form.toString());
        log.info(form.toString());

        // DTO를 엔티티로 변환
        Article article = form.toEntity();
        //System.out.println(article.toString());
        log.info(article.toString());

        // repository 를 엔티티 db에 저장
        Article saved = articleRepository.save(article);
        //System.out.println(saved.toString());
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    // db에서 저장된 form data를 찾아와 보여주는 기능
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);

        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);

        return "articles/show";
    }

    // db에서 저장된 form data 전체를 찾아와 보여주는 기능
    @GetMapping("/articles")
    public String index(Model model){
        List<Article> articleEntitys = (List<Article>) articleRepository.findAll();

        model.addAttribute("articleList", articleEntitys);

        return "articles/index";
    }

    // 수정할 데이터 페이지를 보여주는 기능
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Article article = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", article);

        return "articles/edit";
    }

    // 데이터 수정을 반영하는 기능
    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        Article revised = form.toEntity();
        Article original = articleRepository.findById(revised.getId()).orElse(null);

        if (original != null) {
            articleRepository.save(revised);
        }

        return "redirect:/articles/" + revised.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        Article target = articleRepository.findById(id).orElse(null);

        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제되었습니다");
        }

        return "redirect:/articles";
    }
}
