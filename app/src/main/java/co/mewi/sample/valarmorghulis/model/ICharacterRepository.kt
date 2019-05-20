package co.mewi.sample.valarmorghulis.model

interface ICharacterRepository {

    fun getCharacterList(callback: Callback?, useCache: Boolean = false)

    interface Callback {
        fun onSuccess(characters: List<Character>)

        fun onFailure(errorMessage: String?)
    }
}