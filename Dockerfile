# Stage 1: Build the application with Maven and JDK 17
FROM maven:3.8.4-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Stage 2: Create the Docker container with OpenJDK 17
FROM openjdk:17-slim

# Install PostgreSQL
RUN apt-get update \
    && apt-get install -y postgresql postgresql-contrib \
    && service postgresql start

# Initialize the database and create a new user and database
RUN service postgresql start \
    && su - postgres -c "psql -c \"CREATE USER postgres WITH PASSWORD 'admin';\"" \
    && su - postgres -c "createdb -O postgres diabeticselfed"

COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


# Copy the built jar file
# COPY --from=build /home/app/target/*.jar app.jar


# Set environment variables
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/diabeticselfed
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=admin
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=create-drop
ENV SPRING_MAIL_HOST=smtp.gmail.com
ENV SPRING_MAIL_PORT=587
ENV SPRING_MAIL_USERNAME=Bearsefall23@gmail.com
ENV SPRING_MAIL_PASSWORD=etqridfojgrelwkx
ENV SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=true
ENV SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=true
ENV OPENAI_MODEL=gpt-3.5-turbo
ENV OPENAI_API_URL=https://api.openai.com
ENV OPENAI_API_KEY=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
ENV SPRING_JPA_SHOW_SQL=false
ENV SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=true
ENV SPRING_JPA_DATABASE=postgresql
ENV SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect
ENV APPLICATION_SECURITY_JWT_SECRET_KEY=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
ENV APPLICATION_SECURITY_JWT_EXPIRATION=86400000
ENV APPLICATION_SECURITY_JWT_REFRESH_TOKEN_EXPIRATION=604800000

# Define the command to run your app
ENTRYPOINT ["java","-jar","/app.jar"]
