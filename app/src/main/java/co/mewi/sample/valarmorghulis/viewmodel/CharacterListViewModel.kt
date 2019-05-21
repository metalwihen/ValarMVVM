package co.mewi.sample.valarmorghulis.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import co.mewi.sample.valarmorghulis.model.Character
import co.mewi.sample.valarmorghulis.model.ICharacterRepository
import java.util.*
import kotlin.collections.ArrayList

class CharacterListViewModel(private val characterRepository: ICharacterRepository) : ViewModel() {

    val characterList = MutableLiveData<List<Character>>()
    val pageState = MutableLiveData<PageState>()

    private var selectedSortBy = SortBy.POPULARITY
    private var selectedIsAsc = true

    fun initPage() {
        loadPage()
    }

    fun reloadPage() {
        loadPage()
    }

    fun updateSortSettings(sortBy: SortBy, isAsc: Boolean) {
        if (characterList.value != null) {
            characterList.value = sort(characterList.value!!, sortBy, isAsc)
        }
    }

    private fun sort(originalList: List<Character>, sortBy: SortBy, isAsc: Boolean): List<Character> {
        selectedSortBy = sortBy
        selectedIsAsc = isAsc
        var list = ArrayList<Character>(originalList)
        Collections.sort(list, object : Comparator<Character> {
            override fun compare(o1: Character?, o2: Character?): Int {
                try {
                    when (sortBy) {
                        SortBy.POPULARITY ->
                            return if (o1!!.popularity == o2!!.popularity) {
                                0
                            } else if (o1!!.popularity < o2!!.popularity) {
                                if (isAsc) -1 else 1
                            } else {
                                if (isAsc) 1 else -1
                            }

                        SortBy.AGE ->
                            return if (o1!!.age == o2!!.age) {
                                0
                            } else if (o1!!.age < o2!!.age) {
                                if (isAsc) -1 else 1
                            } else {
                                if (isAsc) 1 else -1
                            }

                        SortBy.HEIGHT ->
                            return if (o1!!.height == o2!!.height) {
                                0
                            } else if (o1!!.height < o2!!.height) {
                                if (isAsc) -1 else 1
                            } else {
                                if (isAsc) 1 else -1
                            }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return 0
            }
        })
        return list
    }

    private fun loadPage() {
        pageState.value = PageState.LOADING
        characterRepository.getCharacterList(
            object : ICharacterRepository.Callback {
                override fun onSuccess(characters: List<Character>) {
                    if (characters.isEmpty()) {
                        pageState.value = PageState.EMPTY
                    } else {
                        pageState.value = PageState.CONTENT
                        characterList.value = sort(characters, selectedSortBy, selectedIsAsc)
                    }
                }

                override fun onFailure(errorMessage: String?) {
                    pageState.value = PageState.ERROR
                }
            })
    }

    enum class PageState {
        LOADING,
        EMPTY,
        ERROR,
        CONTENT
    }

    enum class SortBy {
        AGE,
        POPULARITY,
        HEIGHT
    }

}