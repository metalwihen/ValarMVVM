package co.mewi.sample.valarmorghulis.model.net

import retrofit2.Call
import retrofit2.http.GET

interface CharacterService {

    @GET("/bins/6e60g")
    fun list(): Call<CharacterResponse>

}
