package com.yao.express.service.user.api.advices;

//import static org.springframework.http.HttpStatus.FORBIDDEN;
//import static org.springframework.http.HttpStatus.NOT_FOUND;
//import static org.springframework.http.HttpStatus.UNAUTHORIZED;
//
//import com.yao.express.cloud.micro.service.user.conf.auth.exception.FailedToLoginException;
//import com.yao.express.cloud.micro.service.user.conf.auth.exception.ProfileNotFoundException;
//import java.security.SignatureException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//    @ResponseStatus(NOT_FOUND)
//    @ExceptionHandler(ProfileNotFoundException.class)
//    public void profileNotFound() {
//    }
//
//    @ResponseStatus(UNAUTHORIZED)
//    @ExceptionHandler(FailedToLoginException.class)
//    public void failedToLogin() {
//    }
//
//    @ResponseStatus(FORBIDDEN)
//    @ExceptionHandler(SignatureException.class)
//    public void failedToVerify() {
//        System.out.println("");
//    }
//}
