package com.example.springsecurityjwt.controller;

import com.example.springsecurityjwt.entity.ERole;
import com.example.springsecurityjwt.entity.Roles;
import com.example.springsecurityjwt.entity.User;
import com.example.springsecurityjwt.jwt.JwtTokenProvider;
import com.example.springsecurityjwt.payload.request.SignInRequest;
import com.example.springsecurityjwt.payload.request.SignUpRequest;
import com.example.springsecurityjwt.payload.response.JwtResponse;
import com.example.springsecurityjwt.payload.response.MessageResponse;
import com.example.springsecurityjwt.repository.UserRepository;
import com.example.springsecurityjwt.seccurity.CustomUserDetail;
import com.example.springsecurityjwt.service.RoleService;
import com.example.springsecurityjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {
    @Autowired
    UserRepository userRepository;

    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error : Username is already"));
        }
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error : Email is already"));
        }
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPhone(signUpRequest.getPhone());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setUsername(signUpRequest.getUsername());
        user.setCreated(signUpRequest.getCreated());

        Set<String> role = signUpRequest.getListRoles();
        Set<Roles> listRole = new HashSet<>();
        if (role == null) {
            Roles userRole = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Not found role user"));
            listRole.add(userRole);
        } else {
            role.forEach(rl -> {
                switch (rl) {
                    case "admin":
                        Roles adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Not found role user"));
                        listRole.add(adminRole);
                        break;
                    case "mod":
                        Roles modRole = roleService.findByRoleName(ERole.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Note found role user"));
                        listRole.add(modRole);
                        break;
                    case "user":
                        Roles userRole = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error : Not found role user"));
                        listRole.add(userRole);
                        break;
                }
            });

        }
        userRepository.save(user);
        return ResponseEntity.ok().body(new MessageResponse("User Registered succesfully!"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody SignInRequest signInRequest) {
        // thuc hien xac thuc dang nhap  requestbody tu lop  signInRequest
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(), signInRequest.getPassword()
        ));
        //SecurityContextHolder 1 lop giu  thong  tin bao mat trong suot qua trinhthuc hien yeu cau
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //lay phien dang nhap
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        // tao ma token gan vao trong jwt response tra ve header
        String jwt = jwtTokenProvider.generateToken((CustomUserDetail) authentication);
        List<String> listRole = customUserDetail.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(new JwtResponse(jwt, customUserDetail.getUsername(), customUserDetail.getEmail(), customUserDetail.getPhone(), listRole));
    }
}
