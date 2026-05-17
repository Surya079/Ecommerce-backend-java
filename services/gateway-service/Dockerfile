# Multi-stage build for smaller image size
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

#HEALTHCHECK --interval=30s --timeout=10s --start-period=90s --retries=3 \
#  CMD curl -f http://localhost:8222/actuator/health || exit 1

EXPOSE 8222

# Use exec form for better signal handling
ENTRYPOINT ["java", "-jar", "app.jar"]