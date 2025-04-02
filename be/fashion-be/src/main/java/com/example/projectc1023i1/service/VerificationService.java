package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Exception.BlockEmailException;
import com.example.projectc1023i1.Exception.BlockEmailExceptionUnthorizi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class VerificationService {
    private static final int MAX_ATTEMPTS = 5; // Số lần gửi tối đa
    private static final long BLOCK_TIME = 10 * 60; // Thời gian khóa (10 phút)
    private static final long CODE_EXPIRATION = 10 * 60; // Mã hết hạn sau 10 phút
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationCode(String email) {
        // Kiểm tra xem tài khoản có bị chặn không
        if (redisTemplate.hasKey("blocked:" + email)) {
            throw new BlockEmailException("Tài khoản của bạn đã bị chặn do gửi quá nhiều yêu cầu.");
        }

        // Đếm số lần gửi mã
        String attemptKey = "attempts:" + email;
        Integer attempts = Optional.ofNullable(redisTemplate.opsForValue().get(attemptKey))
                .map(Integer::valueOf).orElse(0);

        if (attempts >= MAX_ATTEMPTS) {
            // Chặn tài khoản trong 10 phút
            redisTemplate.opsForValue().set("blocked:" + email, "true", BLOCK_TIME, TimeUnit.SECONDS);
            throw new BlockEmailExceptionUnthorizi("hiện tại email này đang bị chặn 10 phút bởi vì bạn đã gửi quá 5 lần xác nhận code");
        }

        // Tăng số lần gửi mã
        redisTemplate.opsForValue().set(attemptKey, String.valueOf(attempts + 1), CODE_EXPIRATION, TimeUnit.SECONDS);
        String code = generateRandomCode();
        redisTemplate.opsForValue().set("verification:" + email, code, CODE_EXPIRATION, TimeUnit.SECONDS);
        sendEmail(email, code);
    }

    public boolean verifyCode(String email, String code) {
        String storedCode = redisTemplate.opsForValue().get("verification:" + email);
        if (storedCode == null || !storedCode.equals(code)) {
            return false;
        }
        redisTemplate.delete("verification:" + email);
        redisTemplate.delete("attempts:" + email);
        redisTemplate.delete("blocked:" + email);
        return true;
    }

    private String generateRandomCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }

    private void sendEmail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("phamphuongtran2004@gmail.com"); // Thay bằng email thật
        message.setTo(to);
        message.setSubject("Mã xác thực của bạn");
        message.setText("Mã xác thực của bạn: "+ code);
        mailSender.send(message);
    }

}
