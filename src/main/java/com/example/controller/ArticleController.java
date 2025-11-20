package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.ArticleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class ArticleController {
	private final ArticleService articleService;

	// 글 목록 불러오기
	@GetMapping(value = "/article-list")
	public List<Map<String, String>> getArticleList() {
		return articleService.getArticleList();
	}
}
