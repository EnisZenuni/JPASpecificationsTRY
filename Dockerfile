# Use the official OpenJDK base image
FROM openjdk:21

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY build/libs/movieTask-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that your application runs on
EXPOSE 8080

# Command to run your application
CMD ["java", "-jar", "app.jar"]