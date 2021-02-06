package com.robosoftin.evaluation.controller;

import com.robosoftin.evaluation.dto.GenericServerResponse;
import com.robosoftin.evaluation.dto.request.LoginRequestDto;
import com.robosoftin.evaluation.dto.request.SignUpRequestDto;
import com.robosoftin.evaluation.dto.request.UpdateNewsLanguageRequestDto;
import com.robosoftin.evaluation.dto.response.LanguageListResponseDto;
import com.robosoftin.evaluation.dto.response.LoginResponseDto;
import com.robosoftin.evaluation.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Author nagesh
 * created on 06/02/21
 * inside the package - com.robosoftin.evaluation.controller
 **/
@RestController
@RequestMapping(value = "/api/v1/user")
@Api(value = "UserController", description = "REST Apis related to User Registration & login!!!")
@Log4j2
public class UserController {


	@Autowired
	private UserService userService;

	/**
	 * Controller method to register new user
	 * @param signUpRequestDto
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "User Registration API", response = LoginResponseDto.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 409, message = "Existing User"), @ApiResponse(code = 400, message = "Bad request.")})
	@PostMapping(value = "/signUp")
	public ResponseEntity<GenericServerResponse> signUp(
			@Validated @RequestBody SignUpRequestDto signUpRequestDto) throws Exception {
		log.info("User signup Process has been started for user ===>"+signUpRequestDto.getEmailId());
		return userService.signUcp(signUpRequestDto);
	}


	/**
	 * Login method
	 * @param loginRequestDto
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "User login API", response = LoginResponseDto.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 400, message = "Bad request.")})
	@PostMapping(value = "/login")
	public ResponseEntity<GenericServerResponse> login(
			@Validated @RequestBody LoginRequestDto loginRequestDto) throws Exception {
		log.info("User Login Process has been started for user ===>"+loginRequestDto.getEmailId());
		return userService.login(loginRequestDto);
	}


	/**
	 * Method to update Preffered language for the user
	 * @param updateNewsLanguageRequestDto
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "API to update Preffered News Language for the user", response = GenericServerResponse.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 400, message = "Bad request.")})
	@PostMapping(value = "/newsLanguagePreference")
	public ResponseEntity<GenericServerResponse> updatePrefferedNewslanguage(
			@Validated @RequestBody UpdateNewsLanguageRequestDto updateNewsLanguageRequestDto, @RequestHeader("Authorization") String token) throws Exception {
		log.info("Updating Preffered news language for the user ===>"+updateNewsLanguageRequestDto.getUniqueUserId());
		return userService.updatePrefferedNewslanguage(updateNewsLanguageRequestDto, token);
	}


}
