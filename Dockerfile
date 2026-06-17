# 使用輕量級的 Java 17 環境
FROM eclipse-temurin:17-jdk-jammy

# 設定工作目錄
WORKDIR /app

# 將本地打包好的 JAR 檔複製進去
COPY target/*.jar app.jar

# 執行程式
ENTRYPOINT ["java", "-jar", "app.jar"]