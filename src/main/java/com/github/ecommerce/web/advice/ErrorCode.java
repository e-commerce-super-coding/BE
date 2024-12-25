package com.github.ecommerce.web.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 로그인
    REGISTER_FAILURE(400,"입력하신 데이터에 문제가 있습니다.",HttpStatus.BAD_REQUEST),
    SECESSION_NOT_FOUND(404,"탈퇴한 회원이 아닙니다.",HttpStatus.NOT_FOUND),
    SECESSION_DETAIL(403," 해당 날짜에 탈퇴를 하셨습니다.",HttpStatus.FORBIDDEN),
    USER_SECESSION_FAILURE(401,"지금 상태에서 탈퇴 진행을 하실 수 없습니다.", HttpStatus.UNAUTHORIZED),
    LOGIN_FAILURE(406,"로그인 할 수 없습니다.", HttpStatus.NOT_ACCEPTABLE),
    LOGIN_FAILURE2(406,"탈퇴를 하셔서 로그인이 불가능합니다.", HttpStatus.NOT_ACCEPTABLE),
    EXPIRED_TOKEN(401,"발급된 토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED_TOKEN(401,"발급된 토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    ENTRY_POINT_FAILURE(401,"인증되지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(403,"접근할 수 있는 권한이 없습니다.", HttpStatus.FORBIDDEN),
    EMAIL_NOT_EXIST(404,"해당 이메일의 사용자를 찾을 수 없습니다.",HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_EXIST(400,"이미 존재하는 이메일입니다.",HttpStatus.BAD_REQUEST),
    WEAK_PASSWORD(400,"비밀번호는 최소 8자 이상이어야 합니다.",HttpStatus.BAD_REQUEST),
    UPLOAD_FAILURE(406,"파일 업로드에 실패했습니다.",HttpStatus.NOT_ACCEPTABLE),
    //책 상세페이지
    BOOK_NOT_FOUNDED(404,"해당 책 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CART_ERROR(400,"장바구니의 정보를 불러올 수 없습니다.", HttpStatus.BAD_REQUEST),
    // 마이페이지
    USER_NOT_FOUNDED(404,"해당 유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    USER_ERROR_FORBIDDEN(403,"로그인 정보와 접근 정보가 다릅니다.", HttpStatus.FORBIDDEN),
    //유저정보 수정
    USER_INFO_PUT_ERROR(401,"사용자 정보를 수정 시 에러가 발생했습니다.", HttpStatus.UNAUTHORIZED),
    USER_INFO_ERROR(500,"사용자 정보를 불러올 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    S3_ERROR(500,"이미지 삭제 중 알 수 없는 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    S3_ERROR2(500,"이미지 삭제를 실패했습니다. : ", HttpStatus.INTERNAL_SERVER_ERROR),
    S3_ERROR3(500,"잘못된 URL 형식입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    S3_ERROR4(500,"URL 디코딩을 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    S3_ERROR5(500,"jpg,jpeg,png,gif의 확장자만 사용 가능합니다. 확장자 : ", HttpStatus.INTERNAL_SERVER_ERROR),
    S3_ERROR6(400,"확장자를 찾을 수 없습니다. 파일명: ", HttpStatus.BAD_REQUEST),
    S3_ERROR7(400,"이미지 업로드 중 IO 예외가 발생했습니다.", HttpStatus.BAD_REQUEST),
    S3_ERROR8(404,"이미지가 비어있거나 파일 이름이 없습니다.", HttpStatus.NOT_FOUND),
    CART_ERROR2(500,"장바구니 작업 중 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    CART_NOT_FOUNDED(404,"장바구니에 해당 상품이 없습니다.", HttpStatus.NOT_FOUND),
    CART_QUANTITY_ERROR(403,"장바구니에 담긴 수량이 다릅니다.", HttpStatus.FORBIDDEN),
    CART_ID_ACCESS_ERROR(403,"장바구니 정보에 접근할 수 있는 권한이 없습니다.", HttpStatus.FORBIDDEN),
    CART_ID_IS_NULL(404,"상품을 선택해주세요.", HttpStatus.NOT_FOUND),
    // 결제내역
    PAYMENT_NOT_FOUNDED(404,"결제정보를 찾을 수 없습니다..", HttpStatus.NOT_FOUND);
    private final int statusCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;
}
