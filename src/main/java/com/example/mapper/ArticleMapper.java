package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import com.example.domain.Article;

@Mapper
public interface ArticleMapper {
	int addArticle(Article article);

	List<Article> getArticle();
}
