package com.altatec.myapplication.data.api

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<Result>
)

data class Info(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("prev")
    val prev: String?
)

data class Result(
    @SerializedName("created")
    val created: String,
    @SerializedName("episode")
    val episode: List<String>,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin")
    val origin: Origin,
    @SerializedName("species")
    val species: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)

data class Location(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class Origin(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

object DummyCharacter {
    val character = Result(
        created = "2017-11-04T18:48:46.250Z",
        episode = listOf(
            "https://rickandmortyapi.com/api/episode/1",
            "https://rickandmortyapi.com/api/episode/2"
        ),
        gender = "Male",
        id = 1,
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        location = Location(
            "Citadel of Ricks",
            "https://rickandmortyapi.com/api/location/3"
        ),
        name = "Rick Sanchez",
        origin = Origin("Earth (C-137)", "https://rickandmortyapi.com/api/location/1"),
        species = "Human",
        status = "Alive",
        type = "",
        url = "https://rickandmortyapi.com/api/character/1"
    )
}

object DummyCharacterList{
    val characterList = listOf(
        Result(
            created = "2017-11-04T18:48:46.250Z",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/2"
            ),
            gender = "Male",
            id = 1,
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            location = Location(
                "Citadel of Ricks",
                "https://rickandmortyapi.com/api/location/3"
            ),
            name = "Rick Sanchez",
            origin = Origin("Earth (C-137)", "https://rickandmortyapi.com/api/location/1"),
            species = "Human",
            status = "Alive",
            type = "",
            url = "https://rickandmortyapi.com/api/character/1"
        ),
        Result(
            created = "2017-11-04T18:48:46.250Z",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/2"
            ),
            gender = "Male",
            id = 1,
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            location = Location(
                "Citadel of Ricks",
                "https://rickandmortyapi.com/api/location/3"
            ),
            name = "Rick Sanchez",
            origin = Origin("Earth (C-137)", "https://rickandmortyapi.com/api/location/1"),
            species = "Human",
            status = "Alive",
            type = "",
            url = "https://rickandmortyapi.com/api/character/1"
        ),
        Result(
            created = "2017-11-04T18:48:46.250Z",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/2"
            ),
            gender = "Male",
            id = 1,
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            location = Location(
                "Citadel of Ricks",
                "https://rickandmortyapi.com/api/location/3"
            ),
            name = "Rick Sanchez",
            origin = Origin("Earth (C-137)", "https://rickandmortyapi.com/api/location/1"),
            species = "Human",
            status = "Alive",
            type = "",
            url = "https://rickandmortyapi.com/api/character/1"
        )
    )
}