package com.musinsa.muapi.controller.exception;


import com.musinsa.muapi.controller.CodyController;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class Exception  {
  
  @ExceptionHandler({CodyController.CodyNotFoundException.class})
  public ResponseEntity<Map<String, String>> notFound(CodyController.CodyNotFoundException e) {
    
    log.error(e);
    Map<String, String> errorMap = new HashMap<>();
    
    errorMap.put("TIME", ""+ System.currentTimeMillis());
    errorMap.put("RESULT",  "can not find data");
    
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMap);
  }
}
