package com.ohigraffers.securitytest.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @PostMapping("/posts")
    public ResponseEntity<Void> savePosts() {
        log.info("psots 실행됨!!");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/categories")
    public ResponseEntity<Void> saveCategories() {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
