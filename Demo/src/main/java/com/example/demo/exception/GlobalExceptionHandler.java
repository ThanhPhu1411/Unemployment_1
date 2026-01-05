package com.example.demo.exception;

import com.example.demo.dto.request.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<APIResponse> handlingRuntimeException(RuntimeException exception){
    APIResponse apiResponse = new APIResponse();
    apiResponse.setCode(1001);
    apiResponse.setMessage(exception.getMessage());
    return  ResponseEntity.badRequest()
            .body(apiResponse);
}
@ExceptionHandler(value= MethodArgumentNotValidException.class)
ResponseEntity<String> handlingRuntimeException(MethodArgumentNotValidException exception) {
    return ResponseEntity.badRequest()
            .body(exception.getFieldError().getDefaultMessage());
}

}
