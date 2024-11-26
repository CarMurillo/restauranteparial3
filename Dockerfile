FROM openjdk:23
COPY "./target/RestauranteParcial3-1.jar" "app.jar"
EXPOSE 9090
ENTRYPOINT [ "java", "-jar", "app.jar" ]