package br.com.gservices.rickandmorty.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Location (
    val name: String,
    val url: String
)