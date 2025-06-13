package com.system.auth.service;

import com.system.auth.dto.AuthenticationRequest;
import com.system.auth.dto.AuthenticationResponse;
import com.system.auth.dto.RegisterRequest;
import com.system.auth.token.TokenService;
import com.system.auth.authuser.AuthUser;
import com.system.auth.authuser.AuthUserService;
import com.system.auth.authuser.Role;
import com.system.auth.jwt.JwtService;
import com.system.auth.domain.Token;
import com.system.auth.domain.TokenType;
import com.system.common.util.userinfo.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
@Service
@AllArgsConstructor
public class AuthenticationService {
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthUserService AuthUserService;

    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        UserInfo userInfo = new UserInfo();
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        var user = AuthUser.builder()
                .user_id(request.getUser_id())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .system_create_userid(userInfo.getUserEmail())
                .system_create_date(timestamp)
                .build();
        var jwtToken = jwtService.generateToken(user);
        AuthUserService.save(user);

        var token = Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.AUTHORIZATION)
                .authUser(user)
                .build();
        tokenService.save(token);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
        var user = AuthUserService.findByUserName(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
