package co.mewi.sample.valarmorghulis.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import co.mewi.sample.valarmorghulis.model.ICharacterRepository

class CharacterListViewModelFactory(private val repo: ICharacterRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CharacterListViewModel(repo) as T
    }

}
