package com.example.user_service.service;

import com.example.user_service.dto.request.AuthenticationRequest;
import com.example.user_service.dto.request.IntrospectRequest;
import com.example.user_service.dto.response.AuthenticationResponse;
import com.example.user_service.dto.response.IntrospectResponse;
import com.example.user_service.entity.User;
import com.example.user_service.exception.EnumConfig.ErrorCode;
import com.example.user_service.exception.payload.AppException;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;

    @NonFinal // not inject constructor
    @Value("${jwt.signerKey}")
    protected String signerKey;

    public IntrospectResponse introspect(IntrospectRequest request)
            throws ParseException, JOSEException {
        var token = request.getToken();


        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXITSTED));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        /**
         * Info user login
         * **/
        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(generateToken(user))
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }

    private String generateToken(User user) {
        // create header
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("hoangtien2k3.com")
                .issueTime(new Date())
                .expirationTime(
                        new Date(Instant.now().plus(30, ChronoUnit.DAYS).toEpochMilli())
                )
                .claim("scope", buildSope(user))
                .build();

        // create payload
        Payload payload =  new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Can't create token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildSope(User user) {
        StringJoiner joiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(joiner::add);
        }
        return joiner.toString();
    }

}
