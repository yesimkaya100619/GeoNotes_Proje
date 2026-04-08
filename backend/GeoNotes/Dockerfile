# Build Aşaması
FROM public.ecr.aws/docker/library/maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Sadece pom.xml kopyalayıp bağımlılıkları indirmek cache avantajı sağlar
COPY pom.xml .

# go-offline yerine hatayı engelleyen daha stabil çözüm
RUN mvn dependency:resolve-plugins dependency:resolve -B

# Kaynak kodu kopyala ve paketle
COPY src ./src
RUN mvn clean package -DskipTests

# Çalıştırma Aşaması
FROM public.ecr.aws/docker/library/eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/geonotes-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]