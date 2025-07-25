package br.com.gservices.rickandmorty.data

import br.com.gservices.rickandmorty.data.models.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface RickAndMortyStorageInterface {
    suspend fun saveCharactersObject(characters: List<Character>)
    fun getCharactersObject(): Flow<List<Character>>
    fun getCharacterById(characterId: Long): Flow<Character?>
}

class RickAndMortyStorage: RickAndMortyStorageInterface {
    private val storage = MutableStateFlow(emptyList<Character>())

    override suspend fun saveCharactersObject(characters: List<Character>) {
        storage.value = characters
    }

    override fun getCharactersObject(): Flow<List<Character>> = storage

    override fun getCharacterById(characterId: Long): Flow<Character?> {
        return storage.map { characters ->
            characters.find { it.id == characterId }
        }
    }
}