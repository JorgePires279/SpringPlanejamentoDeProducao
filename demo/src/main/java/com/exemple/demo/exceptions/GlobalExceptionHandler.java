package com.exemple.demo.exceptions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.exemple.demo.dtos.ErroDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErroNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErroDto handleNotFoundException(ErroNaoEncontradoException e) {
        return new ErroDto(e.getTimestamp().truncatedTo(ChronoUnit.SECONDS),HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.getReasonPhrase(),e.getMensagem());
    }

    @ExceptionHandler(ErroConflitoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErroDto handleConflictException(ErroConflitoException e) {
        return new ErroDto(e.getTimestamp().truncatedTo(ChronoUnit.SECONDS),HttpStatus.CONFLICT.value(),HttpStatus.CONFLICT.getReasonPhrase(),e.getMensagem());
    }

    @ExceptionHandler(ErroRequisicaoErradaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErroDto handleBadRequestException(ErroRequisicaoErradaException e) {
        return new ErroDto(
            e.getTimestamp().truncatedTo(ChronoUnit.SECONDS),HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(),e.getMensagem());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErroDto handleValidationException(MethodArgumentNotValidException e) {
        String mensagem = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ErroDto(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(),mensagem);
    }
}
