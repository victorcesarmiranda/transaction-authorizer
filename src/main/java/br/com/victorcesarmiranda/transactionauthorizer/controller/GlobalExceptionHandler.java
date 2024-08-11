package br.com.victorcesarmiranda.transactionauthorizer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.victorcesarmiranda.transactionauthorizer.dto.ResponseDto;
import br.com.victorcesarmiranda.transactionauthorizer.exception.InsufficientBalanceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ResponseDto> insufficientBalanceExceptionHandler(InsufficientBalanceException e) {
        return ResponseEntity.ok(new ResponseDto("51"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> globalExceptionHandler(Exception e) {
        log.error("Erro inesperado", e);
        return ResponseEntity.ok(new ResponseDto("07"));
    }
}
