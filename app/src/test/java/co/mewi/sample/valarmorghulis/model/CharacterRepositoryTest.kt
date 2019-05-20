package co.mewi.sample.valarmorghulis.model

import co.mewi.sample.valarmorghulis.Injector
import co.mewi.sample.valarmorghulis.model.CharacterRepository.Companion.ERROR_INVALID_RESPONSE
import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch

class CharacterRepositoryTest {

    lateinit var server: MockWebServer
    val sampleResponses = SampleResponses()

    @Before
    fun setup() {
        server = MockWebServer()
    }

    @After
    fun teardown() {
        server.shutdown()
    }

    @Test
    fun testSimpleResponse() {
        // Arrange
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(sampleResponses.successThreeItemResponse)
        )
        server.start()
        Injector.baseUrl = server.url("/bins/6e60g/").toString()

        // Act
        val countDownLatch = CountDownLatch(1)
        var characterList: List<CharacterRepository.Character>? = null
        CharacterRepository().getCharacterList(object : CharacterRepository.Callback {
            override fun onSuccess(characters: List<CharacterRepository.Character>) {
                characterList = characters
                countDownLatch.countDown()
            }

            override fun onFailure(errorMessage: String?) {
                fail()
            }
        })
        countDownLatch.await()

        // Assert
        assertEquals(3, characterList!!.size)
        assertEquals(sampleResponses.firstItemObject, characterList!![0])
    }

    @Test
    fun testInvalidResponseErrorMessage() {
        // Arrange
        server.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody(sampleResponses.successThreeItemResponse)
        )
        server.start()
        Injector.baseUrl = server.url("/bins/6e60g/").toString()

        // Act
        val countDownLatch = CountDownLatch(1)
        var message: String? = null
        CharacterRepository().getCharacterList(object : CharacterRepository.Callback {
            override fun onSuccess(characters: List<CharacterRepository.Character>) {
                fail()
            }

            override fun onFailure(errorMessage: String?) {
                message = errorMessage
                countDownLatch.countDown()
            }
        })
        countDownLatch.await()

        // Assert
        assertEquals(ERROR_INVALID_RESPONSE, message)
    }

    @Test
    fun testCache() {
        // Arrange
        val characterRepository = CharacterRepository()
        val success1 = MockResponse()
            .setResponseCode(200)
            .setBody(sampleResponses.successThreeItemResponse)

        val fail2 = MockResponse()
            .setResponseCode(404)

        server.enqueue(success1)
        server.enqueue(fail2)
        server.start()
        Injector.baseUrl = server.url("/bins/6e60g/").toString()

        // Act
        // first request which caches the successful response
        var firstRequestSuccess: Boolean = false
        val countDownLatch = CountDownLatch(1)
        characterRepository.getCharacterList(object : CharacterRepository.Callback {
            override fun onSuccess(characters: List<CharacterRepository.Character>) {
                firstRequestSuccess = true
                countDownLatch.countDown()
            }

            override fun onFailure(errorMessage: String?) {
                firstRequestSuccess = false
                countDownLatch.countDown()
            }
        })
        countDownLatch.await()
        // second request that fails but uses the cache
        var secondRequestSuccess: Boolean = false
        var cachedList: List<CharacterRepository.Character>? = null
        val countDownLatch2 = CountDownLatch(2)
        characterRepository.getCharacterList(object : CharacterRepository.Callback {
            override fun onSuccess(characters: List<CharacterRepository.Character>) {
                cachedList = characters
                secondRequestSuccess = true
                countDownLatch2.countDown()
            }

            override fun onFailure(errorMessage: String?) {
                secondRequestSuccess = false
                countDownLatch2.countDown()
            }
        })
        countDownLatch2.await()


        // Assert
        assertEquals(true, firstRequestSuccess)
        assertEquals(false, secondRequestSuccess)
        assertEquals(sampleResponses.firstItemObject, cachedList!![0])

    }
}