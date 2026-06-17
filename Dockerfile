# 第一階段：編譯 (Build)
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
# 執行打包指令，並跳過測試
RUN ./mvnw clean package -DskipTests

# 第二階段：執行 (Run)
FROM eclipse-temurin:17-jre-jammy
# 從 build 階段把產出的 jar 檔複製過來
COPY --from=build /target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]