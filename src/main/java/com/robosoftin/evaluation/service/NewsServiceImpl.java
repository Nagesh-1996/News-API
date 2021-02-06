package com.robosoftin.evaluation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robosoftin.evaluation.config.AppSettingConfig;
import com.robosoftin.evaluation.config.jwt_auth.JwtTokenUtil;
import com.robosoftin.evaluation.constants.APIConstants;
import com.robosoftin.evaluation.constants.AppConstants;
import com.robosoftin.evaluation.constants.ErrorConstants;
import com.robosoftin.evaluation.dto.GenericServerResponse;
import com.robosoftin.evaluation.dto.request.BookMarkNewsRequestDto;
import com.robosoftin.evaluation.dto.request.GetBookmarkDtaRequestDto;
import com.robosoftin.evaluation.dto.request.GetNewsArticlesRequestDto;
import com.robosoftin.evaluation.dto.request.NewsSourcesRequestDto;
import com.robosoftin.evaluation.dto.response.ArticlesDataResponseDto;
import com.robosoftin.evaluation.dto.response.GetBookmarkResponseDto;
import com.robosoftin.evaluation.dto.response.LanguageListResponseDto;
import com.robosoftin.evaluation.dto.response.NewsSourcesDataResponseDto;
import com.robosoftin.evaluation.dto.response.service.ArticlesData;
import com.robosoftin.evaluation.dto.response.service.NewsSourcesData;
import com.robosoftin.evaluation.exception.UserServiceException;
import com.robosoftin.evaluation.model.LanguageModel;
import com.robosoftin.evaluation.model.NewsBookmarkModel;
import com.robosoftin.evaluation.model.UserModel;
import com.robosoftin.evaluation.repository.LanguageModelRepository;
import com.robosoftin.evaluation.repository.NewsBookmarkRepository;
import com.robosoftin.evaluation.repository.UserRepository;
import com.robosoftin.evaluation.util.GenericRestTemplateUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author nagesh
 * created on 06/02/21
 * inside the package - com.robosoftin.evaluation.service
 **/
@Service
@Log4j2
public class NewsServiceImpl implements NewsService {


	@Autowired
	private LanguageModelRepository languageModelRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AppSettingConfig appSettingConfig;
	@Autowired
	private GenericRestTemplateUtil genericRestTemplateUtil;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private NewsBookmarkRepository newsBookmarkRepository;


	/**
	 * Method to list all supported language Data.
	 *
	 * @return
	 */
	@Override
	public ResponseEntity<GenericServerResponse> getLanguageList() {
		List<LanguageModel> languageModelList = languageModelRepository.findAll();
		List<LanguageListResponseDto> languageListResponseDtoList = languageModelList.stream().map(languageModel -> {
			LanguageListResponseDto languageListResponseDto = new LanguageListResponseDto();
			languageListResponseDto.setLanguageCode(languageModel.getLanguageCode());
			languageListResponseDto.setLanguageName(languageModel.getLanguageName());
			return languageListResponseDto;
		}).collect(Collectors.toList());
		return generateSuccessResponse(languageListResponseDtoList);
	}


	/**
	 * Method to fetch news sources from news API.
	 * @param newsSourcesRequestDto
	 * @return
	 * @throws IOException
	 */
	@Override
	public ResponseEntity<GenericServerResponse> getNewsSourcesList(NewsSourcesRequestDto newsSourcesRequestDto) throws IOException {

		try {
			String languageCode = null;
			if (newsSourcesRequestDto.getUniqueUserId() == null || newsSourcesRequestDto.getUniqueUserId().trim().isEmpty())
				languageCode = "";
			else {
				UserModel userModel = userRepository.findByUniqueUserId(newsSourcesRequestDto.getUniqueUserId());
				if (userModel == null)
					languageCode = "";
				else {
					languageCode = userModel.getPrefferedLanguage();
				}
			}
			ResponseEntity<String> response = getNewsSourcesFromNewsAPI(newsSourcesRequestDto, languageCode);
			return validateAndReturnSourceResponse(response);
		} catch (Exception e) {
			return generateSuccessResponse(Collections.emptyList());
		}
	}

