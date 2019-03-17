package com.iad.courseProject.authentication;

import com.iad.courseProject.data.entities.User;
import com.iad.courseProject.data.entities.types.AccessLevel;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class TokenController {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String SIGNING_KEY = "jo1634pha,Lbc5z!sd2og;qp98z.<XC";
    private static final int TOKEN_LIVE_TIME_SECONDS = 6 * 3600;

    public String createToken(User user) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, TOKEN_LIVE_TIME_SECONDS);

        Date date = new Date();

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("Access", user.getAccessLevel());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(date.getTime() + TOKEN_LIVE_TIME_SECONDS * 1000L))
                .setSubject(Long.toString(user.getId()))
                .signWith(SignatureAlgorithm.HS512, SIGNING_KEY).compact();
    }

    public Authentication getAuthentication(String token) {
        Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token);
        User userDetails = new User();
        userDetails.setAccessLevel(getAccessLevel(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public void setTokenCookie(HttpServletResponse response, User user) {
        String token = createToken(user);
        Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token);
        cookie.setPath("/");
        cookie.setMaxAge(TOKEN_LIVE_TIME_SECONDS);
        response.addCookie(cookie);
    }
    public String resolveToken(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (authorization == null || authorization.isEmpty()) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (AUTHORIZATION_HEADER.equals(cookie.getName())) {
                        return cookie.getValue();
                    }
                }
            }
        }
        return null;
    }

    public boolean isTokenValid(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    public long getUserId(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token);
        return Long.parseLong(claims.getBody().getSubject());
    }
    public AccessLevel getAccessLevel(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token);
        String accessLevel = claims.getBody().get("Access", String.class);
        return AccessLevel.valueOf(accessLevel);
    }
}
