package com.altatec.myapplication.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val rickandmortyService = Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
    .addConverterFactory(GsonConverterFactory.create()).build()

val characterService = rickandmortyService.create(CharacterApi::class.java)

interface CharacterApi {

    @GET("character")
    suspend fun getCharacter(): CharacterResponse

}