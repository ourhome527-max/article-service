package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.domain.Article;
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

	// 글 목록 불러오기 - TEST CODE
//	@GetMapping(value = "/article-list")
//	public List<Map<String, String>> getArticleList() {
//		return articleService.getArticleListTest();
//	}

	// 글 등록 하기
	@PostMapping(value = "/regist", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> registArticle(@RequestPart("data") RegistArticleReq request,
			@RequestPart(value = "files", required = false) List<MultipartFile> files) {

		log.info("게시글 등록 요청: title={}, writerId={}, files={}", request.getTitle(), request.getWriterId(),
				(files != null ? files.size() : 0));

		try {
			int result = articleService.registArticle(request, files);

			if (result > 0) {
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.status(500).build();
			}
		} catch (Exception e) {
			log.error("게시글 등록 중 오류 발생", e);
			return ResponseEntity.status(500).build();
		}
	}

	// 글 목록 불러오기
	@GetMapping(value = "/article-list")
	public ResponseEntity<List<Article>> articleList() {
		return ResponseEntity.ok(articleService.getArticleList());
	}

	@GetMapping("/{articleId}")
	public ResponseEntity<Article> getArticleById(@PathVariable("articleId") int articleId) {
		Article res = articleService.getArticleDetail(articleId);
		return ResponseEntity.ok(res);
	}
}
