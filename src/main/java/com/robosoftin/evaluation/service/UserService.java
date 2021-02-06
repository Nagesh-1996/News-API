package com.robosoftin.evaluation.service;

import com.robosoftin.evaluation.dto.GenericServerResponse;
import com.robosoftin.evaluation.dto.request.LoginRequestDto;
import com.robosoftin.evaluation.dto.request.SignUpRequestDto;
import com.robosoftin.evaluation.dto.request.UpdateNewsLanguageRequestDto;
import org.springframework.http.ResponseEntity;

/**
 * Author nagesh
 * created on 06/02/21
 * inside the package - com.robosoftin.evaluation.service
 **/
public interface UserService {
	ResponseEntity<GenericServerResponse> signUcp(SignUpRequestDto signUpRequestDto);

	ResponseEntity<GenericServerResponse> login(LoginRequestDto loginRequestDto);

	ResponseEntity<GenericServerResponse> updatePrefferedNewslanguage(UpdateNewsLanguageRequestDto updateNewsLanguageRequestDto, String token);
}