	/**
	 * Method to get news Articles by using News API.
	 * @param getNewsArticlesRequestDto
	 * @param token
	 * @return
	 */
	@Override
	public ResponseEntity<GenericServerResponse> getNewsArticles(GetNewsArticlesRequestDto getNewsArticlesRequestDto, String token) {
		try {
			UserModel userInfo = userRepository.findByUniqueUserId(getNewsArticlesRequestDto.getUniqueUserId());
			if(userInfo == null)
				throw new UserServiceException(ErrorConstants.INVALID_USER_ERROR, AppConstants.INVALID_USER_ERROR_MESSAGE);
			jwtTokenUtil.validateUserDataAndToken(userInfo, token);
			ResponseEntity<String> response = getNewsArticleFromNewsAPI(getNewsArticlesRequestDto, userInfo);
			return validateAndReturnNewsArticlesResponse(response);
		} catch(Exception e) {
			return generateSuccessResponse(Collections.emptyList());
		}
	}

	/**
	 * Method to bookmark the news article
	 * @param bookMarkNewsRequestDto
	 * @param token
	 * @return
	 */
	@Override
	public ResponseEntity<GenericServerResponse> bookmarkNewsArticle(BookMarkNewsRequestDto bookMarkNewsRequestDto, String token) {
		try {
			UserModel userInfo = userRepository.findByUniqueUserId(bookMarkNewsRequestDto.getUniqueUserId());
			if(userInfo == null)
				throw new UserServiceException(ErrorConstants.INVALID_USER_ERROR, AppConstants.INVALID_USER_ERROR_MESSAGE);
			jwtTokenUtil.validateUserDataAndToken(userInfo, token);
			return bookmarkNewsArticleAndReturnResponse(bookMarkNewsRequestDto, userInfo.getId());
		} catch(Exception e) {
			throw new UserServiceException(ErrorConstants.BOOKMARK_ERROR, AppConstants.BOOKMARK_ERROR_MESSAGE);
		}
	}

	@Override
	public ResponseEntity<GenericServerResponse> getBookmark(GetBookmarkDtaRequestDto getBookmarkDtaRequestDto, String token) {
		try {
			UserModel userInfo = userRepository.findByUniqueUserId(getBookmarkDtaRequestDto.getUniqueUserId());
			if(userInfo == null)
				throw new UserServiceException(ErrorConstants.INVALID_USER_ERROR, AppConstants.INVALID_USER_ERROR_MESSAGE);
			List<NewsBookmarkModel> newsBookmarkModelList = newsBookmarkRepository.findAllByUserId(userInfo.getId());
			List<GetBookmarkResponseDto> getBookmarkResponseDtoList = newsBookmarkModelList.stream().map(bookmark ->  {
				GetBookmarkResponseDto bookmarkResponseDto = new GetBookmarkResponseDto();
				bookmarkResponseDto.setAuthor(bookmark.getAuthor());
				bookmarkResponseDto.setDescription(bookmark.getDescription());
				bookmarkResponseDto.setPublishedAt(bookmark.getPublishedAt());
				bookmarkResponseDto.setTitle(bookmark.getTitle());
				bookmarkResponseDto.setUniqueUserId(getBookmarkDtaRequestDto.getUniqueUserId());
				bookmarkResponseDto.setUrl(bookmark.getUrl());
				bookmarkResponseDto.setUrlToImage(bookmark.getUrlToImage());
				return bookmarkResponseDto;
			}).collect(Collectors.toList());
			return generateSuccessResponse(getBookmarkResponseDtoList);
		}catch(Exception e) {
			throw new UserServiceException(ErrorConstants.BOOKMARK_ERROR, AppConstants.BOOKMARK_ERROR_MESSAGE);
		}

	}

	/**
	 * Method to save Bookmark data.
	 * @param bookMarkNewsRequestDto
	 * @param id
	 * @return
	 */
	private ResponseEntity<GenericServerResponse> bookmarkNewsArticleAndReturnResponse(BookMarkNewsRequestDto bookMarkNewsRequestDto, int id) {
		NewsBookmarkModel newsBookmarkModel = NewsBookmarkModel.builder()
				.newsSource(bookMarkNewsRequestDto.getNewsSource())
				.author(bookMarkNewsRequestDto.getAuthor())
				.description(bookMarkNewsRequestDto.getDescription())
				.publishedAt(bookMarkNewsRequestDto.getPublishedAt())
				.title(bookMarkNewsRequestDto.getTitle())
				.url(bookMarkNewsRequestDto.getUrl())
				.urlToImage(bookMarkNewsRequestDto.getUrlToImage())
				.userId(id)
				.build();
		newsBookmarkRepository.save(newsBookmarkModel);
		return generateSuccessResponse(null);
	}

