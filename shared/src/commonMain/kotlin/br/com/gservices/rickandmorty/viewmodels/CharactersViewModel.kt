package br.com.gservices.rickandmorty.viewmodels

import br.com.gservices.rickandmorty.data.RickAndMortyRepository
import br.com.gservices.rickandmorty.data.models.Character
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow

class CharactersViewModel(private val repository: RickAndMortyRepository) : ViewModel() {
    @NativeCoroutinesState
    val characters: StateFlow<List<Character>> =
        repository
        .getCharactersList()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}