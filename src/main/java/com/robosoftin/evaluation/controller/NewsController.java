package com.robosoftin.evaluation.controller;

import com.robosoftin.evaluation.dto.GenericServerResponse;
import com.robosoftin.evaluation.dto.request.BookMarkNewsRequestDto;
import com.robosoftin.evaluation.dto.request.GetBookmarkDtaRequestDto;
import com.robosoftin.evaluation.dto.request.GetNewsArticlesRequestDto;
import com.robosoftin.evaluation.dto.request.NewsSourcesRequestDto;
import com.robosoftin.evaluation.dto.response.ArticlesDataResponseDto;
import com.robosoftin.evaluation.dto.response.GetBookmarkResponseDto;
import com.robosoftin.evaluation.dto.response.LanguageListResponseDto;
import com.robosoftin.evaluation.dto.response.NewsSourcesDataResponseDto;
import com.robosoftin.evaluation.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Author nagesh
 * created on 06/02/21
 * inside the package - com.robosoftin.evaluation.controller
 **/

@RestController
@RequestMapping(value = "/api/v1/news")
@Api(value = "NewsController", description = "REST Apis related to news")
@Log4j2
public class NewsController {


	@Autowired
	private NewsService newsService;


	/**
	 * Method to list all the supported languges to the user
	 * @return
	 */
	@ApiOperation(value = "Get API to list different supported languages", response = LanguageListResponseDto.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 400, message = "Bad request.")})
	@GetMapping(value = "/languageList")
	public ResponseEntity<GenericServerResponse> getLanguageList()  {
		log.debug("NewsController  :getLanguageList() execution started");
		return newsService.getLanguageList();
	}

	/**
	 * Method to get list of available news sources to the user
	 * @return
	 */
	@ApiOperation(value = "API to list different news sources list", response = NewsSourcesDataResponseDto.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 400, message = "Bad request.")})
	@PostMapping(value = "/sources")
	public ResponseEntity<GenericServerResponse> getNewsSourcesList(@RequestBody NewsSourcesRequestDto newsSourcesRequestDto) throws IOException {
		log.debug("NewsController  :getPublicKey() execution started");
		return newsService.getNewsSourcesList(newsSourcesRequestDto);
	}


	/**
	 * Method to get list of news article from News API
	 * @return
	 */
	@ApiOperation(value = "API to list news Articles", response = ArticlesDataResponseDto.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 400, message = "Bad request.")})
	@PostMapping(value = "/articles")
	public ResponseEntity<GenericServerResponse> getNewsArticles(@RequestBody GetNewsArticlesRequestDto getNewsArticlesRequestDto, @RequestHeader("Authorization") String token) throws IOException {
		log.debug("NewsController  :getNewsArticles() execution started");
		return newsService.getNewsArticles(getNewsArticlesRequestDto, token);
	}

	/**
	 * Method to bookmark the news Article
	 * @return
	 */
	@ApiOperation(value = "API to bookmark the article", response = GenericServerResponse.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 400, message = "Bad request.")})
	@PostMapping(value = "/bookmark")
	public ResponseEntity<GenericServerResponse> bookmarkNewsArticle(@RequestBody BookMarkNewsRequestDto bookMarkNewsRequestDto, @RequestHeader("Authorization") String token) throws IOException {
		log.debug("NewsController  :bookmarkNewsArticle() execution started");
		return newsService.bookmarkNewsArticle(bookMarkNewsRequestDto, token);
	}

	/**
	 * Method to bookmark the news Article
	 * @return
	 */
	@ApiOperation(value = "API to fetch bookmark data", response = GetBookmarkResponseDto.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 400, message = "Bad request.")})
	@PostMapping(value = "/getBookmark")
	public ResponseEntity<GenericServerResponse> getBookmark(@RequestBody GetBookmarkDtaRequestDto getBookmarkDtaRequestDto, @RequestHeader("Authorization") String token) throws IOException {
		log.debug("NewsController  :bookmarkNewsArticle() execution started");
		return newsService.getBookmark(getBookmarkDtaRequestDto, token);
	}
}
