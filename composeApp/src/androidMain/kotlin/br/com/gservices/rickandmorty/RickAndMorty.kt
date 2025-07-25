package br.com.gservices.rickandmorty

import android.app.Application
import br.com.gservices.rickandmorty.di.initKoin
import br.com.gservices.rickandmorty.viewmodels.CharacterDetailViewModel
import br.com.gservices.rickandmorty.viewmodels.CharactersViewModel
import org.koin.dsl.module

class RickAndMorty: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            listOf(
                module {
                    factory { CharactersViewModel(get()) }
                    factory { CharacterDetailViewModel(get()) }
                }
            )
        )
    }
}