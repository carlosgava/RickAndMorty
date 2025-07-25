package br.com.gservices.rickandmorty.data

import br.com.gservices.rickandmorty.data.models.BaseModel
import br.com.gservices.rickandmorty.data.models.Character
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import io.ktor.utils.io.CancellationException

interface RickAndMortyInterface {
    suspend fun getAllCharacters(page: Int): List<Character>
    suspend fun geetCharacterById(characterId: Int): Character?
}

class RickAndMortyService: RickAndMortyInterface {
    override suspend fun getAllCharacters(page: Int): List<Character> {
        return try {
            val response: BaseModel<Character> = httpClient.get {
                url {
                    appendPathSegments(
                        segments = listOf(
                            "api",
                            "character"),
                        encodeSlash = true
                    )
                    parameters.append(
                        name = "page",
                        value = page.toString()
                    )
                }
            }.body()
            response.results
        } catch (ex: Exception) {
            if (ex is CancellationException) throw ex
            ex.printStackTrace()
            emptyList()
        }
    }

    override suspend fun geetCharacterById(characterId: Int): Character? {
        return try {
            httpClient.get {
                url {
                    appendPathSegments(
                        segments = listOf(
                            "api",
                            "character",
                            characterId.toString()),
                        encodeSlash = true
                    )
                }
            }.body()
        } catch (ex: Exception) {
            if(ex is CancellationException) throw ex
            ex.printStackTrace()
            null
        }
    }
}