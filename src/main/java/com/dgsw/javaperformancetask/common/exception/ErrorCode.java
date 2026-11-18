package com.dgsw.javaperformancetask.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
    TRADE_NOT_FOUND(HttpStatus.NOT_FOUND, "거래를 찾을 수 없습니다."),
    SELF_TRADE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "본인의 상품은 구매할 수 없습니다."),
    TRADE_PRICE_MISMATCH(HttpStatus.BAD_REQUEST, "상품 가격과 거래 가격이 다릅니다."),
    TRADE_SELLER_MISMATCH(HttpStatus.BAD_REQUEST, "상품 판매자와 거래 판매자가 다릅니다."),
    INVALID_TRADE_STATUS(HttpStatus.BAD_REQUEST, "변경할 수 없는 거래 상태입니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;
}
