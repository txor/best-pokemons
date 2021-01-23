FROM openjdk:8-jre-alpine
WORKDIR /opt/best-pokemons
ARG JAR_FILE
COPY ${JAR_FILE} /opt/best-pokemons/best-pokemons.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","best-pokemons.jar"]