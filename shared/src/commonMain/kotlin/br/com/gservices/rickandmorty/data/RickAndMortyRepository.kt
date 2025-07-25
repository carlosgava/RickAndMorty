package br.com.gservices.rickandmorty.data

import br.com.gservices.rickandmorty.data.models.Character
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RickAndMortyRepository(
    private val service: RickAndMortyInterface,
    private val storage: RickAndMortyStorageInterface
) {
    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    private suspend fun refresh() {
        storage.saveCharactersObject(service.getAllCharacters(page = 1))
    }

    fun getCharactersList(): Flow<List<Character>> = storage.getCharactersObject()
    fun getCharactersById(chracterId: Long): Flow<Character?> = storage.getCharacterById(chracterId)
}