package com.robosoftin.evaluation.exception;

import com.robosoftin.evaluation.dto.response.ResultInfo;
import org.springframework.http.HttpStatus;

/**
 * Author nagesh
 * created on /12/19
 * inside the package - com.camelot.auth_service.exception
 **/
public class UserServiceException extends CustomizedException {


    public UserServiceException(String errorCode, String errorMsg) {
        this.resultInfo = new ResultInfo(errorCode, errorMsg);
        this.status = HttpStatus.OK;
    }
}
