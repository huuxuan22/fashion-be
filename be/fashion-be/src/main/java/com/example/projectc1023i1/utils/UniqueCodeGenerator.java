package com.example.projectc1023i1.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
@Component
public class UniqueCodeGenerator {
    public static String generateUniqueCode() {
        // Tạo UUID
        String uuidPart = UUID.randomUUID().toString().replace("-", "").substring(0, 30); // lấy 8 ký tự
        // Tạo số random từ 1000 → 9999
        // Gộp lại thành mã duy nhất
        return uuidPart ;
    }
}
