package co.mewi.sample.valarmorghulis.model.net

import retrofit2.Call
import retrofit2.http.GET

interface CharacterService {

    @GET("/bins/vgffa")
    fun list(): Call<CharacterResponse>

}
