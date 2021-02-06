package com.robosoftin.evaluation.constants;

import com.robosoftin.evaluation.dto.response.ResultInfo;

/**
 * Author nagesh
 * created on 02/02/21
 * inside the package - com.robosoftin.evaluation.constants
 **/
public class ErrorConstants {


	/**
	 * Error codes
	 */
	public static final String DEFAULT_SUCCESS_CODE = "0";
	public static final String DUPLICATE_USER_ERROR_CODE = "-1006";
	public static final String SIGNUP_ERROR = "-1007";
	public static final String LOGIN_ERROR_CODE = "-1008";
	public static final String INVALID_CREDENTIALS = "-1009";
	public static final String INVALID_USER_ERROR = "-1010";
	public static final String TIMEOUT_EXCEPTION = "-1011";
	public static final String BOOKMARK_ERROR = "-1012";
	private static final String GENERAL_EXCEPTION_CODE = "-1000";
	public static final String IO_EXCEPTION = "-1001";
	public static final String MISSING_REQUEST_BODY_FIELD = "-1002";
	private static final String ACCESS_TOKEN_ERROR = "-1003";
	private static final String REFRESH_TOKEN_ERROR = "-1004";
	public static final String DB_EXCEPTION = "-1005";



	/**
	 * Error response
	 */
	public static final ResultInfo GENERAL_EXCEPTION = new ResultInfo(GENERAL_EXCEPTION_CODE, AppConstants.INTERNAL_SERVER_ERROR);
	public static final ResultInfo INVALID_TOKEN = new ResultInfo(ACCESS_TOKEN_ERROR, AppConstants.INVALID_ACCESS_TOKEN);
	public static final ResultInfo INVALID_REFRESH_TOKEN = new ResultInfo(REFRESH_TOKEN_ERROR, AppConstants.INVALID_REFRESH_TOKEN);
}
