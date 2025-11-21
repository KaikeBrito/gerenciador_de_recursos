## Etapa 1: Build (Compilação)
# Usa a imagem base do Maven com Eclipse Temurin JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Define o diretório de trabalho
WORKDIR /app

# 1. Copia apenas o arquivo de configuração do projeto (pom.xml)
# Esta etapa é crucial para o cache de dependências
COPY pom.xml .

# 2. Baixa todas as dependências.
# Se o pom.xml não mudar, esta camada do Docker será cacheada,
# tornando os builds subsequentes muito mais rápidos.
RUN mvn dependency:go-offline

# 3. Copia o código-fonte restante
COPY src ./src

# 4. Compila e empacota a aplicação, pulando os testes
RUN mvn clean package -DskipTests

## Etapa 2: Runtime (Execução)
# Usa uma imagem base mais leve e segura (Temurin 21 com Alpine)
FROM eclipse-temurin:21-jdk-alpine

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo .jar gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

# Define o usuário para ser 'non-root' por segurança (boa prática)
RUN adduser -D springuser
USER springuser

# Expõe a porta que a aplicação vai rodar
EXPOSE 8080

# Comando para iniciar a aplicação (o .jar)
ENTRYPOINT java -Dserver.port=${PORT:-8080} -jar app.jar