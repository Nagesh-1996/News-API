package com.robosoftin.evaluation.exception;

import com.robosoftin.evaluation.constants.ErrorConstants;
import com.robosoftin.evaluation.dto.response.ResultInfo;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;


public class CustomizedException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    protected ResultInfo resultInfo = ErrorConstants.GENERAL_EXCEPTION;
    protected HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public CustomizedException() {
        super();
    }

    public CustomizedException(@Nullable String msg) {
        super(msg);
    }

    public CustomizedException(HttpStatus status, @Nullable String msg) {
        super(msg);
        this.status = status;
    }

    public CustomizedException(HttpStatus status, @Nullable String msg, ResultInfo resultInfo) {
        super(msg);
        this.resultInfo = new ResultInfo(resultInfo.getCode(), resultInfo.getMessage());
        this.status = status;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
