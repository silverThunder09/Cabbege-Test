package com.sparta.cabbagetest.item.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class SellerNotFoundException extends RuntimeException {

    public SellerNotFoundException() {
        super("인증된 회원을 찾을 수 없습니다.");
    }
}
