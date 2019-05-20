package co.mewi.sample.valarmorghulis.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import co.mewi.sample.valarmorghulis.model.Character
import co.mewi.sample.valarmorghulis.model.ICharacterRepository
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.*

class CharacterListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    val characters: List<Character> = listOf(
        Character("John", "120", 21, 100, "http"),
        Character("Tim", "130", 56, 89, "http"),
        Character("Samantha", "100", 12, 44, "http")
    )

    @Test
    fun test_SuccessState() {
        // Arrange
        val mockSuccessCharacterRepo = object : ICharacterRepository {
            override fun getCharacterList(callback: ICharacterRepository.Callback?, useCache: Boolean) {
                callback?.onSuccess(characters)
            }
        }
        val viewModel = CharacterListViewModel(mockSuccessCharacterRepo)

        // Act
        viewModel.initPage()

        // Assert
        assertEquals(CharacterListViewModel.PageState.CONTENT, viewModel.pageState.value)
        assertEquals(characters, viewModel.characterList.value)
    }

    @Test
    fun test_ErrorState() {
        // Arrange
        val mockRepo = object : ICharacterRepository {
            override fun getCharacterList(callback: ICharacterRepository.Callback?, useCache: Boolean) {
                callback?.onFailure(null)
            }
        }
        val viewModel = CharacterListViewModel(mockRepo)

        // Act
        viewModel.initPage()

        // Assert
        assertEquals(CharacterListViewModel.PageState.ERROR, viewModel.pageState.value)
    }

    @Test
    fun test_LoadingState() {
        // Arrange
        val mockRepo = object : ICharacterRepository {
            override fun getCharacterList(callback: ICharacterRepository.Callback?, useCache: Boolean) {
                // do nothing for a while
            }
        }
        val viewModel = CharacterListViewModel(mockRepo)

        // Act
        viewModel.initPage()

        // Assert
        assertEquals(CharacterListViewModel.PageState.LOADING, viewModel.pageState.value)
    }

    @Test
    fun test_EmptyState() {
        // Arrange
        val mockRepo = object : ICharacterRepository {
            override fun getCharacterList(callback: ICharacterRepository.Callback?, useCache: Boolean) {
                callback?.onSuccess(Collections.emptyList())
            }
        }
        val viewModel = CharacterListViewModel(mockRepo)

        // Act
        viewModel.initPage()

        // Assert
        assertEquals(CharacterListViewModel.PageState.EMPTY, viewModel.pageState.value)
    }

    @Test
    fun test_sortByNameDesc() {
        // Arrange
        val mockRepo = object : ICharacterRepository {
            override fun getCharacterList(callback: ICharacterRepository.Callback?, useCache: Boolean) {
                callback?.onSuccess(characters)
            }
        }
        val viewModel = CharacterListViewModel(mockRepo)

        // Act
        viewModel.initPage()
        viewModel.updateSortSettings(CharacterListViewModel.SortBy.AGE, false)

        // Assert
        assertEquals(listOf(characters[1], characters[0], characters[2]), viewModel.characterList.value)
    }

    @Test
    fun test_sortByPopularityAsc() {
        // Arrange
        val mockRepo = object : ICharacterRepository {
            override fun getCharacterList(callback: ICharacterRepository.Callback?, useCache: Boolean) {
                callback?.onSuccess(characters)
            }
        }
        val viewModel = CharacterListViewModel(mockRepo)

        // Act
        viewModel.initPage()
        viewModel.updateSortSettings(CharacterListViewModel.SortBy.POPULARITY, true)

        // Assert
        assertEquals(listOf(characters[2], characters[1], characters[0]), viewModel.characterList.value)
    }

    @Test
    fun test_sortByHeightDesc() {
        // Arrange
        val mockRepo = object : ICharacterRepository {
            override fun getCharacterList(callback: ICharacterRepository.Callback?, useCache: Boolean) {
                callback?.onSuccess(characters)
            }
        }
        val viewModel = CharacterListViewModel(mockRepo)

        // Act
        viewModel.initPage()
        viewModel.updateSortSettings(CharacterListViewModel.SortBy.HEIGHT, true)

        // Assert
        assertEquals(listOf(characters[2], characters[0], characters[1]), viewModel.characterList.value)
    }

}