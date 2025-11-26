package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.domain.dto.RegistArticleReq;
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

	@PostMapping("/regist")
	public ResponseEntity<Void> registArticle(@RequestBody RegistArticleReq request) {
		int result = articleService.registArticle(request);

		if (result > 0) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(500).build();
		}
	}
}
