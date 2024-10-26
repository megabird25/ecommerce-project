# 使用官方的 OpenJDK 作為基礎鏡像
FROM openjdk:17-jdk-alpine

# 設置應用程序運行目錄
WORKDIR /app

# 複製生成的 JAR 文件到容器中
COPY target/ecommerce-backend-0.0.1-SNAPSHOT.jar /app/app.jar

# 暴露應用程序運行的端口
EXPOSE 8080

# 執行應用程序
ENTRYPOINT ["java", "-jar", "app.jar"]