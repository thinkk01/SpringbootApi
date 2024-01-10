package com.example.springsecurityjwt.service.impl;

import com.example.springsecurityjwt.entity.User;
import com.example.springsecurityjwt.model.dto.UserDto;
import com.example.springsecurityjwt.model.request.ChangePassword;
import com.example.springsecurityjwt.repository.UserRepository;
import com.example.springsecurityjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User saveOrUpdate(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<UserDto> convertUserToDto(List<User> listUsers) {
        List<UserDto> dtos = listUsers.stream().map(user -> UserDto.builder()
                        .id(user.getUserId())
                        .email(user.getEmail())
                        .address(user.getAddress())
                        .phone(user.getPhone())
                        .build())
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    public List<UserDto> getAllUser(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<User> pageResult = userRepository.findAll(pageable);
        if (pageResult.hasContent()) {
            List<User> listUsers =pageResult.getContent();
            List<UserDto>dtos = convertUserToDto(listUsers);
            return dtos;
        }else{
            return new ArrayList<UserDto>();
        }
    }

    @Override
    public List<UserDto> getAllUserWithFilter(String keyword,Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo,pageSize,Sort.by(sortBy));
        Page<User> pageResult = userRepository.findAllWithFilter(keyword,pageable);
        if(pageResult.hasContent()){
            List<User> listUsers = pageResult.getContent();
            List<UserDto> dtos = convertUserToDto(listUsers);
            return dtos;
        }else{
            return new ArrayList<UserDto>();
        }
    }

    @Override
    public void changePassword(ChangePassword req) {
        User user = userRepository.findByEmail(req.getEmail());
        Boolean isPasswordCorrect = false;
        if(passwordEncoder.matches(req.getOldPassword(), user.getPassword())){
            isPasswordCorrect =true;
            String encodePassword = passwordEncoder.encode(req.getNewPassword());
            user.setPassword(encodePassword);
            userRepository.save(user);
        }
    }
}
