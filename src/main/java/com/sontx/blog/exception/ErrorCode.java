package com.sontx.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    CATEGORY_EXISTED(2001, "Category existed", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_EXISTED(2002, "Category not existed", HttpStatus.NOT_FOUND),
    POST_NOT_EXISTED(3001, "Post not existed", HttpStatus.NOT_FOUND),
    POST_TITlE_EXISTED(3002, "Post title existed", HttpStatus.BAD_REQUEST),
    MEDIA_NOT_EXISTED(4001, "Media not existed", HttpStatus.NOT_FOUND),
    HASHTAG_NOT_EXISTED(5001, "Hashtag not existed", HttpStatus.NOT_FOUND),
    TITLE_INVALID(6001, "Title not be empty", HttpStatus.BAD_REQUEST),
    TITLE_TOO_LONG(6002, "Title must not exceed {max} characters", HttpStatus.BAD_REQUEST),
    CONTENT_INVALID(6003, "Content not be empty", HttpStatus.BAD_REQUEST),
    CATEGORY_INVALID(6004, "Category not be empty", HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_INVALID(6005, "Category name must not be empty", HttpStatus.BAD_REQUEST)
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    int code;
    String message;
    HttpStatusCode statusCode;
}
