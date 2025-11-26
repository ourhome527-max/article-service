package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.Article;

@Mapper
public interface ArticleMapper {
	int addArticle(Article article);
}
