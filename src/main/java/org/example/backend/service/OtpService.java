package org.example.backend.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.example.backend.dto.request.UserRegisterRequest;
import org.example.backend.persitence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Transactional
@RequiredArgsConstructor
public class OtpService {
    private StringRedisTemplate redisTemplate;
    private static final long OTP_TTL_MINUTES = 2;
    private static final String OTP_PREFIX = "OTP:FORGOT_PASS:";


    public String generateAndSaveOtp(String email)
    {
        String otp=Math.random()+"";
        String key=OTP_PREFIX+email;
        redisTemplate.opsForValue().set(key,otp, Duration.ofMinutes(OTP_TTL_MINUTES));
//        @Param(java.lang.String email);
        return otp;
    }

}
