package co.mewi.sample.valarmorghulis.model.net

data class CharacterResponse(val celebrities: Map<String, CharacterInfo>)

data class CharacterInfo(val height: String, val age: Int, val popularity: Int, val photo: String)