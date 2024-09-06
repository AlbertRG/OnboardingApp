package com.altatec.myapplication.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val rickandmortyService = Retrofit.Builder()
    .baseUrl("https://rickandmortyapi.com/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val characterService: CharacterApi = rickandmortyService.create(CharacterApi::class.java)

interface CharacterApi {

    @GET("character")
    suspend fun getCharacter(@Query("page") page: Int): CharacterResponse

}