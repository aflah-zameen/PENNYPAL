# Build stage

# base image to start the container with 
# this is the JDK 
FROM eclipse-temurin:21-jdk AS build

# to set the working directory in container 
# below scripts will execute in the working directory
WORKDIR /app


# It is used to copy maven wrapper script
# Helps to run without installing maven
# COPY syntax : COPY <source> <destination>
COPY mvnw .

#Copy the entire .mvn folder from your local project into the container’s working directory.
COPY .mvn .mvn

#Copy the pom file to get the info about added dependencies 
COPY pom.xml .

#Download all dependencies now so I can build later without internet.
RUN ./mvnw dependency:go-offline

# copy src folder
COPY src src

# using mvnw create the .jar file of the application 
RUN ./mvnw package -DskipTests

# Runtime stage (slim JRE for smaller image)
# For running the application u only need JRE. It is very small comparing to JDK
FROM eclipse-temurin:21-jre-alpine

# Set up the new working directory  
WORKDIR /app

# It copy the jar file from the directory of Build stage. 
COPY --from=build /app/target/*.jar app.jar

#It is just for marking it running in port 8080
# Springboot run in port 8080 defaultilly
EXPOSE 8080

# Defines the startup command for the container.
# It execute command java -jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
