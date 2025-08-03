FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# ✅ Download and add Application Insights Java Agent
ADD https://github.com/microsoft/ApplicationInsights-Java/releases/latest/download/applicationinsights-agent.jar /app/applicationinsights-agent.jar

# ✅ Copy your Spring Boot JAR
COPY target/employee-0.0.1-SNAPSHOT.jar app.jar

# ✅ Use the agent and read connection string from env variable
ENTRYPOINT ["java", "-javaagent:/app/applicationinsights-agent.jar", "-jar", "app.jar"]FROM eclipse-temurin:17-jdk-alpine
