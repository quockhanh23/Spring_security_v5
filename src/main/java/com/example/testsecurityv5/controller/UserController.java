package com.example.testsecurityv5.controller;

import com.example.testsecurityv5.constants.Constants;
import com.example.testsecurityv5.jwt.JWTService;
import com.example.testsecurityv5.jwt.JwtResponse;
import com.example.testsecurityv5.models.Role;
import com.example.testsecurityv5.models.User;
import com.example.testsecurityv5.models.UserPrinciple;
import com.example.testsecurityv5.repositories.RoleRepository;
import com.example.testsecurityv5.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>("user", HttpStatus.OK);
    }

    @PutMapping("/editUser")
    public ResponseEntity<?> editUser() {
        return new ResponseEntity<>("user", HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser() {
        return new ResponseEntity<>("user", HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        Set<Role> roles = new HashSet<>();
        Role role = (user.getRoles() != null) ?
                roleRepository.findByName(Constants.Roles.ROLE_ADMIN) :
                roleRepository.findByName(Constants.Roles.ROLE_USER);
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            Authentication authentication;
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
            if (userOptional.isPresent()) {
                UserPrinciple userDetail = new UserPrinciple();
                userDetail.setId(userOptional.get().getId());
                userDetail.setUsername(userOptional.get().getUsername());
                userDetail.setPassword(userOptional.get().getPassword());
                String jwt = jwtService.generateToken(userDetail);
                return ResponseEntity.ok(new JwtResponse(jwt, userOptional.get().getId(),
                        userDetail.getUsername(), userDetail.getAuthorities()));
            } else {
                return new ResponseEntity<>("Not found user", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
