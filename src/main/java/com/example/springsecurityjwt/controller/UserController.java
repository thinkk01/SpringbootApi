package com.example.springsecurityjwt.controller;

import com.example.springsecurityjwt.model.dto.UserDto;
import com.example.springsecurityjwt.model.request.ChangePassword;
import com.example.springsecurityjwt.payload.response.MessageResponse;
import com.example.springsecurityjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAllUser(@RequestParam(defaultValue = "0") int pageNo,
                                                    @RequestParam(defaultValue = "10") int pageSize,
                                                    @RequestParam(defaultValue = "id") String sortBy) {
        List<UserDto> dtos = userService.getAllUser(pageNo, pageSize, sortBy);
        return new ResponseEntity<List<UserDto>>(dtos, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> getAllUserWithFilter(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<UserDto> dtos = userService.getAllUserWithFilter(keyword,pageNo, pageSize, sortBy);
        return new ResponseEntity<List<UserDto>>(dtos, HttpStatus.OK);
    }
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword req){
        userService.changePassword(req);
        return new ResponseEntity<MessageResponse>(new MessageResponse("Sucesss"), HttpStatus.OK);
    }
}
