package com.example.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.security.Key;

/**
 * Created by RyanZhu on 4/7/16.
 */
@Service
public class TokenService {

    private final String PAGE_TOKEN = "pageToken";
    private final String PAGE_TOKEN_START = "start";
    private final String PAGE_TOKEN_LENGTH = "length";

    private static final Key key = MacProvider.generateKey();

    public String genPageToken(Integer start, Integer length) {
        return Jwts.builder().setSubject(PAGE_TOKEN).claim(PAGE_TOKEN_START, start)
                .claim(PAGE_TOKEN_LENGTH, length).signWith(SignatureAlgorithm.HS512, key).compact();
    }

    @Data
    @AllArgsConstructor
    public static class PageTokenClass {
        private Integer start;
        private Integer length;
    }

    public PageTokenClass parsePageToken(String token) {
        try {
            Jws<Claims> jws = Jwts.parser().requireSubject(PAGE_TOKEN)
                    .setSigningKey(key).parseClaimsJws(token);
            Integer start = jws.getBody().get(PAGE_TOKEN_START, Integer.class);
            Integer length = jws.getBody().get(PAGE_TOKEN_LENGTH, Integer.class);
            return new PageTokenClass(start,length);
        } catch (InvalidClaimException ex) {
            System.err.println(ex.getMessage());
            throw ex;
        }
    }
}
