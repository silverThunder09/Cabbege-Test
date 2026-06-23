package com.sparta.cabbagetest.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException() {
        super("이미 가입된 이메일입니다.");
    }
}
