# 第一階段：使用 Maven 編譯程式碼
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN ./mvnw clean package -DskipTests

# 第二階段：使用輕量級的 JRE 來執行
FROM eclipse-temurin:17-jre-jammy
COPY --from=build /target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]