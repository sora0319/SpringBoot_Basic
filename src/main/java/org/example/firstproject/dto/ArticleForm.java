package org.example.firstproject.dto;

import org.example.firstproject.entity.Article;

public class ArticleForm {
    private String title;
    private String content;

    public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "ArticleForm{" +
                "content='" + content + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public Article toEntity() {
        return new Article(null, title, content);
    }
}
