package com.github.ecommerce.web.advice;


import com.github.ecommerce.service.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResult> handleNotFoundException(NotFoundException nfe){
        ErrorResult er = new ErrorResult();
        er.setStatusCode(nfe.getHttpStatus().value());
        er.setStatus(nfe.getHttpStatus());
        er.setMessage(nfe.getMessage());
        log.error(nfe.getMessage());

        return ResponseEntity.status(nfe.getHttpStatus()).body(er);
    }
    @ExceptionHandler(NotAcceptException.class)
    public ResponseEntity<ErrorResult> notAcceptException(NotAcceptException nae) {
        ErrorResult er = new ErrorResult();
        er.setStatusCode(nae.getHttpStatus().value());
        er.setStatus(nae.getHttpStatus());
        er.setMessage(nae.getMessage());
        log.error(nae.getMessage());

        return ResponseEntity.status(nae.getHttpStatus()).body(er);
    }
    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<ErrorResult> handleInvalidValueException(InvalidValueException ive){
        ErrorResult er = new ErrorResult();
        er.setStatusCode(ive.getHttpStatus().value());
        er.setStatus(ive.getHttpStatus());
        er.setMessage(ive.getMessage());
        log.error(ive.getMessage());

        return ResponseEntity.status(ive.getHttpStatus()).body(er);
    }
    @ExceptionHandler(CAccessDeniedException.class)
    public ResponseEntity<ErrorResult> handleAccessDeniedException(CAccessDeniedException ade){
        ErrorResult er = new ErrorResult();
        er.setStatusCode(ade.getHttpStatus().value());
        er.setStatus(ade.getHttpStatus());
        er.setMessage(ade.getMessage());
        log.error(ade.getMessage());

        return ResponseEntity.status(ade.getHttpStatus()).body(er);
    }
    @ExceptionHandler(CAuthenticationEntryPointException.class)
    public ResponseEntity<ErrorResult> handleAuthenticationException(CAuthenticationEntryPointException ae){
        ErrorResult er = new ErrorResult();
        er.setStatusCode(ae.getHttpStatus().value());
        er.setStatus(ae.getHttpStatus());
        er.setMessage(ae.getMessage());
        log.error(ae.getMessage());

        return ResponseEntity.status(ae.getHttpStatus()).body(er);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResult> handleBadRequestException(BadRequestException bre){
        ErrorResult er = new ErrorResult();
        er.setStatusCode(bre.getHttpStatus().value());
        er.setStatus(bre.getHttpStatus());
        er.setMessage(bre.getMessage());
        log.error(bre.getMessage());

        return ResponseEntity.status(bre.getHttpStatus()).body(er);
    }
    @ExceptionHandler(DeletedUserException.class)
    public ResponseEntity<ErrorResult> handleDeletedUserException(DeletedUserException due){
        ErrorResult er = new ErrorResult();
        er.setStatusCode(due.getHttpStatus().value());
        er.setStatus(due.getHttpStatus());
        er.setMessage(due.getMessage());
        log.error(due.getMessage());

        return ResponseEntity.status(due.getHttpStatus()).body(er);
    }
    @ExceptionHandler(S3UpLordException.class)
    public ResponseEntity<ErrorResult> handleS3UpLordException(S3UpLordException ule){
        ErrorResult er = new ErrorResult();
        er.setStatusCode(ule.getHttpStatus().value());
        er.setStatus(ule.getHttpStatus());
        er.setMessage(ule.getMessage());
        log.error(ule.getMessage());

        return ResponseEntity.status(ule.getHttpStatus()).body(er);
    }
    @ExceptionHandler(S3Exception.class)
    public ResponseEntity<ErrorResult> handleS3Exception(S3Exception se){
        ErrorResult er = new ErrorResult();
        er.setStatusCode(se.getHttpStatus().value());
        er.setStatus(se.getHttpStatus());
        er.setMessage(se.getMessage());
        log.error(se.getMessage());

        return ResponseEntity.status(se.getHttpStatus()).body(er);
    }
    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ErrorResult> handleExpiredTokenException(ExpiredTokenException ete){
        ErrorResult er = new ErrorResult();
        er.setStatusCode(ete.getHttpStatus().value());
        er.setStatus(ete.getHttpStatus());
        er.setMessage(ete.getMessage());
        log.error(ete.getMessage());

        return ResponseEntity.status(ete.getHttpStatus()).body(er);
    }
}
