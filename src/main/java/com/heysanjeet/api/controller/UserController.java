package com.heysanjeet.api.controller;
import com.heysanjeet.api.domain.User;
import com.heysanjeet.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/users", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public ResponseEntity saveUsers(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
        for (MultipartFile file : files) {
            userService.saveUsers(file);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/users", produces = "application/json")
    public CompletableFuture<ResponseEntity> findAllUsers() {
        return userService.findAllUsers().thenApply(ResponseEntity::ok);
    }

    @GetMapping(value = "/usersByMultithread", produces = "application/json")
    public ResponseEntity getUsers(){
        CompletableFuture<List<User>> allUsers1 = userService.findAllUsers();
        CompletableFuture<List<User>> allUsers2 = userService.findAllUsers();
        CompletableFuture<List<User>> allUsers3 = userService.findAllUsers();

        CompletableFuture.allOf(allUsers1,allUsers2,allUsers3).join();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
