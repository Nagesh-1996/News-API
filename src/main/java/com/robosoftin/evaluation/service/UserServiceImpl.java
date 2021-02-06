package com.robosoftin.evaluation.service;

import com.robosoftin.evaluation.config.jwt_auth.JwtTokenUtil;
import com.robosoftin.evaluation.constants.AppConstants;
import com.robosoftin.evaluation.constants.ErrorConstants;
import com.robosoftin.evaluation.dto.GenericServerResponse;
import com.robosoftin.evaluation.dto.request.LoginRequestDto;
import com.robosoftin.evaluation.dto.request.SignUpRequestDto;
import com.robosoftin.evaluation.dto.request.UpdateNewsLanguageRequestDto;
import com.robosoftin.evaluation.dto.response.LoginResponseDto;
import com.robosoftin.evaluation.exception.UserServiceException;
import com.robosoftin.evaluation.model.UserModel;
import com.robosoftin.evaluation.repository.UserRepository;
import com.robosoftin.evaluation.util.SequenceGeneratorUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Author nagesh
 * created on 06/02/21
 * inside the package - com.robosoftin.evaluation.service
 **/

@Service
@Log4j2
public class UserServiceImpl implements UserService {


	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private SequenceGeneratorUtil sequenceGeneratorUtil;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;


	/**
	 * Method to register new User
	 * @param signUpRequestDto
	 * @return
	 */
	@Override
	public ResponseEntity<GenericServerResponse> signUcp(SignUpRequestDto signUpRequestDto) {
		UserModel userData = userRepository.findByEmailId(signUpRequestDto.getEmailId().trim());
		if (userData != null) {
			if (userData.isRegistered()) {
				throw new UserServiceException(ErrorConstants.DUPLICATE_USER_ERROR_CODE, AppConstants.DUPLICATE_USER_ERROR_MESSAGE);
			}
			userRepository.delete(userData);
		}
		UserModel userInfo = saveNewUserData(signUpRequestDto);
		HttpHeaders headers = generateJwtToken(userInfo);
		GenericServerResponse serverResponse = new GenericServerResponse(ErrorConstants.DEFAULT_SUCCESS_CODE, AppConstants.SUCCESS_MESSAGE, new LoginResponseDto(userInfo.getUniqueUserId(), false));
		return ResponseEntity.ok().headers(headers).body(serverResponse);
	}


	/**
	 * Method to validate & login the user.
	 * @param loginRequestDto
	 * @return
	 */
	@Override
	public ResponseEntity<GenericServerResponse> login(LoginRequestDto loginRequestDto) {
		UserModel userData = userRepository.findByEmailId(loginRequestDto.getEmailId().trim());
		if((userData == null) || (userData != null && !(userData.isRegistered())))
			throw new UserServiceException(ErrorConstants.LOGIN_ERROR_CODE, AppConstants.LOGIN_ERROR_MESSAGE);
		if (!bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), userData.getPassword()))
			throw new UserServiceException(ErrorConstants.INVALID_CREDENTIALS, AppConstants.INVALID_CREDENTIALS_MESSAGE);
		boolean isPreeferedLanguageIsSelected = userData.getPrefferedLanguage() != null && !userData.getPrefferedLanguage().trim().isEmpty();
		HttpHeaders headers = generateJwtToken(userData);
		GenericServerResponse serverResponse = new GenericServerResponse(ErrorConstants.DEFAULT_SUCCESS_CODE, AppConstants.SUCCESS_MESSAGE, new LoginResponseDto(userData.getUniqueUserId(), isPreeferedLanguageIsSelected));
		return ResponseEntity.ok().headers(headers).body(serverResponse);
	}

	/**
	 * Service method to validate the user & to update preffered language for a particular user
	 * @param updateNewsLanguageRequestDto
	 * @param token
	 * @return
	 */
	@Override
	public ResponseEntity<GenericServerResponse> updatePrefferedNewslanguage(UpdateNewsLanguageRequestDto updateNewsLanguageRequestDto, String token) {
		UserModel userInfo = userRepository.findByUniqueUserId(updateNewsLanguageRequestDto.getUniqueUserId());
		if(userInfo == null)
			throw new UserServiceException(ErrorConstants.INVALID_USER_ERROR, AppConstants.INVALID_USER_ERROR_MESSAGE);
		jwtTokenUtil.validateUserDataAndToken(userInfo, token);
		userInfo = updatePrefferedLanguage(updateNewsLanguageRequestDto, userInfo);
		return generateSuccessResponse(null);

	}

	/**
	 * Method to update preffered lanaguage for a particular user
	 * @param updateNewsLanguageRequestDto
	 * @param userInfo
	 * @return
	 */
	private UserModel updatePrefferedLanguage(UpdateNewsLanguageRequestDto updateNewsLanguageRequestDto, UserModel userInfo) {
		userInfo.setPrefferedLanguage(updateNewsLanguageRequestDto.getLanguageCode());
		return userRepository.save(userInfo);
	}


	/**
	 * Method to Save New user Registration Data
	 * @param signUpRequestDto
	 * @return
	 */
	private UserModel saveNewUserData(SignUpRequestDto signUpRequestDto) {
		try {
			String password = bCryptPasswordEncoder.encode(signUpRequestDto.getPassword());
			String uniqueUserId = String.valueOf(sequenceGeneratorUtil.nextId());
			UserModel usermodel = UserModel.builder()
					.password(password)
					.emailId(signUpRequestDto.getEmailId())
					.isRegistered(true)
					.mobileNo(signUpRequestDto.getMobileNo())
					.uniqueUserId(uniqueUserId)
					.userName(signUpRequestDto.getUserName())
					.build();
			return userRepository.save(usermodel);
		} catch (Exception e) {
			throw new UserServiceException(ErrorConstants.SIGNUP_ERROR, AppConstants.SIGNUP_ERROR_MESSAGE);
		}
	}


	/**
	 * Function to generate jwt token
	 *
	 * @param userInfo
	 * @return
	 */
	private HttpHeaders generateJwtToken(UserModel userInfo) {
		Claims claims = Jwts.claims().setSubject(AppConstants.EVALUATION);
		claims.put(AppConstants.USER_ID, userInfo.getEmailId());
		String jwtToken = jwtTokenUtil.generateAccessToken(claims);
		log.info("Generated Jwt token:", jwtToken);
		HttpHeaders headers = new HttpHeaders();
		headers.add(AppConstants.ACCESS_TOKEN, jwtToken);
		return headers;
	}

	/**
	 * Method to generate the success Response
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
