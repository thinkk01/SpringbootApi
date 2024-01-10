package com.example.springsecurityjwt.jwt;

import com.example.springsecurityjwt.seccurity.CustomUserDetail;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${ra.jwt.secret}")
    private String JWT_SECRET;
    @Value("${ra.jwt.expiration}")
    private int JWT_EXPIRATION;
    // Tao ra jwt tu thong tin user
    public String generateToken(CustomUserDetail customUserDetail){
        Date now = new Date();
        Date dateExpiration = new Date(now.getTime()+ JWT_EXPIRATION);
        // Tao chuoi jwt tu username cua user
        return Jwts.builder()
                .setSubject(customUserDetail.getUsername())
                //ngay bat dau hieu luc
                .setIssuedAt(now)
                //ngay het han
                .setExpiration(dateExpiration)
                //ma hoa chuoi jwt duy nhat cho 1 user
                .signWith(SignatureAlgorithm.HS512,JWT_SECRET).compact();
    }
    //Lay thong tin user tu jwt
    public String getUserNameFromJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody();
        //tra lai thong tin username
        return claims.getSubject();
    }
    //validate thoong tin cua jwt
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
            return true;
        }catch(MalformedJwtException ex){
            log.error("Invalid JWT Token");
        }catch(ExpiredJwtException ex){
            log.error("Expired JWT Tken");
        }catch(UnsupportedJwtException ex){
            log.error("Unsupported JWT Token");
        }catch(IllegalArgumentException ex){
            log.error("JWT claims String is empty");
        }
        return false;

    }
}
