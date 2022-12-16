package com.pg.securityauth.exception;


import com.pg.securityauth.util.Errors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SecurityAuthException extends RuntimeException {

    private final String errorCode;
    private final String errorMsg;

    public SecurityAuthException() {

        this.errorCode = Errors.ESA_999.getCode();
        this.errorMsg = Errors.ESA_999.getDescription();
    }
}
