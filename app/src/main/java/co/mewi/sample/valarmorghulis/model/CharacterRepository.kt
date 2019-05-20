package co.mewi.sample.valarmorghulis.model

import co.mewi.sample.valarmorghulis.Injector
import co.mewi.sample.valarmorghulis.model.net.CharacterResponse
import co.mewi.sample.valarmorghulis.model.net.CharacterService
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class CharacterRepository {

    companion object {
        const val ERROR_DEFAULT = "Something has gone wrong"
        const val ERROR_NETWORK = "Unable to connect to server"
        const val ERROR_PARSE = "Unable to parse response"
        const val ERROR_INVALID_RESPONSE = "Invalid response from server"
    }

    private var cachedList: List<Character>? = null

    fun getCharacterList(callback: Callback?) {
        if (!cachedList.isNullOrEmpty()) {
            callback?.onSuccess(cachedList!!)
        }

        getNetworkService()
            .list()
            .enqueue(object : retrofit2.Callback<CharacterResponse> {
                override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                    if (t is IOException) {
                        callback?.onFailure(ERROR_NETWORK)
                    } else {
                        callback?.onFailure(ERROR_DEFAULT)
                    }
                }

                override fun onResponse(call: Call<CharacterResponse>, response: Response<CharacterResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        try {
                            val list = convertNetworkResponse(response.body() as CharacterResponse)
                            cachedList = list
                            callback?.onSuccess(list)
                        } catch (e: Exception) {
                            callback?.onFailure(ERROR_PARSE)
                        }
                    } else {
                        callback?.onFailure(ERROR_INVALID_RESPONSE)
                    }
                }
            })
    }

    private fun convertNetworkResponse(response: CharacterResponse): List<Character> {
        val list = ArrayList<Character>()
        val celebrityNames = response.celebrities.keys
        for (name in celebrityNames) {
            val celebrityInfo = response.celebrities[name]
            list.add(Character(name, celebrityInfo!!.age, celebrityInfo.popularity, celebrityInfo.photo))
        }
        return list
    }

    private fun getNetworkService(): CharacterService {
        val retrofit = Retrofit.Builder()
            .baseUrl(Injector.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(CharacterService::class.java)
    }

    data class Character(val name: String, val age: Int, val popularity: Int, val photo: String)

    interface Callback {
        fun onSuccess(characters: List<Character>)

        fun onFailure(errorMessage: String?)
    }
}