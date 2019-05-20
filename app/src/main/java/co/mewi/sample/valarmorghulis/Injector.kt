package co.mewi.sample.valarmorghulis

import co.mewi.sample.valarmorghulis.model.CharacterRepository

class Injector {

    companion object {
        var baseUrl = "https://api.myjson.com/"
        val characterRepository = CharacterRepository()
    }
}