	/**
	 * Method to validate News article response and return API response
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private ResponseEntity<GenericServerResponse> validateAndReturnNewsArticlesResponse(ResponseEntity<String> response) throws IOException {
		if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
			ArticlesData articlesData = new ObjectMapper().readValue(response.getBody(), ArticlesData.class);
			if (articlesData.getStatus().equalsIgnoreCase("ok")) {
				ArticlesDataResponseDto articlesDataResponseDto = new ArticlesDataResponseDto();
				BeanUtils.copyProperties(articlesData, articlesDataResponseDto);
				return generateSuccessResponse(articlesDataResponseDto);
			}
		}
		return generateSuccessResponse(Collections.emptyList());
	}

	/**
	 * Method to generate Get news Articles URi & get the data
	 * @param getNewsArticlesRequestDto
	 * @param userInfo
	 * @return
	 */
	private ResponseEntity<String> getNewsArticleFromNewsAPI(GetNewsArticlesRequestDto getNewsArticlesRequestDto, UserModel userInfo) {
		String url = appSettingConfig.getNewsBaseUrl() + APIConstants.NEWS_ARTICLES + AppConstants.QUESTION_MARK;
		url = url +"&"+ AppConstants.SOURCE + getNewsArticlesRequestDto.getNewsSource();
		url = url +"&"+ AppConstants.API_KEY + appSettingConfig.getNewsApiKey();
		if(getNewsArticlesRequestDto.getSortBy() == null || getNewsArticlesRequestDto.getSortBy().trim().isEmpty()) {
			//Ignore if it's empty or null
		} else
			url = url +"&"+ AppConstants.SORT_BY + getNewsArticlesRequestDto.getSortBy();
		URI uri = UriComponentsBuilder.fromUriString(url)
				.build().toUri();
		log.info("Generated URI===>"+uri);
		ResponseEntity<String> response = genericRestTemplateUtil.genericRestTemplateForGetWithQueryParameter(uri);
		log.info("Response of News Articles API===>" + response);
		return response;
	}

	/**
	 * Method to validate news source API response & generate API response
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private ResponseEntity<GenericServerResponse> validateAndReturnSourceResponse(ResponseEntity<String> response) throws IOException {

		if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
			NewsSourcesData newsSourcesData = new ObjectMapper().readValue(response.getBody(), NewsSourcesData.class);
			if (newsSourcesData.getStatus().equalsIgnoreCase("ok")) {
				NewsSourcesDataResponseDto newsSourcesDataResponseDto = new NewsSourcesDataResponseDto();
				BeanUtils.copyProperties(newsSourcesData, newsSourcesDataResponseDto);
				return generateSuccessResponse(newsSourcesDataResponseDto);
			}
		}
		return generateSuccessResponse(Collections.emptyList());
	}

	/**
	 * Method to generate News sources API URI
	 * @param newsSourcesRequestDto
	 * @param languageCode
	 * @return
	 */
	private ResponseEntity<String> getNewsSourcesFromNewsAPI(NewsSourcesRequestDto newsSourcesRequestDto, String languageCode) {

		String url = appSettingConfig.getNewsBaseUrl() + APIConstants.NEWS_SOURCES + AppConstants.QUESTION_MARK;
		if (languageCode.trim().isEmpty()) {
			//Ignore if it's empty
		} else {
			url = url + AppConstants.LANGUAGE + languageCode;
		}
		if (newsSourcesRequestDto.getCategory() == null || newsSourcesRequestDto.getCategory().trim().isEmpty()) {
			//Ignore if it's empty or null
		} else {
			url = url +"&"+AppConstants.CATEGORY + newsSourcesRequestDto.getCategory().trim();
		}
		if (newsSourcesRequestDto.getCountry() == null || newsSourcesRequestDto.getCountry().trim().isEmpty()) {
			//Ignore if it's empty or null
		} else {
			url = url+"&"+ AppConstants.COUNTRY + newsSourcesRequestDto.getCountry().trim();
		}
		URI uri = UriComponentsBuilder.fromUriString(url)
				.build().toUri();
		log.info("Generated URI===>"+uri);
		ResponseEntity<String> response = genericRestTemplateUtil.genericRestTemplateForGetWithQueryParameter(uri);
		log.info("Response of Sources API===>" + response);
		return response;
	}


	/**
	 * Method to generate the success Response
	 *
	 * @param responseObject
	 * @return
	 */
	public ResponseEntity<GenericServerResponse> generateSuccessResponse(Object responseObject) {
		log.info("InvestorLoginServiceImpl  :Generating Server response.");
		GenericServerResponse serverResponse =
				new GenericServerResponse(
						ErrorConstants.DEFAULT_SUCCESS_CODE, AppConstants.SUCCESS_MESSAGE, responseObject);
		return new ResponseEntity<>(serverResponse, HttpStatus.OK);
	}
}
