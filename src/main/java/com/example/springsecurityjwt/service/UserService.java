package com.example.springsecurityjwt.service;

import com.example.springsecurityjwt.entity.User;
import com.example.springsecurityjwt.model.dto.UserDto;
import com.example.springsecurityjwt.model.request.ChangePassword;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    User saveOrUpdate(User user);
    List<UserDto> convertUserToDto(List<User> listUsers);
    List<UserDto> getAllUser(Integer pageNo,Integer pageSize,String sortBy);
    List<UserDto> getAllUserWithFilter(String keyword,Integer pageNo,Integer pageSize,String sortBy);
    void changePassword(ChangePassword req);
}
