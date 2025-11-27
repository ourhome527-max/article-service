package com.example.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.domain.Article;
import com.example.domain.dto.RegistArticleReq;
import com.example.mapper.ArticleMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleService {
	private final ArticleMapper articleMapper;

	public List<Map<String, String>> getArticleListTest() {

		// 1. 반환할 리스트 생성
		List<Map<String, String>> list = new ArrayList<>();

		// 2. 임의의 Map 데이터 1 (게시글 1)
		Map<String, String> article1 = new HashMap<>();
		article1.put("title", "첫 번째 게시글입니다.");
		article1.put("info", "5분전 / admin");
		article1.put("recs", "[22]");
		list.add(article1);

		// 3. 임의의 Map 데이터 2 (게시글 2)
		Map<String, String> article2 = new HashMap<>();
		article2.put("title", "Thymeleaf + Spring Boot 연동하기");
		article2.put("info", "10분전 / tester");
		article2.put("recs", "[15]");
		list.add(article2);

		// 4. 임의의 Map 데이터 3 (게시글 3)
		Map<String, String> article3 = new HashMap<>();
		article3.put("title", "피그마 디자인 적용 완료");
		article3.put("info", "1시간전 / designer");
		article3.put("recs", "[9]");
		list.add(article3);

		// 5. 데이터가 담긴 리스트 반환
		return list;
	}

	/*
	 * 게시글 작성
	 */
	public int registArticle(RegistArticleReq request) {
		try {
			Article article = new Article();
			article.setTitle(request.getTitle());
			article.setContent(request.getContent());
			article.setWriterId(request.getWriterId());
			article.setCategory(request.getCategory());
			article.setRegAt(new Date());
			article.setModAt(new Date());

			int result = articleMapper.addArticle(article);
			log.info("게시글 저장됨: {}", article);
			return result; // 성공
		} catch (Exception e) {
			log.error("게시글 등록 실패", e);
			return 0; // 실패
		}
	}

	public List<Article> getArticleList() {
		List<Article> articleList = articleMapper.getArticle();
		return articleList;
	}
}
