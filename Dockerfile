# Use uma imagem oficial do OpenJDK 17 como base
FROM eclipse-temurin:17-jre-alpine

# Diretório de trabalho dentro do container
WORKDIR /app

# Copie o JAR gerado para dentro do container
COPY target/coupon-1.0.jar app.jar

# Exponha a porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]