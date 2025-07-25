package br.com.gservices.rickandmorty

import br.com.gservices.rickandmorty.data.RickAndMortyRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KoinDependencies: KoinComponent {
    val repository: RickAndMortyRepository by inject()
}