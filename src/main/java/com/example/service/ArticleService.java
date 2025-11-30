package com.example.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.client.FileClient;
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
	private final FileClient fileClient;

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
	public int registArticle(RegistArticleReq req, List<MultipartFile> files) {

		// 1) 게시글 객체 생성 및 데이터 설정
		Article article = new Article();
		article.setTitle(req.getTitle());
		article.setContent(req.getContent());
		article.setCategory(req.getCategory()); // 카테고리 저장
		article.setWriterId(req.getWriterId());
		log.info("게시글 번호: {}", article.getId());

		// 2) DB에 게시글 저장 (MyBatis mapper 호출)
		// articleMapper.addArticle(article)이 저장된 행의 개수(1)를 반환한다고 가정
		// MyBatis XML에서 useGeneratedKeys="true" keyProperty="id" 설정 필요 (파일 업로드 시
		// articleId 사용 위해)
		int result = articleMapper.addArticle(article);

		// 3) 파일이 있다면 file-service로 전송
		if (files != null && !files.isEmpty()) {
			// 파일 하나씩 업로드하거나, file-service API에 맞게 리스트로 전송
			// 여기서는 FileClient 인터페이스가 단일 파일 업로드를 지원하는지, 리스트를 지원하는지에 따라 다름
			// 현재 FileClient 코드: uploadFile(@RequestPart("file") MultipartFile file) -> 단일
			// 파일

			// 여러 파일 업로드를 위해 반복문 사용 (또는 FileClient에 다중 업로드 API 추가 필요)
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					// fileClient.uploadFile(file); // 단순 업로드 (파일 메타데이터만 반환)

					// 만약 게시글 ID와 파일을 함께 저장해야 한다면 FileClient API 수정 필요
					// 예: fileClient.uploadArticleFiles(article.getId(), file);
					// 현재 제공된 FileClient에는 uploadFile만 있으므로 이를 호출
					try {
						fileClient.uploadFile(file, article.getId());
						// TODO: 반환된 FileMeta 정보를 이용해 Article_File 매핑 테이블에 저장하는 로직 추가 권장
					} catch (Exception e) {
						log.error("파일 업로드 실패: {}", file.getOriginalFilename(), e);
						// 파일 업로드 실패 시 게시글 저장을 롤백할지 여부 결정 필요
					}
				}
			}
		}

		return result;
	}

	public List<Article> getArticleList() {
		List<Article> articleList = articleMapper.getArticle();
		return articleList;
	}

	public Article getArticleDetail(int articleId) {
		return articleMapper.findById(articleId);
	}
}
