package com.robosoftin.evaluation.config.jwt_auth;

import com.robosoftin.evaluation.constants.AppConstants;
import com.robosoftin.evaluation.constants.ErrorConstants;
import com.robosoftin.evaluation.exception.CustomizedException;
import com.robosoftin.evaluation.exception.UserServiceException;
import com.robosoftin.evaluation.model.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Author nagesh
 * inside the package - com.robosoftin.evaluation.constants
 **/
@Component
public class JwtTokenUtil implements Serializable {

    @Value("${signingKey}")
    private String signingKey;


    public String generateAccessToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("evaluation")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + AppConstants.ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .compact();
    }

    public String generateRefreshToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("evaluation")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + AppConstants.REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .compact();
    }


    public Claims validate(String token) {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
        } catch (Exception ex) {
            throw new CustomizedException(HttpStatus.UNAUTHORIZED, null, ErrorConstants.INVALID_TOKEN);
        }

    }

    public Claims validateRefreshToken(String token) {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
        } catch (Exception ex) {
            throw new CustomizedException(HttpStatus.UNAUTHORIZED, null, ErrorConstants.INVALID_REFRESH_TOKEN);
        }

    }

    public void validateUserDataAndToken(UserModel userInfo, String token) {
        try {
            Claims tokenClaims = validate(token.replaceAll(AppConstants.TOKEN_PREFIX, "").trim());
            String email = tokenClaims.get(AppConstants.USER_ID, String.class);
            if(!userInfo.getEmailId().trim().equalsIgnoreCase(email)) {
                throw new UserServiceException(ErrorConstants.INVALID_USER_ERROR, AppConstants.INVALID_USER_ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserServiceException(ErrorConstants.INVALID_USER_ERROR, AppConstants.INVALID_USER_ERROR_MESSAGE);
        }
    }
}
