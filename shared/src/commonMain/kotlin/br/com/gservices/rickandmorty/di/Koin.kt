package br.com.gservices.rickandmorty.di

import br.com.gservices.rickandmorty.data.RickAndMortyInterface
import br.com.gservices.rickandmorty.data.RickAndMortyRepository
import br.com.gservices.rickandmorty.data.RickAndMortyService
import br.com.gservices.rickandmorty.data.RickAndMortyStorage
import br.com.gservices.rickandmorty.data.RickAndMortyStorageInterface
import br.com.gservices.rickandmorty.data.httpClient
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModules = module {
    single { httpClient }
    single<RickAndMortyInterface> { RickAndMortyService() }
    single<RickAndMortyStorageInterface> { RickAndMortyStorage() }
    single {
        RickAndMortyRepository(get(), get()).apply {
            initialize()
        }
    }
}

fun initKoin() = initKoin(emptyList())

fun initKoin(extraModules: List<Module>) {
    startKoin {
        modules(
            dataModules,
            *extraModules.toTypedArray(),
        )
    }
}
