package com.example.demo.service;

import com.example.demo.model.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class JwtService {
    public String secretKey = "15ca8ae5b933d25719a22fa278fb6353af979f1698fda1c76af0104b129bcbc6";

    public String generateToken(User user)
    {
        //header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        Date issueTime = new Date();
        Date expiredTime = Date.from(issueTime.toInstant().plus(30, ChronoUnit.MINUTES));
//payload
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId().toString())
                .issueTime(issueTime)
                .expirationTime(expiredTime)
                .claim("roles", user.getRoles())
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());
      //
        JWSObject jwsObject = new JWSObject(header,payload);
        try {
            jwsObject.sign(new MACSigner(secretKey));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        return jwsObject.serialize();

    }
    public String getUserNameFromToken(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            return jwsObject.getPayload().toJSONObject().get("sub").toString();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
    }

    public List<String> getRolesFromToken(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            Object rolesObj = jwsObject.getPayload().toJSONObject().get("roles");

            List<String> roles = new ArrayList<>();
            if (rolesObj instanceof List<?> list) {
                for (Object r : list) {
                    roles.add("ROLE_" + r.toString()); // đảm bảo format đúng
                }
            }
            return roles;

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // Kiểm tra token hợp lệ
    public boolean validateToken(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            boolean verified = jwsObject.verify(new MACVerifier(secretKey));

            Date exp = new Date((Long) jwsObject.getPayload().toJSONObject().get("exp") * 1000L);
            return verified && new Date().before(exp);
        } catch (Exception e) {
            return false;
        }
    }

}
