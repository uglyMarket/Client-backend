package com.sparta.uglymarket.exception;

import com.sparta.uglymarket.util.ResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mockito 초기화
    }


    @Test
    @DisplayName("CustomException 예외 처리 테스트")
    void handleCustomException() {
        // given
        ErrorMsg errorMsg = ErrorMsg.UNAUTHORIZED_MEMBER; // 예시 (인증되지 않은 사용자)
        CustomException customException = new CustomException(errorMsg);

        // when
        ResponseEntity<ResponseDto> response = globalExceptionHandler.handleCustomException(customException);

        // then
        assertThat(response.getStatusCode()).isEqualTo(errorMsg.getHttpStatus());
        assertThat(response.getBody().getStatusCode()).isEqualTo(errorMsg.getHttpStatus().value());
        assertThat(response.getBody().getData()).isEqualTo(errorMsg.getDetails());
    }

    @Test
    @DisplayName("BindException 예외 처리 테스트")
    void handleBindException() {
        // given
        BindException bindException = mock(BindException.class);
        FieldError fieldError = new FieldError("objectName", "field", "잘못된 입력입니다.");
        when(bindException.getFieldError()).thenReturn(fieldError);

        // when
        ResponseEntity<ResponseDto<?>> response = globalExceptionHandler.bindException(bindException);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("잘못된 입력입니다.");
    }

    @Test
    @DisplayName("IOException 예외 처리 테스트")
    void handleIOException() {
        // given
        IOException ioException = new IOException("입출력 오류 발생");

        // when
        ResponseEntity<ResponseDto<?>> response = globalExceptionHandler.handleIOException(ioException);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody().getMessage()).isEqualTo("입출력 오류 발생");
    }

    @Test
    @DisplayName("Exception 예외 처리 테스트")
    void handleAllException() {
        // given
        Exception exception = new Exception("알 수 없는 오류 발생");

        // when
        ResponseEntity<ResponseDto<?>> response = globalExceptionHandler.handleAll(exception);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("알 수 없는 오류 발생");
    }
}