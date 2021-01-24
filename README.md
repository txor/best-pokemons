# best-pokemons
Get the heaviest, highest and more experienced Pokemons!

## Statement
We are a gaming fanatics and we want to show in JSON via HTTP's request the following scenarios:

    The 5 heaviest Pokémons.
    The 5 highest Pokémons.
    The 5 Pokémons with more base experience.

To know this information, you must create a platform based on micro-services with the following prerequisites:

    Use Java/SpringBoot
    JUnit Tests
    Use PokéAPI: https://pokeapi.co/api/v2/
    We only want Pokémons of "red version". You can find this information on the section "game_indices" for each Pokémon:
        version_name = "red"
        version_url = "https://pokeapi.co/api/v2/version/1/"

## Docker usage
Build the project and the Docker image with
```
mvn clean install
```
Then run the container exposing port 8080
```
docker run -it -p 8080:8080 best-pokemons:0.0.1-SNAPSHOT
```
Now you can access to the three endpoints with your browser:
* http://localhost:8080/heaviest
* http://localhost:8080/tallest
* http://localhost:8080/most_experienced