package com.example.user_service.controller;

import com.example.user_service.constant.Constants;
import com.example.user_service.dto.request.AuthenticationRequest;
import com.example.user_service.dto.request.IntrospectRequest;
import com.example.user_service.dto.response.ApiResponse;
import com.example.user_service.dto.response.AuthenticationResponse;
import com.example.user_service.dto.response.IntrospectResponse;
import com.example.user_service.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

        AuthenticationService authenticationService;

        @PostMapping({ "/login", "signin" })
        ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
                var result = authenticationService.authenticate(request);
                return ApiResponse.<AuthenticationResponse>builder()
                                .code(HttpStatus.OK.value())
                                .message(result.isAuthenticated() ? Constants.SUCCESS : Constants.FAILED)
                                .data(result)
                                .build();
        }

        @PostMapping("/introspect")
        ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
                        throws ParseException, JOSEException {
                var result = authenticationService.introspect(request);
                return ApiResponse.<IntrospectResponse>builder()
                                .code(HttpStatus.OK.value())
                                .message(
                                                result.isValid() ? Constants.SUCCESS : Constants.FAILED)
                                .data(result)
                                .build();
        }

}
