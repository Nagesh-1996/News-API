package com.robosoftin.evaluation.service;

import com.robosoftin.evaluation.dto.GenericServerResponse;
import com.robosoftin.evaluation.dto.request.BookMarkNewsRequestDto;
import com.robosoftin.evaluation.dto.request.GetBookmarkDtaRequestDto;
import com.robosoftin.evaluation.dto.request.GetNewsArticlesRequestDto;
import com.robosoftin.evaluation.dto.request.NewsSourcesRequestDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

/**
 * Author nagesh
 * created on 06/02/21
 * inside the package - com.robosoftin.evaluation.service
 **/
public interface NewsService {
	ResponseEntity<GenericServerResponse> getLanguageList();

	ResponseEntity<GenericServerResponse> getNewsSourcesList(NewsSourcesRequestDto newsSourcesRequestDto) throws IOException;

	ResponseEntity<GenericServerResponse> getNewsArticles(GetNewsArticlesRequestDto getNewsArticlesRequestDto, String token);

	ResponseEntity<GenericServerResponse> bookmarkNewsArticle(BookMarkNewsRequestDto bookMarkNewsRequestDto, String token);

	ResponseEntity<GenericServerResponse> getBookmark(GetBookmarkDtaRequestDto getBookmarkDtaRequestDto, String token);
}
