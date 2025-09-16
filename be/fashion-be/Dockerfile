# Sử dụng image Java base
FROM eclipse-temurin:21-jdk-jammy


# Tạo thư mục làm việc
WORKDIR /app

# Copy file JAR vào container
COPY build/libs/*.jar app.jar

# Mở port ứng dụng (thay 8080 bằng port của bạn nếu khác)
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]