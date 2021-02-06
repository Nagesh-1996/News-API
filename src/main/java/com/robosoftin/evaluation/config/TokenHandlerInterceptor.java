package com.robosoftin.evaluation.config;


import com.robosoftin.evaluation.config.jwt_auth.JwtTokenUtil;
import com.robosoftin.evaluation.constants.AppConstants;
import com.robosoftin.evaluation.constants.ErrorConstants;
import com.robosoftin.evaluation.exception.CustomizedException;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author nagesh
 * inside the package - com.robosoftin.evaluation.constants
 **/
@Component
@Log4j2
public class TokenHandlerInterceptor implements HandlerInterceptor {
	@Autowired
	private JwtTokenUtil jWTUtil;


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("*****: Token Interceptor");
		if (request.getDispatcherType() != DispatcherType.REQUEST)
			return true;


		log.info("****{}", request.getDispatcherType());
		if (DispatcherType.REQUEST != request.getDispatcherType())
			return true;
		String token = request.getHeader(AppConstants.AUTHORIZATION);
		if (token == null)
			throw new CustomizedException(HttpStatus.UNAUTHORIZED, null, ErrorConstants.INVALID_TOKEN);
		Claims claimToken = jWTUtil.validate(token.replaceAll(AppConstants.TOKEN_PREFIX, "").trim());
 /*       if (claimToken.getIssuer() != null)
            throw new CustomizedException(HttpStatus.UNAUTHORIZED, null, ErrorCodes.INVALID_TOKEN);*/
		// userService.validateUser(String.valueOf(claimToken.get(CLAIMS_USER_ID)));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
		responseWrapper.copyBodyToResponse();
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {

		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
//     responseWrapper.copyBodyToResponse();
		System.out.println("Request and Response is completed");
	}
}
