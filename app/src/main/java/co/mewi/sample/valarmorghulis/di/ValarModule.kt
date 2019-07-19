package co.mewi.sample.valarmorghulis.di

import co.mewi.sample.valarmorghulis.model.CharacterRepository
import co.mewi.sample.valarmorghulis.model.ICharacterRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ValarModule {

    @Provides
    @Named("baseUrl")
    fun baseUrl(): String {
        return "https://api.myjson.com/"
    }

    @Provides
    fun characterRepository(@Named("baseUrl") baseUrl: String): ICharacterRepository {
        return CharacterRepository(baseUrl)
    }
}