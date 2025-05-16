FROM eclipse-temurin:17-jdk AS compile
WORKDIR /app
COPY . .

CMD ["java", "-jar", "app.jar"]

FROM eclipse-temurin:17-jdk AS prod
WORKDIR /app
COPY --from=compile /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]