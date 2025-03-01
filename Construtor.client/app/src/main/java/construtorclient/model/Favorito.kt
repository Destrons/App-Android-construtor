package com.construtorclient.model

data class Favorito(
    val prestadorId: String = "",
    val nome: String = "",
    val email: String = "",
    val servicos: List<String> = listOf(),
    val fotoPerfil: String = ""
)