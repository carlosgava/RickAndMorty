package br.com.gservices.rickandmorty.viewmodels

import br.com.gservices.rickandmorty.data.RickAndMortyRepository
import br.com.gservices.rickandmorty.data.models.Character
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class CharacterDetailViewModel(private val repository: RickAndMortyRepository): ViewModel() {
    private val charactedId = MutableStateFlow<Long?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val character: StateFlow<Character?> = charactedId
        .flatMapLatest {
            val id = it ?: return@flatMapLatest flowOf(null)
            repository.getCharactersById(id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun setId(characterId: Long) {
        this.charactedId.value = characterId
    }
}